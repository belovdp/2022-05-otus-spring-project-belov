package ru.otus.spring.belov.product_service.service;

import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.belov.product_service.domain.EntityCategory;

import java.io.FileInputStream;

/**
 * Сервис по работе с файлами (физически)
 */
public interface FilesStorageService {

    /**
     * Сохраняет файл
     * @param file           файл
     * @param entityCategory категория бизнес сущности
     * @param entityId       идентификатор бизнес сущности
     * @param fileName       имя файла
     */
    void save(MultipartFile file, EntityCategory entityCategory, Long entityId, String fileName);

    /**
     * Улаляет файл
     * @param entityCategory категория бизнес сущности
     * @param entityId       идентификатор бизнес сущности
     * @param fileName       имя файла
     */
    void delete(EntityCategory entityCategory, Long entityId, String fileName);

    /**
     * Удаляет все файлы сущности
     * @param entityCategory категория бизнес сущности
     * @param entityId       идентификатор бизнес сущности
     */
    void deleteEntityFiles(EntityCategory entityCategory, Long entityId);

    /**
     * Возвращает файл в stream
     * @param entityCategory категория бизнес сущности
     * @param entityId       идентификатор бизнес сущности
     * @param fileName       имя файла
     * @return файл в stream
     */
    FileInputStream loadFileAsStream(EntityCategory entityCategory, Long entityId, String fileName);

    FileInputStream loadNoImageAsStream();
}
