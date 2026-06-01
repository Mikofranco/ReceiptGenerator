package com.example.demo.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailImpl implements MailService {
    private final MailSender mailSender;
    @Value("{mail.from.address}")
    private String fromAddress;
    @Value("{mail.from.name}")
    private String fromName;

    @Override
    public void sendMail(String to, String subject, String content) {
        System.out.println("from address: " + fromAddress);
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Mail not sent " +e);
        }

    }

    @Override
    public void sendVerificationCode(String to, String username, String verificationCode) {

    }

    @Override
    public void sendResetPassword(String to, String resetPasswordToken) {

    }
}
