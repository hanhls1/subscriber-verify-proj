package vn.metech.shared;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotFoundErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

		try {
			int statusCode = (int) webRequest.getAttribute("javax.servlet.error.status_code", 0);

			if (statusCode == 404) {
				webRequest.setAttribute("javax.servlet.error.status_code", 200, 0);
				Map<String, Object> errorAttributes = new HashMap<>();
				errorAttributes.put("statusCode", "NOT_FOUND");
				errorAttributes.put("httpStatusCode", 404);

				return errorAttributes;
			}
		} catch (Exception ignored) {		}

		return super.getErrorAttributes(webRequest, includeStackTrace);
	}
}
