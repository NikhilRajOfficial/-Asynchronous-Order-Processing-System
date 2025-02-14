package com.javanik.in.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${notification.receiver.email}")
    private String receiverEmail;

    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void listen(String message) {
        logger.info("Received message from Kafka: {}", message);

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(receiverEmail);
            mailMessage.setSubject("New Order Notification");
            mailMessage.setText("Order created: " + message);

            mailSender.send(mailMessage);
            logger.info("Email notification sent successfully to: {}", receiverEmail);

        } catch (Exception e) {
            logger.error("Error while sending email", e);
            System.out.println("Error while sending email: " + e.getMessage());
        }
    }
}
