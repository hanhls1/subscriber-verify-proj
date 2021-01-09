package com.metechvn.monitor.service;

import com.metechvn.monitor.entity.Email;
import com.metechvn.monitor.repository.MailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JavaMailSender javaMailSender;
    private MailRepository mailRepository;

    @Autowired
    public MailService(JavaMailSender javaMailSender, MailRepository mailRepository) {
        this.javaMailSender = javaMailSender;
        this.mailRepository = mailRepository;
    }

    public void sendEmailError( ) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        Date dateTime = new Date();
        Email toMail = mailRepository.findMailConfig();
        if(toMail != null) {
            mailMessage.setTo(toMail.getEmail().replace(",",";"));
            mailMessage.setSubject(toMail.getSubject());
            mailMessage.setText("Bạn đang nhận được Email cảnh báo từ hệ thống verify. \n" + "Cảnh báo chi tiết như sau:\n" + "DateTime: "
                    + dateTime + "\n" + "Level:" + toMail.getLevel() + "\n" + "Message: " + toMail.getData());
            javaMailSender.send(mailMessage);
            logger.info("Gửi Email cảnh báo lỗi thành công.");
        } else {
            logger.info("Chưa cấu hình cho việc gửi Email.");
        }
    }

    public void addmail(Email mailConfig) {
        mailRepository.save(mailConfig);
    }
}