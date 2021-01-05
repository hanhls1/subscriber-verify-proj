package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.Assert;
import vn.metech.util.HashUtils;
import vn.metech.util.JsonUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HashResponseBase implements Serializable {

	protected String secureHash;

	public String hash(String secureKey) {
		Assert.notNull(secureKey, "secureKey required");

		return HashUtils.sha256(JsonUtils.toJson(this) + "|" + secureKey);
	}
}
