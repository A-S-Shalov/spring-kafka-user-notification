# 🚀 Spring Kafka User Notification Microservices

## 📌 Overview

This project demonstrates an event-driven microservices architecture built with **Spring Boot** and **Apache Kafka**.

The system consists of two microservices:

- **user-service** – manages users and publishes events to Kafka  
- **notification-service** – listens to Kafka events and sends email notifications  

When a user is created or deleted, an event is published to Kafka.  
The notification-service consumes the event and sends an email message.

---

## 🏗 Architecture

User → user-service → Kafka → notification-service → SMTP → MailHog

---

## 🛠 Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Spring Kafka
- Spring Mail (JavaMailSender)
- MailHog (SMTP testing)
- Maven

---

## ⚙️ How to Run

### 1️⃣ Start infrastructure (Docker)

Required services:

- PostgreSQL (port 5432)
- Kafka (port 9092)
- MailHog (SMTP 1025, UI 8025)

MailHog UI:
http://localhost:8025

---

### 2️⃣ Run user-service

Runs on:
http://localhost:8080

---

### 3️⃣ Run notification-service

Runs on:
http://localhost:8081

---

## 📬 Testing

### Test notification-service directly

POST:
http://localhost:8081/api/notifications/email

Body:
{
  "email": "test@test.com",
  "operation": "CREATED"
}

---

### Test full flow (Kafka)

1. Create user using user-service
2. Event is sent to Kafka
3. notification-service receives event
4. Email appears in MailHog

---

## 📦 Event Model

UserEvent:
- operation (CREATED / DELETED)
- email

---

## 🧪 Integration Tests

Includes integration tests for:

- Kafka event consumption
- Email sending using test SMTP server

---

## 🎯 Purpose

This project was created as a practical assignment to demonstrate:

- Microservices architecture
- Event-driven communication
- Kafka integration
- Email notifications
- Clean service-layer design
_________________________________________________________
_________________________________________________________
____