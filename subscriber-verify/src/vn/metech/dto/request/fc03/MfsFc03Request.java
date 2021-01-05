package vn.metech.dto.request.fc03;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsFc03Request extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_TPC_ADV_CAC;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_TPC_ADV_CAC;
    }
}
