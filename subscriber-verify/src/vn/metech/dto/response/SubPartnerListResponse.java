package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.entity.SubPartner;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubPartnerListResponse extends SubPartnerBase {

	public static SubPartnerListResponse from(SubPartner subPartner) {
		if (subPartner == null) {
			return null;
		}

		SubPartnerListResponse response = new SubPartnerListResponse();
		response.setProperties(subPartner);

		return response;
	}

	public static List<SubPartnerListResponse> fromCollection(Collection<SubPartner> subPartners) {
		if (subPartners == null || subPartners.isEmpty()) {
			return Collections.emptyList();
		}

		return subPartners.stream().map(SubPartnerListResponse::from).collect(Collectors.toList());
	}
}
