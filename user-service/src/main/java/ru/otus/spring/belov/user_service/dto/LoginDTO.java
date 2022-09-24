package ru.otus.spring.belov.user_service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Запрос на получение токена
 */
@Getter
@RequiredArgsConstructor
public class LoginDTO {
    /** Логин */
    private final String login;
    /** Пароль */
    private final String password;
}
