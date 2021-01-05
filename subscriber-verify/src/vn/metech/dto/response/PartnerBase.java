package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.Partner;
import vn.metech.util.DateUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PartnerBase implements Serializable {

	private String partnerId;
	private String partnerCode;
	private String partnerName;
	private String createdDate;

	protected void setProperties(Partner partner) {
		this.partnerId = partner.getId();
		this.partnerName = partner.getName();
		this.partnerCode = partner.getPartnerCode();
		this.createdDate = DateUtils.formatDate(partner.getCreatedDate());
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

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
