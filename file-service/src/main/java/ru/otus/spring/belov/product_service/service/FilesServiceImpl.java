package ru.otus.spring.belov.product_service.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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

    @Override
    public List<String> findFiles(EntityCategory entityCategory, Long entityId) {
        return fileInfoRepository.findAllByEntityCategoryAndEntityId(entityCategory, entityId)
                .stream()
                .map(FileInfo::getId)
                .map(UUID::toString)
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

    @Override
    public void deleteEntityFiles(EntityCategory entityCategory, Long entityId) {
        try {
            var filesInfo = fileInfoRepository.findAllByEntityCategoryAndEntityId(entityCategory, entityId);
            for (var fileInfo : filesInfo) {
                var deleteFile = getFilePath(fileInfo).toFile();
                if (deleteFile.exists()) {
                    FileUtils.forceDelete(deleteFile);
                }
            }
            fileInfoRepository.deleteAll(filesInfo);
        } catch (IOException e) {
            throw new ApplicationException("Ошибка удаления файлов для {} {}", entityCategory, entityId, e);
        }
    }

    @Override
    public void loadDocumentAsStream(UUID id, HttpServletResponse response) {
        var fileInfo = fileInfoRepository.getReferenceById(id);
        File readFile = getFilePath(fileInfo).toFile();
        try (var is = new FileInputStream(readFile)) {
            IOUtils.copy(is, response.getOutputStream());
            response.setContentType(fileInfo.getContentType());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.getId() + "\"");
            response.flushBuffer();
        } catch (IOException ex) {
            throw new ApplicationException("Не удалось скачать файл", ex);
        }
    }

    private Path getFilePath(FileInfo fileInfo) {
        return Paths.get(dir, fileInfo.getEntityCategory().getFilesSubDirectory(), String.valueOf(fileInfo.getEntityId()), fileInfo.getId().toString());
    }
}
