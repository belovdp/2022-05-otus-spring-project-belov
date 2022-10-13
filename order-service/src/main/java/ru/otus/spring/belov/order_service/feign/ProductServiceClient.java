package ru.otus.spring.belov.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.belov.order_service.dto.product.ProductItem;

import java.util.List;
import java.util.Set;

/**
 * Клиент для работы с продуктовым сервисом
 */
@FeignClient("product-service")
public interface ProductServiceClient {

    /**
     * Возвращает список продуктов на основе идентификаторов
     * @param ids идентификаторы
     * @return список продуктов
     */
    @GetMapping("/products/list")
    List<ProductItem> getProductsByIds(@RequestParam(name = "ids") Set<Long> ids);
}
