package ru.otus.spring.belov.product_service.service;

import ru.otus.spring.belov.product_service.dto.category.CategoryFilter;
import ru.otus.spring.belov.product_service.dto.category.CategoryTreeItem;
import ru.otus.spring.belov.product_service.dto.category.CategoryItem;
import ru.otus.spring.belov.product_service.dto.category.SaveCategoryRequest;

import java.util.List;

/**
 * Сервис по работе с категориями
 */
public interface CategoryService {

    /**
     * Возвращает дерево категорий
     * @param categoryFilter фильтр
     * @return дерево категорий
     */
    List<CategoryTreeItem> getCategoriesTree(CategoryFilter categoryFilter);

    /**
     * Возвращает категории из корзины
     * @return категории из корзины
     */
    List<CategoryItem> getTrash();

    /**
     * Переводит категории в корзину
     * @param ids идентификаторы категорий
     */
    void moveToTrash(List<Long> ids);

    /**
     * Удаляет категории
     * @param ids идентификаторы категорий
     */
    void delete(List<Long> ids);

    /**
     * Возвращает сохранённую/изменённую категорию
     * @param saveCategoryRequest запрос на изменение/сохранение
     */
    void saveCategory(SaveCategoryRequest saveCategoryRequest);

    /**
     * Возвращает категорию
     * @param catId идентификатор категории
     * @return категория
     */
    CategoryItem getCategoryById(Long catId);
}
