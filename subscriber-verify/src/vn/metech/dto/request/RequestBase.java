package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Command;
import vn.metech.common.Param;
import vn.metech.common.ServiceType;
import vn.metech.entity.base.Hashtable;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RequestBase extends Hashtable {

    @NotNull
    @JsonProperty("command")
    protected Command command;

    //created
//    @NotNull
    @JsonProperty("subPartnerId")
    protected String subPartnerId;

//    @NotNull
    @JsonProperty("partnerId")
    protected String partnerId;

    @JsonProperty("serviceCode")
    protected ServiceType serviceType;

    @Size(min = 1)
    @JsonProperty("params")
    private List<Map<Param, Object>> params;

    public Map<Param, Object> params() {
        Map<Param, Object> params = new HashMap<>();
        for (Map<Param, Object> param : this.params) {
            param.forEach(params::put);
        }   //

        return params;
    }

    @JsonIgnore
    public boolean isValid() {
        Map<Param, Object> params = this.params();
        for (Param param : this.serviceType.requiredParams()) {
            if (params.get(param) == null) {
                return false;
            }
        }

        return true;
    }

    public RequestBase() {
        this.params = new ArrayList<>();
    }

    public RequestBase(Command command, ServiceType serviceType) {
        this();
        this.command = command;
        this.serviceType = serviceType;
    }

    public RequestBase(Command command, ServiceType serviceType, List<Map<Param, Object>> params) {
        this(command, serviceType);
        this.params = params;
    }

    public RequestBase(Command command, ServiceType serviceType, String subPartnerId, String partnerId, List<Map<Param,  Object>> params) {
        this(command, serviceType);
        this.subPartnerId = subPartnerId;
        this.partnerId = partnerId;
        this.params = params;

    }

    public String id() {
        Map<Param, Object> params = params();
        Object requestId = params.get(Param.REQUEST_ID);

        return requestId == null ? null : String.valueOf(requestId);
    }

    public String phoneNumber() {
        Map<Param, Object> params = params();
        Object phoneNumber = params.get(Param.PHONE_NUMBER);

        return phoneNumber == null ? null : String.valueOf(phoneNumber);
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

    public List<Map<Param, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<Param, Object>> params) {
        this.params = params;
    }

    public String getSubPartnerId() {
        return subPartnerId;
    }

    public void setSubPartnerId(String subPartnerId) {
        this.subPartnerId = subPartnerId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
