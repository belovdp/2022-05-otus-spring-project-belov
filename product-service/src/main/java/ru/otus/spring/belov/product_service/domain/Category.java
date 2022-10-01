package ru.otus.spring.belov.product_service.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@NamedEntityGraph(name = "Category.childs",
        attributeNodes = @NamedAttributeNode("childs"))
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

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "parent")
    private List<Category> childs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
}
