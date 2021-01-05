package vn.metech.dto.response.aio;


import vn.metech.common.MessageStatus;
import vn.metech.common.Param;
import vn.metech.entity.ConfirmInfo;

import java.util.*;

public class ConfirmInfoRequestResponse {

    private String id;
    private String requestId;
    private String phoneNumber;
    private String serviceCode;
    private Map<Param, String> requestData;
    private String recordStatus;
    private String messageStatus;
    private Date createdDate;

    public ConfirmInfoRequestResponse() {
        this.requestData = new HashMap<>();
    }

    public static ConfirmInfoRequestResponse of(ConfirmInfo confirmInfo) {
        ConfirmInfoRequestResponse confirmInfoRequestResponse = new ConfirmInfoRequestResponse();
        confirmInfoRequestResponse.requestId = confirmInfo.getRequestId();
        confirmInfoRequestResponse.id = confirmInfo.getId();
        confirmInfoRequestResponse.phoneNumber = confirmInfo.getPhoneNumber();
        confirmInfoRequestResponse.createdDate = confirmInfo.getCreatedDate();
        if (confirmInfo.getRecordStatus() != null) {
            confirmInfoRequestResponse.recordStatus = confirmInfo.getRecordStatus().getDisplayName();
        }
        if (confirmInfo.getMessageStatus() != null) {
            confirmInfoRequestResponse.messageStatus = confirmInfo.getMessageStatus().getDisplayName();
        }
        confirmInfoRequestResponse.serviceCode = confirmInfo.getServiceType().name();
        confirmInfo.getParams().forEach((param, confirmParam) ->
                confirmInfoRequestResponse.requestData.put(param, confirmParam.getValue())
        );

        return confirmInfoRequestResponse;
    }

    public static List<ConfirmInfoRequestResponse> of(Iterable<ConfirmInfo> confirmInfo) {
        List<ConfirmInfoRequestResponse> confirmInfoRequestResponses = new ArrayList<>();
        confirmInfo.forEach(c -> confirmInfoRequestResponses.add(ConfirmInfoRequestResponse.of(c)));

        return confirmInfoRequestResponses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Map<Param, String> getRequestData() {
        return requestData;
    }

    public void setRequestData(Map<Param, String> requestData) {
        this.requestData = requestData;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getMessageStatus() {
        return messageStatus != null ? messageStatus : MessageStatus.UNKNOWN.getDisplayName();
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
