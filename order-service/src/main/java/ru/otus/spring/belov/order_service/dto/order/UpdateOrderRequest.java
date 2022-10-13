package ru.otus.spring.belov.order_service.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Запрос на обновление заказа
 */
@AllArgsConstructor
@Setter
@Getter
public class UpdateOrderRequest {

    /** Идентификатор */
    private Long id;
    /** Email указанный при заказе */
    private String email;
    /** Телефон указанный для связи */
    private String phone;
    /** Заметка */
    private String note;
    /** Адрес */
    private String address;
    /** Имя контактного лица */
    private String username;
}
