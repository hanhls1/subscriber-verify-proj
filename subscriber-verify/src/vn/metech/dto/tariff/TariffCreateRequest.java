package vn.metech.dto.tariff;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TariffCreateRequest implements Serializable {

	@NotNull
	@Size(min = 1)
	private List<TariffDetailDto> services;

	@NotNull
	@Min(100)
	private BigDecimal price;

	public TariffCreateRequest() {
		this.services = new ArrayList<>();
	}

	public List<TariffDetailDto> getServices() {
		return services;
	}

	public void setServices(List<TariffDetailDto> services) {
		this.services = services;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
