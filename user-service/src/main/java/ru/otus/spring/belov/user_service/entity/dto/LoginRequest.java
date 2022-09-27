package ru.otus.spring.belov.user_service.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Запрос на получение токена
 */
@Getter
@RequiredArgsConstructor
public class LoginRequest {
    /** Логин */
    @NotEmpty(message = "Требуется указать логин")
    private final String login;
    /** Пароль */
    @NotEmpty(message = "Требуется указать пароль")
    private final String password;
}
