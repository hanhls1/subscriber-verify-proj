package vn.metech.exception.aio;

import vn.metech.common.Command;
import vn.metech.common.ServiceType;
import vn.metech.common.StatusCode;
import vn.metech.dto.request.RequestBase;

public class BalanceNotEnoughException extends ApiException {

	public BalanceNotEnoughException(RequestBase request, String requestId) {
		this(request.getCommand(), request.getServiceType(),
						requestId, "Balance not enough for request '" + requestId + "'");
	}

	public BalanceNotEnoughException(Command command, ServiceType serviceType, String requestId, String msg) {
		super(command, serviceType, requestId, msg);
	}

	@Override
	public StatusCode getStatusCode() {
		return StatusCode.REQUEST_FAILED;
	}
}
