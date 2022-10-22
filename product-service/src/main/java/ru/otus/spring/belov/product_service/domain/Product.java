package ru.otus.spring.belov.product_service.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Продукт
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Заголовок */
    @Column(name = "title", nullable = false)
    private String title;

    /** Описание */
    @Column(name = "description")
    private String description;

    /** Цена */
    @Column(name = "price")
    private BigDecimal price;

    /** Признак что ресурс в корзине */
    @Column(name = "deleted")
    private boolean deleted;

    /** Опубликован */
    @Column(name = "published")
    private boolean published;

    /** Индекс сортировки */
    @Column(name = "sort_index", nullable = false)
    private int sortIndex;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
}
