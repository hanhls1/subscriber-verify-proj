package vn.metech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import vn.metech.config.RibbonConfiguration;

@RibbonClient(
        name = "load-balancer",
        configuration = RibbonConfiguration.class
)
@EnableEurekaClient
@SpringBootApplication
public class LoadBalancer {
    public static void main(String[] args) {
        SpringApplication.run(LoadBalancer.class, args);
    }
}
