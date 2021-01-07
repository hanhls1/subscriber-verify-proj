package vn.metech;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@RefreshScope
@EnableSwagger2
@EnableEurekaClient
@SpringBootApplication
public class TPCServiceApplication
				implements ApplicationListener<EnvironmentChangeEvent> {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(TPCServiceApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
						.select()
						.apis(RequestHandlerSelectors.basePackage("vn.metech.controller"))
						.build()
						.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
						"Subscriber verify API",
						"",
						"1.0.0",
						"",
						new Contact("Thịnh Phát", "thinhphat.vn", "support@thinhtphat.vn"),
						null, null, Collections.emptyList());
	}

	@Override
	public void onApplicationEvent(EnvironmentChangeEvent event) {
		restart();
	}

	private static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(TPCServiceApplication.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}

}
