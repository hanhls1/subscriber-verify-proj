package vn.metech.dto.request.fc04;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsFc0402Request extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_REF_PHONE;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_REF_PHONE;
    }
}
