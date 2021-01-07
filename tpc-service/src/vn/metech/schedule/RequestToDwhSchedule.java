package vn.metech.schedule;

import net.javacrumbs.shedlock.core.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vn.metech.constant.RequestStatus;
import vn.metech.constant.ResponseStatus;
import vn.metech.entity.InfoDiscoveryRequest;
import vn.metech.entity.InfoDiscoveryResponse;
import vn.metech.repository.InfoDiscoveryRequestCrudRepository;
import vn.metech.repository.InfoDiscoveryResponseCrudRepository;
import vn.metech.service.AIOService;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.util.Date;

@Component
public class RequestToDwhSchedule {

    private final String CRON_KEY = "${dwh.request.cron:30 * * * * *}";
    private final String SERVICE_NAME = "RequestToDwhSchedule > ";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AIOService aioService;
    private InfoDiscoveryRequestCrudRepository infoDiscoveryRequestCrudRepository;
    private InfoDiscoveryResponseCrudRepository infoDiscoveryResponseCrudRepository;

    public RequestToDwhSchedule(
            AIOService aioService,
            InfoDiscoveryRequestCrudRepository infoDiscoveryRequestCrudRepository,
            InfoDiscoveryResponseCrudRepository infoDiscoveryResponseCrudRepository) {
        this.aioService = aioService;
        this.infoDiscoveryRequestCrudRepository = infoDiscoveryRequestCrudRepository;
        this.infoDiscoveryResponseCrudRepository = infoDiscoveryResponseCrudRepository;
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "tpc.sendRequestToDwh.locker")
    public void sendRequestToDwh() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<InfoDiscoveryRequest> infoDiscoveryRequests = infoDiscoveryRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.PENDING, date, pageable);
        while (!infoDiscoveryRequests.isEmpty()) {

            for (InfoDiscoveryRequest infoDiscoveryRequest : infoDiscoveryRequests) {
                int statusCode = aioService.sendInfoDiscoveryRequestToAIO(infoDiscoveryRequest);
                if (statusCode == 0) {
                    infoDiscoveryRequest.setRequestStatus(RequestStatus.QUESTION_SENT);
                    infoDiscoveryRequest.getInfoDiscoveryResponse().setResponseStatus(ResponseStatus.QUESTION_SENT);
                    infoDiscoveryRequestCrudRepository.save(infoDiscoveryRequest);
                    infoDiscoveryResponseCrudRepository.save(infoDiscoveryRequest.getInfoDiscoveryResponse());
                } else if (statusCode == 501) {
                    infoDiscoveryRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
                    infoDiscoveryRequest.getInfoDiscoveryResponse().setResponseStatus(ResponseStatus.CLOSED);
                    infoDiscoveryRequestCrudRepository.save(infoDiscoveryRequest);
                    infoDiscoveryResponseCrudRepository.save(infoDiscoveryRequest.getInfoDiscoveryResponse());
                } else if (statusCode == 1) {
                    infoDiscoveryRequest.setRequestStatus(RequestStatus.PARAM_INVALID);
                    infoDiscoveryRequest.getInfoDiscoveryResponse().setResponseStatus(ResponseStatus.CLOSED);
                    infoDiscoveryRequestCrudRepository.save(infoDiscoveryRequest);
                    infoDiscoveryResponseCrudRepository.save(infoDiscoveryRequest.getInfoDiscoveryResponse());
                } else {
                    infoDiscoveryRequest.setLastFetch(new Date());
                    infoDiscoveryRequest.setFetchTimes(infoDiscoveryRequest.getFetchTimes() + 1);
                    infoDiscoveryRequestCrudRepository.save(infoDiscoveryRequest);
                }
            }

            if (!infoDiscoveryRequests.hasNext()) break;
            infoDiscoveryRequests = infoDiscoveryRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.PENDING, date, infoDiscoveryRequests.nextPageable());
        }
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "tpc.fetchResponse.locker")
    public void fetchResponse() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<InfoDiscoveryRequest> infoDiscoveryRequests = infoDiscoveryRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, pageable);
        while (!infoDiscoveryRequests.isEmpty()) {

            for (InfoDiscoveryRequest infoDiscoveryRequest : infoDiscoveryRequests) {
                String jsonData = aioService.fetchInfoDiscoveryResponseFromAIO(infoDiscoveryRequest.getId());
                if (StringUtils.isEmpty(jsonData)) {

                    if (infoDiscoveryRequest.getCreatedDate().getTime() - System.currentTimeMillis() > 2 * 24 * 3600 * 1000) {
                        infoDiscoveryRequest.setLastFetch(new Date());
                        infoDiscoveryRequest.setRequestStatus(RequestStatus.TIMEOUT);
                        infoDiscoveryRequestCrudRepository.save(infoDiscoveryRequest);
                        continue;
                    } else {
                        infoDiscoveryRequest.setLastFetch(new Date());
                        infoDiscoveryRequestCrudRepository.save(infoDiscoveryRequest);
                        continue;
                    }
                }
                InfoDiscoveryResponse infoDiscoveryResponse = infoDiscoveryRequest.getInfoDiscoveryResponse();
                if (infoDiscoveryResponse == null) {
                    infoDiscoveryResponse = new InfoDiscoveryResponse(infoDiscoveryRequest);
                }
                infoDiscoveryResponse.setResponseData(jsonData);
                infoDiscoveryResponse.setResponseStatus(ResponseStatus.ANSWER_RECEIVED);
                infoDiscoveryResponseCrudRepository.save(infoDiscoveryResponse);

                infoDiscoveryRequest.setRequestStatus(RequestStatus.ANSWER_RECEIVED);
                infoDiscoveryRequestCrudRepository.save(infoDiscoveryRequest);
            }

            if (!infoDiscoveryRequests.hasNext()) break;
            infoDiscoveryRequests = infoDiscoveryRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, infoDiscoveryRequests.nextPageable());
        }
    }
}
