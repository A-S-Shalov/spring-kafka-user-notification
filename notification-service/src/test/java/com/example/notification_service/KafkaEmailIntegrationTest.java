package com.example.notification_service;

import com.example.notification_service.kafka.UserEvent;
import com.example.notification_service.kafka.UserOperation;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = {"user-events"},
        brokerProperties = {
                "log.dir=build/embedded-kafka-logs"
        }
)
class KafkaEmailIntegrationTest {

    // Подменяем bootstrap-servers на embedded kafka
    @DynamicPropertySource
    static void kafkaProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers",
                () -> System.getProperty("spring.embedded.kafka.brokers"));
        registry.add("app.kafka.topic", () -> "user-events");
    }

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(3025, null, "smtp"));
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        if (greenMail != null) {
            greenMail.stop();
        }
    }

    @Test
    void shouldSendEmailAfterKafkaEvent() throws Exception {
        UserEvent event = new UserEvent(UserOperation.DELETED, "kafka@test.com");

        kafkaTemplate.send("user-events", event.getEmail(), event);

        assertThat(greenMail.waitForIncomingEmail(5000, 1)).isTrue();

        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertThat(messages).hasSize(1);
        assertThat(messages[0].getAllRecipients()[0].toString()).isEqualTo("kafka@test.com");
        assertThat(messages[0].getSubject()).isEqualTo("Уведомление");
        assertThat(messages[0].getContent().toString())
                .contains("Здравствуйте! Ваш аккаунт был удалён.");
    }
}