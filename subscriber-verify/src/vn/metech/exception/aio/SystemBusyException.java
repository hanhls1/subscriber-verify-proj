package vn.metech.exception.aio;


import vn.metech.common.StatusCode;
import vn.metech.dto.request.RequestBase;

public class SystemBusyException extends ApiException {

    public SystemBusyException(RequestBase requestBase, String msg) {
        super(requestBase.getCommand(), requestBase.getServiceType(), requestBase.id(), msg);
    }

    @Override
    public StatusCode getStatusCode() {
        return StatusCode.REQUEST_FAILED;
    }
}
