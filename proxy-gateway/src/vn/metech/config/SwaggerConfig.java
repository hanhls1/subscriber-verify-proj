package vn.metech.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig implements SwaggerResourcesProvider {

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		resources.add(swaggerResource("default", "/v2/api-docs", "2.0"));
		resources.add(swaggerResource("auth-service", "/auth/v2/api-docs", "2.0"));
		resources.add(swaggerResource("kyc-02-service", "/kyc/02/v2/api-docs", "2.0"));
		resources.add(swaggerResource("location-verify-service", "/location-verify/v2/api-docs", "2.0"));
		resources.add(swaggerResource("id-reference-service", "/id-reference/v2/api-docs", "2.0"));
		resources.add(swaggerResource("ad-reference-service", "/ad-reference/v2/api-docs", "2.0"));
		resources.add(swaggerResource("call-reference-service", "/call-reference/v2/api-docs", "2.0"));
		resources.add(swaggerResource("tpc-service", "/tpc/v2/api-docs", "2.0"));

		return resources;
	}

	private SwaggerResource swaggerResource(String name, String url, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setUrl(url);
		swaggerResource.setSwaggerVersion(version);

		return swaggerResource;
	}


}
