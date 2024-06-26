package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EMailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper html = new MimeMessageHelper(message, "UTF-8");

        try {
            html.setTo(toEmail);
            html.setSubject(subject);
            html.setText(htmlBody, true); // true indicates HTML content

            mailSender.send(message);
            System.out.println("HTML Email Sent Successfully to: " + toEmail);
        } catch (MessagingException e) {
            System.out.println("Failed to send HTML email to: " + toEmail);
            e.printStackTrace();
        }
    }
}