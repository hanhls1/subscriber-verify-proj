package vn.metech.dto.tariff;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.Tariff;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TariffDetailResponse {

	private String tariffId;
	private BigDecimal price;
	private Boolean combo;

	protected void setProperties(Tariff tariff) {
		tariffId = tariff.getId();
		price = tariff.getPrice();
		combo = tariff.getCombo();
	}

	public static TariffDetailResponse parse(Tariff tariff) {
		if (tariff == null) return null;

		TariffDetailResponse tariffResponse = new TariffDetailResponse();
		tariffResponse.setProperties(tariff);

		return tariffResponse;
	}

	public String getTariffId() {
		return tariffId;
	}

	public void setTariffId(String tariffId) {
		this.tariffId = tariffId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getCombo() {
		return combo;
	}

	public void setCombo(Boolean combo) {
		this.combo = combo;
	}
}
