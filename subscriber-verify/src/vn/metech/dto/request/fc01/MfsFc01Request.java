package vn.metech.dto.request.fc01;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsFc01Request extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_TPC_ID;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_TPC_ID;
    }
}
