package ru.otus.spring.belov.user_service.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Запрос на создание пользователя
 */
@RequiredArgsConstructor
@Getter
public class RegisterUserRequest {
    /** Почта */
    @NotEmpty(message = "Требуется указать email")
    @Email(message = "Неверная почта")
    private final String email;
    /** Пароль */
    @NotEmpty(message = "Требуется указать пароль")
    @Min(value = 8, message = "Минимальное число символов для пкароля: 8")
    private final String password;
    /** Имя пользователя */
    private final String firstname;
    /** Фамилия пользователя */
    private final String lastname;
}

