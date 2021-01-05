package vn.metech.repository;

import vn.metech.dto.request.UserFilterPagedRequest;
import vn.metech.entity.User;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

public interface IUserRepository extends IRepository<User> {


	User getById(String userId);


	User getActiveAgentUserById(String userId);


	PagedResult<User> getUpdatableUsersBy(UserFilterPagedRequest userFilterPagedRequest, String agentId);

}
