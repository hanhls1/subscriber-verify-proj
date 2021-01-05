package vn.metech.dto.tariff;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TariffUpdateRequest extends TariffCreateRequest {

	@NotNull
	@NotEmpty
	private String tariffId;

	public String getTariffId() {
		return tariffId;
	}

	public void setTariffId(String tariffId) {
		this.tariffId = tariffId;
	}
}
