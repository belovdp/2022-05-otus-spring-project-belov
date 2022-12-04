package ru.otus.spring.belov.user_service.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Page<T> {
    /** Количество страниц всего */
    private int totalPages;
    /** Количество строк всего */
    private int totalElements;
    /** Строки согласно пагинации */
    private List<T> content;
}
