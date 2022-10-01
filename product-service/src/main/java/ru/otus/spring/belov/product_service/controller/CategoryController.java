package ru.otus.spring.belov.product_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.belov.product_service.dto.CategoriesTreeItem;
import ru.otus.spring.belov.product_service.service.CategoryService;

import java.util.List;

/**
 * Контроллер для работы с категориями
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    /** Сервис по работе с категориями */
    private final CategoryService categoryService;

    /**
     * Возвращает дерево категорий
     * @return дерево категорий
     */
    @GetMapping("/tree")
    public List<CategoriesTreeItem> getCategoriesTree(@RequestParam(required = false) boolean full) {
        return categoryService.getCategoriesTree(full);
    }
}
