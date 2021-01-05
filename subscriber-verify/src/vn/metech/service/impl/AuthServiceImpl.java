package vn.metech.service.impl;

import io.jsonwebtoken.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.metech.builder.UserInfoBuilder;
import vn.metech.config.JwtProperties;
import vn.metech.constant.Auth;
import vn.metech.dto.response.UserLoginResponse;
import vn.metech.entity.User;
import vn.metech.exception.UnauthorizedException;
import vn.metech.exception.UserInvalidException;
import vn.metech.exception.UserNotFoundException;
import vn.metech.redis.util.RedisUtil;
import vn.metech.repository.IUserRepository;
import vn.metech.repository.IUserRoleRepository;
import vn.metech.repository.jpa.UserCrudRepository;
import vn.metech.repository.jpa.UserRoleCrudRepository;
import vn.metech.service.IAuthService;
import vn.metech.shared.AuthorizeRequest;
import vn.metech.shared.UserInfo;
import vn.metech.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements IAuthService {

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	private IUserRepository userRepository;
	private IUserRoleRepository userRoleRepository;
	private JwtProperties jwtProperties;
	private PasswordEncoder passwordEncoder;
	private RedisUtil<String> stringRedisUtil;
	private RedisUtil<UserInfo> userInfoRedisUtil;
	private UserCrudRepository userCrudRepository;
	private UserRoleCrudRepository userRoleCrudRepository;

	public AuthServiceImpl(
					IUserRepository userRepository,
					IUserRoleRepository userRoleRepository,
					JwtProperties jwtProperties,
					PasswordEncoder passwordEncoder,
					RedisUtil<String> stringRedisUtil,
					RedisUtil<UserInfo> userInfoRedisUtil,
					UserCrudRepository userCrudRepository,
					UserRoleCrudRepository userRoleCrudRepository) {
		this.jwtProperties = jwtProperties;
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
		this.stringRedisUtil = stringRedisUtil;
		this.userInfoRedisUtil = userInfoRedisUtil;
		this.userCrudRepository = userCrudRepository;
		this.userRoleCrudRepository = userRoleCrudRepository;
	}

	@Override
	public UserLoginResponse login(String username, String password, String partnerCode) throws UserInvalidException {
//		User user = userRepository.getUserByUsername(username);
		User user = userCrudRepository.getUserByUsername(username);
		if (user != null && !user.isLocked() && user.isActivated()) {
			if (passwordEncoder.matches(password, user.getPassword())) {
				UserLoginResponse userLoginResponse = new UserLoginResponse();
				userLoginResponse.setUserId(user.getId());
				userLoginResponse.setUsername(user.getUsername());
				userLoginResponse.setToken(generateToken(user));
				userLoginResponse.setPartnerCode(user.getPartner().getPartnerCode());
				userLoginResponse.setApiPaths(userRoleCrudRepository.getAccessPaths(user.getId()));

				return userLoginResponse;
			}
		}

		throw new UserInvalidException("Username or password is invalid");
	}

	@Override
	public String generateToken(User user) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		Key signingKey = new SecretKeySpec(
						jwtProperties.getSecretKey().getBytes(),
						signatureAlgorithm.getJcaName()
		);

		String jti = UUID.randomUUID().toString();

		JwtBuilder builder = Jwts.builder()
						.setId(jti)
						.setIssuedAt(now)
						.setSubject(user.getId())
						.setIssuer(jwtProperties.getIssuer())
						.setHeaderParam("typ", "JWT")
						.signWith(signatureAlgorithm, signingKey);

		String token = builder.compact();

		List<String> accessPaths = userRoleCrudRepository.getAccessPaths(user.getId());
		UserInfo userInfo = new UserInfoBuilder()
						.user(user)
						.accessPaths(accessPaths)
						.build();

		stringRedisUtil.putValue(Auth.Jwt.CACHE_TOKEN_KEY + jti, user.getId());
		userInfoRedisUtil.putValue(Auth.Jwt.CACHE_USER_KEY + user.getId(), userInfo);

		stringRedisUtil.setExpireInSeconds(Auth.Jwt.CACHE_TOKEN_KEY + jti, jwtProperties.getExp());
		userInfoRedisUtil.setExpireInSeconds(Auth.Jwt.CACHE_USER_KEY + user.getId(), jwtProperties.getExp());

		return token;
	}

	@Override
	public User parseToken(String token) throws UserNotFoundException {
		try {
			Jws<Claims> jwsClaims = Jwts.parser()
							.setSigningKey(StringUtils.toBase64(jwtProperties.getSecretKey()))
							.parseClaimsJws(token);
			Claims claims = jwsClaims.getBody();
			String userId = stringRedisUtil.getValue(Auth.Jwt.CACHE_TOKEN_KEY + claims.getId());
			if (!StringUtils.isEmpty(userId)) {
				UserInfo userInfo = userInfoRedisUtil.getValue(Auth.Jwt.CACHE_USER_KEY + userId);

				if (userInfo != null) {
					User user = userRepository.getById(claims.getSubject());
					if (user != null) {
						stringRedisUtil.setExpireInSeconds(
										Auth.Jwt.CACHE_TOKEN_KEY + claims.getId(),
										jwtProperties.getExp()
						);
						userInfoRedisUtil.setExpireInSeconds(
										Auth.Jwt.CACHE_USER_KEY + claims.getSubject(),
										jwtProperties.getExp()
						);

						return user;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		throw new UserNotFoundException("Cannot find user");
	}

	@Override
	public String isAccepted(AuthorizeRequest authRequest) throws UserNotFoundException, UnauthorizedException {
		User loggedUser = parseToken(authRequest.getToken());
		if (loggedUser == null) {
			return null;
		}

		if (userRoleRepository.isAccepted(authRequest.getPath(), loggedUser.getId())) {
			return loggedUser.getId();
		}

		throw new UnauthorizedException();
	}


}
