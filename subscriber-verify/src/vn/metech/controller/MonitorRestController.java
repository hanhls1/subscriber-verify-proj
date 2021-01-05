package vn.metech.controller;

import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import vn.metech.dto.request.RequestBase;
import vn.metech.dto.request.monitor.ConfirmRequest;
import vn.metech.dto.request.monitor.FilterRequest;
import vn.metech.dto.response.*;
import vn.metech.exception.aio.RequestDuplicateException;
import vn.metech.service.IConfirmInfoService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mon")
public class MonitorRestController {

    private final IConfirmInfoService confirmInfoService;

    public MonitorRestController(IConfirmInfoService confirmInfoService) {
        this.confirmInfoService = confirmInfoService;
    }

    @RequestMapping(value = "/listStatus", method = RequestMethod.GET)
    public List<StatusResponse> getAllStatus() {
        return confirmInfoService.getAllStatus();
    }

    @RequestMapping(value = "/listService", method = RequestMethod.GET)
    public List<String> getServiceType() {
        return confirmInfoService.getListServices();
    }

    @RequestMapping(value = "/listPartner", method = RequestMethod.GET)
    public List<PartnerResponse> getPartnerId() {
        return confirmInfoService.getListPartner();
    }

    @RequestMapping(value = "/listSubPartner", method = RequestMethod.GET)
    public List<SubPartnerResponse> getSubPartnerId(@RequestParam("partner-id") String partnerId) {
        return confirmInfoService.getListSubPartner(partnerId);
    }

    @PostMapping("/getFilterRequest")
    public PageResponse<MonitorResponse> getRequest(HttpServletResponse response, @RequestHeader("user-id") String userId,
                                                    @Valid @RequestBody ConfirmRequest confirmRequest, RequestBase req) throws IOException, RequestDuplicateException {
        if (confirmRequest.getIsReport().equals("yes")) {
            PageResponse<MonitorResponse> monitorResponsePageResponse = confirmInfoService.getFilRequest(req, confirmRequest, userId);
            response.setContentType("text/csv; charset=UTF-8");
            DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=listRequest_" + currentDateTime + ".csv";
            response.setHeader(headerKey, headerValue);
            response.setCharacterEncoding("UTF-8");

            long m = monitorResponsePageResponse.getTotal() / monitorResponsePageResponse.getPageSize();
            long n = monitorResponsePageResponse.getTotal() % monitorResponsePageResponse.getPageSize() == 0 ? 0 : 1;
            long k = m < 1 ? 1 : m + n;
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"Request ID", "phone Number", "Status Code", "Status", "Service Type", "Created Date", "Account", "Partner Name", "Partner ID"};
            String[] nameMapping = {"requestId", "phoneNumber", "statusCode", "status", "serviceType", "createdDate", "account", "partnerName", "partnerId"};
            csvWriter.writeHeader(csvHeader);

            for (int i = 1; i <= k; i++) {
                confirmRequest.setCurrentPage(i);
                PageResponse<MonitorResponse> monitorResponsePageResponse1 = confirmInfoService.getFilRequest(req, confirmRequest, userId);

                for (MonitorResponse confirmInfoDto : monitorResponsePageResponse1.getData()) {
                    csvWriter.write(confirmInfoDto, nameMapping);
                }
            }
            csvWriter.close();
        } else {
            PageResponse<MonitorResponse> listResponse = confirmInfoService.getFilRequest(req, confirmRequest, userId);
            return listResponse;
        }
        return null;
    }

    @PostMapping("/getFilter/02")
    public PageResponse<ConfirmInfoResponse> getFilter(@RequestHeader("user-id") String userId,
                                                       @Valid @RequestBody FilterRequest filterRequest, RequestBase req) throws RequestDuplicateException {

        PageResponse<ConfirmInfoResponse> listResponse = confirmInfoService.getFilter(req, filterRequest, userId);
        return listResponse;
    }

}
