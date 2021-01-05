package vn.metech.service;

import vn.metech.dto.request.*;
import vn.metech.dto.response.*;
import vn.metech.entity.User;
import vn.metech.exception.*;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

import java.util.List;

public interface IUserService extends IService<User> {

	User getByUserId(String userId) throws UserNotFoundException;

	User changePassword(UserChangePasswordRequest request, String userId, String ipAddress)
					throws UserPasswordNotMatchException;

	List<User> getActivatedUserWithPartner();

	UserCreateResponse createUser(UserCreateRequest userCreateRequest, String userId)
					throws UserExitingException, UserPasswordNotMatchException, PartnerNotFoundException;

	List<String> getAgentUserIds(String userId);

	UserRoleResponse updateRole(String updateFor, List<String> roleIds, String userId) throws UserInvalidException;

	PagedResult<UserListResponse> getUpdatableUsers(UserFilterPagedRequest userFilterPagedRequest, String userId);

	List<String> getAccessPaths(UserInfoRequest userInfoRequest);

	List<String> getTpcUserIds() throws PartnerNotFoundException;

	List<String> getUserIdsBy(String partnerCode) throws PartnerNotFoundException;

	UserResponseAuth getUserInformation(String userId);

	UserDetailResponse getUserDetails(String userDetailId, String userId) throws UserNotFoundException;

	PagedResult<UserListResponse> getListUsersBy(UserFilterPagedRequest userFilterPagedRequest, String userId);

	UserListResponse updateUser(
					UserUpdateRequest userRequest, String userId) throws UserNotFoundException, PartnerNotFoundException;

	List<String> getRefUserIds(String userId);

	List<String> getRecordsUserIds(String userId);

	List<EmailResponse> getUserEmail ();

	List<PhoneResponse> getUserPhone ();
}
