package vn.metech.dto.response;

public class PartnerResponse {

    private String partnerId;
    private String partnerName;

    public PartnerResponse(String partnerId, String partnerName) {
        this.partnerId = partnerId;
        this.partnerName = partnerName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
}
