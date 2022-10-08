package ru.otus.spring.belov.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Category> findAllByDeletedTrue(Pageable pageable);

    /**
     * Переносит категории в корзину или из карзины
     */
    @Modifying
    @Query("update Category c set c.deleted = :deleted where c.id in :ids")
    void modifyDeleteFlag(@Param("ids") List<Long> ids, @Param("deleted") boolean deleted);
}
