package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.Partner;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PartnerListResponse extends PartnerBase {

	public static PartnerListResponse from(Partner partner) {
		if (partner == null) {
			return null;
		}

		PartnerListResponse response = new PartnerListResponse();
		response.setProperties(partner);

		return response;
	}

	public static List<PartnerListResponse> fromCollection(Collection<Partner> partners) {
		if (partners == null || partners.isEmpty()) {
			return Collections.emptyList();
		}

		return partners.stream().map(PartnerListResponse::from).collect(Collectors.toList());
	}

}
