package vn.metech.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import vn.metech.constant.Auth;
import vn.metech.dto.request.*;
import vn.metech.dto.response.*;
import vn.metech.entity.*;
import vn.metech.exception.*;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.redis.util.RedisUtil;
import vn.metech.repository.*;
import vn.metech.repository.jpa.*;
import vn.metech.service.IUserService;
import vn.metech.shared.PagedResult;
import vn.metech.shared.UserInfo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<User> implements IUserService {

    private final String ServiceName = "IUserService";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private IPartnerRepository partnerRepository;
    private IUserRoleRepository userRoleRepository;
    private ISubPartnerRepository subPartnerRepository;
    private IUserMappingRepository userMappingRepository;
    private PasswordEncoder passwordEncoder;
    private RedisUtil<UserInfo> userInfoRedisUtil;
    private PartnerCrudRepository partnerCrudRepository;
    private SubPartnerCrudRepository subPartnerCrudRepository;
    private UserMappingCrudRepository userMappingCrudRepository;
    private UserCrudRepository userCrudRepository;
    private UserRoleCrudRepository userRoleCrudRepository;
    private RoleCrudRepository roleCrudRepository;

    private final String TPCPartnerCode;
    private final boolean initFlag;

    public UserServiceImpl(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            IPartnerRepository partnerRepository,
            IUserRoleRepository userRoleRepository,
            ISubPartnerRepository subPartnerRepository,
            IUserMappingRepository userMappingRepository,
            PasswordEncoder passwordEncoder,
            RedisUtil<UserInfo> userInfoRedisUtil,
            PartnerCrudRepository partnerCrudRepository,
            SubPartnerCrudRepository subPartnerCrudRepository,
            UserMappingCrudRepository userMappingCrudRepository,
            UserCrudRepository userCrudRepository,
            UserRoleCrudRepository userRoleCrudRepository,
            RoleCrudRepository roleCrudRepository,
            @Value("${auth.init-mode:false}") boolean initFlag,
            @Value("${tpc.partner-code:TPC}") String TPCPartnerCode) {
        super(userRepository);
        this.initFlag = initFlag;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.partnerRepository = partnerRepository;
        this.userRoleRepository = userRoleRepository;
        this.subPartnerRepository = subPartnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInfoRedisUtil = userInfoRedisUtil;
        this.partnerCrudRepository = partnerCrudRepository;
        this.subPartnerCrudRepository = subPartnerCrudRepository;
        this.TPCPartnerCode = TPCPartnerCode;
        this.userMappingRepository = userMappingRepository;
        this.userMappingCrudRepository = userMappingCrudRepository;
        this.userCrudRepository = userCrudRepository;
        this.userRoleCrudRepository = userRoleCrudRepository;
        this.roleCrudRepository = roleCrudRepository;
    }

    @Override
    public User create(User user) {
        logger.info(ServiceName + " > create() > msg: ");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.create(user);
    }

    @Override
    public List<User> batchCreate(Collection<User> users) {
        for (User user : users) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return super.batchCreate(users);
    }

    @Override
    public User getByUserId(String userId) throws UserNotFoundException {
        Assert.notNull(userId, "userId required");

        User user = userRepository.getById(userId);
        if (user != null) {
            return user;
        }

        throw new UserNotFoundException("Cannot find user: " + userId);
    }

    @Override
    public User changePassword(UserChangePasswordRequest request, String userId, String ipAddress)
            throws UserPasswordNotMatchException {
        UserInfo userInfo = userInfoRedisUtil.getValue(Auth.Jwt.CACHE_USER_KEY + userId);
        User user = userRepository.getById(userInfo.getUserId());

        if (!request.getNewPassword().equals(request.getVerifyNewPassword())
                || !passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new UserPasswordNotMatchException("password wrong or not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedBy(userInfo.getUserId());
        user.setLastChangedPassword(new Date());

        return userRepository.update(user);
    }

    @Override
    public List<User> getActivatedUserWithPartner() {
        return userCrudRepository.getActivatedUserWithPartner();
    }

    @Override
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest, String userId)
            throws UserExitingException, UserPasswordNotMatchException, PartnerNotFoundException {
        long emailCounter = userCrudRepository.countByEmail(userCreateRequest.getEmail());
//        long emailCounter = userCrudRepository.countUserByEmail(userCreateRequest.getEmail());
        if (emailCounter > 0) {
            throw new UserExitingException(userCreateRequest.getEmail());
        }
        if (!userCreateRequest.getPassword().equalsIgnoreCase(userCreateRequest.getVerifyPassword())) {
            throw new UserPasswordNotMatchException();
        }
        SubPartner subPartner = subPartnerRepository.getById(userCreateRequest.getSubPartnerId());
        if (subPartner == null) {
            throw new PartnerNotFoundException();
        }
        Partner partner = partnerRepository.getById(subPartner.getPartnerId());
        if (partner == null) {
            throw new PartnerNotFoundException();
        }
        User user = userCreateRequest.toUser(passwordEncoder);
        user.setPartner(partner);
        user.setSubPartner(subPartner);
        user.setCreatedBy(userId);
        user.setActivated(true);
        user.setDefaultCustomerCode(subPartner.getCustomerCode());
        userRepository.create(user);

        return new UserCreateResponse(user);
    }

    @Override
    public List<String> getAgentUserIds(String userId) {
        User user = userRepository.getActiveAgentUserById(userId);
        if (user == null || StringUtils.isEmpty(user.getPartnerId()))
            return Collections.emptyList();

        if (!user.isAgency()) {
            return Collections.singletonList(userId);
        }

        List<User> users = userCrudRepository.findAllByPartnerId(user.getPartnerId());
//        List<User> users = userCrudRepository.getUsersByPartnerId(user.getPartnerId());

        return users
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    @Override
    public UserRoleResponse updateRole(String updateFor, List<String> roleIds, String userId) throws UserInvalidException {
        User user = userRepository.getById(updateFor);
        if (user == null) throw new UserInvalidException(updateFor);
        if (!initFlag) {
            if (userId.equalsIgnoreCase(updateFor) || updateFor.equalsIgnoreCase("admin"))
                throw new UserInvalidException(updateFor);
        }

        userRoleCrudRepository.removeByUserId(updateFor);
        List<Role> roles = roleCrudRepository.findAllByIdIn(roleIds);
        UserRoleResponse userRoleResponse = new UserRoleResponse();
        userRoleResponse.setUserId(user.getId());
        userRoleResponse.setUsername(user.getUsername());
        List<UserRole> userRoles = new ArrayList<>();
        for (Role role : roles) {
            userRoles.add(new UserRole(user, role));
            userRoleResponse.getApiPaths().add(role.getUri());
        }
        userRoleRepository.batchCreate(userRoles);

        return userRoleResponse;
    }

    @Override
    public PagedResult<UserListResponse> getUpdatableUsers(
            UserFilterPagedRequest userFilterPagedRequest, String userId) {
        PagedResult<User> userPagedResult = userRepository
                .getUpdatableUsersBy(userFilterPagedRequest, userFilterPagedRequest.getAgentId());

        return new PagedResult<>(
                userPagedResult.getTotalRecords(),
                UserListResponse.fromCollection(userPagedResult.getData())
        );
    }

    @Override
    public List<String> getAccessPaths(UserInfoRequest userInfoRequest) {
        return userRoleCrudRepository.getAccessPaths(userInfoRequest.getUserId());
    }

    @Override
    public List<String> getTpcUserIds() throws PartnerNotFoundException {
        return getUserIdsBy(TPCPartnerCode);
    }

    @Override
    public List<String> getUserIdsBy(String partnerCode) throws PartnerNotFoundException {
        Partner partner = partnerCrudRepository.findByPartnerCode(partnerCode);
        if (partner == null) {
            throw new PartnerNotFoundException();
        }
        List<User> users = userCrudRepository.findAllByPartnerId(partner.getId());
//        List<User> users = userCrudRepository.getUsersByPartnerId(partner.getId());
        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream().map(User::getId).collect(Collectors.toList());
    }

    @Override
    public UserResponseAuth getUserInformation(String userId) {
        User user = userCrudRepository.findById(userId).get();
        if (user == null) {
            return null;
        }

        SubPartner subPartner = subPartnerCrudRepository.findById(user.getSubPartnerId()).get();
        if (user.isDeleted() || user.isLocked() || !user.isActivated()) {
            UserResponseAuth userResponseAuth = new UserResponseAuth();
            userResponseAuth.setUserId(userId);

            if (subPartner != null) {
                userResponseAuth.setRequestValidInDays(subPartner.getRequestValidInDays());
            }

            return userResponseAuth;
        }

        UserResponseAuth userResponseAuth = new UserResponseAuth(user);
        if (subPartner != null) {
            userResponseAuth.setRequestValidInDays(subPartner.getRequestValidInDays());
            userResponseAuth.setAccount(subPartner.getAccount());
            userResponseAuth.setSecureKey(subPartner.getSecureKey());
            userResponseAuth.setSubPartnerName(subPartner.getName());

        }

        return userResponseAuth;
    }

    @Override
    public UserDetailResponse getUserDetails(String userDetailId, String userId) throws UserNotFoundException {
        User user = userRepository.getById(userDetailId);
        if (user == null) {
            throw new UserNotFoundException(userDetailId);
        }

        return new UserDetailResponse(user);
    }

    @Override
    public PagedResult<UserListResponse> getListUsersBy(UserFilterPagedRequest userFilterPagedRequest, String userId) {
        PagedResult<User> pagedUsers = userRepository.getUpdatableUsersBy(userFilterPagedRequest, userId);

        return new PagedResult<>(pagedUsers.getTotalRecords(), UserListResponse.fromCollection(pagedUsers.getData()));
    }

    @Override
    public UserListResponse updateUser(
            UserUpdateRequest userRequest, String userId) throws UserNotFoundException, PartnerNotFoundException {
        User requestUser = userRepository.getById(userId);
        if (requestUser == null || requestUser.isLocked() || !requestUser.isActivated()) {
            throw new UserNotFoundException("user '" + userId + "' not found");
        }
        User user = userRepository.getById(userRequest.getUserId());
        if (user == null || user.isDeleted()) {
            throw new UserNotFoundException("user '" + userId + "' not found");
        }
        SubPartner subPartner = subPartnerRepository.getById(userRequest.getSubPartnerId());
        if (subPartner == null) {
            throw new PartnerNotFoundException();
        }
        Partner partner = partnerRepository.getById(subPartner.getPartnerId());
        if (partner == null) {
            throw new PartnerNotFoundException();
        }
        if (requestUser.isAdmin()) {
            if (userRequest.getAdmin() != null) {
                user.setAdmin(userRequest.getAdmin());
                user.setUpdatable(!user.isAdmin());
            }
            user.setPartner(partner);
            user.setSubPartner(subPartner);
            user.setDefaultCustomerCode(subPartner.getCustomerCode());
        }
        user = userRequest.toUser(user);

        return UserListResponse.from(userRepository.update(user));
    }

    @Override
    public List<String> getRefUserIds(String userId) {
        User user = userRepository.getById(userId);
        if (user == null || user.isLocked() || user.isDeleted() || !user.isActivated()) {
            return Collections.emptyList();
        }
//        List<UserMapping> userMappings = userMappingRepository.getRefUsersBy(userId);
        List<UserMapping> userMappings = userMappingCrudRepository.findAllByUserId(userId);
        if (userMappings == null || userMappings.isEmpty()) {
            return Collections.singletonList(userId);
        }
        List<String> refUserIds = userMappings.stream().map(UserMapping::getRefUserId).collect(Collectors.toList());
        refUserIds.add(userId);

        return refUserIds;
    }

    @Override
    public List<String> getRecordsUserIds(String userId) {
//        User user = userRepository.getUserIncludePartnersById(userId);
        User user = userCrudRepository.getUserIncludePartnersById(userId);
        if (user == null || !user.isActivated() || user.isLocked()) return Collections.emptyList();

        try {
            if (user.isAgency()
                    && user.getSubPartner() != null
                    && !StringUtils.isEmpty(user.getSubPartner().getPartnerCode())
                    && user.getSubPartner().getPartnerCode().equals("VPB")) {
                return getUserIdsBy("VPB");
            }
        } catch (PartnerNotFoundException ignored) {
        }

        if (!user.isAdmin()) {
            return Collections.singletonList(userId);
        }

//        return userCrudRepository.findAllId();
        return userCrudRepository.getAllUserIds();
    }

    @Override
    public List<EmailResponse> getUserEmail() {

        return userCrudRepository.getAllUserMailAdmins();
    }

    @Override
    public List<PhoneResponse> getUserPhone() {

        return userCrudRepository.getAllUserPhoneAdmins();
    }
}
