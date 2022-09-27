package ru.otus.spring.belov.user_service.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.user_service.entity.dto.TokenInfoResponse;
import ru.otus.spring.belov.user_service.exceptions.ApplicationException;
import ru.otus.spring.belov.user_service.mapper.KeycloakMapper;

/**
 * Сервис по работе с пользователями
 */
@RequiredArgsConstructor
@Service
public class KeycloakAuthServiceImpl implements KeycloakAuthService {

    /** Свойства keycloak */
    private final KeycloakSpringBootProperties kcProperties;
    /** Конфигурация keycloak-authz */
    private final Configuration kcConfig;
    /** Клиент для работы с авторизацией keycloak */
    private final AuthzClient authzClient;
    /** Конвертер keycloak объектов в DTO */
    private final KeycloakMapper mapper;

    @Override
    public TokenInfoResponse getToken(String login, String password) {
        try {
            return mapper.tokenToDTO(authzClient.authorization(login, password)
                    .authorize());
        } catch (Exception ex) {
            throw new AuthenticationServiceException("Не удалось авторизоваться. Попробуйте ещё раз", ex);
        }
    }

    @Override
    public TokenInfoResponse refreshToken(String refreshToken) {
        String url = kcProperties.getAuthServerUrl() + "/realms/" + kcProperties.getRealm() + "/protocol/openid-connect/token";
        String clientId = kcProperties.getResource();
        String secret = (String) kcProperties.getCredentials().get("secret");
        Http http = new Http(kcConfig, (params, headers) -> {
        });

        var tokenResponse = http.<AccessTokenResponse>post(url)
                .authentication()
                .client()
                .form()
                .param("grant_type", "refresh_token")
                .param("refresh_token", refreshToken)
                .param("client_id", clientId)
                .param("client_secret", secret)
                .response()
                .json(AccessTokenResponse.class)
                .execute();
        return mapper.tokenToDTO(tokenResponse);
    }
}
