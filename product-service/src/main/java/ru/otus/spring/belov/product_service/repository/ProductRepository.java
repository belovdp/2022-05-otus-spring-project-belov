package ru.otus.spring.belov.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.belov.product_service.domain.Category;
import ru.otus.spring.belov.product_service.domain.Product;

import java.util.List;

/**
 * Репозиторий по работе с категориями
 */
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    /**
     * Возвращает все продукты не удалённые и опубликованные в категории
     * @return все продукты не удалённые и опубликованные
     */
    Page<Product> findAllByDeletedFalseAndPublishedTrue(Pageable pageable);

    /**
     * Возвращает все продукты не удалённые и опубликованные из категории
     * @return все продукты не удалённые и опубликованные
     */
    Page<Product> findAllByDeletedFalseAndPublishedTrueAndCategoriesContains(Category category, Pageable pageable);

    /**
     * Возвращает все продукты из категории
     * @return все продукты  из категории
     */
    Page<Product> findAllByCategoriesContains(Category category, Pageable pageable);

//    /**
//     * Возвращает список идентификаторов продуктов привязанных к категориям
//     * @param categoryIds идентификаторы категорий
//     * @return список идентификаторов продуктов привязанных к категориям
//     */
//    @Query("select p from Product p inner join p.categories c where c.id in :categoryIds")
//    List<Product> getAllByCategory(@Param("categoryIds") List<Long> categoryIds);

    /**
     * Возвращает все продукты из корзины
     * @return все продукты из корзины
     */
    List<Product> findAllByDeletedTrue();

    /**
     * Переносит продукты в корзину
     */
    @Modifying
    @Query("update Product p set p.deleted = true where p.id in :ids")
    void moveToTrash(@Param("ids") List<Long> ids);
}
