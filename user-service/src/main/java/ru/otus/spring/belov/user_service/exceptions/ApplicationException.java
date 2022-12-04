package ru.otus.spring.belov.user_service.exceptions;

/**
 * Ошибка текст которой транслируется наружу
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Exception ex) {
        super(message, ex);
    }
}
