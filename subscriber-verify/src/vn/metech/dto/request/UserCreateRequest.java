package vn.metech.dto.request;

import org.springframework.security.crypto.password.PasswordEncoder;
import vn.metech.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserCreateRequest {

	@NotNull
	private Boolean agency;

	private Boolean admin;

	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	@Email
	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String password;

	@NotNull
	@NotEmpty
	private String phoneNumber;

	@NotNull
	private String verifyPassword;

	@NotNull
	@NotEmpty
	private String subPartnerId;

	public UserCreateRequest() {
		this.agency = false;
	}

	public User toUser(PasswordEncoder passwordEncoder) {
		User user = new User();
		user.setEmail(email);
		user.setAgency(agency);
		user.setUsername(email);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setPhoneNumber(phoneNumber);
		user.setPassword(passwordEncoder.encode(password));

		return user;
	}

	public Boolean getAgency() {
		return agency;
	}

	public void setAgency(Boolean agency) {
		this.agency = agency;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
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

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	public String getSubPartnerId() {
		return subPartnerId;
	}

	public void setSubPartnerId(String subPartnerId) {
		this.subPartnerId = subPartnerId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
