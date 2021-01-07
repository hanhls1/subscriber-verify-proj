package vn.metech.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.constant.AppService;
import vn.metech.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerResult {

  private AppService appService;
  private String requestBy;
  private String requestId;
  private String customerRequestId;
  private String requestUrl;
  private String resultTopics;
  private Map<String, String> headers;
  private RequestResult requestResult;

  public CustomerResult() {
  }

  public CustomerResult(String resultTopics) {
    this.headers = new HashMap<>();
    this.resultTopics = resultTopics;
  }

  public void addHeader(String key, String value) {
    headers.put(key, value);
  }

  public void addHeaders(Map<String, String> headers) {
    if (headers == null) {
      return;
    }

    this.headers.putAll(headers);
  }

  public AppService getAppService() {
    return appService;
  }

  public void setAppService(AppService appService) {
    this.appService = appService;
  }

  public String getRequestBy() {
    return requestBy;
  }

  public void setRequestBy(String requestBy) {
    this.requestBy = requestBy;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getCustomerRequestId() {
    return customerRequestId;
  }

  public void setCustomerRequestId(String customerRequestId) {
    this.customerRequestId = customerRequestId;
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  public String getResultTopics() {
    return resultTopics;
  }

  public void setResultTopics(String resultTopics) {
    this.resultTopics = resultTopics;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public RequestResult getRequestResult() {
    return requestResult;
  }

  public void setRequestResult(RequestResult requestResult) {
    this.requestResult = requestResult;
  }

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public static class RequestResult {

    @JsonProperty("requestId")
    private String customerRequestId;

    @JsonProperty("responseId")
    private String responseId;

    @JsonProperty("status")
    private int status;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("idNo")
    private String idNo;

    @JsonProperty("description")
    private String description;

    @JsonProperty("result")
    private String result;

    public RequestResult() {
      this.status = -1;
    }

    public String getCustomerRequestId() {
      return customerRequestId;
    }

    public void setCustomerRequestId(String customerRequestId) {
      this.customerRequestId = customerRequestId;
    }

    public String getResponseId() {
      return responseId;
    }

    public void setResponseId(String responseId) {
      this.responseId = responseId;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public String getPhoneNumber() {
      return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      if (!StringUtils.isEmpty(this.phoneNumber)) {
        this.phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
      }
    }

    public String getIdNo() {
      return idNo;
    }

    public void setIdNo(String idNo) {
      this.idNo = idNo;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getResult() {
      return result;
    }

    public void setResult(String result) {
      this.result = result;
    }
  }
}
