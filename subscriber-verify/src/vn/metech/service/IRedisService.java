package vn.metech.service;

import vn.metech.redis.service.ICacheService;

public interface IRedisService extends ICacheService {

	void loadPartnersIntoRedis();

}
