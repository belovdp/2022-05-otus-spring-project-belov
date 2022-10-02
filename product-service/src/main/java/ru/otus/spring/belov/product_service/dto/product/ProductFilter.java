package ru.otus.spring.belov.product_service.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Фильтр на получение продукта
 */
@Builder
@Getter
@Setter
public class ProductFilter {
    /** Идентификатор продукта */
    private Long id;
    /** Категория */
    private Long categoryId;
    /** Признак того что продукт в корзине */
    private Boolean deleted;
    /** Признак того что опубликовано */
    private Boolean published;
}
