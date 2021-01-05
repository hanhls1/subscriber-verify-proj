package vn.metech.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import vn.metech.filter.AuthorizationFilter;
import vn.metech.filter.ExceptionIgnoredFilter;
import vn.metech.filter.ExceptionPostFilter;

@Configuration
public class ProxyConfig {

	private String zuulIgnoreAuthorizedRoutes;

	public ProxyConfig(
					@Value("${zuul.ignore-authorize.routes}") String zuulIgnoreAuthorizedRoutes) {
		this.zuulIgnoreAuthorizedRoutes = zuulIgnoreAuthorizedRoutes;
	}

	@Bean
	public ExceptionPostFilter exceptionPostFilter() {
		return new ExceptionPostFilter();
	}

	@Bean
	public AuthorizationFilter authorizationFilter(LoadBalancerClient loadBalancerClient) {
		return new AuthorizationFilter(loadBalancerClient, zuulIgnoreAuthorizedRoutes);
	}

	@Bean
	public ExceptionIgnoredFilter exceptionIgnoredFilter() {
		return new ExceptionIgnoredFilter();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
