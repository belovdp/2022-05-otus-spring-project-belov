package ru.otus.spring.belov.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Запрос на создание/обновление категории
 */
@AllArgsConstructor
@Getter
public class SaveCategoryRequest {

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
    /** Идентификатор родительской категории */
    private Long parent;
}
