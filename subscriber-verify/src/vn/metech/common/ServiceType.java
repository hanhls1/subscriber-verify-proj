package vn.metech.common;

import vn.metech.constant.VerifyService;
import static vn.metech.constant.VerifyService.*;
import static vn.metech.common.Param.*;

public enum ServiceType {

    FC_CAC(FC03_02, "CAC", PROVINCE, PHONE_NUMBER),
    FC_ADV_CAC(FC03_01, "ADV_CAC", PHONE_NUMBER, HOME_ADDRESS, WORK_ADDRESS),
    FC_BS_CAC_01(FC03_01, "ADV_CAC", PHONE_NUMBER, HOME_ADDRESS, WORK_ADDRESS),
    FC_TPC_ADV_CAC(FC03_01, "TPC_ADV_CAC", PHONE_NUMBER, HOME_ADDRESS, WORK_ADDRESS),
    FC_KYC_03(KYC03, "KYC_03", PHONE_NUMBER, HOME_ADDRESS, WORK_ADDRESS),
    FC_TPC_KYC_03(KYC03, "TPC_KYC_03", PHONE_NUMBER, HOME_ADDRESS, WORK_ADDRESS),
    FC_RE_CAC(FC03_02, "CAC", true, PHONE_NUMBER, REQUEST_ID),
    FC_RE_ADV_CAC(FC03_01, "ADV_CAC", true, PHONE_NUMBER, REQUEST_ID),
    FC_REF_PHONE(FC04_02, "REF_PHONE", PHONE_NUMBER, REF_PHONE_1, REF_PHONE_2),
    FC_ADV_REF_PHONE(FC04_01, "ADV_REF_PHONE", PHONE_NUMBER, REF_PHONE_1, REF_PHONE_2),
    FC_TPC_REF_PHONE(FC04_01, "TPC_REF_PHONE", PHONE_NUMBER, REF_PHONE_1, REF_PHONE_2),
    FC_RE_REF_PHONE(FC04_02, "REF_PHONE", true, PHONE_NUMBER, REQUEST_ID),
    FC_KYC02_VPB(KYC02_VPB, "KYC02_VPB", ID_NUMBER),
    FC_TPC_KYC_02(KYC02, "TPC_KYC_02", ID_NUMBER),
    FC_RE_KYC02_VPB(KYC02_VPB, "KYC02_VPB", true, ID_NUMBER, REQUEST_ID),
    FC_IMEI(FC02_01, "IMEI", PHONE_NUMBER),
    FC_BS_IMEI_02(FC02_02, "BS_IMEI_02", PHONE_NUMBER),
    FC_BS_IMEI_01(FC02_01, "BS_IMEI_01", PHONE_NUMBER),
    FC_RE_IMEI(FC02_01, "IMEI", true, PHONE_NUMBER, REQUEST_ID),
    FC_RE_BS_IMEI_02(FC02_02, "BS_IMEI_02", true, PHONE_NUMBER, REQUEST_ID),
    FC_RE_BS_IMEI_01(FC02_01, "BS_IMEI_01", true, PHONE_NUMBER, REQUEST_ID),
    FC_ID(FC01, "ID", PHONE_NUMBER, ID_NUMBER),
    FC_TPC_ID(FC01, "TPC_ID", PHONE_NUMBER, ID_NUMBER),
    FC_RE_ID(FC01, "ID", true, PHONE_NUMBER, REQUEST_ID),
    FC_ADV_CB01(null, "ADV_CB01", PHONE_NUMBER, HOME_ADDRESS, WORK_ADDRESS, REF_PHONE_1, REF_PHONE_2),
    FC_RE_ADV_CB01(null, "ADV_CB01", true, PHONE_NUMBER, REQUEST_ID),
    FC_ADV_CB02(null, "ADV_CB02", PHONE_NUMBER, HOME_ADDRESS, WORK_ADDRESS, REF_PHONE_1, REF_PHONE_2),
    FC_RE_ADV_CB02(null, "ADV_CB02", true, PHONE_NUMBER, REQUEST_ID),
    FC_ADV_CB03(null, "ADV_CB03", PHONE_NUMBER, HOME_ADDRESS, WORK_ADDRESS, REF_PHONE_1, REF_PHONE_2, ID_NUMBER),
    FC_RE_ADV_CB03(null, "ADV_CB03", true, PHONE_NUMBER, ID_NUMBER, REQUEST_ID),
    FC_BS_CB04(null, "BS_CB04", PHONE_NUMBER, REF_PHONE_1, REF_PHONE_2),//            FC_BS_CB04
    FC_RE_BS_CB04(null, "BS_CB04", true, PHONE_NUMBER, REQUEST_ID),
    FC_BS_CB05(null, "BS_CB05", PHONE_NUMBER, REF_PHONE_1, REF_PHONE_2, PROVINCE),//    FC_BS_CB05
    FC_RE_BS_CB05(null, "BS_05", true, PHONE_NUMBER, REQUEST_ID)
    //
    ;

    String code;
    boolean recall;
    Param[] requiredParams;
    VerifyService verifyService;
//
    ServiceType() {
        this.recall = false;
    }

    ServiceType(VerifyService verifyService, String code, Param... requiredParams) {
        this(verifyService, code, false, requiredParams);
    }

    public static ServiceType fromName(String name){
        for (ServiceType b : ServiceType.values()) {
            if (b.name().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return null;
    }

    ServiceType(VerifyService verifyService, String code, boolean recall, Param... requiredParams) {
        this.code = code;
        this.recall = recall;
        this.requiredParams = requiredParams;
        this.verifyService = verifyService;
    }

    public Param[] requiredParams() {
        return this.requiredParams;
    }

    public boolean isRecall() {
        return this.recall;
    }

    public String code() {
        return this.code;
    }

    public VerifyService verifyService() {
        return this.verifyService;
    }


}
