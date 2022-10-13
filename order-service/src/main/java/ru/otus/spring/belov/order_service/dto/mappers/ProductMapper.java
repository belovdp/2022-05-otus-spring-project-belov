package ru.otus.spring.belov.order_service.dto.mappers;

import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.belov.order_service.domain.OrderItem;
import ru.otus.spring.belov.order_service.dto.product.ProductItem;

/**
 * Конвертер продуктов
 */
@AllArgsConstructor
@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    /**
     * Конвертирует продукт в продукт заказа
     * @param productItem продукт
     * @return продукт заказа
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", source = "productItem.id")
    public abstract OrderItem orderToDto(ProductItem productItem, int count);
}
