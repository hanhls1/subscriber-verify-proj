package vn.metech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import vn.metech.dto.UserInfo;

import vn.metech.dto.response.UserResponseAd;
import vn.metech.dto.response.UserResponse;
import vn.metech.util.JsonUtils;
import vn.metech.util.RestUtils;
import vn.metech.util.RestUtils.RestResponse;

import java.util.Collections;
import java.util.List;

@Service
public class LocalService {

	@Value("${server.port}")
	private String serverPort;

	private LoadBalancerClient loadBalancerClient;

	public LocalService(LoadBalancerClient loadBalancerClient) {
		this.loadBalancerClient = loadBalancerClient;
	}

	private String chooseLocalServiceUrl() {
//		ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);
//		return "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();

		return "http://localhost" + ":" + serverPort;
	}

	@SuppressWarnings("unchecked")
	public List<String> getRefUserIds(String userId) {
		try {
			String url = chooseLocalServiceUrl() + "/local/get-ref-user-ids?user-id=" + userId;
//			String url = chooseLocalServiceUrl("AUTH-SERVICE") + "/local/get-ref-user-ids?user-id=" + userId;
			RestResponse<List> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, List.class);
			return restResponse.getBody();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getTpcUserIds() {
		try {
			String url = chooseLocalServiceUrl() + "/local/get-tpc-user-ids";
			RestResponse<List> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, List.class);

			return restResponse.getBody();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	public UserResponseAd getUserInfoAd(String userId) {
		try {
			String url = chooseLocalServiceUrl() + "/local/get-user-info?user-id=" + userId;
			RestResponse<UserResponseAd> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, UserResponseAd.class);

			return restResponse.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public UserResponse getUserInfo(String userId) {
		try {
			String url = chooseLocalServiceUrl() + "/local/get-user-info?user-id=" + userId;
			RestResponse<UserResponse> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, UserResponse.class);

			return restResponse.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public UserInfo getUserInfoAio(String userId) {
		try {
			String url = chooseLocalServiceUrl() + "/local/get-user-info?user-id=" + userId;
			RestResponse<UserInfo> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, UserInfo.class);
			System.out.println(JsonUtils.toJson(restResponse));

			return restResponse.getBody();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getRecordsUserIds(String userId) {
		try {
			String url = chooseLocalServiceUrl() + "/local/get-records-user-ids?user-id=" + userId;
			RestResponse<List> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, List.class);
			return restResponse.getBody();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	public boolean canAccessTo(String userId, String code) {
		try {
//            String url = chooseLocalServiceUrl("AUTH-SERVICE") + "/local/service-access?user-id=" + userId + "&code=" + code;
			String url = chooseLocalServiceUrl() + "/local/service-access?user-id=" + userId + "&code=" + code;
			RestResponse<Boolean> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, Boolean.class);

			return restResponse.getBody();
		} catch (Exception e) {
			return false;
		}
	}
}
