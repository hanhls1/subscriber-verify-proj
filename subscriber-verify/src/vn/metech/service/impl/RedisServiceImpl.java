package vn.metech.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.constant.CacheKey;
import vn.metech.entity.Partner;
import vn.metech.entity.User;
import vn.metech.redis.util.RedisUtil;
import vn.metech.service.IRedisService;
import vn.metech.service.ISubPartnerService;
import vn.metech.service.IUserService;
import vn.metech.shared.PartnerInfo;

import java.util.List;
import java.util.concurrent.Executors;

@Service
@Transactional
public class RedisServiceImpl implements IRedisService {

	private IUserService userService;
	private ISubPartnerService subPartnerService;
	private RedisUtil<PartnerInfo> partnerInfoRedisUtil;

	public RedisServiceImpl(
					IUserService userService,
					ISubPartnerService subPartnerService,
					RedisUtil<PartnerInfo> partnerInfoRedisUtil) {
		this.userService = userService;
		this.subPartnerService = subPartnerService;
		this.partnerInfoRedisUtil = partnerInfoRedisUtil;
	}

	@Override
	public void refresh() {
		loadPartnersIntoRedis();
	}

	@Override
	public void clear() {

	}

	@Override
	public void loadPartnersIntoRedis() {
		Executors.newFixedThreadPool(1).submit(() -> {
			List<User> users = userService.getActivatedUserWithPartner();

			String key = null;

			for (User user : users) {
				Partner partner = user.getPartner();
				if (partner == null) {
					continue;
				}
				partnerInfoRedisUtil.putValue(
								CacheKey.Auth.partnerOf(user.getId()),
								new PartnerInfo(
												partner.getRedirectUrl(),
												partner.getUsername(),
												partner.getPassword(),
												subPartnerService.getCustomerCodesBy(partner.getId())
								)
				);
			}
		});
	}

}
