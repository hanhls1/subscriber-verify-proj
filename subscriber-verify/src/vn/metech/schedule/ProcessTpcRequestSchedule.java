package vn.metech.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.metech.constant.RequestStatus;
import vn.metech.constant.ResponseStatus;
import vn.metech.constant.StatusCode;
import vn.metech.dto.msg.TpcMessage;
import vn.metech.entity.AdRequest;
import vn.metech.entity.CallRequest;
import vn.metech.repository.jpa.AdRequestCrudRepository;
import vn.metech.repository.jpa.CallRequestCrudRepository;
import vn.metech.service.IAdRequestService;
import vn.metech.service.ICallRequestService;
import vn.metech.shared.ActionResult;
import vn.metech.util.RestUtils;

import java.util.List;

@Component
public class ProcessTpcRequestSchedule {

    @Value("${server.port}")
    private String serverPort;

    private IAdRequestService adRequestService;
    private AdRequestCrudRepository adRequestCrudRepository;
    private LoadBalancerClient loadBalancerClient;
    private CallRequestCrudRepository callRequestCrudRepository;
    private ICallRequestService callRequestService;


    private String chooseLocalServiceUrl() {
//        ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);
//
//        return "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
        return "http://localhost" + ":" + serverPort;
    }

    public ProcessTpcRequestSchedule(
            IAdRequestService adRequestService,
            LoadBalancerClient loadBalancerClient,
            AdRequestCrudRepository adRequestCrudRepository,
            ICallRequestService callRequestService,
            CallRequestCrudRepository callRequestCrudRepository) {
        this.adRequestService = adRequestService;
        this.loadBalancerClient = loadBalancerClient;
        this.adRequestCrudRepository = adRequestCrudRepository;
        this.callRequestService = callRequestService;
        this.callRequestCrudRepository = callRequestCrudRepository;
    }

    @Scheduled(cron = "${tpc.request-sync.cron:0/20 * * * * *}")
//    @SchedulerLock(name = "ad.ref.syncTPCRequestsToTPCService.locker")
    public void syncTPCRequestsToTPCServiceAd() {
        List<AdRequest> adRequests = adRequestService.getSyncibleTpcRequests();
        System.out.println("Number of requests: " + adRequests.size());
        for (AdRequest adRequest : adRequests) {
            if (adRequest.getRequestStatus() == RequestStatus.QUESTION_SENT &&
                    adRequest.getAdResponse().getResponseStatus() == ResponseStatus.ANSWER_RECEIVED) {
                adRequest.setRequestStatus(RequestStatus.ANSWER_RECEIVED);
                adRequestCrudRepository.save(adRequest);
            }

            try {
                sendSummaryRequest(adRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "${tpc.request-sync.cron:0/20 * * * * *}")
//    @SchedulerLock(name = "call.ref.syncTPCRequestsToTPCService.locker")
    public void syncTPCRequestsToTPCServiceCall() {
        List<CallRequest> callRequests = callRequestService.getSyncibleTpcRequests();
        System.out.println("Number of requests: " + callRequests.size());
        for (CallRequest callRequest : callRequests) {
            if (callRequest.getRequestStatus() == RequestStatus.QUESTION_SENT &&
                    callRequest.getCallResponse().getResponseStatus() == ResponseStatus.ANSWER_RECEIVED) {
                callRequest.setRequestStatus(RequestStatus.ANSWER_RECEIVED);
                callRequestCrudRepository.save(callRequest);
            }

            try {
                sendSummaryRequestCall(callRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendSummaryRequest(AdRequest adRequest) throws Exception {
        TpcMessage tpcMessage = new TpcMessage(adRequest);
//        String requestUrl = chooseLocalServiceUrl("TPC-SERVICE") + "/sync/tpc-summary";
        String requestUrl = chooseLocalServiceUrl() + "/sync/tpc-summary";
        RestUtils.RestResponse<ActionResult> restResponse = RestUtils.post(requestUrl, tpcMessage, ActionResult.class);

        if (restResponse.getHttpStatus() != HttpStatus.OK) {
            throw new Exception("summary error");
        }

        ActionResult actionResult = restResponse.getBody();
        if (!actionResult.getStatusCode().equals(StatusCode.SUCCESS)) {
            throw new Exception(actionResult.getStatusCode() + " error: " + actionResult.getResult());
        }
        adRequest.setGroupSync(true);
        adRequestCrudRepository.save(adRequest);
    }

    private void sendSummaryRequestCall(CallRequest callRequest) throws Exception {
        TpcMessage tpcMessage = new TpcMessage(callRequest);
//        String requestUrl = chooseLocalServiceUrl("TPC-SERVICE") + "/sync/tpc-summary";
        String requestUrl = chooseLocalServiceUrl() + "/sync/tpc-summary";
        RestUtils.RestResponse<ActionResult> restResponse = RestUtils.post(requestUrl, tpcMessage, ActionResult.class);

        if (restResponse.getHttpStatus() != HttpStatus.OK) {
            throw new Exception("summary error");
        }

        ActionResult actionResult = restResponse.getBody();
        if (!actionResult.getStatusCode().equals(StatusCode.SUCCESS)) {
            throw new Exception(actionResult.getStatusCode() + " error: " + actionResult.getResult());
        }
        callRequest.setGroupSync(true);
        callRequestCrudRepository.save(callRequest);
    }
}
