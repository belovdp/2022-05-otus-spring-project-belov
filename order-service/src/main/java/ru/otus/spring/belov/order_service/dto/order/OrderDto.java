package ru.otus.spring.belov.order_service.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Заказ
 */
@AllArgsConstructor
@Setter
@Getter
public class OrderDto {

    /** Идентификатор */
    private Long id;
    /** Время создания */
    private LocalDateTime created;
    /** Email указанный при заказе */
    private String email;
    /** Телефон указанный для связи */
    private String phone;
    /** Заметка */
    private String note;
    /** Адрес */
    private String address;
    /** Идентификатор пользователя из keycloak */
    private String userId;
    /** Имя контактного лица */
    private String username;
    /** Продукты в заказе */
    private List<OrderItemDto> items;
}
