package vn.metech.exception.aio;


import vn.metech.common.Command;
import vn.metech.common.ServiceType;
import vn.metech.common.StatusCode;
import vn.metech.dto.request.RequestBase;

public class RequestReferenceInvalidException extends ApiException {

	public RequestReferenceInvalidException(RequestBase requestBase, String requestId) {
		this(requestBase.getCommand(), requestBase.getServiceType(), requestId);
	}

	public RequestReferenceInvalidException(Command command, ServiceType serviceType, String requestId) {
		super(command, serviceType, requestId, "Reference request '" + requestId + "' invalid");
	}

	@Override
	public StatusCode getStatusCode() {
		return StatusCode.REQUEST_REFUSED;
	}

}
