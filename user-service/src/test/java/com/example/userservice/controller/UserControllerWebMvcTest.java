package com.example.userservice.controller;

import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.UpdateUserRequest;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    void getById_returnsDto() throws Exception {
        UserDto dto = new UserDto(1L, "Ivan", "ivan@mail.com", 20, Instant.parse("2025-01-01T00:00:00Z"));
        given(userService.getById(1L)).willReturn(dto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ivan"))
                .andExpect(jsonPath("$.email").value("ivan@mail.com"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void getAll_returnsListOfDto() throws Exception {
        given(userService.getAll()).willReturn(List.of(
                new UserDto(1L, "Ivan", "ivan@mail.com", 20, null),
                new UserDto(2L, "Anna", "anna@mail.com", 30, null)
        ));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void create_returnsCreatedDto() throws Exception {
        CreateUserRequest req = new CreateUserRequest("Ivan", "ivan@mail.com", 20);
        UserDto dto = new UserDto(10L, "Ivan", "ivan@mail.com", 20, null);
        given(userService.create(any(CreateUserRequest.class))).willReturn(dto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Ivan"));
    }

    @Test
    void update_returnsUpdatedDto() throws Exception {
        UpdateUserRequest req = new UpdateUserRequest("New", "new@mail.com", 25);
        UserDto dto = new UserDto(1L, "New", "new@mail.com", 25, null);
        given(userService.update(eq(1L), any(UpdateUserRequest.class))).willReturn(dto);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("new@mail.com"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        willDoNothing().given(userService).delete(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}
