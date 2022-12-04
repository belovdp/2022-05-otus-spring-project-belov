package ru.otus.spring.belov.product_service.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import ru.otus.spring.belov.product_service.PgBaseTest;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.exceptions.ApplicationException;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тест функциональности хрения файлов")
class FilesServiceImplTest extends PgBaseTest {

    private static final String TEST_RESOURCE_FILE_PATH = "classpath:mimimi.jpg";
    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private FilesService filesService;
    @Autowired
    private FilesStorageService filesStorageService;

    @Test
    @DisplayName("Тестирует возвращение идентификатор файлов для бизнес сущности")
    void findFiles() {
        var fileInfos = filesService.findFiles(EntityCategory.PRODUCT, 1L);
        assertThat(fileInfos)
                .usingRecursiveComparison()
                .isEqualTo(List.of(
                        UUID.fromString("4386618c-0026-4eba-b448-7bc10eb53df6"),
                        UUID.fromString("be66ff77-21f3-454c-8040-e6af2dc35d91"))
                );
    }

    @Test
    @DisplayName("Тестирует жизненный цикл файла")
    void saveAndLoadAndDeleteTest() throws IOException {
        // Проверяем сохранение
        var file = new MockMultipartFile("file", resourceLoader.getResource(TEST_RESOURCE_FILE_PATH).getInputStream());
        var info = filesService.save(file, EntityCategory.PRODUCT, 2L);

        // Проверяем чтение
        try (var actualInputStream = filesService.loadFileAsStream(info.getId());
             var expectedInputStream = file.getInputStream()) {
            assertArrayEquals(actualInputStream.readAllBytes(), expectedInputStream.readAllBytes());
        }

        // Проверяем удаление
        filesService.delete(info.getId());
        assertThrows(EntityNotFoundException.class, () -> {
            try (var is = filesService.loadFileAsStream(info.getId())) {
            }
        });
        assertThrows(ApplicationException.class, () -> {
            try (var is = filesStorageService.loadFileAsStream(info.getEntityCategory(), info.getEntityId(), info.getId().toString())) {
            }
        });
    }
}