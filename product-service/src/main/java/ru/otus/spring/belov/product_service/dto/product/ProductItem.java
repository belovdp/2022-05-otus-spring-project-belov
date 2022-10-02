package ru.otus.spring.belov.product_service.dto.product;

import lombok.*;

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
}