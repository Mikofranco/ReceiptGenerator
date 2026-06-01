package com.example.demo.service;

public interface MailService {
    void sendMail(String to, String subject, String content);
    void sendVerificationCode(String to, String username, String verificationCode);
    void sendResetPassword(String to, String resetPasswordToken);
}
