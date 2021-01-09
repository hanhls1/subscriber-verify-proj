package vn.metech.constant;

public class Regex {
	public static final String PHONE_NUMBER = "(0)?[1-9][0-9]{8}";

	private Regex() {
	}

	public static final String DD_MM_YYYY = "((0[1-9])|(1[0-2]))[\\/-]((0[1-9])|(1[0-9])|(2[0-9])|(3[0-1]))[\\/-](\\d{4})";
	public static final String YYYY_MM_DD = "(\\d{4})[\\/-]((0[1-9])|(1[0-9])|(2[0-9])|(3[0-1]))[\\/-]((0[1-9])|(1[0-2]))";
	public static final String PRICE = "\\d{1,3}(,?\\d{3})*(\\.\\d{1,2})?";
	public static final String NUMBER_WITH_2_DIGIT = "^[0-9]{1,2}$";

}
