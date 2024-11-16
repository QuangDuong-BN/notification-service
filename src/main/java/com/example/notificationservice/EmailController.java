package com.example.notificationservice;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/mail-api")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class EmailController {

    JavaMailSender mailSender;
    EmailService emailService;

    @GetMapping("/send")
    public void sendEmail() throws MessagingException, IOException {
        emailService.sendHtmlEmail("Quang Duong", "ct040213@actvn.edu.vn", "https://example.com/verify?token=abc123");
    }

    @KafkaListener(topics = "send-email", groupId = "notification-service")
    public void consume(String message) throws MessagingException, IOException {
        log.info("message: {}", message);
    }

}
