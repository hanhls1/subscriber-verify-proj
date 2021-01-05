package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.shared.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UserRoleMappings")
@Where(clause = "IsDeleted = 0")
public class UserRole extends BaseEntity {

	@Column(name = "UserId")
	private String userId;

	@Column(name = "Username")
	private String username;

	@Column(name = "RoleId")
	private String roleId;

	@Column(name = "RoleName", columnDefinition = "nvarchar(255)")
	private String roleName;

	@Column(name = "Uri")
	private String uri;

	public UserRole() {

	}

	public UserRole(User user, Role role) {
		this();
		userId = user.getId();
		username = user.getUsername();
		roleId = role.getId();
		roleName = role.getName();
		uri = role.getUri();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
