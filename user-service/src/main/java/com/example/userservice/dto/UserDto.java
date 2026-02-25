package com.example.userservice.dto;

import java.time.Instant;

/**
 * DTO, который возвращается из контроллеров.
 */
public record UserDto(
        Long id,
        String name,
        String email,
        Integer age,
        Instant createdAt
) {
}
