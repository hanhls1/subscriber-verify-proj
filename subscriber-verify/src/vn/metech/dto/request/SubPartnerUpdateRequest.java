package vn.metech.dto.request;

import vn.metech.entity.SubPartner;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SubPartnerUpdateRequest {

    @NotNull
    @NotEmpty
    private String subPartnerId;

    @NotNull
    @NotEmpty
    private String name;

    private String partnerCode;

    @NotNull
    @NotEmpty
    private String customerCode;

    @NotNull
    @NotEmpty
    private String accountCode;

    @NotNull
    @NotEmpty
    private String secureKey;

    @NotNull
    @NotEmpty
    private String partnerId;

    public SubPartner toSubPartner(SubPartner subPartner) {
        subPartner.setName(name);
        subPartner.setAccount(accountCode);
        subPartner.setSecureKey(secureKey);
        subPartner.setCustomerCode(customerCode);

        return subPartner;
    }

    public SubPartner toSubPartner() {
        return toSubPartner(new SubPartner());
    }

    public String getSubPartnerId() {
        return subPartnerId;
    }

    public void setSubPartnerId(String subPartnerId) {
        this.subPartnerId = subPartnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
