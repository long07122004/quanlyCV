package com.example.quanlycv.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String email, String token) {
        String resetPasswordLink = "http://localhost:8080/reset-password?token=" + token;
        String subject = "Đặt lại mật khẩu";
        String content = "Click vào link sau để đặt lại mật khẩu: " + resetPasswordLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
