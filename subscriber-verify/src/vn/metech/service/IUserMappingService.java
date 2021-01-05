package vn.metech.service;

import vn.metech.dto.request.UserMappingCreateRequest;
import vn.metech.dto.request.UserMappingUpdateRequest;
import vn.metech.dto.response.RefUserResponse;
import vn.metech.dto.response.UserMappingResponse;
import vn.metech.entity.UserMapping;
import vn.metech.exception.UserMappingDuplicateException;
import vn.metech.exception.UserMappingNotFoundException;
import vn.metech.exception.UserNotFoundException;
import vn.metech.jpa.service.IService;

import java.util.Collection;

public interface IUserMappingService extends IService<UserMapping> {

	Collection<UserMappingResponse> findUserWithMappings(String keyword, String userId, String remoteAddr);

	RefUserResponse createUserMapping(UserMappingCreateRequest userMappingCreateRequest, String userId)
					throws UserNotFoundException, UserMappingDuplicateException;

	RefUserResponse updateUserMapping(UserMappingUpdateRequest userMappingUpdateRequest, String userId)
					throws UserMappingNotFoundException, UserNotFoundException, UserMappingDuplicateException;

	String deleteUserMapping(String id, String userId) throws UserMappingNotFoundException;

}
