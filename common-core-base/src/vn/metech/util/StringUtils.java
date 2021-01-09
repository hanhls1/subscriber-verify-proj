package vn.metech.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

public class StringUtils extends org.springframework.util.StringUtils {

    private StringUtils() {
    }

    public static String toBase64(String str) {
        if (isEmpty(str)) {
            return null;
        }

        return new String(Base64.getEncoder().encode(str.getBytes()));
    }

    public static String fromBase64(String str) {
        if (isEmpty(str)) {
            return null;
        }

        return new String(Base64.getDecoder().decode(str.getBytes()));
    }

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static SecureRandom random = new SecureRandom();

    public static String randomString(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }

    public static String _3tId() {
        final String _3t = "3T";
        final String current = DateUtils.formatDate(new Date(), "yyMMddHHmmsss");

        return _3t + current + randomString(5);
    }
}
