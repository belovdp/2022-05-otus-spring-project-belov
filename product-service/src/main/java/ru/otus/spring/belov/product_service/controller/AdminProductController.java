package ru.otus.spring.belov.product_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.product_service.dto.product.ProductFilter;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductItemFull;
import ru.otus.spring.belov.product_service.service.ProductService;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с продуктами
 */
@Slf4j
@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminProductController {

    /** Сервис по работе с категориями */
    private final ProductService productService;

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
     * Переносит продукт в корзину
     */
    @PostMapping("/trash")
    public void moveToTrash(@RequestBody List<Long> ids) {
        log.info("Перенос продуктов {} в корзину пользователем {}", ids, SecurityContextHolder.getContext().getAuthentication().getName());
        productService.moveToTrash(ids);
    }

    /**
     * Востанавливает из корзины
     * @param ids идентификаторы категорий
     */
    @PostMapping("/trash/restore")
    public void restoreTrash(@RequestBody List<Long> ids) {
        log.info("Востановление продуктов {} из корзины пользователем {}", ids, SecurityContextHolder.getContext().getAuthentication().getName());
        productService.restoreTrash(ids);
    }

    /**
     * Переносит продукт в корзину
     */
    @DeleteMapping("/")
    public void delete(@RequestBody List<Long> ids) {
        log.info("Удаление продуктов {} пользователем {}", ids, SecurityContextHolder.getContext().getAuthentication().getName());
        productService.delete(ids);
    }

    /**
     * Сохраняет/изменяет продукт
     * @param saveProductRequest запрос на сохранение/обновление категорий
     */
    @PostMapping("/")
    public ProductItemFull save(@RequestBody ProductItemFull saveProductRequest) {
        log.info("Создание продукта {} пользователем {}", saveProductRequest.getTitle(),
                SecurityContextHolder.getContext().getAuthentication().getName());
        return productService.saveProduct(saveProductRequest);
    }
}
