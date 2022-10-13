package ru.otus.spring.belov.order_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.spring.belov.order_service.dto.mappers.OrderMapper;
import ru.otus.spring.belov.order_service.dto.order.CreateOrderRequest;
import ru.otus.spring.belov.order_service.dto.order.OrderDto;
import ru.otus.spring.belov.order_service.dto.order.UpdateOrderRequest;
import ru.otus.spring.belov.order_service.exceptions.ApplicationException;
import ru.otus.spring.belov.order_service.repository.OrderRepository;

/**
 * Сервис по работе с заказами для администратора
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderService implements OrderService {

    /** Репозиторий для работы с заказами */
    private final OrderRepository orderRepository;
    /** Конвертер заказов */
    private final OrderMapper orderMapper;

    @Override
    public OrderDto getOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ApplicationException("Не найден заказ"));
        return orderMapper.orderToDto(order);
    }

    @Override
    public Page<OrderDto> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::orderToDto);
    }

    @Override
    public OrderDto create(CreateOrderRequest createOrderRequest) {
        var order = orderMapper.createRequestToOrder(createOrderRequest, null);
        return orderMapper.orderToDto(orderRepository.save(order));
    }

    @Override
    public OrderDto update(UpdateOrderRequest updateOrderRequest) {
        var order = orderRepository.findById(updateOrderRequest.getId())
                .orElseThrow(() -> new ApplicationException("Не найден заказ"));
        orderMapper.updateOrderFromDto(updateOrderRequest, order);
        return orderMapper.orderToDto(orderRepository.save(order));
    }
}
