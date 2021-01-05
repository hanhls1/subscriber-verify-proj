package vn.metech.repository;


import vn.metech.dto.request.PageRequest;
import vn.metech.dto.response.PageResponse;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.repository.base.IRepository;

import java.util.Date;
import java.util.List;

public interface TpcConfirmInfoRepository extends IRepository<TpcConfirmInfo> {

    TpcConfirmInfo getAggregateTpcConfirmInfo(String phoneNumber, Date checkDate, Date duplicateBefore);

    PageResponse<TpcConfirmInfo> getTpcConfirmInfoBy(
            String phoneNumber, Date from, Date to, List<String> userIds, PageRequest page);

}
