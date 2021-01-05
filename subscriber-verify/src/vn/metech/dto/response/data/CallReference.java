package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Param;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CallReference {

    @JsonProperty("referenceReports")
    private List<CallBase> referenceReports;

    @JsonProperty("frequencyReports")
    private List<CallBase> frequencyReports;

    public CallReference() {
        this.referenceReports = new ArrayList<>();
        this.frequencyReports = new ArrayList<>();
    }

    public CallReference(ConfirmInfoReceive confirmInfoReceive) {
        this();
        ConfirmInfo confirmInfo = confirmInfoReceive.getConfirmInfo();
        MfsCallAdvance mfsCallAdvance = JsonUtils.toObject(confirmInfoReceive.getData(), MfsCallAdvance.class);
        if (mfsCallAdvance == null
                || mfsCallAdvance.getStatusReport() == null || mfsCallAdvance.getFreqReport() == null) {
            Map body = JsonUtils.toObject(confirmInfoReceive.getData(), Map.class);
            try {
                Map response = JsonUtils.convert(body.get("callReference"), Map.class);
                mfsCallAdvance = JsonUtils.convert(response.get("data"), MfsCallAdvance.class);
            } catch (Exception ignored) { }
        }
        if (mfsCallAdvance != null
                && mfsCallAdvance.getStatusReport() != null && mfsCallAdvance.getFreqReport() != null) {
            MfsCallStatus callStatus = mfsCallAdvance.getStatusReport();
            MfsCallFrequency callFrequency = mfsCallAdvance.getFreqReport();
            this.referenceReports.add(
                    new RefPhone1(confirmInfo.getParamValue(Param.REF_PHONE_1), callStatus.getRefPhone1Status())
            );
            this.referenceReports.add(
                    new RefPhone2(confirmInfo.getParamValue(Param.REF_PHONE_2), callStatus.getRefPhone2Status())
            );
            this.frequencyReports.add(
                    new RefPhone1(
                            confirmInfo.getParamValue(Param.REF_PHONE_1),
                            callStatus.getRefPhone1Status(),
                            Double.valueOf(callFrequency.getDur1()),
                            Double.valueOf(callFrequency.getFreq1())
                    )
            );
            this.frequencyReports.add(
                    new RefPhone2(
                            confirmInfo.getParamValue(Param.REF_PHONE_2),
                            callStatus.getRefPhone2Status().value(),
                            Double.valueOf(callFrequency.getDur2()),
                            Double.valueOf(callFrequency.getFreq2())
                    )
            );
        }
    }

    public List<CallBase> getReferenceReports() {
        return referenceReports;
    }

    public void setReferenceReports(List<CallBase> referenceReports) {
        this.referenceReports = referenceReports;
    }

    public List<CallBase> getFrequencyReports() {
        return frequencyReports;
    }

    public void setFrequencyReports(List<CallBase> frequencyReports) {
        this.frequencyReports = frequencyReports;
    }
}
