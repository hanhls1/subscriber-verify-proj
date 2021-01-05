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
import vn.metech.entity.*;
import vn.metech.repository.jpa.*;
import vn.metech.service.AIOService;
import vn.metech.util.DateUtils;
import vn.metech.util.StringUtils;

import java.util.Date;

@Component
public class RequestToMbfSchedule {

    private final String CRON_KEY = "${mbf.request.cron:30 * * * * *}";
    private final String SERVICE_NAME = "RequestToMbfSchedule > ";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AIOService aioService;
    private AdRequestCrudRepository adRequestCrudRepository;
    private AdResponseCrudRepository adResponseCrudRepository;
    private CallRequestCrudRepository callRequestCrudRepository;
    private CallResponseCrudRepository callResponseCrudRepository;
    private IdRequestCrudRepository idRequestCrudRepository;
    private IdResponseCrudRepository idResponseCrudRepository;
    private LocationRequestCrudRepository locationRequestCrudRepository;
    private LocationResponseCrudRepository locationResponseCrudRepository;


    public RequestToMbfSchedule(
            AIOService aioService,
            AdRequestCrudRepository adRequestCrudRepository,
            AdResponseCrudRepository adResponseCrudRepository,
            CallRequestCrudRepository callRequestCrudRepository,
            CallResponseCrudRepository callResponseCrudRepository,
            IdRequestCrudRepository idRequestCrudRepository,
            IdResponseCrudRepository idResponseCrudRepository,
            LocationRequestCrudRepository locationRequestCrudRepository,
            LocationResponseCrudRepository locationResponseCrudRepository) {
        this.aioService = aioService;
        this.adRequestCrudRepository = adRequestCrudRepository;
        this.adResponseCrudRepository = adResponseCrudRepository;
        this.callRequestCrudRepository = callRequestCrudRepository;
        this.callResponseCrudRepository = callResponseCrudRepository;
        this.idRequestCrudRepository = idRequestCrudRepository;
        this.idResponseCrudRepository = idResponseCrudRepository;
        this.locationRequestCrudRepository = locationRequestCrudRepository;
        this.locationResponseCrudRepository = locationResponseCrudRepository;
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "ad.ref.sendRequestToMbf.locker")
    public void sendRequestToMbf() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<AdRequest> adRequests = adRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.PENDING, date, pageable);
        while (!adRequests.isEmpty()) {

            for (AdRequest adRequest : adRequests) {
                int statusCode = aioService.sendAdRequestToAIO(adRequest);
                if (statusCode == 0) {
                    adRequest.setRequestStatus(RequestStatus.QUESTION_SENT);
                    adRequest.getAdResponse().setResponseStatus(ResponseStatus.QUESTION_SENT);
                    adRequestCrudRepository.save(adRequest);
                    adResponseCrudRepository.save(adRequest.getAdResponse());
                } else if (statusCode == 501) {
                    adRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
                    adRequest.getAdResponse().setResponseStatus(ResponseStatus.CLOSED);
                    adRequestCrudRepository.save(adRequest);
                    adResponseCrudRepository.save(adRequest.getAdResponse());
                } else if (statusCode == 1) {
                    adRequest.setRequestStatus(RequestStatus.PARAM_INVALID);
                    adRequest.getAdResponse().setResponseStatus(ResponseStatus.CLOSED);
                    adRequestCrudRepository.save(adRequest);
                    adResponseCrudRepository.save(adRequest.getAdResponse());
                } else {
                    adRequest.setLastFetch(new Date());
                    adRequest.setFetchTimes(adRequest.getFetchTimes() + 1);
                    adRequestCrudRepository.save(adRequest);
                }
            }

            if (!adRequests.hasNext()) break;
            adRequests = adRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.PENDING, date, adRequests.nextPageable());
        }
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "call.ref.sendRequestToMbf.locker")
    public void sendCallRequestToMbf() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<CallRequest> callRequests = callRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.PENDING, date, pageable);
        while (!callRequests.isEmpty()) {

            for (CallRequest callRequest : callRequests) {
                int statusCode = aioService.sendCallRequestToAIO(callRequest);
                if (statusCode == 0) {
                    callRequest.setRequestStatus(RequestStatus.QUESTION_SENT);
                    callRequest.getCallResponse().setResponseStatus(ResponseStatus.QUESTION_SENT);
                    callRequestCrudRepository.save(callRequest);
                    callResponseCrudRepository.save(callRequest.getCallResponse());
                } else if (statusCode == 501) {
                    callRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
                    callRequest.getCallResponse().setResponseStatus(ResponseStatus.CLOSED);
                    callRequestCrudRepository.save(callRequest);
                    callResponseCrudRepository.save(callRequest.getCallResponse());
                } else if (statusCode == 1) {
                    callRequest.setRequestStatus(RequestStatus.PARAM_INVALID);
                    callRequest.getCallResponse().setResponseStatus(ResponseStatus.CLOSED);
                    callRequestCrudRepository.save(callRequest);
                    callResponseCrudRepository.save(callRequest.getCallResponse());
                } else {
                    callRequest.setLastFetch(new Date());
                    callRequest.setFetchTimes(callRequest.getFetchTimes() + 1);
                    callRequestCrudRepository.save(callRequest);
                }
            }

            if (!callRequests.hasNext()) break;
            callRequests = callRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.PENDING, date, callRequests.nextPageable());
        }
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "id.ref.sendRequestToMbf.locker")
    public void sendIdRequestToMbf() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<IdRequest> idRequests = idRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.PENDING, date, pageable);
        while (!idRequests.isEmpty()) {

            for (IdRequest idRequest : idRequests) {
                int statusCode = aioService.sendIdRequestToAIO(idRequest);
                if (statusCode == 0) {
                    idRequest.setRequestStatus(RequestStatus.QUESTION_SENT);
                    idRequest.getIdResponse().setResponseStatus(ResponseStatus.QUESTION_SENT);
                    idRequestCrudRepository.save(idRequest);
                    idResponseCrudRepository.save(idRequest.getIdResponse());
                } else if (statusCode == 501) {
                    idRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
                    idRequest.getIdResponse().setResponseStatus(ResponseStatus.CLOSED);
                    idRequestCrudRepository.save(idRequest);
                    idResponseCrudRepository.save(idRequest.getIdResponse());
                } else if (statusCode == 1) {
                    idRequest.setRequestStatus(RequestStatus.PARAM_INVALID);
                    idRequest.getIdResponse().setResponseStatus(ResponseStatus.CLOSED);
                    idRequestCrudRepository.save(idRequest);
                    idResponseCrudRepository.save(idRequest.getIdResponse());
                } else {
                    idRequest.setLastFetch(new Date());
                    idRequest.setFetchTimes(idRequest.getFetchTimes() + 1);
                    idRequestCrudRepository.save(idRequest);
                }
            }

            if (!idRequests.hasNext()) break;
            idRequests = idRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.PENDING, date, idRequests.nextPageable());
        }
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "location.ref.sendRequestToMbf.locker")
    public void sendLocalRequestToMbf() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<LocationRequest> locationRequests = locationRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.PENDING, date, pageable);
        while (!locationRequests.isEmpty()) {

            for (LocationRequest locationRequest : locationRequests) {
                int statusCode = aioService.sendLocationRequestToAIO(locationRequest);
                if (statusCode == 0) {
                    locationRequest.setRequestStatus(RequestStatus.QUESTION_SENT);
                    locationRequest.getLocationResponse().setResponseStatus(ResponseStatus.QUESTION_SENT);
                    locationRequestCrudRepository.save(locationRequest);
                    locationResponseCrudRepository.save(locationRequest.getLocationResponse());
                } else if (statusCode == 501) {
                    locationRequest.setRequestStatus(RequestStatus.REQUEST_NOT_SEND);
                    locationRequest.getLocationResponse().setResponseStatus(ResponseStatus.CLOSED);
                    locationRequestCrudRepository.save(locationRequest);
                    locationResponseCrudRepository.save(locationRequest.getLocationResponse());
                } else if (statusCode == 1) {
                    locationRequest.setRequestStatus(RequestStatus.PARAM_INVALID);
                    locationRequest.getLocationResponse().setResponseStatus(ResponseStatus.CLOSED);
                    locationRequestCrudRepository.save(locationRequest);
                    locationResponseCrudRepository.save(locationRequest.getLocationResponse());
                } else {
                    locationRequest.setLastFetch(new Date());
                    locationRequest.setFetchTimes(locationRequest.getFetchTimes() + 1);
                    locationRequestCrudRepository.save(locationRequest);
                }
            }

            if (!locationRequests.hasNext()) break;
            locationRequests = locationRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.PENDING, date, locationRequests.nextPageable());
        }
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "location.ref.fetchResponse.locker")
    public void fetchLocalResponse() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<LocationRequest> locationRequests = locationRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, pageable);
        while (!locationRequests.isEmpty()) {

            for (LocationRequest locationRequest : locationRequests) {
                String jsonData = aioService.fetchResponseFromAIO(locationRequest.getId());
                if (StringUtils.isEmpty(jsonData)) {

                    if (locationRequest.getCreatedDate().getTime() - System.currentTimeMillis() > 2 * 24 * 3600 * 1000) {
                        locationRequest.setLastFetch(new Date());
                        locationRequest.setRequestStatus(RequestStatus.TIMEOUT);
                        locationRequestCrudRepository.save(locationRequest);
                        continue;
                    } else {
                        locationRequest.setLastFetch(new Date());
                        locationRequestCrudRepository.save(locationRequest);
                        continue;
                    }
                }
                LocationResponse locationResponse = locationRequest.getLocationResponse();
                if (locationResponse == null) {
                    locationResponse = new LocationResponse(locationRequest);
                }
                locationResponse.setResponseData(jsonData);
                locationResponse.setResponseStatus(ResponseStatus.ANSWER_RECEIVED);
                locationResponseCrudRepository.save(locationResponse);

                locationRequest.setRequestStatus(RequestStatus.ANSWER_RECEIVED);
                locationRequestCrudRepository.save(locationRequest);
            }

            if (!locationRequests.hasNext()) break;
            locationRequests = locationRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, locationRequests.nextPageable());
        }
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "call.ref.fetchResponse.locker")
    public void fetchCallResponse() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<CallRequest> callRequests = callRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, pageable);
        while (!callRequests.isEmpty()) {

            for (CallRequest callRequest : callRequests) {
                String jsonData = aioService.fetchResponseFromAIO(callRequest.getId());
                if (StringUtils.isEmpty(jsonData)) {

                    if (callRequest.getCreatedDate().getTime() - System.currentTimeMillis() > 2 * 24 * 3600 * 1000) {
                        callRequest.setLastFetch(new Date());
                        callRequest.setRequestStatus(RequestStatus.TIMEOUT);
                        callRequestCrudRepository.save(callRequest);
                        continue;
                    } else {
                        callRequest.setLastFetch(new Date());
                        callRequestCrudRepository.save(callRequest);
                        continue;
                    }
                }
                CallResponse callResponse = callRequest.getCallResponse();
                if (callResponse == null) {
                    callResponse = new CallResponse(callRequest);
                }
                callResponse.setResponseData(jsonData);
                callResponse.setResponseStatus(ResponseStatus.ANSWER_RECEIVED);
                callResponseCrudRepository.save(callResponse);

                callRequest.setRequestStatus(RequestStatus.ANSWER_RECEIVED);
                callRequestCrudRepository.save(callRequest);
            }

            if (!callRequests.hasNext()) break;
            callRequests = callRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, callRequests.nextPageable());
        }
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "ad.ref.fetchResponse.locker")
    public void fetchResponse() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<AdRequest> adRequests = adRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, pageable);
        while (!adRequests.isEmpty()) {

            for (AdRequest adRequest : adRequests) {
                String jsonData = aioService.fetchResponseFromAIO(adRequest.getId());
                if (StringUtils.isEmpty(jsonData)) {

                    if (adRequest.getCreatedDate().getTime() - System.currentTimeMillis() > 2 * 24 * 3600 * 1000) {
                        adRequest.setLastFetch(new Date());
                        adRequest.setRequestStatus(RequestStatus.TIMEOUT);
                        adRequestCrudRepository.save(adRequest);
                        continue;
                    } else {
                        adRequest.setLastFetch(new Date());
                        adRequestCrudRepository.save(adRequest);
                        continue;
                    }
                }
                AdResponse adResponse = adRequest.getAdResponse();
                if (adResponse == null) {
                    adResponse = new AdResponse(adRequest);
                }

                adResponse.setResponseData(jsonData);
                adResponse.setResponseStatus(ResponseStatus.ANSWER_RECEIVED);
                adResponseCrudRepository.save(adResponse);

                adRequest.setRequestStatus(RequestStatus.ANSWER_RECEIVED);
                adRequestCrudRepository.save(adRequest);
            }

            if (!adRequests.hasNext()) break;
            adRequests = adRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, adRequests.nextPageable());
        }
    }

    @Scheduled(cron = CRON_KEY)
    @SchedulerLock(name = "id.ref.fetchResponse.locker")
    public void fetchIdResponse() {
        Date date = DateUtils.addMinute(new Date(), -2);
        Pageable pageable = PageRequest.of(0, 20, Sort.by("lastFetch"));

        Page<IdRequest> idRequests = idRequestCrudRepository
                .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, pageable);
        while (!idRequests.isEmpty()) {

            for (IdRequest idRequest : idRequests) {
                String jsonData = aioService.fetchResponseFromAIO(idRequest.getId());
                if (StringUtils.isEmpty(jsonData)) {

                    if (idRequest.getCreatedDate().getTime() - System.currentTimeMillis() > 2 * 24 * 3600 * 1000) {
                        idRequest.setLastFetch(new Date());
                        idRequest.setRequestStatus(RequestStatus.TIMEOUT);
                        idRequestCrudRepository.save(idRequest);
                        continue;
                    } else {
                        idRequest.setLastFetch(new Date());
                        idRequestCrudRepository.save(idRequest);
                        continue;
                    }
                }
                IdResponse idResponse = idRequest.getIdResponse();
                if (idResponse == null) {
                    idResponse = new IdResponse(idRequest);
                }
                idResponse.setResponseData(jsonData);
                idResponse.setResponseStatus(ResponseStatus.ANSWER_RECEIVED);
                idResponseCrudRepository.save(idResponse);

                idRequest.setRequestStatus(RequestStatus.ANSWER_RECEIVED);
                idRequestCrudRepository.save(idRequest);
            }

            if (!idRequests.hasNext()) break;
            idRequests = idRequestCrudRepository
                    .findRequestIncludeResponseBy(RequestStatus.QUESTION_SENT, date, idRequests.nextPageable());
        }
    }
}
