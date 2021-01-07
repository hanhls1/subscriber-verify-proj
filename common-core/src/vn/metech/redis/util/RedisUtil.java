package vn.metech.redis.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUtil<T> {

	private RedisTemplate<String, T> redisTemplate;
	private HashOperations<String, String, T> hashOperation;
	private ValueOperations<String, T> valueOperations;

	public RedisUtil(RedisTemplate<String, T> redisTemplate) {
		Assert.notNull(redisTemplate, "redisTemplate is required");

		this.redisTemplate = redisTemplate;
		this.hashOperation = redisTemplate.opsForHash();
		this.valueOperations = redisTemplate.opsForValue();
	}

	public void putMap(String redisKey, String key, T data) {
		hashOperation.put(redisKey, key, data);
	}

	public T getMapAsSingleEntry(String redisKey, Object key) {
		return hashOperation.get(redisKey, key);
	}

	public Map<String, T> getMapAsAll(String redisKey) {
		return hashOperation.entries(redisKey);
	}

	public void putValue(String key, T value) {
		valueOperations.set(key, value);
	}

	public void putValueWithExpireTime(String key, T value, long timeout, TimeUnit unit) {
		valueOperations.set(key, value, timeout, unit);
	}

	public T getValue(String key) {
		return valueOperations.get(key);
	}

	public Boolean removeEntry(String key) {
		return redisTemplate.delete(key);
	}

	public void setExpire(String key, long timeout, TimeUnit unit) {
		redisTemplate.expire(key, timeout, unit);
	}

	public void setExpireInSeconds(String key, long seconds) {
		setExpire(key, seconds, TimeUnit.SECONDS);
	}

	public void setExpire(List<String> keys, long timeout, TimeUnit unit) {
		if (keys == null) {
			return;
		}

		for (String key : keys) {
			setExpire(key, timeout, unit);
		}
	}

	public void setExpireInMinutes(String key, long minutes) {
		setExpire(key, minutes, TimeUnit.MINUTES);
	}

	public void setExpireInHours(String key, long hours) {
		setExpire(key, hours, TimeUnit.HOURS);
	}
}