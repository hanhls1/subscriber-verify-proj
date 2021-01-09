package vn.metech.util;

import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

	private HashUtils() {
	}

	public static String sha256(String message) {
		Assert.notNull(message, "[message] is null");
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}

			digest = sb.toString().toUpperCase();
		} catch (NoSuchAlgorithmException ex) {
			System.out.println(ex.getMessage());
		}

		return digest;
	}

	public static String mbfSecureHash(
					String accountCode, String phoneNumber, String requestId, String secureCode) {
		Assert.notNull(accountCode, "[accountCode] is null");
		Assert.notNull(phoneNumber, "[phoneNumber] is null");
		Assert.notNull(requestId, "[requestId] is null");
		Assert.notNull(secureCode, "[secureCode] is null");

		String hashMsg = accountCode + "|" + phoneNumber + "|" + requestId + "|" + secureCode;

		return sha256(hashMsg);
	}
}
