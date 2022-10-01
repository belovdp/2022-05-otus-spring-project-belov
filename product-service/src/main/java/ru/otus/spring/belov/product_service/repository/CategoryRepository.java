package ru.otus.spring.belov.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.product_service.domain.Category;

import java.util.List;

/**
 * Репозиторий по работе с категориями
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Возвращает все категории
     * @return все категории
     */
    List<Category> findAllByOrderBySortIndex();

    /**
     * Возвращает все категории не удалённые и не скрытые
     * @return все категории не удалённые и не скрытые
     */
    List<Category> findAllByDeletedFalseAndPublishedTrueAndHideMenuFalseOrderBySortIndex();
}
