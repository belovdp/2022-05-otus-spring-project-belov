package ru.otus.spring.belov.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.user_service.entity.GroupEnum;
import ru.otus.spring.belov.user_service.entity.dto.KeycloakError;
import ru.otus.spring.belov.user_service.entity.dto.RegisterUserRequest;
import ru.otus.spring.belov.user_service.entity.dto.TokenInfoResponse;
import ru.otus.spring.belov.user_service.exceptions.ApplicationException;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Сервис по работе с административным API
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    /** Объект для работы с админским API keycloak */
    private final Keycloak keycloak;
    /** Свойства keycloak */
    private final KeycloakSpringBootProperties kcProperties;
    /** Сервис по работе с авторизацией */
    private final KeycloakAuthService keycloakAuthService;

    @Override
    public TokenInfoResponse registerUser(RegisterUserRequest registerUserRequest) {
//        keycloak.tokenManager().getAccessToken();
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(registerUserRequest.getEmail());
        user.setFirstName(registerUserRequest.getFirstname());
        user.setLastName(registerUserRequest.getLastname());
        user.setEmail(registerUserRequest.getEmail());

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(registerUserRequest.getPassword());
        user.setCredentials(List.of(passwordCred));

        RealmResource realmResource = keycloak.realm(kcProperties.getRealm());
        UsersResource usersRessource = realmResource.users();

        Response response = usersRessource.create(user);

        if (response.getStatus() != 201) {
            throw new ApplicationException("Не удалось создать пользователя: " + response.readEntity(KeycloakError.class).getErrorMessage());
        }
        return keycloakAuthService.getToken(registerUserRequest.getEmail(), registerUserRequest.getPassword());
    }

    @Override
    public List<UserRepresentation> getUsers(Pageable pageable) {
        return keycloak.realm(kcProperties.getRealm())
                .users()
                .list(pageable.getPage() * pageable.getSize(), pageable.getSize());
    }

    @Override
    public void setUserGroup(String userId, GroupEnum group) {
        try {
            var groups = keycloak.realm(kcProperties.getRealm()).users().get(userId).groups();
            groups.forEach(groupRepresentation -> keycloak.realm(kcProperties.getRealm())
                    .users()
                    .get(userId)
                    .leaveGroup(groupRepresentation.getId())
            );
            keycloak.realm(kcProperties.getRealm()).users().get(userId).joinGroup(group.getGroupId());
        } catch (Exception ex) {
            throw new ApplicationException("Не удалось изменить группу пользователя. Попробуйте ещё раз или обратитесь к администратору", ex);
        }
    }
}
