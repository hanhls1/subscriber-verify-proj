package vn.metech.dto.request;

import vn.metech.entity.Partner;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PartnerUpdateRequest {

    @NotNull
    @NotEmpty
    private String partnerId;

    @NotNull
    @NotEmpty
    private String name;

    public Partner toPartner() {
        return toPartner(new Partner());
    }

    public Partner toPartner(Partner partner) {
        partner.setName(name);

        return partner;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
