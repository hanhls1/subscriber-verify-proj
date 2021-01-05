package vn.metech.exception.aio;


import vn.metech.common.Command;
import vn.metech.common.ServiceType;
import vn.metech.common.StatusCode;
import vn.metech.dto.request.RequestBase;

public class RequestNotFoundException extends ApiException {

    public RequestNotFoundException(RequestBase requestBase, String requestId) {
        this(requestBase.getCommand(), requestBase.getServiceType(), requestId);
    }

    public RequestNotFoundException(Command command, ServiceType serviceType, String requestId) {
        super(command, serviceType, requestId, "Request không tồn tại hoặc không có kết quả");
    }

    @Override
    public StatusCode getStatusCode() {
        return StatusCode.REQUEST_NOT_FOUND;
    }

}
