package vn.metech.exception.aio;


import vn.metech.common.Command;
import vn.metech.common.ServiceType;
import vn.metech.common.StatusCode;
import vn.metech.dto.request.RequestBase;

public class RequestDuplicateException extends ApiException {

	public RequestDuplicateException(RequestBase requestBase, String requestId) {
		this(requestBase.getCommand(), requestBase.getServiceType(), requestId);
	}

	public RequestDuplicateException(Command command, ServiceType serviceType, String requestId) {
		super(command, serviceType, requestId, "Request '" + requestId + "' duplicated");
	}

	@Override
	public StatusCode getStatusCode() {
		return StatusCode.REQUEST_REFUSED;
	}

}
