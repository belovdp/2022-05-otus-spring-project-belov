package ru.otus.spring.belov.user_service.service;

import org.keycloak.representations.idm.UserRepresentation;
import org.springdoc.core.converters.models.Pageable;
import ru.otus.spring.belov.user_service.entity.GroupEnum;
import ru.otus.spring.belov.user_service.entity.dto.RegisterUserRequest;
import ru.otus.spring.belov.user_service.entity.dto.TokenInfoResponse;

import java.util.List;

/**
 * Сервис по работе с административным API
 */
public interface KeycloakAdminService {

    /**
     * Возвращает список пользователей
     * @param registerUserRequest пагинация
     * @return список пользователей
     */
    TokenInfoResponse registerUser(RegisterUserRequest registerUserRequest);

    /**
     * Возвращает пользователя по идентификатору
     * @param id идентификатор
     * @return пользователь
     */
    UserRepresentation getUser(String id);

    /**
     * Возвращает список пользователей
     * @param pageable пагинация
     * @return список пользователей
     */
    List<UserRepresentation> getUsers(Pageable pageable);

    /**
     * Устанавливает группу пользователя
     * @param userId идентификатор пользователя
     * @param group группа пользователей
     */
    void setUserGroup(String userId, GroupEnum group);
}
