package vn.metech.dto.request.fc02;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsFc0202Request extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_BS_IMEI_02;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_BS_IMEI_02;
    }
}
