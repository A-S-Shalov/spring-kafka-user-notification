package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "name не должен быть пустым")
        String name,

        @NotBlank(message = "email не должен быть пустым")
        @Email(message = "email некорректный")
        String email,

        @Min(value = 1, message = "age должен быть > 0")
        Integer age
) {
}
