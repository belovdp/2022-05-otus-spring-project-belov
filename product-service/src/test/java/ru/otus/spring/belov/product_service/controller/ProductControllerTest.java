package ru.otus.spring.belov.product_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тесты контроллера работы с продуктами")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
@Import(WebSecurityConfiguration.class)
class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Тест получения продуктов")
    @Test
    void getProducts() throws Exception {
        var catId = 5L;
        var pageable = Pageable.ofSize(2);
        var filterMustBe = ProductFilter.builder().deleted(false).published(true).categoryId(catId).build();
        var products = List.of(
                new ProductItem(1L, "Категория 1", true, true, 0, new BigDecimal(12)),
                new ProductItem(2L, "Категория 2", false, false, 1, new BigDecimal(12))
        );
        var page = new PageImpl<>(products, pageable, 5);
        when(productService.getProducts(refEq(filterMustBe), any())).thenReturn(page);
        mockMvc.perform(get("/products/")
                        .param("catId", String.valueOf(catId)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
        verify(productService).getProducts(refEq(filterMustBe), any());
    }

    @DisplayName("Тест получения продукта")
    @Test
    void getProduct() throws Exception {
        var productId = 2L;
        var product = new ProductItemFull(productId, "title product", true, true, 0, "description", new BigDecimal(123), null);
        when(productService.getProductById(productId)).thenReturn(product);
        mockMvc.perform(get("/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @DisplayName("Тест получения продуктов по идентификаторам")
    @Test
    void testGetProductsByIds() throws Exception {
        var ids = List.of(1L, 2L);
        var products = List.of(
                new ProductItem(1L, "Категория 1", true, true, 0, new BigDecimal(12)),
                new ProductItem(2L, "Категория 2", false, false, 1, new BigDecimal(12))
        );
        when(productService.getActiveProductsByIds(eq(ids))).thenReturn(products);
        mockMvc.perform(get("/products/list")
                        .param("ids", "1")
                        .param("ids", "2")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(products)));
    }
}