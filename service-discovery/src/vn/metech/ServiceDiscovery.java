package vn.metech;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

@EnableEurekaServer
@SpringBootApplication
public class ServiceDiscovery implements ApplicationListener<EnvironmentChangeEvent> {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(ServiceDiscovery.class, args);
	}

	@Override
	public void onApplicationEvent(EnvironmentChangeEvent event) {
		restart();
	}

	private static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(ServiceDiscovery.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}
}
