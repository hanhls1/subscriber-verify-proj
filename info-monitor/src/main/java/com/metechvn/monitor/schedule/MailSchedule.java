package com.metechvn.monitor.schedule;

import com.metechvn.monitor.dto.StatusResponse;
import com.metechvn.monitor.entity.Email;
import com.metechvn.monitor.entity.Message;
import com.metechvn.monitor.repository.MailRepository;
import com.metechvn.monitor.repository.MessageRepository;
import com.metechvn.monitor.service.MailService;
import com.metechvn.monitor.service.MessageService;
import com.metechvn.monitor.util.JsonUtils;
import com.metechvn.monitor.util.RestUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MailSchedule {

    private LoadBalancerClient loadBalancerClient;
    private final MailService mailService;
    private final MailRepository mailRepository;
    private final MessageRepository messageRepository;

    private final MessageService messageService;

    public MailSchedule(LoadBalancerClient loadBalancerClient, MailService mailService,
                        MessageService messageService, MailRepository mailRepository, MessageRepository messageRepository) {
        this.loadBalancerClient = loadBalancerClient;
        this.mailService = mailService;
        this.messageService = messageService;
        this.mailRepository = mailRepository;
        this.messageRepository = messageRepository;
    }

    private String chooseLocalServiceUrl(String serviceId) {
        ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);
        return "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
    }

    @Scheduled(cron = "0 * * * * ?")
    public void sendEmailError() {
        try {
            String url = chooseLocalServiceUrl("SUBSCRIBER-VERIFY") + "/actuator/health" ;
            Email toMail = mailRepository.findMailConfig();
            RestUtils.RestResponse<StatusResponse> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, StatusResponse.class);
            System.out.println(JsonUtils.toJson(restResponse));
            if(restResponse.getHttpStatus().value() != 200 && toMail.getLevel() != "Informational" && toMail.getLevel() != "Debugging") {
                mailService.sendEmailError();
            }
            if(restResponse.getHttpStatus().value() == 200 && (toMail.getLevel() == "Informational" || toMail.getLevel() == "Debugging")) {
                mailService.sendEmailError();
            }

        } catch (Exception e) {

        }
    }

    @Scheduled(cron = "0 * * * * ?")
    public void sendMessageError() {
        try {
            String url = chooseLocalServiceUrl("SUBSCRIBER-VERIFY") + "/actuator/health" ;
            Message message = messageRepository.findPhoneNumber();
            RestUtils.RestResponse<StatusResponse> restResponse = RestUtils.exchange(url, HttpMethod.GET, null, null, StatusResponse.class);
            System.out.println(JsonUtils.toJson(restResponse));
            if(restResponse.getHttpStatus().value() != 200 && message.getLevel() != "Informational" && message.getLevel() != "Debugging") {
                messageService.sendMessage();
            }
            if(restResponse.getHttpStatus().value() == 200 && (message.getLevel() == "Informational" || message.getLevel() == "Debugging")) {
                .sendMessage();
            }

        } catch (Exception e) {

        }
    }
}
