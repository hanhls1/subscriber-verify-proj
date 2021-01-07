package vn.metech.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.util.Assert;

import vn.metech.dto.DwhTpcResponse;
import vn.metech.dto.DwhTpcSyncResponse;
import vn.metech.entity.InfoDiscoveryResponse;
import vn.metech.util.DateUtils;
import vn.metech.util.JsonUtils;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MtInfoDiscoveryResponse implements Serializable {

	private String responseId;
	private String idNumber;
	private Integer score1;
	private Integer score2;
	private Integer score3;
	private String createdDate;

	public MtInfoDiscoveryResponse(InfoDiscoveryResponse response) {
		Assert.notNull(response, "[response] is null");

		DwhTpcSyncResponse dwhResponse = JsonUtils
						.toObject(response.getResponseData(), DwhTpcSyncResponse.class);
		DwhTpcResponse data = JsonUtils.toObject(response.getResponseData(), DwhTpcResponse.class);
		if (dwhResponse != null && dwhResponse.getData() != null) {
			this.score1 = dwhResponse.getData().getScore1();
			this.score2 = dwhResponse.getData().getScore2();
			this.score3 = dwhResponse.getData().getScore3();
		}

		if (data != null) {
			this.score1 = data.getScore1();
			this.score2 = data.getScore2();
			this.score3 = data.getScore3();
		}

		this.responseId = response.getId();
		this.idNumber = response.getIdNumber();
		this.createdDate = DateUtils.formatDate(response.getCreatedDate());
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getScore1() {
		return score1;
	}

	public void setScore1(Integer score1) {
		this.score1 = score1;
	}

	public Integer getScore2() {
		return score2;
	}

	public void setScore2(Integer score2) {
		this.score2 = score2;
	}

	public Integer getScore3() {
		return score3;
	}

	public void setScore3(Integer score3) {
		this.score3 = score3;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
