package ru.otus.spring.belov.product_service.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Ошибка для потребителей нашего API
 */
@RequiredArgsConstructor
@Getter
public class ErrorInfo {

    /** Статус */
    private final String status;
    /** Сообщение */
    private final String message;
    /** Детали */
    private final String details;
}
