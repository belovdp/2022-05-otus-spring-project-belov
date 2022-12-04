package ru.otus.spring.belov.user_service.mapper;

import org.keycloak.representations.AccessTokenResponse;
import org.mapstruct.Mapper;
import ru.otus.spring.belov.user_service.entity.dto.TokenInfoResponse;

@Mapper(componentModel = "spring")
public interface KeycloakMapper {
    TokenInfoResponse tokenToDTO(AccessTokenResponse tokenResponse);
}
