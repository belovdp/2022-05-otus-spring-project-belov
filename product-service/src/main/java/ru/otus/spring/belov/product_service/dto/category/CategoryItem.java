package ru.otus.spring.belov.product_service.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Категория продуктов
 */
@AllArgsConstructor
@Setter
@Getter
public class CategoryItem {

    /** Идентификатор */
    private Long id;
    /** Заголовок */
    private String title;
    /** Признак что ресурс в корзине */
    private boolean deleted;
    /** Скрыт в меню */
    private boolean hideMenu;
    /** Опубликован */
    private boolean published;
    /** Индекс сортировки */
    private int sortIndex;
    /** Идентификатор родителя */
    private int parent;
}
