package vn.metech.kafka.mbf.message;

import org.springframework.util.Assert;
import vn.metech.kafka.mbf.MbfStatus;

public class ResponseMsgBuilder {

    private ResponseKafkaMsg _instance = new ResponseKafkaMsg();

    public ResponseMsgBuilder requestId(String requestId) {
        _instance.setRequestId(requestId);

        return this;
    }

    public ResponseMsgBuilder responseId(String responseId) {
        _instance.setResponseId(responseId);

        return this;
    }

    public ResponseMsgBuilder partnerResponseId(String partnerResponseId) {
        _instance.setPartnerResponseId(partnerResponseId);

        return this;
    }

    public ResponseMsgBuilder status(MbfStatus status) {
        _instance.setStatus(status);

        return this;
    }

    public ResponseMsgBuilder data(Object data) {
        _instance.setData(data);

        return this;
    }

    public ResponseKafkaMsg build() {
        Assert.notNull(_instance.getRequestId(), "[requestId] is null");
        Assert.notNull(_instance.getResponseId(), "[responseId] is null");
        Assert.notNull(_instance.getPartnerResponseId(), "[responseId] is null");
        Assert.notNull(_instance.getData(), "[data] is null");

        return _instance;
    }
}
