package com.example.travel_app_api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail(String toEmail,String subject,String body){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("travel-app");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
    }
    public void sendMailHtml(String toEmail,String subject,String body){
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,"utf-8");
        try {
            helper.setText(body,true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("travelapp@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
