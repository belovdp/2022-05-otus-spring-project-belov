package ru.otus.spring.belov.product_service.service;

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
     * @param full признак того что отображаем всё дерево (вместе с теми что скрыты и теми что в корзине)
     * @return дерево категорий
     */
    List<CategoryTreeItem> getCategoriesTree(boolean full);

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
}
