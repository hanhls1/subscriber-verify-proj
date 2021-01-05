package vn.metech.dto.request;


import vn.metech.entity.ConfirmInfo;

public class MfsMessageRequest extends MfsRequestBase {

    public MfsMessageRequest(ConfirmInfo confirmInfo, String account, String hashKey) {
        super(account, confirmInfo.getCustomerCode(), confirmInfo.getPhoneNumber(), hashKey);
    }

}
