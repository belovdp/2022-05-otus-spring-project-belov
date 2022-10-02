package ru.otus.spring.belov.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.belov.product_service.domain.Category;

import java.util.List;

/**
 * Репозиторий по работе с категориями
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {

    /**
     * Возвращает все категории из корзины
     * @return все категории из корзины
     */
    List<Category> findAllByDeletedTrue();

    /**
     * Переносит категории в корзину
     */
    @Modifying
    @Query("update Category c set c.deleted = true where c.id in :ids")
    void moveToTrash(@Param("ids") List<Long> ids);
}
