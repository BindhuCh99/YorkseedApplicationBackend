//package com.yorkseed.backend.service;
//
//import com.yorkseed.backend.Interface.EmailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.env.Environment;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailServiceImpl implements EmailService {
//    private final JavaMailSender mailSender;
//    private final Environment env;
//
//    @Override
//    public void sendVerificationEmail(String to, String token) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(env.getProperty("spring.mail.username"));
//        message.setTo(to);
//        message.setSubject("Verify your email address");
//        message.setText("Please click the link below to verify your email address:\n\n" +
//                "http://localhost:8000/verify-email.html?token=" + token + "\n\n" +
//                "This link will expire in 24 hours.");
//        mailSender.send(message);
//    }
//
//}
