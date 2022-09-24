package ru.otus.spring.belov.user_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.belov.user_service.service.KeycloakAdminService;

import java.util.List;

/**
 * Контроллер для административных функций
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    /** Сервис по работе с админским API */
    private final KeycloakAdminService keycloakAdminService;

    /**
     * Возвращает список пользователей
     * @param pageable пагинация
     * @return список пользователей
     */
    @GetMapping("/users")
    public List<UserRepresentation> getUsers(Pageable pageable) {
        return keycloakAdminService.getUsers(pageable);
    }
}
