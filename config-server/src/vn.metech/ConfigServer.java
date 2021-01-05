package vn.metech;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

@EnableConfigServer
@SpringBootApplication
public class ConfigServer implements ApplicationListener<EnvironmentChangeEvent> {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(ConfigServer.class, args);
	}

	@Override
	public void onApplicationEvent(EnvironmentChangeEvent event) {
		restart();
	}

	private static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(ConfigServer.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}
}
