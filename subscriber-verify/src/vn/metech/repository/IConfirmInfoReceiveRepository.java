package vn.metech.repository;


import vn.metech.common.ServiceType;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.repository.base.IRepository;

import java.util.List;

public interface IConfirmInfoReceiveRepository extends IRepository<ConfirmInfoReceive> {

    ConfirmInfoReceive findByPhoneNumberAndServiceType(String phoneNumber, ServiceType serviceType, List<String> userIds);

    ConfirmInfoReceive findByRequestId(String requestId, List<String> userIds);
}
