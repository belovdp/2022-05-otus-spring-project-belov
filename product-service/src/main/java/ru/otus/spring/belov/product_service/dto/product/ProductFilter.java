package ru.otus.spring.belov.product_service.dto.product;

import lombok.*;

/**
 * Фильтр на получение продукта
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductFilter {
    /** Категория */
    private Long categoryId;
    /** Признак того что продукт в корзине */
    private Boolean deleted;
    /** Признак того что опубликовано */
    private Boolean published;
}
