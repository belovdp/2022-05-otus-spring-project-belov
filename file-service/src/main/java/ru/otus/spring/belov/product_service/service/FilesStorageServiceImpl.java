package ru.otus.spring.belov.product_service.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    @Value("${file-service.dir}")
    private String dir;
    @Value("${file-service.no-image}")
    private String noImage;

    @Override
    public void save(MultipartFile file, EntityCategory entityCategory, Long entityId, String fileName) {
        try {
            var path = getFilePath(entityCategory, entityId, fileName);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
            }
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ApplicationException("Ошибка сохранения файла", e);
        }
    }

    @Override
    public void delete(EntityCategory entityCategory, Long entityId, String fileName) {
        try {
            var deleteFile = getFilePath(entityCategory, entityId, fileName).toFile();
            if (deleteFile.exists()) {
                FileUtils.forceDelete(deleteFile);
            }
        } catch (IOException e) {
            throw new ApplicationException("Ошибка удаления файла", e);
        }
    }

    @Override
    public void deleteEntityFiles(EntityCategory entityCategory, Long entityId) {
        try {
            var deleteFile = Paths.get(dir, entityCategory.getFilesSubDirectory(), String.valueOf(entityId)).toFile();
            if (deleteFile.exists()) {
                FileUtils.forceDelete(deleteFile);
            }
        } catch (IOException e) {
            throw new ApplicationException("Ошибка удаления файлов для %s %s", entityCategory, entityId, e);
        }
    }

    @Override
    public FileInputStream loadFileAsStream(EntityCategory entityCategory, Long entityId, String fileName) {
        File readFile = getFilePath(entityCategory, entityId, fileName).toFile();
        try {
            return new FileInputStream(readFile);
        } catch (FileNotFoundException e) {
            throw new ApplicationException("Файл не найден");
        }
    }

    @Override
    public FileInputStream loadNoImageAsStream() {
        try {
            return new FileInputStream(Paths.get(noImage).toFile());
        } catch (FileNotFoundException e) {
            throw new ApplicationException("Файл не найден");
        }
    }

    private Path getFilePath(EntityCategory entityCategory, Long entityId, String fileName) {
        return Paths.get(dir, entityCategory.getFilesSubDirectory(), String.valueOf(entityId), fileName);
    }
}
