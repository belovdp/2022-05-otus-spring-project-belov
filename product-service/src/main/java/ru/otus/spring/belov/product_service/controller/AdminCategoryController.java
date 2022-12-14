package ru.otus.spring.belov.product_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
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
    public CategoryItem getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * Возвращает категории в корзине
     * @return категории
     */
    @GetMapping("/trash")
    public Page<CategoryItem> getTrash(Pageable pageable) {
        return categoryService.getTrash(pageable);
    }

    /**
     * Переносит категории в корзину
     */
    @PostMapping("/trash")
    public void moveToTrash(@RequestBody List<Long> ids) {
        log.info("Перенос категорий {} в корзину пользователем {}", ids, SecurityContextHolder.getContext().getAuthentication().getName());
        categoryService.moveToTrash(ids);
    }

    /**
     * Востанавливает из корзины
     * @param ids идентификаторы категорий
     */
    @PostMapping("/trash/restore")
    public void restoreTrash(@RequestBody List<Long> ids) {
        log.info("Востановление категорий {} из корзины пользователем {}", ids, SecurityContextHolder.getContext().getAuthentication().getName());
        categoryService.restoreTrash(ids);
    }

    /**
     * Переносит категории в корзину
     */
    @DeleteMapping("/")
    public void delete(@RequestBody List<Long> ids) {
        log.info("Удаление категорий {} пользователем {}", ids, SecurityContextHolder.getContext().getAuthentication().getName());
        categoryService.delete(ids);
    }

    /**
     * Сохраняет/изменяет категорию
     * @param saveCategoryRequest запрос на сохранение/обновление категорий
     */
    @PostMapping("/")
    public CategoryItem save(@RequestBody @Valid SaveCategoryRequest saveCategoryRequest) {
        log.info("Создание категории {} пользователем {}", saveCategoryRequest.getTitle(),
                SecurityContextHolder.getContext().getAuthentication().getName());
        return categoryService.saveCategory(saveCategoryRequest);
    }
}
