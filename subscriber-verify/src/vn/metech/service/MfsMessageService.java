package vn.metech.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.metech.common.MessageStatus;
import vn.metech.common.MfsStatus;
import vn.metech.common.ValueKey;
import vn.metech.dto.request.MfsMessageRequest;
import vn.metech.dto.request.MfsRequestBase;
import vn.metech.dto.response.MfsGenericResponse;
import vn.metech.dto.response.data.MfsMessage;
import vn.metech.dto.response.data.MfsMessageList;
import vn.metech.entity.ApiRequestLog;
import vn.metech.entity.ConfirmInfo;
import vn.metech.entity.Message;
import vn.metech.repository.jpa.ApiRequestLogCrudRepository;
import vn.metech.repository.jpa.ConfirmInfoCrudRepository;
import vn.metech.repository.jpa.MessageCrudRepository;
import vn.metech.util.JsonUtils;
import vn.metech.util.RestUtils;
import vn.metech.util.StringUtils;

@Service
public class MfsMessageService {

    private final String baseUrl;

    private final MessageCrudRepository messageCrudRepository;
    private final ConfirmInfoCrudRepository confirmInfoCrudRepository;
    private final ApiRequestLogCrudRepository apiRequestLogRepository;

    public MfsMessageService(
            @Value(ValueKey.PARTNER_MFS_REQUEST_URL) String baseUrl,
            MessageCrudRepository messageCrudRepository,
            ConfirmInfoCrudRepository confirmInfoCrudRepository,
            ApiRequestLogCrudRepository apiRequestLogRepository) {
        this.baseUrl = baseUrl;
        this.messageCrudRepository = messageCrudRepository;
        this.confirmInfoCrudRepository = confirmInfoCrudRepository;
        this.apiRequestLogRepository = apiRequestLogRepository;

    }

    public void getAndSaveMessage(ConfirmInfo confirmInfo, int messageType) {
        String absolutePath = messageType == 0 ? "/api/v2/subscriber/getMT" : "/api/v2/subscriber/getMO";
        MfsMessageRequest mfsMessageRequest =
                new MfsMessageRequest(confirmInfo, confirmInfo.getAccount(), confirmInfo.getSecureKey());
        getAndSaveMessage(absolutePath, confirmInfo, mfsMessageRequest, messageType);
    }

    private <T extends MfsRequestBase> void getAndSaveMessage(
            String absolutePath, ConfirmInfo confirmInfo, T req, int messageType) {
        try {
            Assert.notNull(absolutePath, "absolutePath required");
            RestUtils.RestResponse<MfsGenericResponse> res =
                    RestUtils.post(baseUrl + absolutePath, null, req, MfsGenericResponse.class);
            ApiRequestLog apiRequestLog = new ApiRequestLog(res, confirmInfo);
            if (res.getBody() != null) {
                apiRequestLog.setResponseId(res.getBody().getResponseId());
                MfsStatus mfsStatus = res.getBody().getMfsStatus();

                if (mfsStatus == MfsStatus.SUCCESS) {
                    MfsMessageList mfsMessageList =
                            JsonUtils.convert(res.getBody().getResponseData(), MfsMessageList.class);
                    saveMessages(confirmInfo, mfsMessageList, messageType);
                }

            }

            apiRequestLogRepository.save(apiRequestLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMessages(ConfirmInfo confirmInfo, MfsMessageList mfsMessageList, int messageType) {
        if (confirmInfo == null || mfsMessageList == null || mfsMessageList.getSize() == 0) {
            return;
        }
        Long latestTime = messageCrudRepository.getMaxDateTimeInMillisecondsBy(confirmInfo, messageType);
        for (MfsMessage mfsMessage : mfsMessageList.getMessages()) {
            Message message = new Message(mfsMessage, confirmInfo, messageType);
            if (latestTime != null && message.getDateTimeInMilliseconds() <= latestTime) {
                continue;
            }

            if (StringUtils.isEmpty(message.getContent())) {
                message.setProcessed(true);
                messageCrudRepository.save(message);
                continue;
            }

            if (message.getMessageType() == 0
                    && confirmInfo.getMessageStatus() == MessageStatus.REQUEST_SENT) { // 0 = out message

                confirmInfo.setAggregateStatus(69);
                confirmInfo.setMessageStatus(MessageStatus.CUSTOMER_PENDING);
                confirmInfoCrudRepository.save(confirmInfo);
            } else if (message.getMessageType() == 1) { // 1 = in message
                String acceptSyntax = "Y " + confirmInfo.getCustomerCode();
                String rejectSyntax = "N " + confirmInfo.getCustomerCode();

                if (message.getContent().equalsIgnoreCase(acceptSyntax.replaceAll("_", "§"))) {
                    confirmInfo.setAggregateStatus(69);
                    confirmInfo.setMessageStatus(MessageStatus.ACCEPTED);
                } else if (message.getContent().equalsIgnoreCase(rejectSyntax.replaceAll("_", "§"))) {
                    confirmInfo.setAggregateStatus(69);
                    confirmInfo.setMessageStatus(MessageStatus.REJECTED);
                } else {
                    confirmInfo.setAggregateStatus(69);
                    confirmInfo.setMessageStatus(MessageStatus.SYNTAX_ERROR);
                }

                confirmInfoCrudRepository.save(confirmInfo);
            }
            messageCrudRepository.save(message);
        }
    }
}
