package vn.metech.shared;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements IAudit, ISoftDelete {

	@Id
	@Column(name = "Id", length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	protected String id;

	@Column(name = "IsDeleted", columnDefinition = "bit")
	protected boolean deleted;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate;

	@Column(name = "CreatedBy")
	protected String createdBy;

	@Column(name = "UpdatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updatedDate;

	@Column(name = "UpdatedBy")
	protected String updatedBy;

	@Version
	@Column(name = "Version")
	protected Integer version;

	protected BaseEntity() {
		createdDate = new Date();
		deleted = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Date getUpdatedDate() {
		return updatedDate;
	}

	@Override
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
