package vn.metech.dto.response;


import vn.metech.common.MessageStatus;
import vn.metech.common.TpcStatus;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.util.DateUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TpcConfirmInfoListResponse {

    private String responseId;
    private String checkCallDate;
    private String checkImeiDate;
    private String phoneNumber;
    private TpcStatus tpcStatus;
    private String createdDate;
    private String subscriberCallStatus;
    private String subscriberImeiStatus;

    public static TpcConfirmInfoListResponse from(TpcConfirmInfo tpcConfirm) {
        if (tpcConfirm == null) {
            return null;
        }

        TpcConfirmInfoListResponse tpcConfirmInfoListResponse = new TpcConfirmInfoListResponse();
        tpcConfirmInfoListResponse.responseId = tpcConfirm.getId();
        tpcConfirmInfoListResponse.tpcStatus = tpcConfirm.getTpcStatus();
        tpcConfirmInfoListResponse.phoneNumber = tpcConfirm.getPhoneNumber();
        tpcConfirmInfoListResponse.checkCallDate = DateUtils.formatDate(tpcConfirm.getCheckCallDate());
        tpcConfirmInfoListResponse.checkImeiDate = DateUtils.formatDate(tpcConfirm.getCheckImeiDate());
        tpcConfirmInfoListResponse.createdDate = DateUtils.formatDate(tpcConfirm.getCheckDateTime());

        if (tpcConfirm.getCallMessageStatus() != null) {
            tpcConfirmInfoListResponse.subscriberCallStatus = tpcConfirm.getCallMessageStatus().getDisplayName();
        } else {
            tpcConfirmInfoListResponse.subscriberCallStatus = MessageStatus.UNKNOWN.getDisplayName();
        }

        if (tpcConfirm.getImeiMessageStatus() != null) {
            tpcConfirmInfoListResponse.subscriberImeiStatus = tpcConfirm.getImeiMessageStatus().getDisplayName();
        } else {
            tpcConfirmInfoListResponse.subscriberImeiStatus = MessageStatus.UNKNOWN.getDisplayName();
        }


        return tpcConfirmInfoListResponse;
    }

    public static List<TpcConfirmInfoListResponse> fromCollections(Collection<TpcConfirmInfo> tpcConfirms) {
        if (tpcConfirms == null || tpcConfirms.isEmpty()) {
            return Collections.emptyList();
        }

        return tpcConfirms.stream().map(TpcConfirmInfoListResponse::from).collect(Collectors.toList());
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
    }

    public TpcStatus getTpcStatus() {
        return tpcStatus;
    }

    public void setTpcStatus(TpcStatus tpcStatus) {
        this.tpcStatus = tpcStatus;
    }

    public String getSubscriberCallStatus() {
        return subscriberCallStatus;
    }

    public void setSubscriberCallStatus(String subscriberCallStatus) {
        this.subscriberCallStatus = subscriberCallStatus;
    }

    public String getSubscriberImeiStatus() {
        return subscriberImeiStatus;
    }

    public void setSubscriberImeiStatus(String subscriberImeiStatus) {
        this.subscriberImeiStatus = subscriberImeiStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
