package vn.metech.dto.request.combo;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsCombo01Request extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_ADV_CB01;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_ADV_CB01;
    }
}
