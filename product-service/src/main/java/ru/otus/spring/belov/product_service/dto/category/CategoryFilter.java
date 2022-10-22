package ru.otus.spring.belov.product_service.dto.category;

import lombok.*;

/**
 * Фильтр на получение категорий
 */
@NoArgsConstructor
@AllArgsConstructor
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
