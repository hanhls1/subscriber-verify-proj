package vn.metech.service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import vn.metech.util.RestUtils;
import vn.metech.util.RestUtils.RestResponse;

import java.util.Collections;
import java.util.List;

@Service
public class LocalService {

	private LoadBalancerClient loadBalancerClient;

	public LocalService(LoadBalancerClient loadBalancerClient) {
		this.loadBalancerClient = loadBalancerClient;
	}

	private String chooseLocalServiceUrl(String serviceId) {
		ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);

		return "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
	}

	@SuppressWarnings("unchecked")
	public List<String> getRefUserIds(String userId) {
		try {
			String url = chooseLocalServiceUrl("AUTH-SERVICE") + "/local/get-ref-user-ids?user-id=" + userId;
			RestResponse<List> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, List.class);
			return restResponse.getBody();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getTpcUserIds() {
		try {
			String url = chooseLocalServiceUrl("AUTH-SERVICE") + "/local/get-tpc-user-ids";
			RestResponse<List> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, List.class);

			return restResponse.getBody();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
