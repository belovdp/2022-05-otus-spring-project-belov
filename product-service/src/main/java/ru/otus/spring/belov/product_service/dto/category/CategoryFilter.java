package ru.otus.spring.belov.product_service.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Фильтр на получение категорий
 */
@Builder
@Getter
@Setter
public class CategoryFilter {
    /** Признак того что категория в корзине */
    private Boolean deleted;
    /** Признак того что опубликовано */
    private Boolean published;
    /** Признак того что скрыто из меню */
    private Boolean hideMenu;
}
