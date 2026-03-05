# 🚀 Spring Kafka User Notification Microservices

## 📌 Обзор
Проект демонстрирует **event-driven (событийную) микросервисную архитектуру** на **Spring Boot** и **Apache Kafka**.

Система состоит из двух микросервисов:

- **user-service** — управляет пользователями и публикует события в Kafka  
- **notification-service** — слушает события из Kafka и отправляет email-уведомления

Когда пользователь **создаётся или удаляется**, событие публикуется в Kafka.  
**notification-service** потребляет событие и отправляет письмо.

---

## 🏗 Архитектура
```
User → user-service → Kafka → notification-service → SMTP → MailHog
```

---

## 🛠 Технологии
- Java 17  
- Spring Boot  
- Spring Data JPA  
- PostgreSQL  
- Apache Kafka  
- Spring Kafka  
- Spring Mail (JavaMailSender)  
- MailHog (SMTP для тестирования писем)  
- Maven  
- **Springdoc OpenAPI (Swagger)**  
- **Spring HATEOAS**

---

## 📖 Документация API (Swagger)
В проекте подключена **интерактивная документация API** через **Springdoc OpenAPI (Swagger)**.

Swagger UI позволяет:
- быстро посмотреть доступные endpoints
- увидеть схемы запросов/ответов
- тестировать API прямо в браузере (Try it out)

Swagger UI доступен по адресу:
```
http://localhost:8080/swagger-ui/index.html
```

Примеры задокументированных endpoints:
- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

Каждый endpoint описан с помощью аннотаций:
- `@Tag`
- `@Operation`
- `@ApiResponses`

---

## 🔗 Поддержка HATEOAS
API реализует **HATEOAS (Hypermedia as the Engine of Application State)** с помощью **Spring HATEOAS**.

Ответы API содержат навигационные ссылки (`_links`), чтобы клиент мог **переходить по ресурсам**, не «хардкодя» URL.

Пример ответа:
```json
{
  "id": 6,
  "name": "Test User",
  "email": "test1@mail.com",
  "age": 20,
  "createdAt": "2026-03-05T15:46:22.019709Z",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/users/6"
    },
    "users": {
      "href": "http://localhost:8080/api/users"
    }
  }
}
```

Формат ответа — **HAL**:
```
Content-Type: application/hal+json
```

---

## ⚙️ Как запустить

### 1️⃣ Поднять инфраструктуру (Docker)
Нужные сервисы:
- PostgreSQL (порт **5432**)
- Kafka (порт **9092**)
- MailHog

Порты MailHog:
```
SMTP: 1025
UI:   8025
```

MailHog UI:
```
http://localhost:8025
```

---

### 2️⃣ Запустить user-service
Сервис доступен по адресу:
```
http://localhost:8080
```

---

### 3️⃣ Запустить notification-service
Сервис доступен по адресу:
```
http://localhost:8081
```

---

## 📬 Тестирование

### Тест notification-service напрямую
```
POST http://localhost:8081/api/notifications/email
```

Body:
```json
{
  "email": "test@test.com",
  "operation": "CREATED"
}
```

---

### Тест полного потока (Kafka)
1) Создать пользователя через **user-service**  
2) Событие отправится в **Kafka**  
3) **notification-service** получит событие  
4) Письмо появится в **MailHog**

---

## 📦 Модель события

### UserEvent
Поля:
- `operation` (CREATED / DELETED)
- `email`

Пример:
```json
{
  "operation": "CREATED",
  "email": "user@test.com"
}
```

---

## 🧪 Интеграционные тесты
В проекте есть интеграционные тесты для:
- потребления Kafka-событий
- отправки email через тестовый SMTP (MailHog)

---

## 🎯 Назначение
Проект создан как практическое задание для демонстрации:
- микросервисной архитектуры
- событийного взаимодействия (event-driven)
- интеграции Kafka
- email-уведомлений
- Swagger-документации API
- принципов HATEOAS
- аккуратного разделения слоёв (controller/service/repository)
