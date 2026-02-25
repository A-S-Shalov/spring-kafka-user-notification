package com.example.userservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisabledOnOs(value = OS.WINDOWS, disabledReason = "Testcontainers не может подключиться к Docker на Windows в этом окружении")
@Testcontainers
class TestcontainersSmokeTest {

    @Test
    void dockerShouldBeReachableByTestcontainers() {
        try (GenericContainer<?> container = new GenericContainer<>("alpine:3.20")
                .withCommand("sh", "-c", "echo ok && sleep 1")) {

            container.start();
            assertTrue(container.isRunning(), "Container should be running");
        }
    }
}
