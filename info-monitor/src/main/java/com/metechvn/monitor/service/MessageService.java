package com.metechvn.monitor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metechvn.monitor.common.TokenProvider;
import com.metechvn.monitor.entity.Message;
import com.metechvn.monitor.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String requestUri;
    private final String serviceNumber;
    private final String securityKey;
    private final int expireInSeconds;
    private final ObjectMapper objectMapper;
    private final MessageRepository messageRepository;

    public MessageService(
            @Value("${sms.request-uri}") String requestUri,
            @Value("${sms.service-number}") String serviceNumber,
            @Value("${sms.security-key}") String securityKey,
            @Value("${sms.expire-in-seconds:600}") int expireInSeconds, MessageRepository messageRepository) {
        this.requestUri = requestUri;
        this.serviceNumber = serviceNumber;
        this.securityKey = securityKey;
        this.expireInSeconds = expireInSeconds;
        this.objectMapper = new ObjectMapper();
        this.messageRepository = messageRepository;
    }

    public int sendMessage() throws JsonProcessingException {
        Date dateTime = new Date();
        Message phoneNumber = messageRepository.findPhoneNumber();
        String content = "Bạn đang nhận được SMS cảnh báo từ hệ thống verify. \n" + "Cảnh báo chi tiết như sau:\n" + "DateTime: "
                + dateTime + "\n" + "Level:" + phoneNumber.getLevel() + "\n" + "Message: " + phoneNumber.getData();

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("token", smsContent(phoneNumber.getPhone(), content));
        ResponseEntity<Object> res = restTemplate.postForEntity(requestUri + "/sendmt", params, Object.class);
        logger.info("Request: " + objectMapper.writeValueAsString(params));
        logger.info("Response: " + objectMapper.writeValueAsString(res.getBody()));

        return res.getStatusCodeValue();
    }

    private String smsContent(String phoneNumber, String content) {
        return TokenProvider
                .getInstance()
                .addClaim("content", content)
                .addClaim("number", phoneNumber)
                .addClaim("service_number", serviceNumber)
                .expAfter(expireInSeconds)
                .signWithBase64(securityKey)
                .build();
    }

    public void addPhone(Message message) {
        messageRepository.save(message);
    }
}
