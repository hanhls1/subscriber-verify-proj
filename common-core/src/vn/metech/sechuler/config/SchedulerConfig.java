package vn.metech.sechuler.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableSchedulerLock(
				defaultLockAtMostFor = "${schedule.default-lock-at-most:PT1M}",
				defaultLockAtLeastFor = "${schedule.default-lock-at-least:PT10S}"
)
public class SchedulerConfig {

	private final String subscriberVerifyShedLockEVN = "vn:metech:subscriber-verify:shedlock:env:";

	@Bean
	public LockProvider lockProvider(RedisConnectionFactory redisConnectionFactory) {
		return new RedisLockProvider(redisConnectionFactory, subscriberVerifyShedLockEVN);
	}

}
