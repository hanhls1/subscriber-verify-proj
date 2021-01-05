package vn.metech.schedule;//package vn.metech.schedule;
//
//import net.javacrumbs.shedlock.core.SchedulerLock;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import vn.metech.constant.RequestStatus;
//import vn.metech.constant.ResponseStatus;
//import vn.metech.entity.AdRequest;
//import vn.metech.kafka.mbf.MbfStatus;
//import vn.metech.service.IAdRequestService;
//
//import java.util.List;
//
//@Component
//public class ProcessTimeoutRequestSchedule {
//
//	private final String SERVICE_NAME = "ProcessTimeoutRequestSchedule > ";
//	private final String CRON_KEY = "${customer.request-timeout.cron:0 */10 * * * *}";
//
//	private IAdRequestService adRequestService;
//
//	public ProcessTimeoutRequestSchedule(
//					IAdRequestService adRequestService) {
//		this.adRequestService = adRequestService;
//	}
//
//	@Scheduled(cron = CRON_KEY)
//	@SchedulerLock(name = "ProcessTimeoutRequestSchedule.ad.changeRequestToTimeoutStatus()")
//	public void changeRequestToTimeoutStatus() {
//		List<AdRequest> timeoutRequests =
//						adRequestService.getTimeoutRequestsIncludeResponseWithLimit();
//
//		for (AdRequest adRequest : timeoutRequests) {
//			adRequest.setRequestStatus(RequestStatus.TIMEOUT);
//			if (adRequest.getAdResponse() != null) {
//				adRequest.getAdResponse().setMbfStatus(MbfStatus.TIMEOUT);
//				adRequest.getAdResponse().setResponseStatus(ResponseStatus.TIMEOUT);
//			}
//		}
//
//		adRequestService.batchUpdate(timeoutRequests);
//	}
//}
