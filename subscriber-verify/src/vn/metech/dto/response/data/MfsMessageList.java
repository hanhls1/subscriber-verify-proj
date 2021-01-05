package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfsMessageList {

    private int size;

    @JsonProperty("sms")
    private MfsMessage[] messages;

    public MfsMessageList() {
        this.size = 0;
        this.messages = new MfsMessage[0];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MfsMessage[] getMessages() {
        return messages;
    }

    public void setMessages(MfsMessage[] messages) {
        this.messages = messages;
    }
}
