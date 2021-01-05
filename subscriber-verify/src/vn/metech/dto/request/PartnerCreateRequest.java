package vn.metech.dto.request;

import vn.metech.entity.Partner;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PartnerCreateRequest {

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String partnerCode;


	public Partner toPartner() {
		return toPartner(new Partner());
	}

	public Partner toPartner(Partner partner) {
		partner.setName(name);

		return partner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

}
