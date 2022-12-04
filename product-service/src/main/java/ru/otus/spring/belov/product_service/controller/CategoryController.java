package ru.otus.spring.belov.product_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.belov.product_service.dto.category.CategoryItem;
import ru.otus.spring.belov.product_service.dto.category.CategoryFilter;
import ru.otus.spring.belov.product_service.dto.category.CategoryTreeItem;
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
     * Возвращает дерево категорий для юзеров. Без скрытых и удалённых
     * @return дерево категорий
     */
    @GetMapping("/tree")
    public List<CategoryTreeItem> getCategoriesTree() {
        var filter = CategoryFilter.builder()
                .deleted(false)
                .hideMenu(false)
                .published(true)
                .build();
        return categoryService.getCategoriesTree(filter);
    }

    @GetMapping("/{id}")
    public CategoryItem getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}
