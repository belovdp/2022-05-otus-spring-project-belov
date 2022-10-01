package ru.otus.spring.belov.product_service.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Категория продуктов
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Заголовок */
    @Column(name = "title", nullable = false)
    private String title;

    /** Признак что ресурс в корзине */
    @Column(name = "deleted")
    private boolean deleted;

    /** Скрыт в меню */
    @Column(name = "hide")
    private boolean hideMenu;

    /** Опубликован */
    @Column(name = "published")
    private boolean published;

    /** Индекс сортировки */
    @Column(name = "sort_index", nullable = false)
    private int sortIndex;

    /** Дочерние категории */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> childs;

    /** Родительская категория */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    /**
     * Продуткы
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}
