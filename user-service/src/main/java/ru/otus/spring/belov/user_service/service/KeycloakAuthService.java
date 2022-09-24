package ru.otus.spring.belov.user_service.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.user_service.dto.LoginDTO;

public interface KeycloakAuthService {

    /**
     * Возвращает токен
     * @param loginDTO запрос на получение токена
     * @return токен
     */
    AccessTokenResponse getToken(LoginDTO loginDTO);

    /**
     * Возвращает токен на основе refresh Токена
     * @param refreshToken refresh Токен
     * @return токен
     */
    AccessTokenResponse refreshToken(String refreshToken);
}
