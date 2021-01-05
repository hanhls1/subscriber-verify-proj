package vn.metech.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonInclude(NON_EMPTY)
public class MbfAddress {

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lng")
    private String lng;

    @JsonProperty("r")
    private Integer radius;

    public MbfAddress() {
    }

    public MbfAddress(String lat, String lng, Integer radius) {
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }
}
