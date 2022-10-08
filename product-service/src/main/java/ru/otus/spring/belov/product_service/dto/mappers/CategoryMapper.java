package ru.otus.spring.belov.product_service.dto.mappers;

import org.mapstruct.*;
import ru.otus.spring.belov.product_service.domain.Category;
import ru.otus.spring.belov.product_service.dto.category.CategoryTreeItem;
import ru.otus.spring.belov.product_service.dto.category.CategoryItem;
import ru.otus.spring.belov.product_service.dto.category.SaveCategoryRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * Конвертер категорий
 */
@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    /** Ключ группировки для категорий без родителей */
    private static final long CATEGORY_WITH_NO_PARENTS_GROUP = -1;

    /**
     * Конвертирует список категорий в категории без учёта детей
     * @param categories категории
     * @return список категорий в категории без учёта детей
     */
    public abstract List<CategoryItem> categoryToCategoryItem(List<Category> categories);

    /**
     * Конвертирует категорию в dto
     * @param categories категории
     * @return категорию
     */
    @Mapping(target = "parent", source = "parent.id")
    public abstract CategoryItem categoryToCategoryItem(Category categories);

    /**
     * Обновляет сущность на основе DTO
     * @param source   dto
     * @param parent   родительские категории
     * @param category обновляемая сущность
     */
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "title", source = "source.title")
    @Mapping(target = "deleted", source = "source.deleted")
    @Mapping(target = "hideMenu", source = "source.hideMenu")
    @Mapping(target = "published", source = "source.published")
    @Mapping(target = "sortIndex", source = "source.sortIndex")
    @Mapping(target = "parent", source = "parent")
    public abstract void updateCategoryFromDto(SaveCategoryRequest source, Category parent, @MappingTarget Category category);

    /**
     * Возвращает дерево категорий
     * @param categories список категорий
     * @return дерево категорий
     */
    public List<CategoryTreeItem> categoriesToTree(List<Category> categories) {
        var groupByParent = categories.stream()
                .collect(Collectors.groupingBy(cat -> ofNullable(cat.getParent())
                                .map(Category::getId)
                                .orElse(CATEGORY_WITH_NO_PARENTS_GROUP),
                        Collectors.toList()));
        return groupByParent.get(CATEGORY_WITH_NO_PARENTS_GROUP).stream().map(category -> categoryToCategoriesTreeItem(category, groupByParent)).toList();
    }

    /**
     * Возвращает узел дерева категорий
     * @param category       категория
     * @param groupsByParent группировка категорий по родителям
     * @return узел дерева категорий
     */
    @Mapping(target = "childs", ignore = true)
    abstract CategoryTreeItem categoryToCategoriesTreeItem(Category category, @Context Map<Long, List<Category>> groupsByParent);

    /**
     * Пост конвертер. Устанавливает детей категории в дереве
     * @param categoryDto    конвертированная сущность без детей
     * @param groupsByParent группировка категорий по родителям
     */
    @AfterMapping
    void afterMapping(@MappingTarget CategoryTreeItem categoryDto, @Context Map<Long, List<Category>> groupsByParent) {
        if (groupsByParent.containsKey(categoryDto.getId())) {
            var childs = groupsByParent.get(categoryDto.getId()).stream().map(cat -> categoryToCategoriesTreeItem(cat, groupsByParent)).toList();
            categoryDto.setChilds(childs);
        }
    }
}
