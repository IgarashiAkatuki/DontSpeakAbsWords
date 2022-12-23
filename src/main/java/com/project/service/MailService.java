package com.project.service;

import com.mysql.cj.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class MailService {

//    @Autowired
//    private JavaMailSenderImpl javaMailSender;
//
//    @Scheduled(cron = "0 0 18 0 0 ?")
//    @Async
//    void sendStatistics(){
//        SimpleMailMessage message = new SimpleMailMessage();
//    }
}
