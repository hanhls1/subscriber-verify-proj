package vn.metech.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GarbageCollectSchedule {

    @Scheduled(cron = "0 * * * * *")
    public void clearGC() {
        System.gc();
    }

}
