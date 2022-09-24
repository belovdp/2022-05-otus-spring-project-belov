package ru.otus.spring.belov.user_service.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.belov.user_service.dto.LoginDTO;
import ru.otus.spring.belov.user_service.service.KeycloakAuthService;

/**
 * Контроллер для работы с авторизацией
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /** Сервис по работе с авторизацией */
    private final KeycloakAuthService keycloakAuthService;

    /**
     * Возвращает токен
     * @param loginDTO запрос на получение токена
     * @return токен
     */
    @GetMapping("/token")
    public AccessTokenResponse getToken(LoginDTO loginDTO) {
        return keycloakAuthService.getToken(loginDTO);
    }

    /**
     * Возвращает токен на основе refresh Токена
     * @param refreshToken refresh Токен
     * @return токен
     */
    @GetMapping("/refresh")
    public AccessTokenResponse refresh(String refreshToken) {
        return keycloakAuthService.refreshToken(refreshToken);
    }
}
