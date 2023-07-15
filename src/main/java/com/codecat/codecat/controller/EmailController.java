package com.codecat.codecat.controller;

import com.codecat.codecat.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/email")
@CrossOrigin("*")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Value("${client.url}")
    String CLIENT_URL;

    @PostMapping("/sendEmail/{email}")
    private ResponseEntity<String> sendEmail(@PathVariable String email, HttpServletResponse response) throws IOException {
        emailService.sendEmail(email);
        response.sendRedirect(CLIENT_URL);
        return ResponseEntity.ok("Email sent");
    }
}
