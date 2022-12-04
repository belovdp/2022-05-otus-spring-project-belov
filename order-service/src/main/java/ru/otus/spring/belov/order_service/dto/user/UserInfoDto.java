package ru.otus.spring.belov.order_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Пользователь
 */
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UserInfoDto {
    private String id;
    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
}
