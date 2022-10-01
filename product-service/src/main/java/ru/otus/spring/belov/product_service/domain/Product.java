package ru.otus.spring.belov.product_service.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    /** Признак что ресурс в корзине */
    @Column(name = "deleted")
    private boolean deleted;

    /** Опубликован */
    @Column(name = "published")
    private boolean published;

    /** Индекс сортировки */
    @Column(name = "sort_index", nullable = false)
    private int sortIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Image> images;
}
