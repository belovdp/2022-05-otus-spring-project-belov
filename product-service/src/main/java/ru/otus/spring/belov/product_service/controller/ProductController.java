package ru.otus.spring.belov.product_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductFilter;
import ru.otus.spring.belov.product_service.dto.product.ProductItemFull;
import ru.otus.spring.belov.product_service.service.ProductService;
import ru.otus.spring.belov.product_service.service.ProductServiceImpl;

import java.util.List;

/**
 * Контроллер для работы с продуктами
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    /** Сервис по работе с продуктами */
    private final ProductService productService;

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
    public ProductItemFull getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/list")
    public List<ProductItem> getProductsByIds(@RequestParam(name = "ids") List<Long> ids) {
        return productService.getActiveProductsByIds(ids);
    }
}
