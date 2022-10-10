package ru.otus.spring.belov.product_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductFilter;
import ru.otus.spring.belov.product_service.dto.product.ProductItemFull;
import ru.otus.spring.belov.product_service.service.ProductServiceImpl;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с продуктами
 */
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminProductController {

    /** Сервис по работе с категориями */
    private final ProductServiceImpl productService;

    /**
     * Возвращает продукты не удалённые и опубликованные из категории
     * @param request  фильтр
     * @param pageable пагинация
     * @return продукты не удалённые и опубликованные из категории
     */
    @GetMapping("/")
    public Page<ProductItem> getProducts(@Valid ProductFilter request,
                                         Pageable pageable) {
        return productService.getProducts(request, pageable);
    }

    /**
     * Вовзращает продукт
     * @param id идентификатор продукта
     * @return продукт
     */
    @GetMapping("/{id}")
    public ProductItemFull getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * Возвращает продукты в корзине
     * @return продукты
     */
    @GetMapping("/trash")
    public Page<ProductItem> getTrash(Pageable pageable) {
        return productService.getTrash(pageable);
    }

    /**
     * Переносит категории в корзину
     */
    @PostMapping("/trash")
    public void moveToTrash(@RequestBody List<Long> ids) {
        productService.moveToTrash(ids);
    }

    /**
     * Востанавливает из корзины
     * @param ids идентификаторы категорий
     */
    @PostMapping("/trash/restore")
    public void restoreTrash(@RequestBody List<Long> ids) {
        productService.restoreTrash(ids);
    }

    /**
     * Переносит категории в корзину
     */
    @DeleteMapping("/")
    public void delete(@RequestBody List<Long> ids) {
        productService.delete(ids);
    }

    /**
     * Сохраняет/изменяет категорию
     * @param saveProductRequest запрос на сохранение/обновление категорий
     */
    @PostMapping("/")
    public ProductItemFull save(@RequestBody ProductItemFull saveProductRequest) {
        return productService.saveProduct(saveProductRequest);
    }
}
