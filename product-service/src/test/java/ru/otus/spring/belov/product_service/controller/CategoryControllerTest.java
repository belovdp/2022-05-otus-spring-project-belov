package ru.otus.spring.belov.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.product_service.config.WebSecurityConfiguration;
import ru.otus.spring.belov.product_service.dto.category.CategoryFilter;
import ru.otus.spring.belov.product_service.dto.category.CategoryItem;
import ru.otus.spring.belov.product_service.dto.category.CategoryTreeItem;
import ru.otus.spring.belov.product_service.service.CategoryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты контроллера работы с категориями")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CategoryController.class)
@Import(WebSecurityConfiguration.class)
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Тест получения дерева категорий")
    @Test
    void getCategoriesTree() throws Exception {
        var categories = List.of(
                new CategoryTreeItem(1L, "Категория 1", false, false, true, 0, null),
                new CategoryTreeItem(2L, "Категория 2", false, false, true, 1, null)
        );
        var filterMustBe = CategoryFilter.builder().deleted(false).published(true).hideMenu(false).build();
        when(categoryService.getCategoriesTree(refEq(filterMustBe))).thenReturn(categories);
        mockMvc.perform(get("/categories/tree"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categories)));
        verify(categoryService).getCategoriesTree(refEq(filterMustBe));
    }

    @DisplayName("Тест получения категории")
    @Test
    void getCategory() throws Exception {
        var catId = 2L;
        var category = new CategoryItem(1L, "Категория 1", true, true, true, 0, 2);
        when(categoryService.getCategoryById(catId)).thenReturn(category);
        mockMvc.perform(get("/categories/{id}", catId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(category)));
    }
}