package vn.metech.entity.base;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public class EntityBase {

	@Id
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "is_deleted", columnDefinition = "bit default 0")
	private boolean deleted;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "created_by", length = 36)
	private String createdBy;

	public EntityBase() {
		this.deleted = false;
		this.createdDate = new Date();
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
