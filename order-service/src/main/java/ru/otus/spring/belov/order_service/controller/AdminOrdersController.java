package ru.otus.spring.belov.order_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.order_service.dto.order.OrderDto;
import ru.otus.spring.belov.order_service.dto.order.OrderShortDto;
import ru.otus.spring.belov.order_service.dto.order.UpdateOrderRequest;
import ru.otus.spring.belov.order_service.service.OrderService;

/**
 * Контроллер для работы с заказами через админ панель
 */
@Slf4j
@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminOrdersController {

    /** Сервис по работе с заказами */
    private final OrderService adminOrderService;

    /**
     * Вовзращает заказ
     * @param orderId идентификатор заказа
     * @return заказ
     */
    @GetMapping("/{orderId}")
    public OrderDto getProduct(@PathVariable Long orderId) {
        return adminOrderService.getOrder(orderId);
    }

    /**
     * Возвращает заказы пользователя
     * @param pageable пагинация
     * @return заказы
     */
    @GetMapping("/")
    public Page<OrderShortDto> getOrders(Pageable pageable) {
        return adminOrderService.getOrders(pageable);
    }

    /**
     * Изменяет данные заказа
     * @param updateOrderRequest запрос на изменение заказа
     */
    @PostMapping("/")
    public OrderDto update(@RequestBody UpdateOrderRequest updateOrderRequest) {
        log.info("Обновление заказа с id {} пользователем {}", updateOrderRequest.getId(), SecurityContextHolder.getContext().getAuthentication().getName());
        return adminOrderService.update(updateOrderRequest);
    }
}
