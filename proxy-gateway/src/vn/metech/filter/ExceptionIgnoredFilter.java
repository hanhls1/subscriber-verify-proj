package vn.metech.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.HttpStatus;
import vn.metech.constant.StatusCode;
import vn.metech.shared.BaseException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

public class ExceptionIgnoredFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		return -1;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext();

		return requestContext.getThrowable() != null
						&& !requestContext.getBoolean("sendErrorFilter.ran", false);
	}

	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		requestContext.set("sendErrorFilter.ran", true);
		requestContext.set("baseException", new BaseException("Have an error while process!") {

			@Override
			public String getStatusCode() {
				return StatusCode.ERROR;
			}

			@Override
			public HttpStatus getHttpStatusCode() {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}

		});

		return null;
	}
}
