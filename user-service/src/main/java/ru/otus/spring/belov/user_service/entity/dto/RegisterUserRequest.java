package ru.otus.spring.belov.user_service.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    @Size(min = 2, message = "Минимальное число символов для пароля: 8")
    private final String password;
    /** Имя пользователя */
    private final String firstname;
    /** Фамилия пользователя */
    private final String lastname;
}

