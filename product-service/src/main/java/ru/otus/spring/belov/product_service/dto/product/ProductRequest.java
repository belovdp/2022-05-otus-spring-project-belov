package ru.otus.spring.belov.product_service.dto.product;

import lombok.Builder;
import lombok.Getter;

/**
 * Запрос на получение продукта
 */
@Builder
@Getter
public class ProductRequest {
    /** Категория */
    private Long categoryId;
    /** Признак того что продукт в корзине */
    private Boolean deleted;
    /** Признак того что опубликовано */
    private Boolean published;
}
