package vn.metech.entity;

import org.hibernate.annotations.Where;
import vn.metech.constant.AppService;
import vn.metech.constant.VerifyService;
import vn.metech.shared.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Tariff")
@Where(clause = "IsDeleted = 0")
public class Tariff extends BaseEntity {

	@Column(name = "IsCombo")
	private Boolean combo;

	@Column(name = "Price")
	private BigDecimal price;

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

	public Tariff() {
		this.combo = false;
	}

	public Boolean getCombo() {
		return combo;
	}

	public void setCombo(Boolean combo) {
		this.combo = combo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public AppService getAppService() {
		return appService;
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
