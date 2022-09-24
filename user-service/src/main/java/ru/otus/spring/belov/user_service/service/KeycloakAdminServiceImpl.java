package ru.otus.spring.belov.user_service.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис по работе с административным API
 */
@RequiredArgsConstructor
@Service
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    /** Объект для работы с админским API keycloak */
    private final Keycloak keycloak;
    /** Свойства keycloak */
    private final KeycloakSpringBootProperties kcProperties;

    @Override
    public List<UserRepresentation> getUsers(Pageable pageable) {
        return keycloak.realm(kcProperties.getRealm())
                .users()
                .list(pageable.getPage() * pageable.getSize(), pageable.getSize());
    }
}
