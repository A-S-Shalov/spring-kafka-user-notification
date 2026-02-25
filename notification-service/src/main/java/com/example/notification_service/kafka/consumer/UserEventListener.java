package com.example.notification_service.kafka.consumer;

import com.example.notification_service.kafka.UserEvent;
import com.example.notification_service.kafka.UserOperation;
import com.example.notification_service.mail.EmailSender;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    private final EmailSender emailSender;

    public UserEventListener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "notification-service")
    public void handle(UserEvent event) {
        if (event == null || event.getEmail() == null) {
            return;
        }

        String subject = "Уведомление";
        String text;

        if (event.getOperation() == UserOperation.DELETED) {
            text = "Здравствуйте! Ваш аккаунт был удалён.";
        } else if (event.getOperation() == UserOperation.CREATED) {
            text = "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
        } else {
            // на всякий случай
            return;
        }

        emailSender.send(event.getEmail(), subject, text);
    }
}