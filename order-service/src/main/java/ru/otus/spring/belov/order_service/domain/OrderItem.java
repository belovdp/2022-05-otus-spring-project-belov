package ru.otus.spring.belov.order_service.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Продукты заказа
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Идентификатор товара */
    @Column(name = "product_id")
    private Long productId;

    /** Заголовок товара */
    @Column(name = "title")
    private String title;

    /** Цена */
    @Column(name = "price")
    private BigDecimal price;

    /** Количество */
    @Column(name = "count")
    private int count;

    /** Родительская категория */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
