package ru.otus.spring.belov.order_service.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * Запрос на создание заказа
 */
@AllArgsConstructor
@Setter
@Getter
public class CreateOrderRequest {

    /** Email указанный при заказе */
    private String email;
    /** Телефон указанный для связи */
    @NotEmpty(message = "Требуется указать телефон")
    private String phone;
    /** Заметка */
    private String note;
    /** Адрес */
    @NotEmpty(message = "Требуется указать адрес")
    private String address;
    /** Имя контактного лица */
    private String username;
    /** Карта идентификаторов продуктов и их количества */
    @NotEmpty(message = "Требуется указать имя пользователя")
    private Map<Long, Integer> productIds;
}
