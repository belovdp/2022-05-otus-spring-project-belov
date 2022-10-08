package ru.otus.spring.belov.product_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.product_service.dto.category.CategoryFilter;
import ru.otus.spring.belov.product_service.dto.category.CategoryItem;
import ru.otus.spring.belov.product_service.dto.category.CategoryTreeItem;
import ru.otus.spring.belov.product_service.dto.category.SaveCategoryRequest;
import ru.otus.spring.belov.product_service.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для работы с категориями через админ панель
 */
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminCategoryController {

    /** Сервис по работе с категориями */
    private final CategoryService categoryService;

    /**
     * Возвращает дерево категорий для админки
     * @return дерево категорий
     */
    @GetMapping("/tree")
    public List<CategoryTreeItem> getCategoriesTree(@Valid CategoryFilter request) {
        return categoryService.getCategoriesTree(request);
    }

    /**
     * Вовзращает категорию
     * @param id идентификатор категории
     * @return категория
     */
    @GetMapping("/{id}")
    public CategoryItem getProduct(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * Возвращает категории в корзине
     * @return категории
     */
    @GetMapping("/trash")
    public List<CategoryItem> getTrash() {
        return categoryService.getTrash();
    }

    /**
     * Переносит категории в корзину
     */
    @PostMapping("/trash")
    public void getTrash(@RequestBody List<Long> ids) {
        categoryService.moveToTrash(ids);
    }

    /**
     * Переносит категории в корзину
     */
    @DeleteMapping("/")
    public void delete(@RequestBody List<Long> ids) {
        categoryService.delete(ids);
    }

    /**
     * Сохраняет/изменяет категорию
     * @param saveCategoryRequest запрос на сохранение/обновление категорий
     */
    @PostMapping("/")
    public CategoryItem save(@RequestBody @Valid SaveCategoryRequest saveCategoryRequest) {
        return categoryService.saveCategory(saveCategoryRequest);
    }
}
