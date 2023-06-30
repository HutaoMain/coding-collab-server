package com.codecat.codecat.controller;

import com.codecat.codecat.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@CrossOrigin("*")
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sendEmail")
    private ResponseEntity<String> sendEmail(String email) {
        emailService.sendEmail(email);
        return ResponseEntity.ok("Email sent");
    }
}
