package vn.metech.entity;

import vn.metech.common.Command;
import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;
import vn.metech.entity.base.EntityBase;
import vn.metech.util.JsonUtils;

import javax.persistence.*;

@Entity
@Table(name = "api_receive_log")
public class ApiReceiveLog extends EntityBase {

    @Enumerated(EnumType.STRING)
    @Column(name = "command", length = 36)
    private Command command;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_code", length = 100)
    private ServiceType serviceType;

    @Column(name = "secure_hash", length = 64)
    private String secureHash;

    @Column(name = "number_of_params", length = 2)
    private int numberOfParams;

    @Column(name = "is_aio")
    private boolean aio;

    @Column(name = "data", columnDefinition = "nvarchar(4000)")
    private String data;

    public ApiReceiveLog() {
        this.numberOfParams = 0;
    }

    public ApiReceiveLog(RequestBase requestBase, String createdBy) {
        this();
        this.command = requestBase.getCommand();
        this.serviceType = requestBase.getServiceType();
        this.secureHash = requestBase.getSecureHash();
        this.numberOfParams = requestBase.params().size();
        this.data = JsonUtils.toJson(requestBase);
        this.setCreatedBy(createdBy);
    }

    public ApiReceiveLog(RequestBase requestBase) {
        this(requestBase, null);
    }

    public ApiReceiveLog(RequestBase requestBase, String createdBy, boolean aio) {
        this(requestBase, createdBy);
        this.aio = aio;
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

    public String getSecureHash() {
        return secureHash;
    }

    public void setSecureHash(String secureHash) {
        this.secureHash = secureHash;
    }

    public int getNumberOfParams() {
        return numberOfParams;
    }

    public void setNumberOfParams(int numberOfParams) {
        this.numberOfParams = numberOfParams;
    }

    public boolean isAio() {
        return aio;
    }

    public void setAio(boolean aio) {
        this.aio = aio;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
