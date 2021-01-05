package vn.metech.entity.base;

import org.springframework.util.Assert;
import vn.metech.util.HashUtils;
import vn.metech.util.JsonUtils;

public class Hashtable implements IHashtable {

	protected String secureHash;

	public String hash(String secureKey) {
		Assert.notNull(secureKey, "secureKey required");

		String requestedHash = secureHash;
		secureHash = null;
		String hashed = HashUtils.sha256(JsonUtils.toJson(this) + "|" + secureKey);
		secureHash = requestedHash;

		return hashed;
	}

	public boolean isHashMatch(String secureKey) {
		String hashed = hash(secureKey);

		return hashed.equalsIgnoreCase(secureHash);
	}

	@Override
	public String getSecureHash() {
		return secureHash;
	}

	@Override
	public void setSecureHash(String secureHash) {
		this.secureHash = secureHash;
	}
}
