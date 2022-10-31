package ru.otus.spring.belov.product_service.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.domain.FileInfo;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;
import ru.otus.spring.belov.product_service.repository.FileInfoRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

    private final FileInfoRepository fileInfoRepository;
    @Value("${file-service.dir}")
    private String dir;
    @Value("${file-service.no-image}")
    private String noImage;

    @Override
    public List<UUID> findFiles(EntityCategory entityCategory, Long entityId) {
        return fileInfoRepository.findAllByEntityCategoryAndEntityId(entityCategory, entityId)
                .stream()
                .map(FileInfo::getId)
                .toList();
    }

    @Transactional
    @Override
    public FileInfo save(MultipartFile file, EntityCategory entityCategory, Long entityId) {
        try {
            var fileInfo = fileInfoRepository.save(FileInfo.builder()
                    .entityCategory(entityCategory)
                    .entityId(entityId)
                    .contentType(file.getContentType())
                    .build());
            var path = getFilePath(fileInfo);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
            }
            Files.copy(file.getInputStream(), path);
            return fileInfo;
        } catch (IOException e) {
            throw new ApplicationException("Ошибка сохранения файла", e);
        }
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        try {
            var fileInfo = fileInfoRepository.getReferenceById(id);
            var deleteFile = getFilePath(fileInfo).toFile();
            if (deleteFile.exists()) {
                FileUtils.forceDelete(deleteFile);
            }
            fileInfoRepository.delete(fileInfo);
        } catch (IOException e) {
            throw new ApplicationException("Ошибка удаления файла", e);
        }
    }

    @Transactional
    @Override
    public void deleteEntityFiles(EntityCategory entityCategory, Long entityId) {
        try {
            fileInfoRepository.deleteAllByEntityCategoryAndEntityId(entityCategory, entityId);
            FileUtils.forceDelete(Paths.get(dir, entityCategory.getFilesSubDirectory(), String.valueOf(entityId)).toFile());
        } catch (IOException e) {
            throw new ApplicationException("Ошибка удаления файлов для {} {}", entityCategory, entityId, e);
        }
    }

    @Override
    public void loadFileAsStream(UUID id, HttpServletResponse response) {
        var fileInfo = fileInfoRepository.getReferenceById(id);
        File readFile = getFilePath(fileInfo).toFile();
        try (var is = new FileInputStream(readFile)) {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new ApplicationException("Не удалось скачать файл", ex);
        }
    }

    @Override
    public void loadPreviewAsStream(EntityCategory entityCategory, Long entityId, HttpServletResponse response) {
        File readFile = fileInfoRepository.findAllByEntityCategoryAndEntityId(entityCategory, entityId)
                .stream()
                .findFirst()
                .map(this::getFilePath)
                .orElse(Paths.get(noImage))
                .toFile();
        try (var is = new FileInputStream(readFile)) {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new ApplicationException("Не удалось скачать файл", ex);
        }
    }

    private Path getFilePath(FileInfo fileInfo) {
        return Paths.get(dir, fileInfo.getEntityCategory().getFilesSubDirectory(), String.valueOf(fileInfo.getEntityId()), fileInfo.getId().toString());
    }
}
