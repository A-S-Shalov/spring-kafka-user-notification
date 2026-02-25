package com.example.notification_service;

import com.example.notification_service.kafka.UserOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import jakarta.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiEmailIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        // SMTP на 3025 — совпадает с application.yml
        greenMail = new GreenMail(new ServerSetup(3025, null, "smtp"));
        greenMail.start();
    }

    @Test
    void shouldSendEmailViaApi() throws Exception {
        String json = """
                {
                  "email": "user@test.com",
                  "operation": "%s"
                }
                """.formatted(UserOperation.CREATED.name());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Void> response = restTemplate.postForEntity(
                "/api/notifications/email",
                new HttpEntity<>(json, headers),
                Void.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);

        assertThat(greenMail.waitForIncomingEmail(3000, 1)).isTrue();

        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertThat(messages).hasSize(1);
        assertThat(messages[0].getAllRecipients()[0].toString()).isEqualTo("user@test.com");
        assertThat(messages[0].getSubject()).isEqualTo("Уведомление");
        assertThat(messages[0].getContent().toString())
                .contains("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
    }
}