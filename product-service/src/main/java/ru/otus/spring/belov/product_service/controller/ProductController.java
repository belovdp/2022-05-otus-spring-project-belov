package ru.otus.spring.belov.product_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.product_service.dto.category.CategoryFilter;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductFilter;
import ru.otus.spring.belov.product_service.service.ProductServiceImpl;

/**
 * Контроллер для работы с продуктами
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    /** Сервис по работе с категориями */
    private final ProductServiceImpl productService;

    /**
     * Возвращает продукты не удалённые и опубликованные из категории
     * @param catId категория
     * @param pageable пагинация
     * @return продукты не удалённые и опубликованные из категории
     */
    @GetMapping("/")
    public Page<ProductItem> getProducts(@RequestParam(name = "catId") Long catId,
                                         Pageable pageable) {
        var request = ProductFilter.builder()
                .categoryId(catId)
                .deleted(false)
                .published(true)
                .build();
        return productService.getProducts(request, pageable);
    }

    @GetMapping("/{id}")
    public ProductItem getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
