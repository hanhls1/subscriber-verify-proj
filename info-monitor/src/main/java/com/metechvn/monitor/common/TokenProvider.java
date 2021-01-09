package com.metechvn.monitor.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

public class TokenProvider {

	private TokenProvider() {
		this.claims = new HashMap<>();
		this.exp = new Date();
		this.algorithm = SignatureAlgorithm.HS256;
	}

	private static TokenProvider _instance = new TokenProvider();

	public static TokenProvider getInstance() {
		if (_instance == null) {
			_instance = new TokenProvider();
		}

		return _instance;
	}

	private Map<String, Object> claims;
	private String base64SecretKey;
	private Date exp;
	private SignatureAlgorithm algorithm;

	public TokenProvider addClaim(String key, Object value) {
		_instance.claims.put(key, value);

		return this;
	}

	public TokenProvider expAfter(int seconds) {
		Calendar c = Calendar.getInstance();
		c.setTime(_instance.exp);
		c.add(Calendar.SECOND, seconds);
		_instance.exp = c.getTime();

		return this;
	}

	public TokenProvider signWith(String secretKey) {
		_instance.base64SecretKey = new String(
						Base64
										.getEncoder()
										.encode(secretKey.getBytes())
		);

		return this;
	}

	public TokenProvider signWithBase64(String base64SecretKey) {
		_instance.base64SecretKey = base64SecretKey;

		return this;
	}

	public String build() {
		return Jwts
						.builder()
						.setClaims(_instance.claims)
						.setExpiration(_instance.exp)
						.signWith(algorithm, _instance.base64SecretKey)
						.compact();
	}

}
