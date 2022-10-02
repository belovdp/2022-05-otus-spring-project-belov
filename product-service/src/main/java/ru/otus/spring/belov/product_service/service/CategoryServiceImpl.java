package ru.otus.spring.belov.product_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.product_service.domain.Category;
import ru.otus.spring.belov.product_service.dto.category.CategoryItem;
import ru.otus.spring.belov.product_service.dto.category.CategoryTreeItem;
import ru.otus.spring.belov.product_service.dto.category.SaveCategoryRequest;
import ru.otus.spring.belov.product_service.dto.mappers.CategoryMapper;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;
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
    public List<CategoryTreeItem> getCategoriesTree(boolean full) {
        var categories = full ? categoryRepository.findAllByOrderBySortIndex() :
                categoryRepository.findAllByDeletedFalseAndPublishedTrueAndHideMenuFalseOrderBySortIndex();
        return categoryMapper.categoriesToTree(categories);
    }

    @Override
    public List<CategoryItem> getTrash() {
        return categoryMapper.categoryToCategoryItem(categoryRepository.findAllByDeletedTrue());
    }

    @Transactional
    @Override
    public void moveToTrash(List<Long> ids) {
        categoryRepository.moveToTrash(ids);
    }

    @Transactional
    @Override
    public void delete(List<Long> ids) {
        var cats = categoryRepository.findAllById(ids);
        if (cats.stream().anyMatch(cat -> !cat.isDeleted())) {
            throw new ApplicationException("Удалить можно только категории, находящиеся в корзине");
        }
        categoryRepository.deleteAll(cats);
    }

    @Override
    public void saveCategory(SaveCategoryRequest saveCategoryRequest) {
        var category = ofNullable(saveCategoryRequest.getId())
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new ApplicationException("Вы пытаетесь изменить не существующую категорию с идентификатором %s", saveCategoryRequest.getId())))
                .orElseGet(Category::new);
        var parentCategory = ofNullable(saveCategoryRequest.getParent())
                .flatMap(categoryRepository::findById)
                .orElse(null);
        categoryMapper.updateCategoryFromDto(saveCategoryRequest, parentCategory, category);
        categoryRepository.save(category);
    }
}
