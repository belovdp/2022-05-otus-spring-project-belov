package ru.otus.spring.belov.product_service.service;

import ru.otus.spring.belov.product_service.dto.CategoriesTreeItem;

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
    List<CategoriesTreeItem> getCategoriesTree(boolean full);
}
