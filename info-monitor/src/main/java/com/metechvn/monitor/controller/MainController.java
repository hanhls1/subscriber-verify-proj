package com.metechvn.monitor.controller;

import com.metechvn.monitor.dto.MailResponse;
import com.metechvn.monitor.dto.PhoneResponse;
import com.metechvn.monitor.entity.Email;
import com.metechvn.monitor.entity.Log;
import com.metechvn.monitor.entity.Message;
import com.metechvn.monitor.repository.LogRepository;
import com.metechvn.monitor.repository.MailRepository;
import com.metechvn.monitor.service.LocalService;
import com.metechvn.monitor.service.MailService;
import com.metechvn.monitor.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/add")
public class MainController {

    private final MailService mailService;
    private final LocalService localService;
    private final MailRepository mailRepository;
    private final MessageService messageService;
    private final LogRepository logRepository;

    public MainController( MailService mailService, LocalService localService, MailRepository mailRepository,
                           MessageService messageService, LogRepository logRepository) {

        this.mailService = mailService;
        this.localService = localService;
        this.mailRepository = mailRepository;
        this.messageService = messageService;
        this.logRepository = logRepository;
    }

    @PostMapping("/config-mail")
    public void setMailConfigs(@RequestParam("email") String emailAddress, @RequestParam("level") String level,
                               @RequestParam("subject") String subject, @RequestParam("data") String data) {
        Email email = new Email();
        email.setEmail(emailAddress);
        email.setLevel(level);
        email.setSubject(subject);
        email.setData(data);
        mailService.addmail(email);
    }

    @PostMapping("/config-phone")
    public void setPhoneNumbers(@RequestParam("phone-number") String number, @RequestParam("level") String level,
                                @RequestParam("data") String data) {
        Message message = new Message();
        message.setPhone(number);
        message.setLevel(level);
        message.setData(data);
        messageService.addPhone(message);
    }

    @GetMapping("/email-admin")
    public List<MailResponse> getMailAdmin() {
        return localService.getEmail();
    }

    @GetMapping("/get-phone-number")
    public List<PhoneResponse> getPhoneAdmin() {
        return localService.getPhoneNumber();
    }

    @GetMapping("/get-log")
    public List<Log> getLog() {
        return logRepository.findAll();
    }
}
