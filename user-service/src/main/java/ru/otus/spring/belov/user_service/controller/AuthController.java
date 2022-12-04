package ru.otus.spring.belov.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.user_service.entity.dto.LoginRequest;
import ru.otus.spring.belov.user_service.entity.dto.RegisterUserRequest;
import ru.otus.spring.belov.user_service.entity.dto.TokenInfoResponse;
import ru.otus.spring.belov.user_service.service.KeycloakAdminService;
import ru.otus.spring.belov.user_service.service.KeycloakAuthService;

import javax.validation.Valid;

/**
 * Контроллер для работы с авторизацией
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    /** Сервис по работе с авторизацией */
    private final KeycloakAuthService keycloakAuthService;
    /** Сервис по работе с админским API */
    private final KeycloakAdminService keycloakAdminService;

    /**
     * Возвращает токен
     * @param loginRequest запрос на получение токена
     * @return токен
     */
    @PostMapping(value = "/token")
    public TokenInfoResponse getToken(@RequestBody @Valid LoginRequest loginRequest) {
        return keycloakAuthService.getToken(loginRequest.getLogin(), loginRequest.getPassword());
    }

    /**
     * Возвращает токен на основе refresh Токена
     * @param refreshToken refresh Токен
     * @return токен
     */
    @PostMapping("/refreshToken")
    public TokenInfoResponse refresh(@RequestBody String refreshToken) {
        return keycloakAuthService.refreshToken(refreshToken);
    }

    /**
     * Регистрирует пользователя
     * @param registerUserRequest регистрирует пользователя
     * @return токен
     */
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenInfoResponse registerUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        return keycloakAdminService.registerUser(registerUserRequest);
    }
}
