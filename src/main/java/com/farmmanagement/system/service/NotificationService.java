package com.farmmanagement.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendNotification(String to, String subject, String message) {
        // In a real application, this would integrate with an email service (JavaMail)
        // or a push notification service (FCM).
        logger.info("--- NOTIFICATION ---");
        logger.info("To: {}", to);
        logger.info("Subject: {}", subject);
        logger.info("Message: {}", message);
        logger.info("--------------------");
    }
}
