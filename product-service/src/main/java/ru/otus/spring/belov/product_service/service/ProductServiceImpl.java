package ru.otus.spring.belov.product_service.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.product_service.domain.Product;
import ru.otus.spring.belov.product_service.domain.QProduct;
import ru.otus.spring.belov.product_service.dto.mappers.ProductMapper;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductRequest;
import ru.otus.spring.belov.product_service.dto.product.SaveProductRequest;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;
import ru.otus.spring.belov.product_service.repository.CategoryRepository;
import ru.otus.spring.belov.product_service.repository.ProductRepository;

import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Сервис по работе с категориями
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    /** Конвертер продуктов */
    private final ProductMapper productMapper;
    /** Репозиторий по работе с продуктами */
    private final ProductRepository productRepository;
    /** Репозиторий по работе с категориями */
    private final CategoryRepository categoryRepository;

    @Override
    public Page<ProductItem> getProducts(ProductRequest productRequest, Pageable pageable) {
        var products = productRepository.findAll(getProductPredicate(productRequest), pageable);
        return products.map(productMapper::productToProductItem);
    }

    @Override
    public List<ProductItem> getTrash() {
        return productMapper.productToProductItem(productRepository.findAllByDeletedTrue());
    }

    @Override
    @Transactional
    public void moveToTrash(List<Long> ids) {
        productRepository.moveToTrash(ids);
    }

    @Override
    public void delete(List<Long> ids) {
        var cats = productRepository.findAllById(ids);
        if (cats.stream().anyMatch(cat -> !cat.isDeleted())) {
            throw new ApplicationException("Удалить можно только продукты, находящиеся в корзине");
        }
        productRepository.deleteAll(cats);
    }

    @Override
    public void saveProduct(SaveProductRequest saveProductRequest) {
        var product = ofNullable(saveProductRequest.getId())
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() ->
                                new ApplicationException("Вы пытаетесь изменить не существующую продукт с идентификатором %s", saveProductRequest.getId())))
                .orElseGet(Product::new);
        var categories = ofNullable(saveProductRequest.getCategories())
                .map(categoryRepository::findAllById)
                .orElse(null);
        productMapper.updateProductFromDto(saveProductRequest, categories, product);
        productRepository.save(product);
    }

    private Predicate getProductPredicate(ProductRequest productRequest) {
        var qProduct = QProduct.product;
        var booleanBuilder = new BooleanBuilder(Expressions.ONE.eq(1));
        if (productRequest.getCategoryId() != null) {
            var category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ApplicationException("Не найдена просматриваемая категория"));
            booleanBuilder.and(qProduct.categories.contains(category));
        }
        if (productRequest.getDeleted() != null) {
            booleanBuilder.and(qProduct.deleted.eq(productRequest.getDeleted()));
        }
        if (productRequest.getPublished() != null) {
            booleanBuilder.and(qProduct.deleted.eq(productRequest.getPublished()));
        }
        return booleanBuilder.getValue();
    }
}