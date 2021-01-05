package vn.metech.dto.response;

public class SubPartnerResponse {

    private String subPartnerId;
    private String subPartnerName;

    public SubPartnerResponse(String subPartnerId, String subPartnerName) {
        this.subPartnerId = subPartnerId;
        this.subPartnerName = subPartnerName;
    }

    public String getSubPartnerId() {
        return subPartnerId;
    }

    public void setSubPartnerId(String subPartnerId) {
        this.subPartnerId = subPartnerId;
    }

    public String getSubPartnerName() {
        return subPartnerName;
    }

    public void setSubPartnerName(String subPartnerName) {
        this.subPartnerName = subPartnerName;
    }
}
