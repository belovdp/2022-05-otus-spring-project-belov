package ru.otus.spring.belov.user_service.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Информация о токене
 */
@RequiredArgsConstructor
@Getter
public class TokenInfoResponse {
    /** Токен */
    private final String token;
    /** Время жизни токена */
    private final long expiresIn;
    /** Токен для обновления токена */
    private final String refreshToken;
    /** Время жизни токена для обновления токена */
    private final long refreshExpiresIn;
    /** Тип токена */
    private final String tokenType;
    /** Время получения токена */
    private final LocalDateTime loginResponseTime = LocalDateTime.now();
}
