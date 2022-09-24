package ru.otus.spring.belov.user_service.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.user_service.dto.LoginDTO;

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

    @Override
    public AccessTokenResponse getToken(LoginDTO loginDTO) {
        return authzClient.authorization(loginDTO.getLogin(), loginDTO.getPassword())
                .authorize();
    }

    @Override
    public AccessTokenResponse refreshToken(String refreshToken) {
        String url = kcProperties.getAuthServerUrl() + "/realms/" + kcProperties.getRealm() + "/protocol/openid-connect/token";
        String clientId = kcProperties.getResource();
        String secret = (String) kcProperties.getCredentials().get("secret");
        Http http = new Http(kcConfig, (params, headers) -> {
        });

        return http.<AccessTokenResponse>post(url)
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
    }
}
