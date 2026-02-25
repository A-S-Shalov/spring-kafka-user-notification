# User Service (Spring Boot + REST API)

Учебный проект: **Spring Boot** приложение с REST API для управления пользователями (CRUD) в PostgreSQL.

## Технологии
- Java 17
- Spring Boot (Web, Data JPA, Validation)
- PostgreSQL (Docker)
- Maven
- MockMvc tests + Testcontainers (PostgreSQL)

## Запуск базы данных (Docker)

```bash
docker compose up -d
```

Поднимается PostgreSQL на `localhost:5432` и создаётся таблица `users` из `db/init.sql`.

## Настройки приложения

`src/main/resources/application.yml`:

- url: `jdbc:postgresql://localhost:5432/user_service`
- username/password: `app/app`
- `ddl-auto: validate` (схема должна существовать — создаётся init.sql)

## Запуск приложения

В IntelliJ IDEA: запустить `com.example.userservice.UserServiceApplication`

или из консоли:

```bash
mvn spring-boot:run
```

Приложение стартует на `http://localhost:8080`.

## REST API

Базовый путь: `/api/users`

- `GET /api/users` — список пользователей (DTO)
- `GET /api/users/{id}` — получить пользователя по id (DTO)
- `POST /api/users` — создать пользователя
- `PUT /api/users/{id}` — обновить пользователя
- `DELETE /api/users/{id}` — удалить пользователя

**Важно:** контроллеры возвращают только DTO (`record UserDto`), Entity наружу не отдаётся.

## Тесты

- `UserControllerWebMvcTest` — `@WebMvcTest` + MockMvc (сервис замокан)
- `UserControllerIntegrationTest` — `@SpringBootTest` + MockMvc + Testcontainers PostgreSQL

Запуск:

```bash
mvn test
```
______________________________________________________________
______________________________________________________________