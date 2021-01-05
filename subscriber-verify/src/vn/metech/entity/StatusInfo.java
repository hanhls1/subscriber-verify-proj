package vn.metech.entity;

import vn.metech.entity.base.EntityBase;

import javax.persistence.*;

@Entity
@Table(name = "StatusInfo")
public class StatusInfo extends EntityBase {

    @Column(name = "status_code", length = 15)
    private String statusCode;

    @Column(name = "status", length = 64)
    private String status;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StatusInfo() {
    }

    public StatusInfo(String statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }
}
