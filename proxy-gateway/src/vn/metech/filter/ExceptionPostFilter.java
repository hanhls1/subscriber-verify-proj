package vn.metech.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import vn.metech.shared.BaseException;
import vn.metech.shared.ExceptionResult;
import vn.metech.util.JsonUtils;
import vn.metech.util.StringUtils;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Slf4j
public class ExceptionPostFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return -1;
	}

	@Override
	public boolean shouldFilter() {
		return RequestContext.getCurrentContext().get("baseException") != null;
	}

	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		try {
			baseException(requestContext);
		} catch (Exception ignored) {
			//log.error("error",ignored);
			ignored.printStackTrace();
			baseException(requestContext);
		}

		return null;
	}

	void baseException(RequestContext requestContext) {
		Object obj = requestContext.get("baseException");

		if (obj instanceof BaseException) {
			BaseException baseException = (BaseException) obj;

			requestContext.setSendZuulResponse(false);
			requestContext.getResponse().setContentType("application/json");
			requestContext.getResponse().setStatus(200);
			String result = JsonUtils.toJson(new ExceptionResult(baseException));
			try {
				requestContext
								.getResponse()
								.getWriter()
								.write(StringUtils.isEmpty(result) ? "" : result);
			} catch (Exception ignored) {
				ignored.printStackTrace();
//				log.error("error",ignored);

			}
		}
	}
}
