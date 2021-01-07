package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DwhTpcResponse implements Serializable {

    @JsonProperty("nationalId")
    private String idNumber;

    @JsonProperty("score1")
    private Integer score1;

    @JsonProperty("score2")
    private Integer score2;

    @JsonProperty("score3")
    private Integer score3;

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
}
