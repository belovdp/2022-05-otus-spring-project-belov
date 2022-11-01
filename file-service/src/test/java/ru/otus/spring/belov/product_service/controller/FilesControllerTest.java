package ru.otus.spring.belov.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.belov.product_service.config.WebSecurityConfiguration;
import ru.otus.spring.belov.product_service.domain.EntityCategory;
import ru.otus.spring.belov.product_service.domain.FileInfo;
import ru.otus.spring.belov.product_service.service.FilesService;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FilesController.class)
@Import(WebSecurityConfiguration.class)
@DisplayName("Тест контроллера работы с файлами")
class FilesControllerTest {

    private static final String TEST_RESOURCE_FILE_PATH = "classpath:mimimi.jpg";
    @MockBean
    private FilesService filesService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    @DisplayName("Тест получения списка идентификаторов файлов")
    void getFilesId() throws Exception {
        var result = List.of(UUID.randomUUID(), UUID.randomUUID());
        var entityCategory = EntityCategory.PRODUCT;
        var entityId = 3L;
        when(filesService.findFiles(refEq(entityCategory), eq(entityId))).thenReturn(result);
        mockMvc.perform(get("/files/{entityCategory}/{entityId}", entityCategory, entityId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @WithMockUser(roles = "EDITOR")
    @Test
    @DisplayName("Тест загрузки файлов")
    void uploadFiles() throws Exception {
        var entityCategory = EntityCategory.PRODUCT;
        var entityId = 3L;
        try (var is = resourceLoader.getResource(TEST_RESOURCE_FILE_PATH).getInputStream()) {
            var file = new MockMultipartFile("file", is);
            var fileInfo = new FileInfo(UUID.randomUUID(), "jpg", entityCategory, entityId);
            when(filesService.save(eq(file), refEq(entityCategory), eq(entityId))).thenReturn(fileInfo);
            mockMvc.perform(multipart("/files/{entityCategory}/{entityId}", entityCategory, entityId)
                            .file(file))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(fileInfo)));
        }
    }

    @WithMockUser(roles = "EDITOR")
    @Test
    @DisplayName("Тест удаления файлов")
    void deleteFile() throws Exception {
        var fileId = UUID.randomUUID();
        mockMvc.perform(delete("/files/{id}", fileId))
                .andExpect(status().isOk());
        verify(filesService).delete(eq(fileId));
    }

    @WithMockUser(roles = "EDITOR")
    @Test
    @DisplayName("Тест удаления файлов для сущности")
    void deleteEntityFiles() throws Exception {
        var entityCategory = EntityCategory.PRODUCT;
        var entityId = 3L;
        mockMvc.perform(delete("/files/{entityCategory}/{entityId}", entityCategory, entityId))
                .andExpect(status().isOk());
        verify(filesService).deleteEntityFiles(refEq(entityCategory), eq(entityId));
    }

    @Test
    @DisplayName("Тест получения файла")
    void getFile() throws Exception {
        var fileId = UUID.randomUUID();
        try (var mockInputStream = resourceLoader.getResource(TEST_RESOURCE_FILE_PATH).getInputStream();
             var responseInputStream = resourceLoader.getResource(TEST_RESOURCE_FILE_PATH).getInputStream()) {
            when(filesService.loadFileAsStream(eq(fileId))).thenReturn(mockInputStream);
            mockMvc.perform(get("/files/{fileId}", fileId))
                    .andExpect(status().isOk())
                    .andExpect(content().bytes(responseInputStream.readAllBytes()));
        }
    }

    @Test
    @DisplayName("Тест получения файла первьюшки")
    void getPreviewFile() throws Exception {
        var entityCategory = EntityCategory.PRODUCT;
        var entityId = 3L;
        try (var mockInputStream = resourceLoader.getResource(TEST_RESOURCE_FILE_PATH).getInputStream();
             var responseInputStream = resourceLoader.getResource(TEST_RESOURCE_FILE_PATH).getInputStream()) {
            when(filesService.loadPreviewAsStream(refEq(entityCategory), eq(entityId))).thenReturn(mockInputStream);
            mockMvc.perform(get("/files/{entityCategory}/{entityId}/preview", entityCategory, entityId))
                    .andExpect(status().isOk())
                    .andExpect(content().bytes(responseInputStream.readAllBytes()));
        }
    }
}