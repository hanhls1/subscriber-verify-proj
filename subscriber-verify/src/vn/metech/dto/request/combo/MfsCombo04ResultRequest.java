package vn.metech.dto.request.combo;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsCombo04ResultRequest extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_RE_BS_CB04;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_RE_BS_CB04;
    }
}
