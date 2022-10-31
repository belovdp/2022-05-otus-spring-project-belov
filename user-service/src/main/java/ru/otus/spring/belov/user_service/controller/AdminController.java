package ru.otus.spring.belov.user_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.user_service.entity.dto.Page;
import ru.otus.spring.belov.user_service.service.KeycloakAdminService;

import java.util.List;

/**
 * Контроллер для административных функций
 */
@Slf4j
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
    public Page<UserRepresentation> getUsers(Pageable pageable) {
        return keycloakAdminService.getUsers(pageable);
    }

    /**
     * Устанавливает группу пользователя
     * @param userId  идентификатор пользователя
     * @param groupId группа пользователей
     */
    @PostMapping(value = "/users/{userId}/group")
    public void setUserGroup(@PathVariable("userId") String userId, @RequestBody String groupId) {
        log.info("Установка группы {} для пользователя {} пользователем {}", groupId, userId,
                SecurityContextHolder.getContext().getAuthentication().getName());
        keycloakAdminService.setUserGroup(userId, groupId);
    }

    /**
     * Возвращает список групп
     * @return список групп
     */
    @GetMapping("/groups")
    public List<GroupRepresentation> getGroups() {
        return keycloakAdminService.getGroups();
    }

    /**
     * Изменяет статус пользователя
     * @param userId   идентификатор пользователя
     * @param activate статус пользователя
     */
    @PostMapping("/users/{userId}/activator")
    public void onChangeEnableStatus(@PathVariable("userId") String userId, @RequestBody boolean activate) {
        log.info("Изменение статуса пользователю {} на {} пользователем {}", userId, activate,
                SecurityContextHolder.getContext().getAuthentication().getName());
        keycloakAdminService.onChangeEnableStatus(userId, activate);
    }
}
