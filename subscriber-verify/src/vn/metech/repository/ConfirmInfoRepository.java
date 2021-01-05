package vn.metech.repository;


import vn.metech.common.ServiceType;
import vn.metech.dto.ConfirmInfoFilterIdResponse;
import vn.metech.dto.request.PageRequest;
import vn.metech.dto.request.monitor.ConfirmRequest;
import vn.metech.dto.request.monitor.FilterRequest;
import vn.metech.dto.response.ConfirmInfoResponse;
import vn.metech.dto.response.PageResponse;
import vn.metech.dto.response.SubPartnerResponse;
import vn.metech.entity.ConfirmInfo;
import vn.metech.repository.base.IRepository;

import java.util.Date;
import java.util.List;

public interface ConfirmInfoRepository extends IRepository<ConfirmInfo> {

    long countConfirmInfoByRequest(String requestId);

    ConfirmInfo getConfirmInfoByQRequest(String qRequestId);

    PageResponse<String> getConfirmInfoIdsBy(
            String phoneNumber, ServiceType serviceType, Date from, Date to, List<String> userIds, PageRequest page);

    ConfirmInfoFilterIdResponse fillConfirmInfoIds(
            String phoneNumber, ServiceType serviceType, Date from, Date to, List<String> userIds, PageRequest page);

    /////////////////////////////////////////////

    List<SubPartnerResponse> getConfirmInfoSubPartnerIdBy(String partnerId);

    PageResponse<ConfirmInfo> getConfirmInfoBy(ConfirmRequest confirmRequest, PageRequest page);

    List<ConfirmInfo> getConfirmInfoByBy(ConfirmRequest confirmRequest, PageRequest page);

    PageResponse<ConfirmInfoResponse> getFilterRequest(FilterRequest filter, PageRequest page);

    List<ConfirmInfoResponse> getFilterRequestBy(FilterRequest filter, PageRequest page);
}
