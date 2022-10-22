package ru.otus.spring.belov.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.product_service.config.WebSecurityConfiguration;
import ru.otus.spring.belov.product_service.dto.category.CategoryFilter;
import ru.otus.spring.belov.product_service.dto.category.CategoryItem;
import ru.otus.spring.belov.product_service.dto.category.CategoryTreeItem;
import ru.otus.spring.belov.product_service.dto.category.SaveCategoryRequest;
import ru.otus.spring.belov.product_service.service.CategoryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты контроллера работы с категориями для админки")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminCategoryController.class)
@Import(WebSecurityConfiguration.class)
class AdminCategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Тест получения дерева категорий")
    @WithMockUser(roles = "VIEWER")
    @Test
    void getCategoriesTree() throws Exception {
        var filter = CategoryFilter.builder().deleted(true).published(false).hideMenu(true).build();
        var categories = List.of(
                new CategoryTreeItem(1L, "Категория 1", true, true, true, 0, null),
                new CategoryTreeItem(2L, "Категория 2", false, false, false, 1, null)
        );
        when(categoryService.getCategoriesTree(refEq(filter))).thenReturn(categories);
        mockMvc.perform(get("/admin/categories/tree")
                        .param("deleted", String.valueOf(filter.getDeleted()))
                        .param("published", String.valueOf(filter.getPublished()))
                        .param("hideMenu", String.valueOf(filter.getHideMenu())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categories)));
    }

    @DisplayName("Тест получения продукта")
    @WithMockUser(roles = "VIEWER")
    @Test
    void getCategory() throws Exception {
        var catId = 2L;
        var category = new CategoryItem(1L, "Категория 1", true, true, true, 0, 2);
        when(categoryService.getCategoryById(catId)).thenReturn(category);
        mockMvc.perform(get("/admin/categories/{id}", catId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(category)));
    }

    @DisplayName("Тест получения корзины категорий")
    @WithMockUser(roles = "VIEWER")
    @Test
    void getTrash() throws Exception {
        var request = Pageable.ofSize(10);
        Page<CategoryItem> page = Page.empty(request);
        when(categoryService.getTrash(request)).thenReturn(page);
        mockMvc.perform(get("/admin/categories/trash")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @DisplayName("Тест перемещения в корзину категорий")
    @WithMockUser(roles = "EDITOR")
    @Test
    void moveToTrash() throws Exception {
        var ids = List.of(1L, 2L);
        mockMvc.perform(post("/admin/categories/trash")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());
        verify(categoryService).moveToTrash(eq(ids));
    }

    @DisplayName("Тест востановления из корзины категорий")
    @WithMockUser(roles = "EDITOR")
    @Test
    void restoreTrash() throws Exception {
        var ids = List.of(1L, 2L);
        mockMvc.perform(post("/admin/categories/trash/restore")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());
        verify(categoryService).restoreTrash(eq(ids));
    }

    @DisplayName("Тест удаления")
    @WithMockUser(roles = "EDITOR")
    @Test
    void testDelete() throws Exception {
        var ids = List.of(1L, 2L);
        mockMvc.perform(delete("/admin/categories/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());
        verify(categoryService).delete(eq(ids));
    }

    @DisplayName("Тест сохранения")
    @WithMockUser(roles = "EDITOR")
    @Test
    void save() throws Exception {
        var request = new SaveCategoryRequest(1L, "Category 1", false, false, false, 0, 2L);
        var result = new CategoryItem(1L, "Категория 1", true, true, true, 0, 2);
        when(categoryService.saveCategory(refEq(request))).thenReturn(result);
        mockMvc.perform(post("/admin/categories/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }
}