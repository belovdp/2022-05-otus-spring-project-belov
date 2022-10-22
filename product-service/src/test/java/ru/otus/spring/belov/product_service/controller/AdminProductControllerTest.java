package ru.otus.spring.belov.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.product_service.config.WebSecurityConfiguration;
import ru.otus.spring.belov.product_service.dto.product.ProductFilter;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductItemFull;
import ru.otus.spring.belov.product_service.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты контроллера работы с продуктами для админки")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminProductController.class)
@Import(WebSecurityConfiguration.class)
class AdminProductControllerTest {

    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Тест получения продуктов")
    @WithMockUser(roles = "VIEWER")
    @Test
    void getProducts() throws Exception {
        var pageable = Pageable.ofSize(2);
        var filter = ProductFilter.builder().deleted(true).published(false).build();
        var products = List.of(
                new ProductItem(1L, "Категория 1", true, true, 0, new BigDecimal(12)),
                new ProductItem(2L, "Категория 2", false, false, 1, new BigDecimal(12))
        );
        var page = new PageImpl<>(products, pageable, 5);
        when(productService.getProducts(refEq(filter), any())).thenReturn(page);
        mockMvc.perform(get("/admin/products/")
                        .param("deleted", String.valueOf(filter.getDeleted()))
                        .param("published", String.valueOf(filter.getPublished()))
                        .param("pageSize", String.valueOf(pageable.getPageSize()))
                        .param("pageNumber", String.valueOf(pageable.getPageNumber()))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @DisplayName("Тест получения продукта")
    @WithMockUser(roles = "VIEWER")
    @Test
    void getProduct() throws Exception {
        var productId = 2L;
        var product = new ProductItemFull(productId, "title product", true, true, 0, "description", new BigDecimal(123), null);
        when(productService.getProductById(productId)).thenReturn(product);
        mockMvc.perform(get("/admin/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @DisplayName("Тест получения продуктов из корзины")
    @WithMockUser(roles = "VIEWER")
    @Test
    void getTrash() throws Exception {
        var products = List.of(new ProductItem(1L, "title product", true, true, 0, new BigDecimal(123)));
        var pageable = Pageable.ofSize(2);
        var page = new PageImpl<>(products, pageable, 5);
        when(productService.getTrash(any())).thenReturn(page);
        mockMvc.perform(get("/admin/products/trash"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @DisplayName("Тест переноса продуктов в корзину")
    @WithMockUser(roles = "EDITOR")
    @Test
    void moveToTrash() throws Exception {
        var ids = List.of(1L, 2L);
        mockMvc.perform(post("/admin/products/trash")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());
        verify(productService).moveToTrash(eq(ids));
    }

    @DisplayName("Тест востановления из корзины")
    @WithMockUser(roles = "EDITOR")
    @Test
    void restoreTrash() throws Exception {
        var ids = List.of(1L, 2L);
        mockMvc.perform(post("/admin/products/trash/restore")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());
        verify(productService).restoreTrash(eq(ids));
    }

    @DisplayName("Тест удаления")
    @WithMockUser(roles = "EDITOR")
    @Test
    void testDelete() throws Exception {
        var ids = List.of(1L, 2L);
        mockMvc.perform(delete("/admin/products/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());
        verify(productService).delete(eq(ids));
    }

    @DisplayName("Тест сохранения")
    @WithMockUser(roles = "EDITOR")
    @Test
    void save() throws Exception {
        var result = new ProductItemFull(1L, "title product", true, true, 0, "description", new BigDecimal(123), null);
        when(productService.saveProduct(refEq(result))).thenReturn(result);
        mockMvc.perform(post("/admin/products/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(result)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }
}