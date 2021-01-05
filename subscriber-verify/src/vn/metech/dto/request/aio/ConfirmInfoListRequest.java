package vn.metech.dto.request.aio;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.common.ServiceType;
import vn.metech.dto.request.PageRequest;
import vn.metech.util.StringUtils;

import java.util.Date;

public class ConfirmInfoListRequest extends PageRequest {

    private String phoneNumber;
    private ServiceType serviceType;

    @JsonProperty("from")
    @JsonAlias("fromDate")
    private Date fromDate;

    @JsonProperty("to")
    @JsonAlias("toDate")
    private Date toDate;

    public String getPhoneNumber() {
        return StringUtils.isEmpty(phoneNumber) ? "" : phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Date getFromDate() {
        return fromDate /* != null ? from : DateUtils.parseDate("1970-01-01") */;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate /*!= null ? to : DateUtils.parseDate("2999-01-01")*/;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
