//package vn.metech.schedule;
//
//import net.javacrumbs.shedlock.core.SchedulerLock;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import vn.metech.constant.RequestStatus;
//import vn.metech.constant.ResponseStatus;
//import vn.metech.entity.InfoDiscoveryRequest;
//import vn.metech.kafka.dwh.constant.DwhStatus;
//import vn.metech.service.IInfoDiscoveryRequestService;
//
//import java.util.List;
//
//@Component
//public class ProcessTimeoutRequestSchedule {
//
//	private final String SERVICE_NAME = "ProcessTimeoutRequestSchedule > ";
//	private final String CRON_KEY = "${customer.request-timeout.cron:0 */10 * * * *}";
//
//	private IInfoDiscoveryRequestService infoDiscoveryRequestService;
//
//	public ProcessTimeoutRequestSchedule(
//					IInfoDiscoveryRequestService infoDiscoveryRequestService) {
//		this.infoDiscoveryRequestService = infoDiscoveryRequestService;
//	}
//
//	@Scheduled(cron = CRON_KEY)
//	@SchedulerLock(name = "ProcessTimeoutRequestSchedule.info-discovery.changeRequestToTimeoutStatus()")
//	public void changeRequestToTimeoutStatus() {
//		List<InfoDiscoveryRequest> timeoutRequests =
//						infoDiscoveryRequestService.getTimeoutRequestsIncludeResponseWithLimit();
//
//		for (InfoDiscoveryRequest infoDiscoveryRequest : timeoutRequests) {
//			infoDiscoveryRequest.setRequestStatus(RequestStatus.TIMEOUT);
//			if (infoDiscoveryRequest.getInfoDiscoveryResponse() != null) {
//				infoDiscoveryRequest.getInfoDiscoveryResponse().setDwhStatus(DwhStatus.TIMEOUT);
//				infoDiscoveryRequest.getInfoDiscoveryResponse().setResponseStatus(ResponseStatus.TIMEOUT);
//			}
//		}
//
//		infoDiscoveryRequestService.batchUpdate(timeoutRequests);
//	}
//}
