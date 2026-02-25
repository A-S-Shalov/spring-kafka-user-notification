package com.example.userservice.kafka;

public class UserEvent {
    private UserOperation operation;
    private String email;

    public UserEvent() {
    }

    public UserEvent(UserOperation operation, String email) {
        this.operation = operation;
        this.email = email;
    }

    public UserOperation getOperation() {
        return operation;
    }

    public void setOperation(UserOperation operation) {
        this.operation = operation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}