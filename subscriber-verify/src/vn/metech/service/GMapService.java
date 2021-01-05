package vn.metech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.metech.common.ValueKey;
import vn.metech.dto.gplace.GGeocoding;
import vn.metech.dto.gplace.GGeocodingResult;
import vn.metech.dto.gplace.GPlace;
import vn.metech.dto.gplace.GPlaceLocation;
import vn.metech.entity.ApiRequestLog;
import vn.metech.util.RestUtils;
import vn.metech.util.RestUtils.RestResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GMapService {

	private final String gplaceBaseUrl;
	private final String gplaceApiKey;
	private final String gplaceLanguage;
	private final String gplaceResponseFields;

	private CrudRepository<ApiRequestLog, String> apiRequestLogCrudRepository;

	public GMapService(
					CrudRepository<ApiRequestLog, String> apiRequestLogCrudRepository,
					@Value(ValueKey.GPLACE_REQUEST_BASE_URL) String gplaceBaseUrl,
					@Value(ValueKey.GPLACE_REQUEST_API_KEY) String gplaceApiKey,
					@Value(ValueKey.GPLACE_REQUEST_LANGUAGE) String gplaceLanguage,
					@Value(ValueKey.GPLACE_RESPONSE_FIELDS) String gplaceResponseFields) {
		this.apiRequestLogCrudRepository = apiRequestLogCrudRepository;
		this.gplaceBaseUrl = gplaceBaseUrl;
		this.gplaceApiKey = gplaceApiKey;
		this.gplaceLanguage = gplaceLanguage;
		this.gplaceResponseFields = gplaceResponseFields;
	}

	public GPlaceLocation getPlaceOf(String address) {
		Assert.notNull(address, "address required");
		Map<String, Object> params = new HashMap<>();
		params.put("input", address);
		params.put("inputtype", "textquery");
		params.put("fields", gplaceResponseFields);
		params.put("key", gplaceApiKey);
		params.put("language", gplaceLanguage);

		RestResponse<GPlace> gplaceLocationResponse =
						RestUtils.get(gplaceBaseUrl + "/place/findplacefromtext/json", params, GPlace.class);
		ApiRequestLog apiRequestLog = new ApiRequestLog(gplaceLocationResponse);
		apiRequestLogCrudRepository.save(apiRequestLog);
		try {
			return gplaceLocationResponse.getBody().location();
		} catch (Exception e) {
			apiRequestLog.setMessage(e.getMessage());
			apiRequestLogCrudRepository.save(apiRequestLog);
			return null;
		}
	}

	public String getAddressOf(Double lat, Double lng) {
		Assert.notNull(lat, "lat required");
		Assert.notNull(lng, "lng required");
		Map<String, Object> params = new HashMap<>();
		params.put("latlng", lat + "," + lng);
		params.put("key", gplaceApiKey);
		params.put("language", gplaceLanguage);

		RestResponse<GGeocoding> geocodingRestResponse =
						RestUtils.get(gplaceBaseUrl + "/geocode/json", params, GGeocoding.class);
		ApiRequestLog apiRequestLog = new ApiRequestLog(geocodingRestResponse);
		apiRequestLogCrudRepository.save(apiRequestLog);
		try {
			List<GGeocodingResult> results = geocodingRestResponse.getBody().getResults();
			if (results.size() == 0) {
				return getCompoundAddress(geocodingRestResponse.getBody().getPlusCode().getCompoundCode());
			}
			GGeocodingResult result = results.get(0);
			return result.getFormattedAddr();
		} catch (Exception e) {
			apiRequestLog.setMessage(e.getMessage());
			apiRequestLogCrudRepository.save(apiRequestLog);
			return null;
		}
	}

	private String getCompoundAddress(String compoundCode) {
		int index = compoundCode.indexOf(" ");

		return index == -1 ? compoundCode : compoundCode.substring(index);
	}
}
