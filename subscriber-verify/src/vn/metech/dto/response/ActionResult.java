package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.common.Command;
import vn.metech.common.ServiceType;
import vn.metech.common.StatusCode;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActionResult<T> implements Serializable {

    private Command command;
    private ServiceType serviceType;
    private int statusCode;
    private String description;
    private String requestId;
    private T data;

    public static <T> ActionResult<T> of(Command command, ServiceType serviceType,
                                         int statusCode, String requestId, T data, String description) {
        ActionResult<T> actionResult = new ActionResult<>();
        actionResult.command = command;
        actionResult.serviceType = serviceType;
        actionResult.statusCode = statusCode;
        actionResult.description = description;
        actionResult.requestId = requestId;
        actionResult.data = data;

        return actionResult;
    }

    public static <T> ActionResult<T> of(Command command, ServiceType serviceType,
                                         StatusCode statusCode, String requestId, T data, String description) {
        return of(command, serviceType,
                statusCode == null ? StatusCode.UNDEFINDED.value() : statusCode.value(), requestId, data, description);
    }

    public static <T> ActionResult<T> ok(ServiceType serviceType, String requestId, T data, String description) {
        return of(Command.PROD, serviceType, StatusCode.OK, requestId, data, description);
    }

    public static <T> ActionResult<T> ok(Command command, ServiceType serviceType, String requestId, T data) {
        return of(command, serviceType, StatusCode.OK, requestId, data, null);
    }

    public static <T> ActionResult<T> ok(ServiceType serviceType, String requestId, T data) {
        return ok(serviceType, requestId, data, null);
    }

    public static <T> ActionResult<T> ok(StatusCode statusCode, String requestId, T data, String description) {
        return ActionResult.of(null, null, statusCode, requestId, data, description);
    }

    public static <T> ActionResult<T> ok(StatusCode statusCode, String requestId, T data) {
        return ok(statusCode, requestId, data, null);
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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
