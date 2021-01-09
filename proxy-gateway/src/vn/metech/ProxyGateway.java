package vn.metech;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@RefreshScope
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ProxyGateway implements ApplicationListener<EnvironmentChangeEvent> {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(ProxyGateway.class, args);
	}

	@Override
	public void onApplicationEvent(EnvironmentChangeEvent event) {
		restart();
	}

	private static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(ProxyGateway.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}
}
