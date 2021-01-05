package vn.metech.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
						.allowedHeaders("*")
						.allowedOrigins("*")
						.allowedMethods("*");
	}

	@Bean
	public Docket api() {
		return new Docket(SWAGGER_2)
						.select()
						.paths(PathSelectors.any())
						.build().apiInfo(apiInfo())
						.securitySchemes(Lists.newArrayList(apiKey()))
						.securityContexts(Collections.singletonList(securityContext()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
						"Subscriber verify API",
						"",
						"1.0",
						"",
						new Contact("Thịnh Phát", "thinhphat.vn", "support@thinhtphat.vn"),
						null, null, Collections.emptyList());
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().scopeSeparator(",")
						.additionalQueryStringParams(null)
						.useBasicAuthenticationWithAccessCodeGrant(false).build();
	}

	private ApiKey apiKey() {
		return new ApiKey("apiKey", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
						.forPaths(PathSelectors.any()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Collections.singletonList(new SecurityReference("apiKey", authorizationScopes));
	}
}