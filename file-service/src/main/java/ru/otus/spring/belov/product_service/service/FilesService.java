package ru.otus.spring.belov.product_service.service;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.domain.FileInfo;

import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

/**
 * Сервис по работе с файлами
 */
public interface FilesService {

    /**
     * Возвращает файл по идентификатору
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     * @return идентификаторы файлов
     */
    List<UUID> findFiles(EntityCategory entityCategory, Long entityId);

    /**
     * Сохраняет файл
     */
    FileInfo save(MultipartFile file, EntityCategory entityCategory, Long entityId);

    /**
     * Удаляет файл
     * @param id идентификатор файла
     */
    void delete(UUID id);

    /**
     * Удаляет файлы сущности
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     */
    void deleteEntityFiles(EntityCategory entityCategory, Long entityId);

    FileInputStream loadFileAsStream(UUID id);

    FileInputStream loadPreviewAsStream(EntityCategory entityCategory, Long entityId);
}
