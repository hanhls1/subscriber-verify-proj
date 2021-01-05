package vn.metech.constant;

public class CacheKey {
	private CacheKey() {
	}


	public static class Auth {
		private Auth() {
		}

		private static final String PARTNER_INFO_CACHE_KEY = "auth:partner-info:user:";

		public static String partnerOf(String userId) {
			return PARTNER_INFO_CACHE_KEY + userId;
		}
	}
}
