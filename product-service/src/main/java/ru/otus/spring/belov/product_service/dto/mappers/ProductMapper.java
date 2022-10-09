package ru.otus.spring.belov.product_service.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.otus.spring.belov.product_service.domain.Category;
import ru.otus.spring.belov.product_service.domain.Product;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.SaveProductRequest;

import java.util.List;

/**
 * Конвертер продуктов
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Конвертирует продукт в dto
     * @param products продукты
     * @return продукт dto
     */
    ProductItem productToProductItem(Product products);

    /**
     * Обновляет сущность на основе DTO
     * @param source     dto
     * @param categories родительские категории
     * @param product    обновляемая сущность
     */
    @Mapping(target = "categories", source = "categories")

    void updateProductFromDto(SaveProductRequest source, List<Category> categories, @MappingTarget Product product);
}
