package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.SubPartner;
import vn.metech.util.DateUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubPartnerBase implements Serializable {

    private String subPartnerId;
    private String customerCode;
    private String subPartnerName;
    private String accountCode;
    private String secureKey;
    private String createdDate;
    private String partnerId;

    protected void setProperties(SubPartner subPartner) {
        this.subPartnerId = subPartner.getId();
        this.customerCode = subPartner.getCustomerCode();
        this.subPartnerName = subPartner.getName();
        this.accountCode = subPartner.getAccount();
        this.secureKey = subPartner.getSecureKey();
        this.createdDate = DateUtils.formatDate(subPartner.getCreatedDate());
        this.partnerId = subPartner.getPartnerId();
    }

    public String getSubPartnerId() {
        return subPartnerId;
    }

    public void setSubPartnerId(String subPartnerId) {
        this.subPartnerId = subPartnerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getSubPartnerName() {
        return subPartnerName;
    }

    public void setSubPartnerName(String subPartnerName) {
        this.subPartnerName = subPartnerName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        this.secureKey = secureKey;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
