package com.example.notification_service.api.dto;

import com.example.notification_service.kafka.UserOperation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class SendEmailRequest {

    @NotNull
    @Email
    private String email;

    @NotNull
    private UserOperation operation;

    public SendEmailRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserOperation getOperation() {
        return operation;
    }

    public void setOperation(UserOperation operation) {
        this.operation = operation;
    }
}