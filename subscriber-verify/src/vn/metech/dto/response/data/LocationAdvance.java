package vn.metech.dto.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.service.GMapService;
import vn.metech.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LocationAdvance {

    @JsonProperty("mostVisitedLocation")
    private List<MostVisitLocation> mostVisitLocations;

    @JsonProperty("regularlyLocationReport")
    private List<RegularlyLocation> regularlyLocations;

    public LocationAdvance() {
        this.mostVisitLocations = new ArrayList<>();
        this.regularlyLocations = new ArrayList<>();
    }

    public LocationAdvance(ConfirmInfoReceive confirmInfoReceive, GMapService gMapService) {
        this();
        MfsLocationAdvance mfsLocAdvance = JsonUtils.toObject(confirmInfoReceive.getData(), MfsLocationAdvance.class);
        if (mfsLocAdvance == null ||
                mfsLocAdvance.getMostVisitLocations() == null || mfsLocAdvance.getLocationFrequencies() == null ||
                mfsLocAdvance.getMostVisitLocations().isEmpty() || mfsLocAdvance.getLocationFrequencies().isEmpty()) {
            Map body = JsonUtils.toObject(confirmInfoReceive.getData(), Map.class);
            try {
                Map response = JsonUtils.convert(body.get("cac"), Map.class);
                mfsLocAdvance = JsonUtils.convert(response.get("data"), MfsLocationAdvance.class);
            } catch (Exception ignored) { }
        }
        if (mfsLocAdvance != null
                && mfsLocAdvance.getMostVisitLocations() != null && mfsLocAdvance.getLocationFrequencies() != null) {
            AtomicInteger counterTask = new AtomicInteger(mfsLocAdvance.getMostVisitLocations().size());
            for (MfsMostVisitLocation mfsMostVisitLocation : mfsLocAdvance.getMostVisitLocations()) {
                setMostVisitedLocationAsync(counterTask, mfsMostVisitLocation, gMapService);
            }
            for (MfsLocationFrequency locationFrequency : mfsLocAdvance.getLocationFrequencies()) {
                regularlyLocations.add(new RegularlyLocation(locationFrequency.getDay(), locationFrequency.locations()));
            }
            while (true) {
                if (counterTask.get() == 0) {
                    return;
                }
            }
        }

    }

    private void setMostVisitedLocationAsync(
            AtomicInteger counterTask, MfsMostVisitLocation mostVisitedLocation, GMapService gMapService) {
        if (gMapService == null) {
            return;
        }

        new Thread(() -> {
            String address = gMapService.getAddressOf(mostVisitedLocation.getLat(), mostVisitedLocation.getLng());
//			String address = "";
            mostVisitLocations.add(new MostVisitLocation(address, mostVisitedLocation.getPercent()));
            counterTask.decrementAndGet();
        }).start();
    }

    public List<MostVisitLocation> getMostVisitLocations() {
        return mostVisitLocations;
    }

    public void setMostVisitLocations(List<MostVisitLocation> mostVisitLocations) {
        this.mostVisitLocations = mostVisitLocations;
    }

    public List<RegularlyLocation> getRegularlyLocations() {
        return regularlyLocations;
    }

    public void setRegularlyLocations(List<RegularlyLocation> regularlyLocations) {
        this.regularlyLocations = regularlyLocations;
    }
}
