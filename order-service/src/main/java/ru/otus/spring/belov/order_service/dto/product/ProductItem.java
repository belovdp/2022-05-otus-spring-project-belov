package ru.otus.spring.belov.order_service.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Продукт
 */
@AllArgsConstructor
@Setter
@Getter
public class ProductItem {

    /** Идентификатор */
    private Long id;
    /** Заголовок */
    private String title;
    /** Признак что ресурс в корзине */
    private boolean deleted;
    /** Опубликован */
    private boolean published;
    /** Индекс сортировки */
    private int sortIndex;
    /** Цена */
    private BigDecimal price;
}