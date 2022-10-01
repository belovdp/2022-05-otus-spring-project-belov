package ru.otus.spring.belov.product_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.product_service.dto.CategoryItem;
import ru.otus.spring.belov.product_service.dto.CategoryTreeItem;
import ru.otus.spring.belov.product_service.dto.SaveCategoryRequest;
import ru.otus.spring.belov.product_service.service.CategoryService;

import java.util.List;

/**
 * Контроллер для работы с категориями через админ панель
 */
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    /** Сервис по работе с категориями */
    private final CategoryService categoryService;

    /**
     * Возвращает дерево категорий для админки
     * @return дерево категорий
     */
    @GetMapping("/tree")
    public List<CategoryTreeItem> getCategoriesTree() {
        return categoryService.getCategoriesTree(true);
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
    public void getTrash(@RequestBody SaveCategoryRequest saveCategoryRequest) {
        categoryService.saveCategory(saveCategoryRequest);
    }
}
