package ru.otus.spring.belov.product_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EntityCategory {

    PRODUCT("products");

    /** Под-дирректория для хранения файлов */
    private String filesSubDirectory;
}
