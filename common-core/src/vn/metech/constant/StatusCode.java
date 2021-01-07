package vn.metech.constant;

public class StatusCode {
	private StatusCode() {

	}

	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	public static final String NOT_FOUND = "NOT_FOUND";
	public static final String INPUT_ERROR = "INPUT_ERROR";

	public static class Auth {
		private Auth() {
		}

		public static final String UN_AUTHORIZED = "UN_AUTHORIZED";
		public static final String FORBIDDEN = "FORBIDDEN";
	}

	public static class User {
		private User() {
		}

		public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
		public static final String USER_INVALID = "USER_INVALID";
		public static final String USER_EXISTING = "USER_EXISTING";
		public static final String PASSWORD_NOT_MATCH = "PASSWORD_NOT_MATCH";
	}

	public static class Partner {
		private Partner() {
		}

		public static final String PARTNER_NOT_FOUND = "PARTNER_NOT_FOUND";
	}

	public static class LocationVerify {
		private LocationVerify() {
		}

		public static final String REQUEST_DUPLICATE = "REQUEST_DUPLICATE";
		public static final String CUSTOMER_CODE_NOT_FOUND = "CUSTOMER_CODE_NOT_FOUND";
	}

	public static class IdReference {
		private IdReference() {
		}

		public static final String REQUEST_DUPLICATE = "REQUEST_DUPLICATE";
		public static final String CUSTOMER_CODE_NOT_FOUND = "CUSTOMER_CODE_NOT_FOUND";
	}

	public static class AdReference {
		private AdReference() {
		}

		public static final String REQUEST_DUPLICATE = "REQUEST_DUPLICATE";
		public static final String CUSTOMER_CODE_NOT_FOUND = "CUSTOMER_CODE_NOT_FOUND";
	}

	public static class CallReference {
		private CallReference() {
		}

		public static final String REQUEST_DUPLICATE = "REQUEST_DUPLICATE";
		public static final String CUSTOMER_CODE_NOT_FOUND = "CUSTOMER_CODE_NOT_FOUND";
	}

	public static class Kyc02 {
		private Kyc02() {
		}

		public static final String NO_RESULTS_YET = "NO_HAVE_RESULT";
		public static final String REQUEST_DUPLICATE = "REQUEST_DUPLICATE";
	}

	public static class Balance {
		private Balance() {
		}

		public static final String BALANCE_NOT_FOUND = "BALANCE_NOT_FOUND";
		public static final String BALANCE_NOT_ENOUGH = "BALANCE_NOT_ENOUGH";
	}

	public static class BalanceTransaction {
		private BalanceTransaction() {
		}

		public static final String TRANSACTION_INVALID = "TRANSACTION_INVALID";
	}

	public static class Tariff {
		private Tariff() {
		}

		public static final String TARIFF_NOT_FOUND = "TARIFF_NOT_FOUND";
	}
}
