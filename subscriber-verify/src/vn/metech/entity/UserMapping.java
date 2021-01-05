package vn.metech.entity;

import vn.metech.shared.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "UserMapping")
//@Where(clause = "IsDeleted = 0")
public class UserMapping extends BaseEntity {

	@Column(name = "UserId", length = 36, updatable = false, insertable = false)
	private String userId;

	@Column(name = "Email")
	private String email;

	@Column(name = "RefUserId", length = 36, updatable = false, insertable = false)
	private String refUserId;

	@Column(name = "RefEmail")
	private String refEmail;

	@JoinColumn(name = "UserId")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@JoinColumn(name = "RefUserId")
	@ManyToOne(fetch = FetchType.LAZY)
	private User refUser;

	public UserMapping() {
	}

	public UserMapping(String userId, String email, String refUserId, String refEmail) {
		this.userId = userId;
		this.email = email;
		this.refUserId = refUserId;
		this.refEmail = refEmail;
	}

	public UserMapping(User user, User refUser) {
		this(user.getId(), user.getEmail(), refUser.getId(), refUser.getEmail());
		this.user = user;
		this.refUser = refUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRefUserId() {
		return refUserId;
	}

	public void setRefUserId(String refUserId) {
		this.refUserId = refUserId;
	}

	public String getRefEmail() {
		return refEmail;
	}

	public void setRefEmail(String refEmail) {
		this.refEmail = refEmail;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getRefUser() {
		return refUser;
	}

	public void setRefUser(User refUser) {
		this.refUser = refUser;
	}
}
