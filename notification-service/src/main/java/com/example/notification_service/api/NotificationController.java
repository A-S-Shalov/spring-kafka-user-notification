package com.example.notification_service.api;

import com.example.notification_service.api.dto.SendEmailRequest;
import com.example.notification_service.kafka.UserOperation;
import com.example.notification_service.mail.EmailSender;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailSender emailSender;

    public NotificationController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody SendEmailRequest request) {
        String subject = "Уведомление";
        String text = buildText(request.getOperation());

        emailSender.send(request.getEmail(), subject, text);
        return ResponseEntity.ok().build();
    }

    private String buildText(UserOperation operation) {
        if (operation == UserOperation.DELETED) {
            return "Здравствуйте! Ваш аккаунт был удалён.";
        }
        if (operation == UserOperation.CREATED) {
            return "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.";
        }
        throw new IllegalArgumentException("Unsupported operation: " + operation);
    }
}