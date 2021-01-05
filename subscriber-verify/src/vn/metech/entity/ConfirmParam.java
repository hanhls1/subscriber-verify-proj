package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.common.Param;
import vn.metech.common.ServiceType;
import vn.metech.entity.base.EntityBase;

import javax.persistence.*;

@Entity
@Where(clause = "is_deleted = 0")
@Table(name = "confirm_param", indexes = {
				@Index(columnList = "service_type"), @Index(columnList = "param")
})
public class ConfirmParam extends EntityBase {

	@Enumerated(EnumType.STRING)
	@Column(name = "service_type", length = 100)
	private ServiceType serviceType;

	@Column(name = "service_code", length = 100)
	private String serviceCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "param")
	private Param param;

	@Column(name = "value", columnDefinition = "nvarchar(255)")
	private String value;

	@JoinColumn(name = "confirm_info_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private ConfirmInfo confirmInfo;

	public ConfirmParam() {
	}

	public ConfirmParam(Param param, String value) {
		this.param = param;
		this.value = value;
	}

	public ConfirmParam(Param param, String value, ConfirmInfo confirmInfo) {
		this.param = param;
		this.value = value;
		this.confirmInfo = confirmInfo;
		if (confirmInfo.getServiceType() != null) {
			this.serviceType = confirmInfo.getServiceType();
			this.serviceCode = serviceType.code();
		}
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ConfirmInfo getConfirmInfo() {
		return confirmInfo;
	}

	public void setConfirmInfo(ConfirmInfo confirmInfo) {
		this.confirmInfo = confirmInfo;
	}
}
