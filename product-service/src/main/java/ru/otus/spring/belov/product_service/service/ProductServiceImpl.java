package ru.otus.spring.belov.product_service.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.belov.product_service.domain.Product;
import ru.otus.spring.belov.product_service.domain.QProduct;
import ru.otus.spring.belov.product_service.dto.mappers.ProductMapper;
import ru.otus.spring.belov.product_service.dto.product.ProductItem;
import ru.otus.spring.belov.product_service.dto.product.ProductFilter;
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
    public Page<ProductItem> getProducts(ProductFilter productFilter, Pageable pageable) {
        var products = productRepository.findAll(getProductPredicate(productFilter), pageable);
        return products.map(productMapper::productToProductItem);
    }

    @Override
    @PostAuthorize("hasAnyRole('VIEWER', 'EDITOR', 'ADMIN') or ((returnObject.deleted == false) and (returnObject.published = true))")
    public ProductItem getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Товар не найден"));
        return productMapper.productToProductItem(product);
    }

    @Override
    public Page<ProductItem> getTrash(Pageable pageable) {
        return productRepository.findAllByDeletedTrue(pageable).map(productMapper::productToProductItem);
    }

    @Transactional
    @Override
    public void moveToTrash(List<Long> ids) {
        productRepository.modifyDeleteFlag(ids, true);
    }

    @Transactional
    @Override
    public void restoreTrash(List<Long> ids) {
        productRepository.modifyDeleteFlag(ids, false);
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

    private Predicate getProductPredicate(ProductFilter productFilter) {
        var qProduct = QProduct.product;
        var booleanBuilder = new BooleanBuilder(Expressions.ONE.eq(1));
        if (productFilter.getCategoryId() != null) {
            var category = categoryRepository.findById(productFilter.getCategoryId())
                    .orElseThrow(() -> new ApplicationException("Не найдена просматриваемая категория"));
            booleanBuilder.and(qProduct.categories.contains(category));
        }
        if (productFilter.getDeleted() != null) {
            booleanBuilder.and(qProduct.deleted.eq(productFilter.getDeleted()));
        }
        if (productFilter.getPublished() != null) {
            booleanBuilder.and(qProduct.published.eq(productFilter.getPublished()));
        }
        return booleanBuilder.getValue();
    }
}