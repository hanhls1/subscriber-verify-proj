package com.metechvn.monitor.service;

import com.metechvn.monitor.dto.MailResponse;
import com.metechvn.monitor.dto.PhoneResponse;
import com.metechvn.monitor.util.JsonUtils;
import com.metechvn.monitor.util.RestUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

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

    public List<MailResponse> getEmail() {
        try {
            String url = chooseLocalServiceUrl("SUBSCRIBER-VERIFY") + "/local/get-email-admin?user-id=";
            RestUtils.RestResponse<List> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, List.class);
            System.out.println(JsonUtils.toJson(restResponse));
            return restResponse.getResponseBody();
        } catch (Exception e) {
            return null;
        }
    }

    public List<PhoneResponse> getPhoneNumber() {
        try {
            String url = chooseLocalServiceUrl("SUBSCRIBER-VERIFY") + "/local/get-phone-admin?user-id=";
            RestUtils.RestResponse<List> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, List.class);
            System.out.println(JsonUtils.toJson(restResponse));
            return restResponse.getResponseBody();
        } catch (Exception e) {
            return null;
        }
    }
}
