package ru.otus.spring.belov.order_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.belov.order_service.dto.order.CreateOrderRequest;
import ru.otus.spring.belov.order_service.dto.order.OrderDto;
import ru.otus.spring.belov.order_service.dto.order.OrderShortDto;
import ru.otus.spring.belov.order_service.service.OrderService;

/**
 * Контроллер для работы с заказами
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrdersController {

    /** Сервис по работе с заказами */
    private final OrderService userOrderService;

    /**
     * Вовзращает заказ
     * @param orderId идентификатор заказа
     * @return заказ
     */
    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        return userOrderService.getOrder(orderId);
    }

    /**
     * Возвращает заказы пользователя
     * @return заказ
     */
    @GetMapping("/")
    public Page<OrderShortDto> getOrders(Pageable pageable) {
        return userOrderService.getOrders(pageable);
    }

    /**
     * Создаёт новый заказ
     * @param createOrderRequest запрос на сохранение заказа
     */
    @PostMapping("/")
    public OrderDto save(@RequestBody CreateOrderRequest createOrderRequest) {
        return userOrderService.create(createOrderRequest);
    }
}
