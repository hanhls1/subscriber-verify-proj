package vn.metech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.metech.entity.AdRequest;
import vn.metech.entity.CallRequest;
import vn.metech.entity.IdRequest;
import vn.metech.entity.LocationRequest;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;

import java.util.*;

@Service
public class AIOService {

    @Value("${server.port}")
    private String serverPort;

    private final LoadBalancerClient loadBalancerClient;

    public AIOService(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    private String chooseLocalServiceUrl() {
//        ServiceInstance serviceInstance = this.loadBalancerClient.choose("VERIFY-SERVICE");
//        return "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
        return "http://localhost" + ":" + serverPort;

    }

    public int sendAdRequestToAIO(AdRequest adRequest) {
        String requestUrl = chooseLocalServiceUrl() + "/fc02/02/request";

        List<Map<String, Object>> requestParams = new ArrayList<>();
        requestParams.add(Collections.singletonMap("PHONE_NUMBER", adRequest.getPhoneNumber().trim()));
        requestParams.add(Collections.singletonMap("CHECK_DATE", DateUtils.formatDate(adRequest.getDateToCheck())));
        requestParams.add(Collections.singletonMap("REQUEST_ID", adRequest.getId()));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("params", requestParams);
        requestBody.put("command", "TPC");
        requestBody.put("serviceCode", "FC_BS_IMEI_02");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("user-id", adRequest.getCreatedBy());

        System.out.println(JsonUtils.toJson(requestBody));

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, Map.class);

        Map responseBody = responseEntity.getBody();
        if (responseBody == null || responseBody.get("statusCode") == null) return 500;

        return (int) responseBody.get("statusCode");
    }

    public int sendCallRequestToAIO(CallRequest callRequest) {
        String requestUrl = chooseLocalServiceUrl() + "/fc04/01/request";

        List<Map<String, Object>> requestParams = new ArrayList<>();
        requestParams.add(Collections.singletonMap("PHONE_NUMBER", callRequest.getPhoneNumber().trim()));
        requestParams.add(Collections.singletonMap("REQUEST_ID", callRequest.getId()));
        requestParams.add(Collections.singletonMap("REF_PHONE_1", callRequest.getRefPhone1()));
        requestParams.add(Collections.singletonMap("REF_PHONE_2", callRequest.getRefPhone2()));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("params", requestParams);
        requestBody.put("command", "TPC");
        requestBody.put("serviceCode", "FC_TPC_REF_PHONE");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("user-id", callRequest.getCreatedBy());

        System.out.println(JsonUtils.toJson(requestBody));

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, Map.class);

        Map responseBody = responseEntity.getBody();
        if (responseBody == null || responseBody.get("statusCode") == null) return 500;

        return (int) responseBody.get("statusCode");
    }

    public int sendIdRequestToAIO(IdRequest idRequest) {
        String requestUrl = chooseLocalServiceUrl() + "/fc01/request";

        List<Map<String, Object>> requestParams = new ArrayList<>();
        requestParams.add(Collections.singletonMap("PHONE_NUMBER", idRequest.getPhoneNumber().trim()));
        requestParams.add(Collections.singletonMap("ID_NUMBER", idRequest.getIdNumber()));
        requestParams.add(Collections.singletonMap("CHECK_DATE", DateUtils.formatDate(idRequest.getDateToCheck())));
        requestParams.add(Collections.singletonMap("REQUEST_ID", idRequest.getId()));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("params", requestParams);
        requestBody.put("command", "TPC");
        requestBody.put("serviceCode", "FC_TPC_ID");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("user-id", idRequest.getCreatedBy());

        System.out.println(JsonUtils.toJson(requestBody));

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, Map.class);

        Map responseBody = responseEntity.getBody();
        if (responseBody == null || responseBody.get("statusCode") == null) return 500;

        return (int) responseBody.get("statusCode");
    }

    public int sendLocationRequestToAIO(LocationRequest locationRequest) {
        String requestUrl = chooseLocalServiceUrl() + "/fc03/request";

        List<Map<String, Object>> requestParams = new ArrayList<>();
        requestParams.add(Collections.singletonMap("PHONE_NUMBER", locationRequest.getPhoneNumber().trim()));
        requestParams.add(Collections.singletonMap("HOME_ADDRESS", locationRequest.getHome()));
        requestParams.add(Collections.singletonMap("WORK_ADDRESS", locationRequest.getWork()));
        requestParams.add(Collections.singletonMap("REF_ADDRESS", locationRequest.getRef()));
        requestParams.add(Collections.singletonMap("REQUEST_ID", locationRequest.getId()));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("params", requestParams);
        requestBody.put("command", "TPC");
        requestBody.put("serviceCode", "FC_TPC_ADV_CAC");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("user-id", locationRequest.getCreatedBy());

        System.out.println(JsonUtils.toJson(requestBody));

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(requestUrl, httpEntity, Map.class);

        Map responseBody = responseEntity.getBody();
        if (responseBody == null || responseBody.get("statusCode") == null) return 500;

        return (int) responseBody.get("statusCode");
    }

    public String fetchResponseFromAIO(String requestId) {
        String requestUrl = chooseLocalServiceUrl() + "/tpc/response?id=" + requestId;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUrl, String.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) return null;

        return responseEntity.getBody();
    }

}
