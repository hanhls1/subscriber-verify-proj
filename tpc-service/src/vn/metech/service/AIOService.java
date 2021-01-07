package vn.metech.service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.metech.entity.InfoDiscoveryRequest;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;

import java.util.*;

@Service
public class AIOService {

    private final LoadBalancerClient loadBalancerClient;

    public AIOService(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    private String chooseLocalServiceUrl() {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("VERIFY-SERVICE");

        return "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }

    public int sendInfoDiscoveryRequestToAIO(InfoDiscoveryRequest infoDiscoveryRequest) {
        String requestUrl = chooseLocalServiceUrl() + "/kyc02/request";

        List<Map<String, Object>> requestParams = new ArrayList<>();
        requestParams.add(Collections.singletonMap("PHONE_NUMBER", infoDiscoveryRequest.getPhoneNumber().trim()));
        requestParams.add(Collections.singletonMap("ID_NUMBER", infoDiscoveryRequest.getIdNumber().trim()));
        requestParams.add(Collections.singletonMap("CUSTOMER_NAME", infoDiscoveryRequest.getCustomerName()));
        requestParams.add(Collections.singletonMap("NUMBER_OF_DEPENDENCY", infoDiscoveryRequest.getNumOfDependent()));
        requestParams.add(Collections.singletonMap("REQUEST_ID", infoDiscoveryRequest.getId()));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("params", requestParams);
        requestBody.put("command", "TPC");
        requestBody.put("serviceCode", "FC_TPC_KYC_02");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("user-id", infoDiscoveryRequest.getCreatedBy());

        System.out.println(JsonUtils.toJson(requestBody));

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, Map.class);

        Map responseBody = responseEntity.getBody();
        if (responseBody == null || responseBody.get("statusCode") == null) return 500;

        return (int) responseBody.get("statusCode");
    }

    public String fetchInfoDiscoveryResponseFromAIO(String requestId) {
        String requestUrl = chooseLocalServiceUrl() + "/tpc/response?id=" + requestId;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUrl, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) return null;

        return responseEntity.getBody();
    }
}
