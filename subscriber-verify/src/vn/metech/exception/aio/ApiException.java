package vn.metech.exception.aio;


import vn.metech.common.Command;
import vn.metech.common.ServiceType;

public abstract class ApiException extends BaseException {

	private Command command;
	private ServiceType serviceType;
	private String requestId;

	public ApiException(Command command, ServiceType serviceType, String requestId, String msg) {
		super(msg);
		this.command = command;
		this.serviceType = serviceType;
		this.requestId = requestId;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
