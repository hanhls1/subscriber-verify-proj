package vn.metech.builder;

import io.jsonwebtoken.lang.Assert;
import vn.metech.entity.User;
import vn.metech.shared.UserInfo;

import java.util.List;

public class UserInfoBuilder {

	private UserInfo _instance = new UserInfo();

	public UserInfoBuilder() {
	}

	public UserInfoBuilder user(User user) {
		return userId(user.getId())
						.username(user.getUsername())
						.partnerId(user.getPartner().getId())
						.partnerName(user.getPartner().getName())
						.accountCode(user.getPartner().getAccountCode())
						.defaultCustomerCode(user.getDefaultCustomerCode());
	}

	public UserInfoBuilder userId(String userId) {
		_instance.setUserId(userId);

		return this;
	}

	public UserInfoBuilder username(String username) {
		_instance.setUsername(username);

		return this;
	}

	public UserInfoBuilder partnerId(String partnerId) {
		_instance.setPartnerId(partnerId);

		return this;
	}

	public UserInfoBuilder partnerName(String partnerName) {
		_instance.setPartnerName(partnerName);

		return this;
	}

	public UserInfoBuilder accountCode(String accountCode) {
		_instance.setAccountCode(accountCode);

		return this;
	}

	public UserInfoBuilder accessPaths(List<String> accessPaths) {
		_instance.setAcceptPaths(accessPaths);

		return this;
	}

	public UserInfoBuilder addAccessPath(String accessPath) {
		_instance.getAcceptPaths().add(accessPath);

		return this;
	}

	public UserInfoBuilder defaultCustomerCode(String defaultCustomerCode) {
		_instance.setDefaultCustomerCode(defaultCustomerCode);

		return this;
	}

	public UserInfo build() {
		Assert.notNull(_instance.getUserId(), "[userId] is null");
		Assert.notNull(_instance.getUsername(), "[username] is null");

		return _instance;
	}
}
