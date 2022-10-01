package ru.otus.spring.belov.product_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.product_service.domain.Category;
import ru.otus.spring.belov.product_service.dto.CategoriesTreeItem;
import ru.otus.spring.belov.product_service.dto.mappers.CategoryMapper;
import ru.otus.spring.belov.product_service.repository.CategoryRepository;

import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Сервис по работе с категориями
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    /** Конвертер категорий */
    private final CategoryMapper categoryMapper;
    /** Репозиторий по работе с категориями */
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoriesTreeItem> getCategoriesTree(boolean full) {
        var categories = full ? categoryRepository.findAllByOrderBySortIndex() :
                categoryRepository.findAllByDeletedFalseAndPublishedTrueAndHideMenuFalseOrderBySortIndex();
        return categoryMapper.categoriesToTree(categories);
    }
}
