package ru.otus.spring.belov.order_service.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Продукты заказа
 */
@AllArgsConstructor
@Setter
@Getter
public class OrderItemDto {

    /** Идентификатор */
    private Long id;
    /** Идентификатор товара */
    private Long productId;
    /** Заголовок товара */
    private String title;
    /** Цена */
    private BigDecimal price;
    /** Количество */
    private int count;
}
