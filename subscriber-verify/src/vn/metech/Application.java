package vn.metech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vn.metech.service.IRedisService;

@RefreshScope
@EnableSwagger2
@EnableEurekaClient
@SpringBootApplication
@EnableJpaRepositories(value={"vn.metech.repository"})
public class Application implements ApplicationListener<EnvironmentChangeEvent>, CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private static ConfigurableApplicationContext context;
	private IRedisService authRedisService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		logger.info("debug enabled: {}", logger.isDebugEnabled());
		logger.trace("trace");
		logger.debug("Debugging - Debugging message");
		logger.info("Informational - Informational message");
		logger.warn("Warning - Warning condition");
		logger.error("Error - Error condition");

	}

	public Application(IRedisService authRedisService) {
		this.authRedisService = authRedisService;
	}

	@Override
	public void onApplicationEvent(EnvironmentChangeEvent event) {
		restart();
	}

	private static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(Application.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}

	@Override
	public void run(String... args) {
		authRedisService.loadPartnersIntoRedis();
	}
}
