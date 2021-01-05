package vn.metech.dto.response.fc02;


import vn.metech.dto.response.data.MfsImeiStatus;
import vn.metech.dto.response.data.MfsLatestImeiStatus;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.util.JsonUtils;

import java.util.Map;

public class FC0202Response {

    public FC0202Response(ConfirmInfoReceive confirmInfoReceive) {

        MfsImeiStatus mfsImeiStatus = JsonUtils.toObject(confirmInfoReceive.getData(), MfsImeiStatus.class);
        if (mfsImeiStatus == null || mfsImeiStatus.getCurrentImeiStatus() == null) {
            Map body = JsonUtils.toObject(confirmInfoReceive.getData(), Map.class);
            try {
                Map response = JsonUtils.convert(body.get("adReference"), Map.class);
                mfsImeiStatus = JsonUtils.convert(response.get("data"), MfsImeiStatus.class);
            } catch (Exception ignored) { }
        }
        if (mfsImeiStatus != null && mfsImeiStatus.getLatestImeiStatus() != null) {
            MfsLatestImeiStatus imeiStatus = mfsImeiStatus.getLatestImeiStatus();
            this.status = imeiStatus.getImeiStatus() == null ? null : imeiStatus.getImeiStatus().value();
            this.reportDate = imeiStatus.getCheckDate();
        }
    }

    private Integer status;
    private String reportDate;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }
}
