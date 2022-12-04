package ru.otus.spring.belov.product_service.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.belov.product_service.domain.Category;
import ru.otus.spring.belov.product_service.dto.category.CategoryFilter;
import ru.otus.spring.belov.product_service.dto.category.SaveCategoryRequest;
import ru.otus.spring.belov.product_service.dto.mappers.CategoryMapperImpl;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Интеграционный тест сервиса категорий")
@DataJpaTest
@Import({CategoryMapperImpl.class, CategoryServiceImpl.class})
class CategoryServiceImplTest {

    private static final Long CATEGORY_ID_IN_TRASH = 3L;
    private static final Long CATEGORY_ID_UNPUBLISHED = 5L;
    private static final Long CATEGORY_ID_HIDE = 6L;

    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Тест получения дерева категорий")
    @Test
    void getCategoriesTree() {
        var filter = new CategoryFilter();
        var categories = categoryService.getCategoriesTree(filter);
        assertEquals(4, categories.size(), "Неверное количество категорий");
        assertEquals(3, categories.get(0).getChilds().size(), "Неверное количество категорий");
        assertEquals(2, categories.get(0).getChilds().get(0).getChilds().size(), "Неверное количество категорий");

        filter.setDeleted(false);
        categories = categoryService.getCategoriesTree(filter);
        assertEquals(3, categories.size(), "Неверное количество категорий");

        filter.setPublished(true);
        categories = categoryService.getCategoriesTree(filter);
        assertEquals(2, categories.get(0).getChilds().size(), "Неверное количество категорий");

        filter.setHideMenu(false);
        categories = categoryService.getCategoriesTree(filter);
        assertEquals(1, categories.get(0).getChilds().size(), "Неверное количество категорий");
    }

    @DisplayName("Тест возвращения списка категорий")
    @Test
    void getTrash() {
        var categories = categoryService.getTrash(Pageable.unpaged());
        assertEquals(1, categories.getTotalElements());
        assertEquals(CATEGORY_ID_IN_TRASH, categories.getContent().get(0).getId());
    }

    @DisplayName("Тест переноса в корзину")
    @Test
    void moveToTrash() {
        var categoryIdToTrash = 18L;
        assertFalse(em.find(Category.class, categoryIdToTrash).isDeleted(), "Категория не должна быть в корзине");
        categoryService.moveToTrash(List.of(categoryIdToTrash));
        em.clear();
        assertTrue(em.find(Category.class, categoryIdToTrash).isDeleted(), "Категория не переместилась в корзину");
    }

    @DisplayName("Тест востановления из корзины")
    @Test
    void restoreTrash() {
        assertTrue(em.find(Category.class, CATEGORY_ID_IN_TRASH).isDeleted(), "Категория не должна быть в корзине");
        categoryService.restoreTrash(List.of(CATEGORY_ID_IN_TRASH));
        em.clear();
        assertFalse(em.find(Category.class, CATEGORY_ID_IN_TRASH).isDeleted(), "Категория не переместилась в корзину");
    }

    @DisplayName("Тест удаления")
    @Test
    void delete() {
        assertNotNull(em.find(Category.class, CATEGORY_ID_IN_TRASH), "Категория не найдена");
        categoryService.delete(List.of(CATEGORY_ID_IN_TRASH));
        assertNull(em.find(Category.class, CATEGORY_ID_IN_TRASH), "Категория не удалилася");

        assertThrows(ApplicationException.class, () -> categoryService.delete(List.of(2L)),
                "Мы должны былии свалиться с ошибкой о том что не можем удалить продукт не из корзины");
    }

    @DisplayName("Тест сохранения")
    @Test
    void saveCategory() {
        var categoryToSave = new SaveCategoryRequest(null, "title product", false, false, true, 0, 1L);
        var category = categoryService.saveCategory(categoryToSave);
        assertNotNull(category.getId(), "Категория не сохранилась");
        assertNotNull(em.find(Category.class, category.getId()), "Категория не сохранился");
    }

    @DisplayName("Тест получения категории по идентификатору")
    @Test
    void getCategoryById() {
        var category = categoryService.getCategoryById(1L);
        assertEquals(1L, category.getId());
        assertEquals("Категория 1", category.getTitle());
    }
}