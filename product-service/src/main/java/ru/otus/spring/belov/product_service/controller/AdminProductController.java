package ru.otus.spring.belov.product_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductRequest;
import ru.otus.spring.belov.product_service.dto.product.SaveProductRequest;
import ru.otus.spring.belov.product_service.service.ProductServiceImpl;

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
    public Page<ProductItem> getProducts(ProductRequest request,
                                         Pageable pageable) {
        return productService.getProducts(request, pageable);
    }

    /**
     * Возвращает продукты в корзине
     * @return продукты
     */
    @GetMapping("/trash")
    public List<ProductItem> getTrash() {
        return productService.getTrash();
    }

    /**
     * Переносит категории в корзину
     */
    @PostMapping("/trash")
    public void getTrash(@RequestBody List<Long> ids) {
        productService.moveToTrash(ids);
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
    public void save(@RequestBody SaveProductRequest saveProductRequest) {
        productService.saveProduct(saveProductRequest);
    }

}
