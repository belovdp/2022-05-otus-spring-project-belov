package ru.otus.spring.belov.order_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.order_service.domain.Order;

import java.util.Optional;

/**
 * Репозиторий по работе с заказами
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Возвращает все заказы пользователя
     * @param userId   идентификатор пользователя
     * @param pageable пагинация
     * @return все заказы пользователя
     */
    Page<Order> findAllByUserId(String userId, Pageable pageable);

    /**
     * Возвращает заказ пользователя
     * @param userId  идентификатор пользователя
     * @param orderId идентификатор заказа
     * @return заказ пользователя
     */
    Optional<Order> findByUserIdAndId(String userId, long orderId);
}
