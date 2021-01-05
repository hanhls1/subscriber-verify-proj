package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.shared.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Roles")
@Where(clause = "IsDeleted = 0")
public class Role extends BaseEntity {

	@Column(name = "Name", columnDefinition = "nvarchar(255)")
	private String name;

	@Column(name = "Uri")
	private String uri;

	@Column(name = "RoleGroupId")
	private String roleGroupId;

	@Column(name = "RoleGroupName", columnDefinition = "nvarchar(255)")
	private String roleGroupName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRoleGroupId() {
		return roleGroupId;
	}

	public void setRoleGroupId(String roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	public String getRoleGroupName() {
		return roleGroupName;
	}

	public void setRoleGroupName(String roleGroupName) {
		this.roleGroupName = roleGroupName;
	}
}
