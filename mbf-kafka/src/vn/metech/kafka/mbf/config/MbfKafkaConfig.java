package vn.metech.kafka.mbf.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;


@EnableKafka
@Configuration
@EnableConfigurationProperties(value = {
				MbfKafkaProperties.class
})
public class MbfKafkaConfig {

}
