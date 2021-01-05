package vn.metech.dto.tariff;

import vn.metech.constant.AppService;
import vn.metech.constant.VerifyService;
import vn.metech.entity.Tariff;
import vn.metech.util.DateUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TariffListResponse {

	private String tariffId;
	private AppService appService;
	private VerifyService verifyService;
	private String createdDate;

	public TariffListResponse() {
	}

	public TariffListResponse(Tariff tariff) {
		this();
		this.tariffId = tariff.getId();
		this.appService = tariff.getAppService();
		this.verifyService = tariff.getVerifyService();
		this.createdDate = DateUtils.formatDate(tariff.getCreatedDate());
	}

	public static List<TariffListResponse> fromCollections(Collection<Tariff> tariffs) {
		if (tariffs == null || tariffs.isEmpty()) {
			return Collections.emptyList();
		}

		return tariffs.stream().map(TariffListResponse::new).collect(Collectors.toList());
	}

	public String getTariffId() {
		return tariffId;
	}

	public void setTariffId(String tariffId) {
		this.tariffId = tariffId;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
