package vn.metech.service;

import vn.metech.dto.msg.TpcMessage;
import vn.metech.entity.TpcConfirm;
import vn.metech.entity.TpcConfirmInfo;
import vn.metech.jpa.service.IService;

public interface TpcConfirmInfoService extends IService<TpcConfirmInfo> {

	TpcConfirmInfo getConfirmInfoBy(TpcMessage tpcMessage, TpcConfirm tpcConfirm);

}
