package ru.otus.spring.belov.product_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.domain.FileInfo;
import ru.otus.spring.belov.product_service.repository.FileInfoRepository;

import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

    private final FileInfoRepository fileInfoRepository;
    private final FilesStorageService filesStorageService;
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
        var fileInfo = fileInfoRepository.save(FileInfo.builder()
                .entityCategory(entityCategory)
                .entityId(entityId)
                .contentType(file.getContentType())
                .build());
        filesStorageService.save(file, fileInfo.getEntityCategory(), fileInfo.getEntityId(), fileInfo.getId().toString());
        return fileInfo;
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        var fileInfo = fileInfoRepository.getReferenceById(id);
        fileInfoRepository.delete(fileInfo);
        filesStorageService.delete(fileInfo.getEntityCategory(), fileInfo.getEntityId(), fileInfo.getId().toString());
    }

    @Transactional
    @Override
    public void deleteEntityFiles(EntityCategory entityCategory, Long entityId) {
        fileInfoRepository.deleteAllByEntityCategoryAndEntityId(entityCategory, entityId);
        filesStorageService.deleteEntityFiles(entityCategory, entityId);
    }

    @Override
    public FileInputStream loadFileAsStream(UUID id) {
        var fileInfo = fileInfoRepository.getReferenceById(id);
        return filesStorageService.loadFileAsStream(fileInfo.getEntityCategory(), fileInfo.getEntityId(), fileInfo.getId().toString());
    }

    @Override
    public FileInputStream loadPreviewAsStream(EntityCategory entityCategory, Long entityId) {
        return fileInfoRepository.findAllByEntityCategoryAndEntityId(entityCategory, entityId)
                .stream()
                .findFirst()
                .map(fileInfo -> filesStorageService.loadFileAsStream(fileInfo.getEntityCategory(), fileInfo.getEntityId(), fileInfo.getId().toString()))
                .orElse(filesStorageService.loadNoImageAsStream());
    }
}
