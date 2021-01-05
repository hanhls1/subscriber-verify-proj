package vn.metech.dto.request.fc01;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsFc01ResultRequest extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_RE_ID;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_RE_ID;
    }
}
