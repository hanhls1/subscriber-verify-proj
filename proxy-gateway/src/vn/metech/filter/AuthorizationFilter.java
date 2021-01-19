package vn.metech.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.RestTemplate;
import vn.metech.constant.Auth;
import vn.metech.constant.StatusCode;
import vn.metech.exception.ForbiddenException;
import vn.metech.exception.UnauthorizedException;
import vn.metech.shared.ActionResult;
import vn.metech.shared.AuthorizeRequest;
import vn.metech.shared.ExceptionResult;
import vn.metech.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
@Slf4j
public class AuthorizationFilter extends ZuulFilter {

	private LoadBalancerClient loadBalancerClient;
	private RestTemplate restTemplate;
	private PathMatcher pathMatcher;
	private String ignorePaths;

	private AuthorizationFilter() {
		this.pathMatcher = new AntPathMatcher();
		this.restTemplate = new RestTemplate();
	}

	public AuthorizationFilter(
					LoadBalancerClient loadBalancerClient,
					String ignorePaths) {
		this();
		this.loadBalancerClient = loadBalancerClient;
		this.ignorePaths = ignorePaths;
	}

	private boolean isIgnorePaths(String requestPath) {
		if (StringUtils.isEmpty(ignorePaths)) {
			return false;
		}

		String[] paths = ignorePaths.split(",");
		for (String path : paths) {
			if (pathMatcher.match(path, requestPath)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		try {
			HttpServletRequest httpServletRequest = requestContext.getRequest();
			String requestUrl = httpServletRequest.getRequestURI();
			String remoteAddr = httpServletRequest.getRemoteAddr();
			String userId, token;

			if (isIgnorePaths(requestUrl)) {
				return null;
			}

			final String authHeader = httpServletRequest.getHeader(Auth.AUTHORIZATION_HEADER);
			if (StringUtils.isEmpty(authHeader) || !authHeader.contains(Auth.Jwt.TOKEN_PREFIX)) {
				forbiddenException(requestContext);

				return null;
			}

			token = authHeader.substring(Auth.Jwt.TOKEN_PREFIX.length());
			String url = authorizeURL();

			if (StringUtils.isEmpty(token) || StringUtils.isEmpty(url)) {
				forbiddenException(requestContext);

				return null;
			}

			ActionResult actionResult = restTemplate
							.postForEntity(url,
											new AuthorizeRequest(requestUrl, token, remoteAddr),
											ActionResult.class
							).getBody();

			if (actionResult == null || StringUtils.isEmpty(actionResult.getStatusCode())) {
				forbiddenException(requestContext);
			}

			if (actionResult.getStatusCode().equalsIgnoreCase(StatusCode.User.USER_INVALID)) {
				forbiddenException(requestContext);
			} else if (actionResult.getStatusCode().equalsIgnoreCase(StatusCode.Auth.UN_AUTHORIZED)) {
				unauthorizedException(requestContext);
			} else if (actionResult.getStatusCode().equalsIgnoreCase(StatusCode.SUCCESS)) {
				requestContext.addZuulRequestHeader(Auth.USER_KEY, String.valueOf(actionResult.getResult()));
				requestContext.addZuulRequestHeader(Auth.TOKEN_KEY, token);
				requestContext.addZuulRequestHeader(Auth.IP_ADDRESS_KEY, remoteAddr);
			} else {
				forbiddenException(requestContext);
			}
		} catch (Exception e) {
//			log.error("error",e);
			e.printStackTrace();
			forbiddenException(requestContext);
		}

		return null;
	}

	private String authorizeURL() {
		String SUBSCRIBER_VERIFY=  "subscriber-verify";
		ServiceInstance authService = loadBalancerClient.choose(SUBSCRIBER_VERIFY);
		if (authService == null) {
			return null;
		}


		return authService.getUri().toString() +
						"/" +
						Auth.AUTH_CONTROLLER +
						"/" +
						Auth.AUTH_CHECK_PATH;
	}

	private void forbiddenException(RequestContext requestContext) {
		String responseJson = "";
		try {
			responseJson = new ObjectMapper()
							.writeValueAsString(new ExceptionResult(new ForbiddenException("forbidden")));
		} catch (Exception ignored) {
		}
		requestContext.setSendZuulResponse(false);
		requestContext.setResponseStatusCode(200);
		requestContext.getResponse().setContentType("application/json");
		requestContext.setResponseBody(responseJson);
	}

	private void unauthorizedException(RequestContext requestContext) {
		String responseJson = "";
		try {
			responseJson = new ObjectMapper()
							.writeValueAsString(new ExceptionResult(new UnauthorizedException("un_authorized")));
		} catch (Exception ignored) {
			ignored.printStackTrace();
//			log.error("error",ignored);

		}
		requestContext.setSendZuulResponse(false);
		requestContext.setResponseStatusCode(200);
		requestContext.getResponse().setContentType("application/json");
		requestContext.setResponseBody(responseJson);
	}
}
