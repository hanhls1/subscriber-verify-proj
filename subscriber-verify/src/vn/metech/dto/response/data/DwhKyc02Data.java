package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DwhKyc02Data implements Serializable {

	@JsonProperty("nationalId")
	private String idNumber;

	@JsonProperty("fullname")
	private String fullName;

	private String issuedDate;
	private String issuedPlace;
	private String dateOfBirth;
	private String gender;
	private String nationality;
	private String addressPRB;
	private String individualTaxCode;
	private String numberOfDependentsPeople;
	private String socialCode;
	private Double score1;
	private Double score2;
	private Double score3;
	private Double score1Top;
	private Double score2Top;
	private Double score3Top;
	private String companyName;
	private String companyAddress;
	private String establishedDate;
	private String businessField;
	private String companySize;
	private String companyOwner;
	private String companyOwnerAddr;
	private String companyTaxCode;
	private String startDateOfTax;
	private String businessType;
	private String operationStatus;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getNumberOfDependentsPeople() {
		return numberOfDependentsPeople;
	}

	public void setNumberOfDependentsPeople(String numberOfDependentsPeople) {
		this.numberOfDependentsPeople = numberOfDependentsPeople;
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

	public Double getScore1Top() {
		return score1Top;
	}

	public void setScore1Top(Double score1Top) {
		this.score1Top = score1Top;
	}

	public Double getScore2Top() {
		return score2Top;
	}

	public void setScore2Top(Double score2Top) {
		this.score2Top = score2Top;
	}

	public Double getScore3Top() {
		return score3Top;
	}

	public void setScore3Top(Double score3Top) {
		this.score3Top = score3Top;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
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

	public String getCompanyOwner() {
		return companyOwner;
	}

	public void setCompanyOwner(String companyOwner) {
		this.companyOwner = companyOwner;
	}

	public String getCompanyOwnerAddr() {
		return companyOwnerAddr;
	}

	public void setCompanyOwnerAddr(String companyOwnerAddr) {
		this.companyOwnerAddr = companyOwnerAddr;
	}

	public String getCompanyTaxCode() {
		return companyTaxCode;
	}

	public void setCompanyTaxCode(String companyTaxCode) {
		this.companyTaxCode = companyTaxCode;
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
