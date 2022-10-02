package ru.otus.spring.belov.product_service.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Продукт
 */
@AllArgsConstructor
@Setter
@Getter
public class SaveProductRequest {

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
    /** Список идентификаторов категорий */
    private List<Long> categories;
}