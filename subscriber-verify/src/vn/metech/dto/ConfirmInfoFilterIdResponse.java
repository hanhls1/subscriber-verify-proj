package vn.metech.dto;

import java.util.ArrayList;
import java.util.List;

public class ConfirmInfoFilterIdResponse {

    private Long total;
    private List<String> confirmInfoIds;

    public ConfirmInfoFilterIdResponse() {
        this.total = 0L;
        this.confirmInfoIds = new ArrayList<>();
    }

    public ConfirmInfoFilterIdResponse(Long total, List<String> confirmInfoIds) {
        this();
        this.total = total;
        this.confirmInfoIds = confirmInfoIds;
    }

    public static ConfirmInfoFilterIdResponse of(long total, List<String> ids) {
        return new ConfirmInfoFilterIdResponse(total, ids);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<String> getConfirmInfoIds() {
        return confirmInfoIds;
    }

    public void setConfirmInfoIds(List<String> confirmInfoIds) {
        this.confirmInfoIds = confirmInfoIds;
    }
}
