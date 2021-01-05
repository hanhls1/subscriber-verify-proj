package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.MfsStatus;
import vn.metech.common.Result;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.util.JsonUtils;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImeiReference {

    @JsonProperty("status")
    private Integer result;

    @JsonIgnore
    private Integer result2;

    @JsonProperty("reportDate")
    private String reportDate;

    public ImeiReference() {
    }

    public ImeiReference(Integer result, String reportDate) {
        this();
        this.result = result;
        this.reportDate = reportDate;
    }

    public ImeiReference(ConfirmInfoReceive confirmInfoReceive) {
        MfsImeiStatus mfsImeiStatus = JsonUtils.toObject(confirmInfoReceive.getData(), MfsImeiStatus.class);
        if (mfsImeiStatus == null || mfsImeiStatus.getLatestImeiStatus() == null) {
            Map body = JsonUtils.toObject(confirmInfoReceive.getData(), Map.class);
            try {
                Map response = JsonUtils.convert(body.get("adReference"), Map.class);
                mfsImeiStatus = JsonUtils.convert(response.get("data"), MfsImeiStatus.class);
            } catch (Exception ignored) {
            }
        }
        if (mfsImeiStatus != null && mfsImeiStatus.getLatestImeiStatus() != null) {
            MfsLatestImeiStatus imeiStatus = mfsImeiStatus.getLatestImeiStatus();
            this.result = imeiStatus.getImeiStatus() == null ? null : imeiStatus.getImeiStatus().value();
            this.reportDate = imeiStatus.getCheckDate();
        }
        if (mfsImeiStatus != null && mfsImeiStatus.getCurrentImeiStatus() != null) {
            MfsCurrentImeiStatus imeiStatus = mfsImeiStatus.getCurrentImeiStatus();
            this.result = imeiStatus.getImeiStatus() == null ? null : imeiStatus.getImeiStatus().value();
//            this.reportDate = imeiStatus.getCheckDate();
        }
    }

//    public ImeiReference(ConfirmInfoReceive confirmInfoReceive) {
//        MfsImeiStatus mfsImeiStatus = JsonUtils.toObject(confirmInfoReceive.getData(), MfsImeiStatus.class);
//        Assert.notNull(mfsImeiStatus, "data must be not null");
//        Assert.notNull(mfsImeiStatus.getLatestImeiStatus(), "data must be not null");
//        MfsLatestImeiStatus imeiStatus = mfsImeiStatus.getLatestImeiStatus();
//        this.result = imeiStatus.getImeiStatus() == null ? null : imeiStatus.getImeiStatus().value();
//        this.reportDate = imeiStatus.getCheckDate();
//    }

    public ImeiReference(MfsStatus result, String reportDate) {
        this(result == null ? null : result.value(), reportDate);
    }


    public ImeiReference(Result result, String reportDate) {
        this(result == null ? null : result.value(), reportDate);
    }

    public Integer getResult() {
        return result;
    }

    public Integer getResult2() {
        return result2;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
