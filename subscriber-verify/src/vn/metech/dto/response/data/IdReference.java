package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Param;
import vn.metech.common.Result;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.util.JsonUtils;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdReference {

    @JsonProperty("status")
    private Integer result;

    @JsonProperty("reportDate")
    private String reportDate;

    @JsonProperty("idNumber")
    private String idNumber;

    public IdReference() {
    }

    public IdReference(Integer result, String reportDate) {
        this();
        this.result = result;
        this.reportDate = reportDate;
    }

    public IdReference(ConfirmInfoReceive confirmInfoReceive) {
        ConfirmInfo confirmInfo = confirmInfoReceive.getConfirmInfo();
        MfsIdStatus mfsIdStatus = JsonUtils.toObject(confirmInfoReceive.getData(), MfsIdStatus.class);
        if (mfsIdStatus == null || mfsIdStatus.getCurrentIdStatus() == null) {
            Map body = JsonUtils.toObject(confirmInfoReceive.getData(), Map.class);
            try {
                Map response = JsonUtils.convert(body.get("idReference"), Map.class);
                mfsIdStatus = JsonUtils.convert(response.get("data"), MfsIdStatus.class);
            } catch (Exception ignored) { }
        }
        if (mfsIdStatus != null && mfsIdStatus.getCurrentIdStatus() != null) {
            MfsCurrentIdStatus idStatus = mfsIdStatus.getCurrentIdStatus();
            this.result = idStatus.getIdStatus() == null ? null : idStatus.getIdStatus().value();
            this.reportDate = idStatus.getCheckDate();
            this.idNumber = confirmInfo.getParamValue(Param.ID_NUMBER);
            if (this.reportDate == null) {
                this.reportDate = confirmInfo.getParamValue(Param.CHECK_DATE);
            }
        }
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public IdReference(Result result, String reportDate) {
        this(result == null ? null : result.value(), reportDate);
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
}
