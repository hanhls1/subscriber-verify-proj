package vn.metech.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.Param;
import vn.metech.entity.ConfirmInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfsCombo03Request extends MfsCombo02Request {

    @JsonProperty("dateCheckId")
    private String idCheckDate;

    @JsonProperty("id")
    private String idNumber;

    public MfsCombo03Request(ConfirmInfo confirmInfo, String account, String hashKey) {
        super(confirmInfo, account, hashKey);
        this.idCheckDate = getCheckDate();
        this.idNumber = confirmInfo.getParamValue(Param.ID_NUMBER);
    }

    public String getIdCheckDate() {
        return idCheckDate;
    }

    public void setIdCheckDate(String idCheckDate) {
        this.idCheckDate = idCheckDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
