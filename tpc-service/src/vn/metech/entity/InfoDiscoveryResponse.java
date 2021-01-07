package vn.metech.entity;

import vn.metech.constant.DwhStatus;
import vn.metech.constant.RequestType;
import vn.metech.constant.ResponseStatus;
import vn.metech.shared.BaseEntity;
import vn.metech.util.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "InfoDiscoveryResponse")
public class InfoDiscoveryResponse extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "RequestType", length = 50)
	private RequestType requestType;

	@Column(name = "PhoneNumber", length = 20)
	private String phoneNumber;

	@Column(name = "IdNumber", length = 15)
	private String idNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponseStatus", length = 50)
	private ResponseStatus responseStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "ResponseCode", length = 50)
	private DwhStatus dwhStatus;

	@Column(name = "ResponseData", length = 3060)
	private String responseData;

	@MapKey(name = "id")
	@OneToMany(mappedBy = "infoDiscoveryResponse", fetch = LAZY)
	private Map<String, InfoDiscoveryRequest> infoDiscoveryRequests;

	public InfoDiscoveryResponse() {
		this.infoDiscoveryRequests = new HashMap<>();
		this.responseStatus = ResponseStatus.PENDING;
	}

	public InfoDiscoveryResponse(InfoDiscoveryRequest request, DwhStatus dwhStatus, String responseData) {
		this();
		this.dwhStatus = dwhStatus;
		this.responseData = responseData;
		this.idNumber = request.getIdNumber();
		this.createdBy = request.getCreatedBy();
		this.requestType = request.getRequestType();
		this.setPhoneNumber(request.getPhoneNumber());
		this.infoDiscoveryRequests.put(request.getId(), request);
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		if (!StringUtils.isEmpty(this.phoneNumber)) {
			this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
		}
	}

	public InfoDiscoveryResponse(InfoDiscoveryRequest request) {
		this(request, null, null);
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public DwhStatus getDwhStatus() {
		return dwhStatus;
	}

	public void setDwhStatus(DwhStatus dwhStatus) {
		this.dwhStatus = dwhStatus;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public Map<String, InfoDiscoveryRequest> getInfoDiscoveryRequests() {
		return infoDiscoveryRequests;
	}

	public void setInfoDiscoveryRequests(Map<String, InfoDiscoveryRequest> infoDiscoveryRequests) {
		this.infoDiscoveryRequests = infoDiscoveryRequests;
	}
}
