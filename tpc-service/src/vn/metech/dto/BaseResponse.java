package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.constant.DwhStatus;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class BaseResponse<T> implements Serializable {

    @JsonProperty("requestId")
    private String partnerRequestId;

    @JsonProperty("status")
    private DwhStatus status;

    @JsonProperty("msg")
    private String description;

    @JsonProperty("data")
    private T data;

    public String getPartnerRequestId() {
        return partnerRequestId;
    }

    public void setPartnerRequestId(String partnerRequestId) {
        this.partnerRequestId = partnerRequestId;
    }

    public DwhStatus getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        if (status instanceof String) {
            this.status = DwhStatus.valueOf((String) status);
        } else if (status instanceof Integer) {
            this.status = DwhStatus.valueOf((int) status);
        } else {
            this.status = DwhStatus.UNDEFINED;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
