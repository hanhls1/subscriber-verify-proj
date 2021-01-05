package vn.metech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.metech.dto.request.TpcConfirmInfoListRequest;
import vn.metech.dto.response.PageResponse;
import vn.metech.dto.response.TpcConfirmInfoListResponse;
import vn.metech.entity.ConfirmInfoReceive;
import vn.metech.repository.jpa.ConfirmInfoReceiveCrudRepository;
import vn.metech.service.TpcConfirmInfoSearchService;
import vn.metech.util.StringUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/tpc"})
public class TPCRestController {

    private final TpcConfirmInfoSearchService tpcConfirmInfoSearchService;
    private final ConfirmInfoReceiveCrudRepository confirmInfoReceiveCrudRepository;

    public TPCRestController(TpcConfirmInfoSearchService tpcConfirmInfoSearchService,
                             ConfirmInfoReceiveCrudRepository confirmInfoReceiveCrudRepository) {
        this.tpcConfirmInfoSearchService = tpcConfirmInfoSearchService;
        this.confirmInfoReceiveCrudRepository = confirmInfoReceiveCrudRepository;
    }

    @PostMapping({"/response/get-list"})
    public PageResponse<TpcConfirmInfoListResponse> findResponse(
            @RequestHeader("user-id") String userId,
            @Valid @RequestBody TpcConfirmInfoListRequest requestBase) {

        return tpcConfirmInfoSearchService.getResponsesBy(requestBase, userId);
    }

    @GetMapping("/response")
    public ResponseEntity<String> findResponseJsonFrom(@RequestParam("id") String id) {
        List<ConfirmInfoReceive> confirmInfoReceives = confirmInfoReceiveCrudRepository
                .findByRequestId(id);

        if (confirmInfoReceives != null && !confirmInfoReceives.isEmpty()) {
            for (ConfirmInfoReceive confirmInfoReceive : confirmInfoReceives) {
                if (StringUtils.isEmpty(confirmInfoReceive.getData())) continue;

                return ResponseEntity.ok(confirmInfoReceive.getData());
            }
        }

        return ResponseEntity.ok("");
    }
}
