package ru.otus.spring.belov.product_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.domain.FileInfo;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;
import ru.otus.spring.belov.product_service.service.FilesService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/files")
public class FilesController {

    private final FilesService filesService;

    /**
     * Возвращает файл по идентификатору сущности и типу сущности
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     * @return идентификаторы файлов
     */
    @GetMapping("/{entityCategory}/{entityId}")
    public List<UUID> getFilesId(@PathVariable EntityCategory entityCategory,
                                 @PathVariable Long entityId) {
        return filesService.findFiles(entityCategory, entityId);
    }

    /**
     * Сохраняет файл
     * @param file           файл
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     */
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/{entityCategory}/{entityId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileInfo uploadFiles(@RequestParam(value = "file") MultipartFile file,
                                @PathVariable EntityCategory entityCategory,
                                @PathVariable Long entityId) {
        log.info("Сохранение файла для {} с id {} пользователем {}", entityCategory, entityId,
                SecurityContextHolder.getContext().getAuthentication().getName());
        return filesService.save(file, entityCategory, entityId);
    }

    /**
     * Удаляет файл
     * @param id идентификатор файла
     */
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/{id}")
    public void deleteFile(@PathVariable UUID id) {
        log.info("Удаление файла с id {} пользователем {}", id, SecurityContextHolder.getContext().getAuthentication().getName());
        filesService.delete(id);
    }

    /**
     * Удаляет файлы сущности
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     */
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{entityCategory}/{entityId}")
    public void deleteEntityFiles(@PathVariable EntityCategory entityCategory,
                                  @PathVariable Long entityId) {
        log.info("Удаление файлов для {} с id {} пользователем {}", entityCategory, entityId, SecurityContextHolder.getContext().getAuthentication().getName());
        filesService.deleteEntityFiles(entityCategory, entityId);
    }

    /**
     * Возвращает файл
     */
    @GetMapping(value = "/{id}")
    public void getFile(@PathVariable UUID id, HttpServletResponse response) {
        try (var is = filesService.loadFileAsStream(id)) {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new ApplicationException("Не удалось скачать файл", ex);
        }
    }

    /**
     * Возвращает превью файл для сущности
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     */
    @GetMapping("/{entityCategory}/{entityId}/preview")
    public void getPreviewFile(@PathVariable EntityCategory entityCategory,
                               @PathVariable Long entityId,
                               HttpServletResponse response) {
        try (var is = filesService.loadPreviewAsStream(entityCategory, entityId)) {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new ApplicationException("Не удалось скачать файл", ex);
        }
    }
}
