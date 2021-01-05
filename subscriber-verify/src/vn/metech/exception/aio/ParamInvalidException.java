package vn.metech.exception.aio;


import vn.metech.common.Command;
import vn.metech.common.Param;
import vn.metech.common.ServiceType;
import vn.metech.common.StatusCode;
import vn.metech.dto.request.RequestBase;

public class ParamInvalidException extends ApiException {


    public ParamInvalidException(RequestBase requestBase, String requestId) {
        this(requestBase.getCommand(), requestBase.getServiceType(), requestId, "Params is invalid");
    }

    public ParamInvalidException(RequestBase requestBase, String requestId, Param param) {
        this(requestBase.getCommand(), requestBase.getServiceType(), requestId, "Param '" + param.name() + "' required");
    }

    public ParamInvalidException(Command command, ServiceType serviceType, String requestId, String msg) {
        super(command, serviceType, requestId, msg);
    }

    @Override
    public StatusCode getStatusCode() {
        return StatusCode.PARAM_INVALID;
    }

}
