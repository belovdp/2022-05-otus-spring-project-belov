package ru.otus.spring.belov.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.domain.FileInfo;

import java.util.List;
import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID> {

    List<FileInfo> findAllByEntityCategoryAndEntityId(EntityCategory entityCategory, Long entityId);

    void deleteAllByEntityCategoryAndEntityId(EntityCategory entityCategory, Long entityId);
}
