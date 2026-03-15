# 🚀 Spring Kafka User Notification Microservices

## 📌 Обзор

Проект демонстрирует **event-driven (событийную) микросервисную
архитектуру** на **Spring Boot**, **Apache Kafka** и **Spring Cloud**.

Система состоит из двух основных микросервисов:

-   **user-service** --- управляет пользователями и публикует события в
    Kafka
-   **notification-service** --- слушает события из Kafka и отправляет
    email‑уведомления

Когда пользователь **создаётся или удаляется**, событие публикуется в
Kafka. **notification-service** потребляет событие и отправляет письмо.

Дополнительно в проекте реализованы инфраструктурные компоненты **Spring
Cloud**:

-   API Gateway
-   Service Discovery (Eureka)
-   External Configuration (Config Server)
-   Circuit Breaker (Resilience4j)

------------------------------------------------------------------------

# 🏗 Архитектура

Client │ ▼ API Gateway (8085) │ ▼ user-service (8080) │ ▼ Kafka │ ▼
notification-service (8081) │ ▼ SMTP │ ▼ MailHog

Инфраструктурные сервисы:

Eureka Server (Service Discovery) → 8761 Config Server (External
Configuration) → 8888

------------------------------------------------------------------------

# ☁ Spring Cloud архитектурные паттерны

## API Gateway

Реализован с помощью **Spring Cloud Gateway**.

Gateway является **единой точкой входа** для всех внешних запросов.

Пример запроса:

GET http://localhost:8085/api/users

------------------------------------------------------------------------

## Service Discovery

Реализован с помощью **Spring Cloud Netflix Eureka**.

Все сервисы регистрируются в **Eureka Server** и могут находить друг
друга по имени.

Eureka UI: http://localhost:8761

Зарегистрированные сервисы:

-   user-service
-   notification-service
-   api-gateway
-   config-server

------------------------------------------------------------------------

## External Configuration

Реализован через **Spring Cloud Config Server**.

Config Server централизует конфигурацию сервисов.

Адрес: http://localhost:8888

------------------------------------------------------------------------

## Circuit Breaker

Реализован с помощью **Resilience4j**.

Если notification-service недоступен:

-   user-service не падает
-   используется fallback‑механизм

Пример лога:

Notification service unavailable. Fallback worked

------------------------------------------------------------------------

# 🛠 Технологии

-   Java 17
-   Spring Boot
-   Spring Cloud
-   Spring Data JPA
-   PostgreSQL
-   Apache Kafka
-   Spring Kafka
-   Spring Mail
-   MailHog
-   Maven
-   Springdoc OpenAPI (Swagger)
-   Spring HATEOAS
-   Resilience4j

------------------------------------------------------------------------

# 📖 Документация API (Swagger)

Swagger UI: http://localhost:8080/swagger-ui/index.html

Основные endpoints:

GET /api/users\
GET /api/users/{id}\
POST /api/users\
PUT /api/users/{id}\
DELETE /api/users/{id}

------------------------------------------------------------------------

# 🔗 Поддержка HATEOAS

API реализует **HATEOAS** с помощью Spring HATEOAS.

Пример ответа:

{ "id": 6, "name": "Test User", "email": "test@mail.com", "age": 20,
"createdAt": "2026-03-05T15:46:22.019709Z", "\_links": { "self": {
"href": "http://localhost:8080/api/users/6" }, "users": { "href":
"http://localhost:8080/api/users" } } }

------------------------------------------------------------------------

# ⚙️ Как запустить

## 1. Поднять инфраструктуру

Необходимые сервисы:

-   PostgreSQL (5432)
-   Kafka (9092)
-   MailHog

MailHog:

SMTP: 1025\
UI: http://localhost:8025

------------------------------------------------------------------------

## 2. Запустить инфраструктурные сервисы

1.  discovery-server
2.  config-server
3.  api-gateway

------------------------------------------------------------------------

## 3. Запустить микросервисы

1.  user-service → http://localhost:8080
2.  notification-service → http://localhost:8081

------------------------------------------------------------------------

# 📬 Тестирование

Создание пользователя:

POST http://localhost:8085/api/users

{ "name": "Test User", "email": "test@test.com", "age": 25 }

Полный поток:

1.  Создание пользователя
2.  Событие отправляется в Kafka
3.  notification-service получает событие
4.  письмо появляется в MailHog

------------------------------------------------------------------------

# 🎯 Назначение

Проект демонстрирует:

-   микросервисную архитектуру
-   событийное взаимодействие (event‑driven)
-   интеграцию Kafka
-   email‑уведомления
-   Swagger документацию API
-   принципы HATEOAS
-   Spring Cloud паттерны
___
___