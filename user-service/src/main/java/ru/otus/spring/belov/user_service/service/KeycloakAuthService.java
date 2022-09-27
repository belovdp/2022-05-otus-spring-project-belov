package ru.otus.spring.belov.user_service.service;

import ru.otus.spring.belov.user_service.entity.dto.TokenInfoResponse;

public interface KeycloakAuthService {

    /**
     * Возвращает токен
     * @param login    логин
     * @param password пароль
     * @return токен
     */
    TokenInfoResponse getToken(String login, String password);

    /**
     * Возвращает токен на основе refresh Токена
     * @param refreshToken refresh Токен
     * @return токен
     */
    TokenInfoResponse refreshToken(String refreshToken);
}
