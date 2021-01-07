package vn.metech.repository;

import vn.metech.entity.TpcConfirmInfo;
import vn.metech.jpa.repository.IRepository;

public interface ITpcConfirmInfoRepository extends IRepository<TpcConfirmInfo> {

	TpcConfirmInfo getConfirmInfoBy(String requestId, String tpcConfirmId);

}
