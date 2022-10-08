package ru.otus.spring.belov.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.belov.product_service.domain.Product;

import java.util.List;

/**
 * Репозиторий по работе с категориями
 */
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    /**
     * Возвращает все продукты из корзины
     * @return все продукты из корзины
     */
    Page<Product> findAllByDeletedTrue(Pageable pageable);

    /**
     * Переносит продукты в корзину
     */
    @Modifying
    @Query("update Product p set p.deleted = true where p.id in :ids")
    void moveToTrash(@Param("ids") List<Long> ids);
}
