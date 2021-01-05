package vn.metech.dto.response.fc03;

import com.fasterxml.jackson.annotation.JsonInclude;
import vn.metech.dto.response.data.LocationAdvance;
import vn.metech.entity.ConfirmInfoReceive;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KYC03Response extends LocationAdvance {

    public KYC03Response() {
    }

    public KYC03Response(ConfirmInfoReceive confirmInfoReceive) {
        super(confirmInfoReceive, null);
        this.setRegularlyLocations(null);
    }
}