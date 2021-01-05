package vn.metech.dto.response;

import org.springframework.util.Assert;
import vn.metech.constant.RequestStatus;
import vn.metech.dto.MtLocation;
import vn.metech.entity.LocationRequest;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MtLocationRequestListResponse implements Serializable {

  private String requestId;
  private String phoneNumber;
  private MtLocation workAddress;
  private MtLocation homeAddress;
  private MtLocation referAddress;
  private RequestStatus requestStatus;
  private String dateToCheck;
  private String province;
  private String createdDate;

  public static MtLocationRequestListResponse from(LocationRequest locationRequest) {
    Assert.notNull(locationRequest, "[locationRequest] is null");

    MtLocationRequestListResponse locationResponse = new MtLocationRequestListResponse();
    locationResponse.phoneNumber = locationRequest.getPhoneNumber();
    locationResponse.workAddress = JsonUtils.toObject(locationRequest.getWorkAddress(), MtLocation.class);
    locationResponse.homeAddress = JsonUtils.toObject(locationRequest.getHomeAddress(), MtLocation.class);
    locationResponse.referAddress = JsonUtils.toObject(locationRequest.getReferAddress(), MtLocation.class);
    locationResponse.requestStatus = locationRequest.getRequestStatus();
    locationResponse.requestId = locationRequest.getId();
    locationResponse.dateToCheck = DateUtils.formatDate(locationRequest.getDateToCheck());
    locationResponse.province = locationRequest.getProvince();
    locationResponse.createdDate = DateUtils.formatDate(locationRequest.getCreatedDate());

    return locationResponse;
  }

  public static List<MtLocationRequestListResponse> fromList(List<LocationRequest> locationRequests) {
    List<MtLocationRequestListResponse> locationResponses = new ArrayList<>();
    for (LocationRequest locationRequest : locationRequests) {
      locationResponses.add(from(locationRequest));
    }

    return locationResponses;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
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

  public MtLocation getWorkAddress() {
    return workAddress;
  }

  public void setWorkAddress(MtLocation workAddress) {
    this.workAddress = workAddress;
  }

  public MtLocation getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(MtLocation homeAddress) {
    this.homeAddress = homeAddress;
  }

  public MtLocation getReferAddress() {
    return referAddress;
  }

  public void setReferAddress(MtLocation referAddress) {
    this.referAddress = referAddress;
  }

  public RequestStatus getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(RequestStatus requestStatus) {
    this.requestStatus = requestStatus;
  }

  public String getDateToCheck() {
    return dateToCheck;
  }

  public void setDateToCheck(String dateToCheck) {
    this.dateToCheck = dateToCheck;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }
}
