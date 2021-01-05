package vn.metech.dto.request.combo;

import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsCombo04Request extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_BS_CB04;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_BS_CB04;
    }
}
