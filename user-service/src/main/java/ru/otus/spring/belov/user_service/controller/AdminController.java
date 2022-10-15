package ru.otus.spring.belov.user_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.user_service.entity.GroupEnum;
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
     * Возвращает пользователя по идентификатору
     * @param id идентификатор
     * @return пользователь
     */
    @GetMapping("/users/{id}")
    public UserRepresentation getUser(@PathVariable String id) {
        return keycloakAdminService.getUser(id);
    }

    /**
     * Возвращает список пользователей
     * @param pageable пагинация
     * @return список пользователей
     */
    @GetMapping("/users")
    public List<UserRepresentation> getUsers(Pageable pageable) {
        return keycloakAdminService.getUsers(pageable);
    }

    /**
     * Устанавливает группу пользователя
     * @param userId идентификатор пользователя
     * @param group группа пользователей
     */
    @PostMapping("/users/{userId}/group")
    public void setUserGroup(@PathVariable("userId") String userId, @RequestBody GroupEnum group) {
        keycloakAdminService.setUserGroup(userId, group);
    }
}
