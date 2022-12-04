package ru.otus.spring.belov.product_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Клиент для работы с файлами
 */
@FeignClient(name = "file-service",
        configuration = FileServiceClientConfiguration.class)
public interface FileServiceClient {

    /**
     * Удаляет файлы сущности
     * @param entityCategory бизнес сущность к которой достаём файлы
     * @param entityId       идентификатор бизнес сущности
     */
    @DeleteMapping("/files/{entityCategory}/{entityId}")
    void deleteEntityFiles(@PathVariable EntityCategory entityCategory, @PathVariable Long entityId);

    enum EntityCategory {
        PRODUCT
    }
}
