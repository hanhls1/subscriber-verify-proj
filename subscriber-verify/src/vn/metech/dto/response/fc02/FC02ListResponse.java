package vn.metech.dto.response.fc02;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vn.metech.common.Param;
import vn.metech.common.Result;
import vn.metech.dto.response.data.ImeiReference;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.util.DateUtils;

import java.util.Date;

public class FC02ListResponse {

    private String phoneNumber;
    private Date checkDate;
    private Result result1;
    private Result result2;
    private Date createdDate;

    @JsonIgnore
    private Date createdDateTime;

    public FC02ListResponse() {
    }

    public FC02ListResponse(ConfirmInfoReceive confirmInfoReceive) {
        ImeiReference imeiReference = new ImeiReference(confirmInfoReceive);
        phoneNumber = confirmInfoReceive.getPhoneNumber();
        checkDate = DateUtils.parseDate(confirmInfoReceive.getConfirmInfo().getParamValue(Param.CHECK_DATE));
        createdDate = confirmInfoReceive.getConfirmInfo().getCreatedDate();
        createdDateTime = confirmInfoReceive.getCreatedDate();
        if (imeiReference.getResult() != null) {
            result1 = Result.of(imeiReference.getResult());
        }
        if (imeiReference.getResult2() != null) {
            result2 = Result.of(imeiReference.getResult2());
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Result getResult1() {
        return result1;
    }

    public void setResult1(Result result1) {
        this.result1 = result1;
    }

    public Result getResult2() {
        return result2;
    }

    public void setResult2(Result result2) {
        this.result2 = result2;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
