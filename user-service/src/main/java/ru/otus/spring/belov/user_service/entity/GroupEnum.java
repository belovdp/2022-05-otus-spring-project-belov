package ru.otus.spring.belov.user_service.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Группы пользователей
 */
@RequiredArgsConstructor
@Getter
public enum GroupEnum {

    /** Группа администраторов */
    ADMINS("ea1cf792-cb27-4d02-855f-b2b1a2d2fb80"),
    /** Группа менеджеров с возможность редактирования */
    EDITOR("769f5586-cdfb-4c66-a43b-12a98d455c76"),
    /** Группа пользователей */
    USERS("a2af7c21-aec9-4713-bd3f-3af2585b1260"),
    /** Группа менеджеров без возможности редактирования */
    VIEWERS("ec9af33f-101c-4c76-822f-67b6a2fe642a");

    /** Идентификатор в keycloak. Он фиксированный, т.к. проливается при старте */
    private final String groupId;
}
