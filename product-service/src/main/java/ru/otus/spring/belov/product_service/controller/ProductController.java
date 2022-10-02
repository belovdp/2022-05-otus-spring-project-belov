package ru.otus.spring.belov.product_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductRequest;
import ru.otus.spring.belov.product_service.service.ProductServiceImpl;

/**
 * Контроллер для работы с продуктами
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
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
        var request = ProductRequest.builder()
                .categoryId(catId)
                .deleted(false)
                .published(true)
                .build();
        return productService.getProducts(request, pageable);
    }
}
