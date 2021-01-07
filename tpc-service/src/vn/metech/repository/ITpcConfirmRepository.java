package vn.metech.repository;

import vn.metech.dto.MtTpcConfirmFilter;
import vn.metech.entity.TpcConfirm;
import vn.metech.jpa.repository.IRepository;
import vn.metech.shared.PagedResult;

import java.util.Date;
import java.util.List;

public interface ITpcConfirmRepository extends IRepository<TpcConfirm> {

	TpcConfirm getConfirmBy(String phoneNumber, Date checkedDate, Date duplicateBefore);

	PagedResult<TpcConfirm> getResponsesBy(MtTpcConfirmFilter tpcConfirmFilter, List<String> userIds);

}
