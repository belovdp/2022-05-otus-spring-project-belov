package ru.otus.spring.belov.product_service.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Продукт
 */
@AllArgsConstructor
@Setter
@Getter
public class ProductItemFull {

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
    /** Описание */
    private String description;
    /** Цена */
    private BigDecimal price;
    /** Список идентификаторов категорий */
    private List<Long> categories;
}