package ru.otus.spring.belov.product_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductFilter;
import ru.otus.spring.belov.product_service.dto.product.SaveProductRequest;

import java.util.List;

/**
 * Сервис по работе с продуктами
 */
public interface ProductService {

    /**
     * Возвращает продукты
     * @param productFilter запрос на фильтрацию
     * @param pageable      пагинация
     * @return продукты
     */
    Page<ProductItem> getProducts(ProductFilter productFilter, Pageable pageable);

    /**
     * Возвращает продукт
     * @param id идентификатор продукта
     * @return продукт
     */
    ProductItem getProductById(Long id);

    /**
     * Возвращает продукты из корзины
     * @return продукты из корзины
     */
    List<ProductItem> getTrash();

    /**
     * Переводит продукты в корзину
     * @param ids идентификаторы продуктов
     */
    void moveToTrash(List<Long> ids);

    /**
     * Удаляет продукты
     * @param ids идентификаторы продуктов
     */
    void delete(List<Long> ids);

    /**
     * Возвращает сохранённый/изменённый продукт
     * @param saveProductRequest запрос на изменение/сохранение
     */
    void saveProduct(SaveProductRequest saveProductRequest);
}