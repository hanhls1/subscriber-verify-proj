package vn.metech.dto.tariff;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.constant.AppService;
import vn.metech.constant.VerifyService;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TariffDetailDto implements Serializable {

	private AppService service;

	private VerifyService verifyService;

	public AppService getService() {
		return service;
	}

	public void setService(AppService service) {
		this.service = service;
	}

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
	}
}
