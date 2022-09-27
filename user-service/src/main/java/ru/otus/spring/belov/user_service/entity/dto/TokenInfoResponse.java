package ru.otus.spring.belov.user_service.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Информация о токене
 */
@RequiredArgsConstructor
@Getter
public class TokenInfoResponse {
    /** Токен */
    protected final String token;
    /** Время жизни токена */
    protected final long expiresIn;
    /** Токен для обновления токена */
    protected final String refreshToken;
    /** Время жизни токена для обновления токена */
    protected final long refreshExpiresIn;
    /** Тип токена */
    protected final String tokenType;
}
