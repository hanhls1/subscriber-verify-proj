package vn.metech.shared;

import java.util.Date;

public interface IAudit {

    Date getCreatedDate();

    void setCreatedDate(Date createdDate);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getUpdatedDate();

    void setUpdatedDate(Date updatedDate);

    String getUpdatedBy();

    void setUpdatedBy(String updatedBy);
}
