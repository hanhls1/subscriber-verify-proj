package vn.metech.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class RestUtils {

	private static HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
		try {
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
							.loadTrustMaterial(null, (x509Certificates, s) -> true)
							.build();
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			requestFactory.setReadTimeout(60000);
			requestFactory.setConnectTimeout(60000);
			requestFactory.setConnectionRequestTimeout(60000);

			return requestFactory;
		} catch (Exception e) {
			return null;
		}
	}

	public static <Res> RestResponse<Res> exchange(
					String url, HttpMethod method, Map<String, String> headers,
					Object requestBody, Class<Res> resClass) {
		ResponseEntity<Res> resEntity;
		long begin = System.currentTimeMillis();
		String msg = "Request successfully";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
		try {
			if (headers != null) {
				headers.forEach((key, value) -> httpHeaders.put(key, Collections.singletonList(value)));
			}
			RequestEntity<Object> requestEntity = new RequestEntity<>(requestBody, httpHeaders, method, new URI(url));
			RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory());
			resEntity = restTemplate.exchange(requestEntity, resClass);
		} catch (Exception e) {
			msg = e.getMessage();
			resEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		long takeTime = System.currentTimeMillis() - begin;
		RestResponse<Res> restResponse = new RestResponse<>(resEntity, url, httpHeaders, msg, takeTime);
		restResponse.setRequestBody(requestBody);

		return restResponse;
	}

	public static <Res> RestResponse<Res> get(
					String url, Map<String, String> headers, Map<String, Object> params, Class<Res> resClass) {
		return exchange(urlWithQueryParams(url, params), HttpMethod.GET, headers, null, resClass);
	}

	public static <Res> RestResponse<Res> get(
					String url, Map<String, Object> params, Class<Res> resClass) {
		return exchange(urlWithQueryParams(url, params), HttpMethod.GET, null, null, resClass);
	}

	public static <Res> RestResponse<Res> post(
					String url, Object requestBody, Class<Res> resClass) {

		return post(url, null, requestBody, resClass);
	}

	public static <Res> RestResponse<Res> post(
					String url, Map<String, String> headers, Object requestBody, Class<Res> resClass) {

		return exchange(url, HttpMethod.POST, headers, requestBody, resClass);
	}

	private static String urlWithQueryParams(String url, Map<String, Object> params) {
		if (StringUtils.isEmpty(url) || params == null || params.isEmpty()) {
			return "";
		}
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(url);
		params.forEach(urlBuilder::queryParam);

		return urlBuilder.toUriString();
	}

	public static class RestResponse<Res> {

		private String requestUrl;
		private HttpHeaders requestHeaders;
		private Object requestBody;
		private HttpHeaders headers;
		private Res body;
		private HttpStatus httpStatus;
		private long takeTime;
		private String responseMsg;

		private RestResponse() {
			this.takeTime = 0;
			this.headers = new HttpHeaders();
			this.httpStatus = HttpStatus.OK;
		}

		public RestResponse(ResponseEntity<Res> resEntity, String responseMsg, long takeTime) {
			this();
			Assert.notNull(resEntity, "[resEntity] is null");

			this.body = resEntity.getBody();
			this.headers = resEntity.getHeaders();
			this.httpStatus = resEntity.getStatusCode();
			this.responseMsg = responseMsg;
			this.takeTime = takeTime;
		}

		public RestResponse(
						ResponseEntity<Res> resEntity, String requestUrl,
						String responseMsg, long takeTime) {
			this(resEntity, responseMsg, takeTime);
			this.requestUrl = requestUrl;
		}

		public RestResponse(
						ResponseEntity<Res> resEntity, String requestUrl,
						HttpHeaders requestHeaders, String responseMsg, long takeTime) {
			this(resEntity, requestUrl, responseMsg, takeTime);
			this.requestHeaders = requestHeaders;
		}

		public String getRequestUrl() {
			return requestUrl;
		}

		public void setRequestUrl(String requestUrl) {
			this.requestUrl = requestUrl;
		}

		public HttpHeaders getRequestHeaders() {
			return requestHeaders;
		}

		public void setRequestHeaders(HttpHeaders requestHeaders) {
			this.requestHeaders = requestHeaders;
		}

		public Object getRequestBody() {
			return requestBody;
		}

		public void setRequestBody(Object requestBody) {
			this.requestBody = requestBody;
		}

		public HttpHeaders getHeaders() {
			return headers;
		}

		public void setHeaders(HttpHeaders headers) {
			this.headers = headers;
		}

		public Res getBody() {
			return body;
		}

		public void setBody(Res body) {
			this.body = body;
		}

		public HttpStatus getHttpStatus() {
			return httpStatus;
		}

		public void setHttpStatus(HttpStatus httpStatus) {
			this.httpStatus = httpStatus;
		}

		public long getTakeTime() {
			return takeTime;
		}

		public void setTakeTime(long takeTime) {
			this.takeTime = takeTime;
		}

		public String getResponseMsg() {
			return responseMsg;
		}

		public void setResponseMsg(String responseMsg) {
			this.responseMsg = responseMsg;
		}
	}
}
