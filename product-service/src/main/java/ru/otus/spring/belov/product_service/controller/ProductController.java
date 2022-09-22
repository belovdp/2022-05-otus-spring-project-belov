package ru.otus.spring.belov.user_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для работы с продуктами
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
public class ProductController {


    @GetMapping("/test")
    public List<String> getAuthors() {
        // TODO
        return List.of("sdf", "fg");
    }
}
