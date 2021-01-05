package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ConfirmInfoResponse {

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
    private Date createdDate;
    private String subPartnerName;
    private String partnerName;
    private long thanhCong;
    private long thatBai;
    private long sum;

    public ConfirmInfoResponse() {
    }

    public ConfirmInfoResponse( String partnerName, String subPartnerName, long thanhCong, long thatBai, long sum, Date createdDate) {

        this.subPartnerName = subPartnerName;
        this.partnerName = partnerName;
        this.thanhCong = thanhCong;
        this.thatBai = thatBai;
        this.sum = sum;
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getSubPartnerName() {
        return subPartnerName;
    }

    public void setSubPartnerName(String subPartnerName) {
        this.subPartnerName = subPartnerName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public long getThanhCong() {
        return thanhCong;
    }

    public void setThanhCong(long thanhCong) {
        this.thanhCong = thanhCong;
    }

    public long getThatBai() {
        return thatBai;
    }

    public void setThatBai(long thatBai) {
        this.thatBai = thatBai;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
