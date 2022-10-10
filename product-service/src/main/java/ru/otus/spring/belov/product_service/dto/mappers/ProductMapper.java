package ru.otus.spring.belov.product_service.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.otus.spring.belov.product_service.domain.Category;
import ru.otus.spring.belov.product_service.domain.Product;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductItemFull;

import java.util.List;
import java.util.Set;

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
     * Конвертирует продукт в dto максимально полное для отображения
     * @param products продукты
     * @return продукт dto
     */
    @Mapping(target = "categories", qualifiedByName = "categoriesToCategoryIds")
    ProductItemFull productToProductItemFull(Product products);

    /**
     * Обновляет сущность на основе DTO
     * @param source     dto
     * @param categories родительские категории
     * @param product    обновляемая сущность
     */
    @Mapping(target = "categories", source = "categories")
    void updateProductFromDto(ProductItemFull source, List<Category> categories, @MappingTarget Product product);

    /**
     * Конвертирует категории в идентификаторы категорий
     * @param categories категории
     * @return идентификаторы категорий
     */
    @Named("categoriesToCategoryIds")
    default List<Long> categoriesToCategoryIds(Set<Category> categories) {
        return categories.stream().map(Category::getId).toList();
    }
}
