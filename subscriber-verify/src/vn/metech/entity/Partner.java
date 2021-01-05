package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.shared.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Partners")
@Where(clause = "IsDeleted = 0")
public class Partner extends BaseEntity {

	@Column(name = "Name", columnDefinition = "nvarchar(255)")
	private String name;

	@Column(name = "PartnerCode")
	private String partnerCode;

	@Column(name = "AccountCode")
	private String accountCode;

	@Column(name = "RedirectUrl")
	private String redirectUrl;

	@Column(name = "BasicUsername")
	private String username;

	@Column(name = "BasicPassword")
	private String password;

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

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
