package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.dto.request.UserMappingCreateRequest;
import vn.metech.dto.request.UserMappingUpdateRequest;
import vn.metech.dto.response.RefUserResponse;
import vn.metech.dto.response.UserMappingResponse;
import vn.metech.entity.User;
import vn.metech.entity.UserMapping;
import vn.metech.exception.UserMappingDuplicateException;
import vn.metech.exception.UserMappingNotFoundException;
import vn.metech.exception.UserNotFoundException;
import vn.metech.jpa.service.ServiceImpl;
import vn.metech.repository.IUserMappingRepository;
import vn.metech.repository.IUserRepository;
import vn.metech.repository.jpa.UserMappingCrudRepository;
import vn.metech.service.IUserMappingService;

import java.util.*;

@Service
@Transactional
public class UserMappingServiceImpl extends ServiceImpl<UserMapping> implements IUserMappingService {

	private IUserRepository userRepository;
	private IUserMappingRepository userMappingRepository;
	private UserMappingCrudRepository userMappingCrudRepository;

	public UserMappingServiceImpl(
					IUserRepository userRepository,
					IUserMappingRepository userMappingRepository,
					UserMappingCrudRepository userMappingCrudRepository) {
		super(userMappingRepository);
		this.userRepository = userRepository;
		this.userMappingRepository = userMappingRepository;
		this.userMappingCrudRepository = userMappingCrudRepository;
	}


	@Override
	public Collection<UserMappingResponse> findUserWithMappings(String keyword, String userId, String remoteAddr) {
		User user = userRepository.getById(userId);
		if (user == null || !user.isAdmin() || user.isDeleted() || user.isLocked() || !user.isActivated()) {
			return Collections.emptyList();
		}
//		List<UserMapping> userMappings = userMappingRepository.findUserWithMappingsBy(keyword);
		List<UserMapping> userMappings = userMappingCrudRepository.findUserWithMappingsBy(keyword);
		Map<String, UserMappingResponse> userMappingResponses = new HashMap<>();
		for (UserMapping userMapping : userMappings) {
			UserMappingResponse userMappingResponse = userMappingResponses.get(userMapping.getUserId());
			if (userMappingResponse == null) {
				userMappingResponse = new UserMappingResponse(userMapping.getUserId(), userMapping.getEmail());
				userMappingResponses.put(userMapping.getUserId(), userMappingResponse);
			}
			userMappingResponse.addRefUser(userMapping);
		}

		return userMappingResponses.values();
	}

	@Override
	public RefUserResponse createUserMapping(UserMappingCreateRequest userMappingCreateRequest, String userId)
					throws UserNotFoundException, UserMappingDuplicateException {
		User requestUser = userRepository.getById(userId);
		if (requestUser == null || !requestUser.isAdmin() || requestUser.isLocked() || !requestUser.isActivated()) {
			return null;
		}
		if (userMappingCreateRequest.getUserId().equalsIgnoreCase(userMappingCreateRequest.getRefUserId())) {
			return null;
		}
		User user = userRepository.getById(userMappingCreateRequest.getUserId());
		if (user == null) throw new UserNotFoundException(userMappingCreateRequest.getUserId());
		User refUser = userRepository.getById(userMappingCreateRequest.getRefUserId());
		if (refUser == null) throw new UserNotFoundException(userMappingCreateRequest.getRefUserId());
		long userMappingCounter = userMappingCrudRepository
						.countIdByUserIdAndRefUserId(userMappingCreateRequest.getUserId(), userMappingCreateRequest.getRefUserId());
		if (userMappingCounter > 0) throw new UserMappingDuplicateException();

		return new RefUserResponse(userMappingRepository.create(new UserMapping(user, refUser)));
	}

	@Override
	public RefUserResponse updateUserMapping(
					UserMappingUpdateRequest userMappingUpdateRequest,
					String userId) throws UserMappingNotFoundException, UserNotFoundException, UserMappingDuplicateException {
		User requestUser = userRepository.getById(userId);
		if (requestUser == null || !requestUser.isAdmin() || requestUser.isLocked() || !requestUser.isActivated()) {
			return null;
		}
		UserMapping userMapping = userMappingRepository.getById(userMappingUpdateRequest.getMappingId());
		if (userMapping == null) throw new UserMappingNotFoundException(userMappingUpdateRequest.getMappingId());
		if (userMapping.getUserId().equalsIgnoreCase(userMappingUpdateRequest.getRefUserId())) {
			return null;
		}
		long userMappingCounter = userMappingCrudRepository
						.countIdByUserIdAndRefUserId(userMapping.getUserId(), userMappingUpdateRequest.getRefUserId());
		if (userMappingCounter > 0) throw new UserMappingDuplicateException();
		User refUser = userRepository.getById(userMappingUpdateRequest.getRefUserId());
		if (refUser == null) throw new UserNotFoundException(userMappingUpdateRequest.getRefUserId());
		userMapping.setRefUser(refUser);

		return new RefUserResponse(userMappingRepository.update(userMapping));
	}

	@Override
	public String deleteUserMapping(String id, String userId) throws UserMappingNotFoundException {
		User requestUser = userRepository.getById(userId);
		if (requestUser == null || !requestUser.isAdmin() || requestUser.isLocked() || !requestUser.isActivated()) {
			return null;
		}
		UserMapping userMapping = userMappingRepository.getById(id);
		if (userMapping == null) throw new UserMappingNotFoundException(id);
		userMappingRepository.delete(userMapping);

		return id;
	}
}
