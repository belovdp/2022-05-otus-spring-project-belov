package ru.otus.spring.belov.product_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.domain.FileInfo;
import ru.otus.spring.belov.product_service.service.FilesService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/files")
public class FilesController {

    private final FilesService filesService;

    /**
     * Возвращает файл по идентификатору
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     * @return идентификаторы файлов
     */
    @GetMapping("/{entityCategory}/{entityId}")
    public List<String> findFilesId(@PathVariable EntityCategory entityCategory,
                                    @PathVariable Long entityId) {
        return filesService.findFiles(entityCategory, entityId);
    }

    /**
     * Сохраняет файл
     * @param file           файл
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     */
    @PostMapping(value = "/{entityCategory}/{entityId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileInfo uploadFiles(@RequestParam(value = "file") MultipartFile file,
                                      @PathVariable EntityCategory entityCategory,
                                      @PathVariable Long entityId) {
        return filesService.save(file, entityCategory, entityId);
    }

    /**
     * Удаляет файл
     * @param id идентификатор файла
     */
    @DeleteMapping(value = "/{id}")
    public void deleteFile(UUID id) {
        filesService.delete(id);
    }

    /**
     * Удаляет файлы сущности
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     */
    @DeleteMapping("/{entityCategory}/{entityId}")
    public void deleteEntityFiles(@PathVariable EntityCategory entityCategory,
                                  @PathVariable Long entityId) {
        filesService.deleteEntityFiles(entityCategory, entityId);
    }

    /**
     * Возвращает файл
     */
    @GetMapping(value = "/download/{id}")
    public void getFile(@PathVariable UUID id, HttpServletResponse response) {
        filesService.loadDocumentAsStream(id, response);
    }
}
