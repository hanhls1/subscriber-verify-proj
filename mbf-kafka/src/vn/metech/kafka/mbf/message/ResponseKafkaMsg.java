package vn.metech.kafka.mbf.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.kafka.mbf.MbfStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class ResponseKafkaMsg {

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("responseId")
    private String responseId;

    @JsonProperty("partnerResponseId")
    private String partnerResponseId;

    @JsonProperty("status")
    private MbfStatus status;

    @JsonProperty("data")
    private Object data;

    public static ResponseMsgBuilder builder() {
        return new ResponseMsgBuilder();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getPartnerResponseId() {
        return partnerResponseId;
    }

    public void setPartnerResponseId(String partnerResponseId) {
        this.partnerResponseId = partnerResponseId;
    }

    public MbfStatus getStatus() {
        return status;
    }

    public void setStatus(MbfStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
