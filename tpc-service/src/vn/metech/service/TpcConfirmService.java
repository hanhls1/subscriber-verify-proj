package vn.metech.service;

import vn.metech.dto.MtTpcConfirmFilter;
import vn.metech.dto.msg.TpcMessage;
import vn.metech.dto.response.MtTpcConfirmListResponse;
import vn.metech.entity.TpcConfirm;
import vn.metech.jpa.service.IService;
import vn.metech.shared.PagedResult;

public interface TpcConfirmService extends IService<TpcConfirm> {

	TpcConfirm getConfirmBy(TpcMessage tpcMessage);

	PagedResult<MtTpcConfirmListResponse> getResponsesBy(MtTpcConfirmFilter toFilter, String userId);

}
