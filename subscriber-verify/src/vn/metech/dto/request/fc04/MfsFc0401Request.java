package vn.metech.dto.request.fc04;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsFc0401Request extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_TPC_REF_PHONE;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_TPC_REF_PHONE;
    }
}
