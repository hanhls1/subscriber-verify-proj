package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsMessage {

    @JsonProperty("customerCode")
    private String customerCode;

    @JsonProperty("message")
    private String content;

    @JsonProperty("createTime")
    private String dateTime;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
