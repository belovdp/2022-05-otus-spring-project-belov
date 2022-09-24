package ru.otus.spring.belov.user_service.service;

import org.keycloak.representations.idm.UserRepresentation;
import org.springdoc.core.converters.models.Pageable;

import java.util.List;

/**
 * Сервис по работе с административным API
 */
public interface KeycloakAdminService {

    /**
     * Возвращает список пользователей
     * @param pageable пагинация
     * @return список пользователей
     */
    List<UserRepresentation> getUsers(Pageable pageable);
}
