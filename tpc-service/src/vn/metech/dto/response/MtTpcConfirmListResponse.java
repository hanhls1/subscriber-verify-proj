package vn.metech.dto.response;

import vn.metech.constant.TpcStatus;
import vn.metech.entity.TpcConfirm;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MtTpcConfirmListResponse {

    private String responseId;
    private String checkCallDate;
    private String checkImeiDate;
    private String phoneNumber;
    private String idNumber;
    private TpcStatus tpcStatus;
    private String createdDate;
    private String subscriberCallStatus;
    private String subscriberImeiStatus;

    public static MtTpcConfirmListResponse from(TpcConfirm tpcConfirm) {
        if (tpcConfirm == null) {
            return null;
        }

        MtTpcConfirmListResponse mtTpcConfirmListResponse = new MtTpcConfirmListResponse();
        mtTpcConfirmListResponse.responseId = tpcConfirm.getId();
        mtTpcConfirmListResponse.idNumber = tpcConfirm.getIdNumber();
        mtTpcConfirmListResponse.tpcStatus = tpcConfirm.getTpcStatus();
        mtTpcConfirmListResponse.phoneNumber = tpcConfirm.getPhoneNumber();
        mtTpcConfirmListResponse.checkCallDate = DateUtils.formatDate(tpcConfirm.getCheckedCallDate());
        mtTpcConfirmListResponse.checkImeiDate = DateUtils.formatDate(tpcConfirm.getCheckedImeiDate());
        mtTpcConfirmListResponse.subscriberCallStatus = tpcConfirm.getSubscriberCallStatus();
        mtTpcConfirmListResponse.subscriberImeiStatus = tpcConfirm.getSubscriberImeiStatus();
        mtTpcConfirmListResponse.createdDate = DateUtils.formatDate(tpcConfirm.getCreatedDate());

        return mtTpcConfirmListResponse;
    }

    public static List<MtTpcConfirmListResponse> fromCollections(Collection<TpcConfirm> tpcConfirms) {
        if (tpcConfirms == null || tpcConfirms.isEmpty()) {
            return Collections.emptyList();
        }

        return tpcConfirms.stream().map(MtTpcConfirmListResponse::from).collect(Collectors.toList());
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getCheckCallDate() {
        return checkCallDate;
    }

    public void setCheckCallDate(String checkCallDate) {
        this.checkCallDate = checkCallDate;
    }

    public String getCheckImeiDate() {
        return checkImeiDate;
    }

    public void setCheckImeiDate(String checkImeiDate) {
        this.checkImeiDate = checkImeiDate;
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public TpcStatus getTpcStatus() {
        return tpcStatus;
    }

    public void setTpcStatus(TpcStatus tpcStatus) {
        this.tpcStatus = tpcStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSubscriberCallStatus() {
        return StringUtils.isEmpty(subscriberCallStatus) ? "NO_STATUS" : subscriberCallStatus;
    }

    public void setSubscriberCallStatus(String subscriberCallStatus) {
        this.subscriberCallStatus = subscriberCallStatus;
    }

    public String getSubscriberImeiStatus() {
        return StringUtils.isEmpty(subscriberImeiStatus) ? "NO_STATUS" : subscriberImeiStatus;
    }

    public void setSubscriberImeiStatus(String subscriberImeiStatus) {
        this.subscriberImeiStatus = subscriberImeiStatus;
    }
}
