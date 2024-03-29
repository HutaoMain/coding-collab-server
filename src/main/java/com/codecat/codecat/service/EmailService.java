package com.codecat.codecat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String userEmail) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String subject = "Account Verification";
        String message = "Please click the following link to verify your account: http://localhost:8081/api/user/isEnable/userID/" + userEmail;
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("codecattt@gmail.com");
        javaMailSender.send(mailMessage);


//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//        helper.setText(email, true);
//        helper.setTo(to);
//        helper.setSubject("Confirm your email");
//        helper.setFrom("admin.rimsti@stamaria.sti.edu.ph");
//        javaMailSender.send(mimeMessage);
    }
}
