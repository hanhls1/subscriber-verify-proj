package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.shared.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SubPartners")
@Where(clause = "IsDeleted = 0")
public class SubPartner extends BaseEntity {

	@Column(name = "Name", columnDefinition = "nvarchar(255)")
	private String name;

	@Column(name = "PartnerCode")
	private String partnerCode;

	@Column(name = "CustomerCode")
	private String customerCode;

	@Column(name = "PartnerId")
	private String partnerId;

	@Column(name = "RequestValidInDays")
	private Integer requestValidInDays;

	@Column(name = "Account")
	private String account;

	@Column(name = "SecureKey")
	private String secureKey;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getRequestValidInDays() {
		return requestValidInDays;
	}

	public void setRequestValidInDays(Integer requestValidInDays) {
		this.requestValidInDays = requestValidInDays;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}
}
