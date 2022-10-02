package ru.otus.spring.belov.product_service.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.product_service.domain.Category;
import ru.otus.spring.belov.product_service.domain.QCategory;
import ru.otus.spring.belov.product_service.dto.category.CategoryItem;
import ru.otus.spring.belov.product_service.dto.category.CategoryFilter;
import ru.otus.spring.belov.product_service.dto.category.CategoryTreeItem;
import ru.otus.spring.belov.product_service.dto.category.SaveCategoryRequest;
import ru.otus.spring.belov.product_service.dto.mappers.CategoryMapper;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;
import ru.otus.spring.belov.product_service.repository.CategoryRepository;

import java.util.List;
import java.util.stream.StreamSupport;

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
    public List<CategoryTreeItem> getCategoriesTree(CategoryFilter categoryFilter) {
        var categories = categoryRepository.findAll(getCategoryPredicate(categoryFilter), Sort.by(Sort.Direction.ASC, "sortIndex"));
        return categoryMapper.categoriesToTree(StreamSupport.stream(categories.spliterator(), false)
                .toList());
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

    @Override
    public CategoryItem getCategoryById(Long catId) {
        // TODO проверка прав
        var category = categoryRepository.getReferenceById(catId);
        return categoryMapper.categoryToCategoryItem(category);
    }

    private Predicate getCategoryPredicate(CategoryFilter categoryFilter) {
        var qCategory = QCategory.category;
        var booleanBuilder = new BooleanBuilder(Expressions.ONE.eq(1));
        if (categoryFilter.getDeleted() != null) {
            booleanBuilder.and(qCategory.deleted.eq(categoryFilter.getDeleted()));
        }
        if (categoryFilter.getPublished() != null) {
            booleanBuilder.and(qCategory.published.eq(categoryFilter.getPublished()));
        }
        if (categoryFilter.getHideMenu() != null) {
            booleanBuilder.and(qCategory.hideMenu.eq(categoryFilter.getHideMenu()));
        }
        return booleanBuilder.getValue();
    }
}
