package vn.metech.exception.aio;


import vn.metech.common.Command;
import vn.metech.common.ServiceType;
import vn.metech.common.StatusCode;
import vn.metech.dto.request.RequestBase;

public class RequestInvalidException extends ApiException {

	public RequestInvalidException(RequestBase requestBase, String requestId) {
		this(requestBase.getCommand(), requestBase.getServiceType(), requestId);
	}

    public RequestInvalidException(RequestBase requestBase, String requestId, String desc) {
        super(requestBase.getCommand(), requestBase.getServiceType(), requestId, desc);
    }

	public RequestInvalidException(Command command, ServiceType serviceType, String requestId) {
		super(command, serviceType, requestId, "Request '" + requestId + "' refused");
	}

	@Override
	public StatusCode getStatusCode() {
		return StatusCode.REQUEST_REFUSED;
	}

}
