package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.AppService;
import vn.metech.constant.VerifyService;
import vn.metech.dto.tariff.TariffDetailDto;
import vn.metech.shared.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "TariffDetail")
@Where(clause = "IsDeleted = 0")
public class TariffDetail extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "AppService")
	private AppService appService;

	@Column(name = "AppServiceName")
	private String appServiceName;

	@Enumerated(EnumType.STRING)
	@Column(name = "VerifyService")
	private VerifyService verifyService;

	@Column(name = "VerifyServiceName")
	private String verifyServiceName;

	@Column(name = "TariffId", columnDefinition = "uniqueidentifier")
	private String tariffId;

	public TariffDetail() {
	}

	public TariffDetail(TariffDetailDto tariffDetailDto, String tariffId) {
		this();
		this.appService = tariffDetailDto.getService();
		this.verifyService = tariffDetailDto.getVerifyService();
		this.tariffId = tariffId;
	}

	public AppService getAppService() {
		return appService;
	}

	public String getTariffId() {
		return tariffId;
	}

	public void setTariffId(String tariffId) {
		this.tariffId = tariffId;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
		if (appService != null) {
			this.appServiceName = appService.name();
		}
	}

	public String getAppServiceName() {
		return appServiceName;
	}

	public void setAppServiceName(String appServiceName) {
		this.appServiceName = appServiceName;
	}

	public VerifyService getVerifyService() {
		return verifyService;
	}

	public void setVerifyService(VerifyService verifyService) {
		this.verifyService = verifyService;
		if (verifyService != null) {
			this.verifyServiceName = verifyService.name();
		}
	}

	public String getVerifyServiceName() {
		return verifyServiceName;
	}

	public void setVerifyServiceName(String verifyServiceName) {
		this.verifyServiceName = verifyServiceName;
	}
}
