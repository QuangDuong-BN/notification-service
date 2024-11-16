package com.example.notificationservice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String recipientName, String recipientEmail, String verificationLink) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("quangduong19992001@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject("Xác thực tài khoản của bạn");

        // Đọc nội dung HTML từ file
        String htmlContent = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/email-template.html")), StandardCharsets.UTF_8);

        // Thay thế các placeholder bằng giá trị thực tế
        htmlContent = htmlContent.replace("{{name}}", recipientName);
        htmlContent = htmlContent.replace("{{verificationLink}}", verificationLink);

        // Thêm nội dung HTML vào email
        helper.setText(htmlContent, true);

        // Thêm hình ảnh đính kèm (nếu có)
        helper.addAttachment("hehe.png", new File("image/img.png"));

        mailSender.send(message);
    }

}
