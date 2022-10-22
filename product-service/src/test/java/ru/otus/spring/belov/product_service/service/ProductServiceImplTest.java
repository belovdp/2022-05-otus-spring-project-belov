package ru.otus.spring.belov.product_service.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.belov.product_service.domain.Product;
import ru.otus.spring.belov.product_service.dto.mappers.ProductMapperImpl;
import ru.otus.spring.belov.product_service.dto.product.ProductFilter;
import ru.otus.spring.belov.product_service.dto.product.ProductItemFull;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Интеграционный тест сервиса продуктов")
@DataJpaTest
@Import({ProductServiceImpl.class, ProductMapperImpl.class})
class ProductServiceImplTest {

    private static final Long PRODUCT_ID_IN_TRASH = 19L;
    private static final Long PRODUCT_ID_UNPUBLISHED = 5L;

    @Autowired
    private ProductService productService;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Тест получения продуктов")
    @Test
    void getProducts() {
        var filter = new ProductFilter();
        var products = productService.getProducts(filter, Pageable.ofSize(5));
        assertEquals(55, products.getTotalElements(), "Неверное количество элементов");
        assertEquals(11, products.getTotalPages(), "Неверное количество страниц");

        filter.setCategoryId(1L);
        products = productService.getProducts(filter, Pageable.ofSize(6));
        assertEquals(16, products.getTotalElements(), "Неверное количество элементов");
        assertEquals(3, products.getTotalPages(), "Неверное количество страниц");

        filter.setPublished(true);
        products = productService.getProducts(filter, Pageable.ofSize(5));
        assertEquals(15, products.getTotalElements(), "Неверное количество элементов");
        assertEquals(3, products.getTotalPages(), "Неверное количество страниц");

        filter.setDeleted(false);
        products = productService.getProducts(filter, Pageable.ofSize(5));
        assertEquals(14, products.getTotalElements(), "Неверное количество элементов");
        assertEquals(3, products.getTotalPages(), "Неверное количество страниц");
    }

    @DisplayName("Тест получения продукта по идентификатору")
    @Test
    void getProductById() {
        var product = productService.getProductById(44L);
        assertEquals(44L, product.getId());
        assertEquals("Продукт 44", product.getTitle());
        assertEquals(new BigDecimal(199), product.getPrice());
        assertEquals(4, product.getCategories().size());
    }

    @DisplayName("Тест получения активных продуктов по идентификаторам")
    @Test
    void getActiveProductsByIds() {
        var result = productService.getActiveProductsByIds(List.of(
                PRODUCT_ID_UNPUBLISHED,
                PRODUCT_ID_IN_TRASH,
                1L, 2L, 3L
        ));
        assertEquals(3, result.size());
        assertEquals(0, result.stream()
                .filter(product -> List.of(PRODUCT_ID_UNPUBLISHED, PRODUCT_ID_IN_TRASH).contains(product.getId()))
                .count());
    }

    @DisplayName("Тест получения корзины продуктов")
    @Test
    void getTrash() {
        var trash = productService.getTrash(Pageable.unpaged());
        assertEquals(1, trash.getTotalElements());
        assertEquals(PRODUCT_ID_IN_TRASH, trash.getContent().get(0).getId());
    }

    @DisplayName("Тест переноса в корзину")
    @Test
    void moveToTrash() {
        var productIdToTrash = 18L;
        assertFalse(em.find(Product.class, productIdToTrash).isDeleted(), "Продукт не должен быть в корзине");
        productService.moveToTrash(List.of(productIdToTrash));
        em.clear();
        assertTrue(em.find(Product.class, productIdToTrash).isDeleted(), "Продукт не переместился в корзину");
    }

    @DisplayName("Тест востановления из корзины")
    @Test
    void restoreTrash() {
        assertTrue(em.find(Product.class, PRODUCT_ID_IN_TRASH).isDeleted(), "Продукт должен быть в корзине");
        productService.restoreTrash(List.of(PRODUCT_ID_IN_TRASH));
        em.clear();
        assertFalse(em.find(Product.class, PRODUCT_ID_IN_TRASH).isDeleted(), "Продукт не востановился из корзины");
    }

    @DisplayName("Тест удаления")
    @Test
    void delete() {
        assertNotNull(em.find(Product.class, PRODUCT_ID_IN_TRASH), "Продукт не найден");
        productService.delete(List.of(PRODUCT_ID_IN_TRASH));
        assertNull(em.find(Product.class, PRODUCT_ID_IN_TRASH), "Продукт не удалился");

        assertThrows(ApplicationException.class, () -> productService.delete(List.of(2L)),
                "Мы должны былии свалиться с ошибкой о том что не можем удалить продукт не из корзины");
    }

    @DisplayName("Тест сохранения")
    @Test
    void saveProduct() {
        var productToSave = new ProductItemFull(null, "title product", true, true, 0, "description", new BigDecimal(123), List.of(1L, 5L, 9L));
        var product = productService.saveProduct(productToSave);
        assertNotNull(product.getId(), "Продукт не сохранился");
        assertNotNull(em.find(Product.class, product.getId()), "Продукт не сохранился");
    }
}