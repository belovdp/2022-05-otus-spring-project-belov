package ru.otus.spring.belov.user_service.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Ошибка от keycloak
 */
@Getter
@Setter
@NoArgsConstructor
public class KeycloakError {
    private String errorMessage;
}


