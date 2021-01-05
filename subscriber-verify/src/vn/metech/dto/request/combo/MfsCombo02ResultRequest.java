package vn.metech.dto.request.combo;


import vn.metech.common.ServiceType;
import vn.metech.dto.request.RequestBase;

public class MfsCombo02ResultRequest extends RequestBase {

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FC_RE_ADV_CB02;
    }

    @Override
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = ServiceType.FC_RE_ADV_CB02;
    }
}
