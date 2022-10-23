package ru.otus.spring.belov.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.user_service.KeycloakBaseTest;
import ru.otus.spring.belov.user_service.entity.dto.LoginRequest;
import ru.otus.spring.belov.user_service.entity.dto.RegisterUserRequest;
import ru.otus.spring.belov.user_service.service.KeycloakAuthService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирует публичную работу с keycloak")
@AutoConfigureMockMvc
class AuthControllerTest extends KeycloakBaseTest {

    private static final LoginRequest USER_CREDENTIALS = new LoginRequest("user", "user");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KeycloakAuthService authService;

    @DisplayName("Тестирует получение токена")
    @Test
    void getToken() throws Exception {
        mockMvc.perform(post("/users/token")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.expiresIn").isNotEmpty())
                .andExpect(jsonPath("$.refreshExpiresIn").isNotEmpty());
    }

    @DisplayName("Тестирует обновление токена")
    @Test
    void refresh() throws Exception {
        var refreshToken = authService.getToken(USER_CREDENTIALS.getLogin(), USER_CREDENTIALS.getPassword()).getRefreshToken();
        mockMvc.perform(post("/users/refreshToken")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(refreshToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.expiresIn").isNotEmpty())
                .andExpect(jsonPath("$.refreshExpiresIn").isNotEmpty());
    }

    @DisplayName("Тестирует регистрацию пользователя")
    @Test
    void registerUser() throws Exception {
        var request = new RegisterUserRequest("test@test.ru", "qweasdzxc", "Василий", "Пупкин");
        mockMvc.perform(post("/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.expiresIn").isNotEmpty())
                .andExpect(jsonPath("$.refreshExpiresIn").isNotEmpty());
    }
}