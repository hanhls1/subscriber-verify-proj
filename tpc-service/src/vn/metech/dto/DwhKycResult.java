package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DwhKycResult {

	@JsonProperty("nationalId")
	private String idNumber;

	@JsonProperty("name")
	private String customerName;

	@JsonProperty("issuedDate")
	private String issuedDate;

	@JsonProperty("issuedPlace")
	private String issuedPlace;

	@JsonProperty("dob")
	private String dateOfBirth;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("nationality")
	private String nationality;

	@JsonProperty("addressPRB")
	private String addressPRB;

	@JsonProperty("individualTaxCode")
	private String individualTaxCode;

	@JsonProperty("numOfDependentPeople")
	private String numOfDependentPeople;

	@JsonProperty("socialCode")
	private String socialCode;

	@JsonProperty("score1")
	private Double score1;

	@JsonProperty("score2")
	private Double score2;

	@JsonProperty("score3")
	private Double score3;

	@JsonProperty("companyName")
	private String companyName;

	@JsonProperty("taxCode")
	private String taxCode;

	@JsonProperty("address")
	private String address;

	@JsonProperty("establishedDate")
	private String establishedDate;

	@JsonProperty("businessField")
	private String businessField;

	@JsonProperty("companySize")
	private String companySize;

	@JsonProperty("companyDirector")
	private String companyDirector;

	@JsonProperty("startDateOfTax")
	private String startDateOfTax;

	@JsonProperty("businessType")
	private String businessType;

	@JsonProperty("operationStatus")
	private String operationStatus;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getIssuedPlace() {
		return issuedPlace;
	}

	public void setIssuedPlace(String issuedPlace) {
		this.issuedPlace = issuedPlace;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAddressPRB() {
		return addressPRB;
	}

	public void setAddressPRB(String addressPRB) {
		this.addressPRB = addressPRB;
	}

	public String getIndividualTaxCode() {
		return individualTaxCode;
	}

	public void setIndividualTaxCode(String individualTaxCode) {
		this.individualTaxCode = individualTaxCode;
	}

	public String getNumOfDependentPeople() {
		return numOfDependentPeople;
	}

	public void setNumOfDependentPeople(String numOfDependentPeople) {
		this.numOfDependentPeople = numOfDependentPeople;
	}

	public String getSocialCode() {
		return socialCode;
	}

	public void setSocialCode(String socialCode) {
		this.socialCode = socialCode;
	}

	public Double getScore1() {
		return score1;
	}

	public void setScore1(Double score1) {
		this.score1 = score1;
	}

	public Double getScore2() {
		return score2;
	}

	public void setScore2(Double score2) {
		this.score2 = score2;
	}

	public Double getScore3() {
		return score3;
	}

	public void setScore3(Double score3) {
		this.score3 = score3;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEstablishedDate() {
		return establishedDate;
	}

	public void setEstablishedDate(String establishedDate) {
		this.establishedDate = establishedDate;
	}

	public String getBusinessField() {
		return businessField;
	}

	public void setBusinessField(String businessField) {
		this.businessField = businessField;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getCompanyDirector() {
		return companyDirector;
	}

	public void setCompanyDirector(String companyDirector) {
		this.companyDirector = companyDirector;
	}

	public String getStartDateOfTax() {
		return startDateOfTax;
	}

	public void setStartDateOfTax(String startDateOfTax) {
		this.startDateOfTax = startDateOfTax;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
}
