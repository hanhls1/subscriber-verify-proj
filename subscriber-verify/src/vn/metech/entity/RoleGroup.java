package vn.metech.entity;

import vn.metech.shared.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RoleGroups")
public class RoleGroup extends BaseEntity {

	@Column(name = "Name", columnDefinition = "nvarchar(255)")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
