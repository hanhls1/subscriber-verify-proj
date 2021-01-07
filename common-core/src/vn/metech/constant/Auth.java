package vn.metech.constant;

public class Auth {
	private Auth() {
	}

	public static final String USER_KEY = "user-id";
	public static final String TOKEN_KEY = "user-token";
	public static final String IP_ADDRESS_KEY = "ip-address";
	public static final String AUTH_SERVICE = "auth-service";
	public static final String AUTH_CONTROLLER = "auth";
	public static final String AUTH_CHECK_PATH = "can-access";
	public static final String AUTHORIZATION_HEADER = "Authorization";

	public static class Jwt {
		public static final String TOKEN_PREFIX = "Bearer ";
		public static final String CACHE_USER_KEY = "auth:jwt:user:";
		public static final String CACHE_TOKEN_KEY = "auth:jwt:token:";
		public static final String CACHE_USER_INFO_KEY = "auth:jwt:user-info:";
	}
}
