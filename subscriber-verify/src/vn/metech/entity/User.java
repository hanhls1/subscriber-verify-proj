package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.shared.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Users")
@Where(clause = "IsDeleted = 0 AND IsLocked = 0 AND IsActivated = 1")
public class User extends BaseEntity {

	@Column(name = "Username")
	private String username;

	@Column(name = "Email")
	private String email;

	@Column(name = "Password")
	private String password;

	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@Column(name = "FirstName", columnDefinition = "nvarchar(50)")
	private String firstName;

	@Column(name = "LastName", columnDefinition = "nvarchar(50)")
	private String lastName;

	@Column(name = "LastLogin")
	private Date lastLogin;

	@Column(name = "LastChangePassword")
	private Date lastChangedPassword;

	@Column(name = "IsAgency")
	private Boolean agency;

	@Column(name = "IsAdmin", columnDefinition = "bit default 0")
	private Boolean admin;

	@Column(name = "IsUpdatable")
	private Boolean updatable;

	@Column(name = "IsActivated", columnDefinition = "bit")
	private Boolean activated;

	@Column(name = "IsLocked", columnDefinition = "bit")
	private Boolean locked;

	@Column(name = "DefaultCustomerCode", length = 36)
	private String defaultCustomerCode;

	@Column(name = "PartnerId", length = 36, insertable = false, updatable = false)
	private String partnerId;

	@JoinColumn(name = "PartnerId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Partner partner;

	@Column(name = "SubPartnerId", length = 36, insertable = false, updatable = false)
	private String subPartnerId;

	@JoinColumn(name = "SubPartnerId")
	@ManyToOne(fetch = FetchType.LAZY)
	private SubPartner subPartner;

	public User() {
		activated = false;
		locked = false;
		updatable = true;
		admin = false;
		agency = false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getLastChangedPassword() {
		return lastChangedPassword;
	}

	public void setLastChangedPassword(Date lastChangedPassword) {
		this.lastChangedPassword = lastChangedPassword;
	}

	public boolean isAgency() {
		return agency == null ? false : agency;
	}

	public void setAgency(boolean agency) {
		this.agency = agency;
	}

	public boolean isAdmin() {
		return admin == null ? false : admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean getUpdatable() {
		return updatable == null ? true : updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public boolean isActivated() {
		return activated == null ? false : activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getDefaultCustomerCode() {
		return defaultCustomerCode;
	}

	public void setDefaultCustomerCode(String defaultCustomerCode) {
		this.defaultCustomerCode = defaultCustomerCode;
	}

	public boolean isLocked() {
		return locked == null ? false : locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public String getSubPartnerId() {
		return subPartnerId;
	}

	public void setSubPartnerId(String subPartnerId) {
		this.subPartnerId = subPartnerId;
	}

	public SubPartner getSubPartner() {
		return subPartner;
	}

	public void setSubPartner(SubPartner subPartner) {
		this.subPartner = subPartner;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
