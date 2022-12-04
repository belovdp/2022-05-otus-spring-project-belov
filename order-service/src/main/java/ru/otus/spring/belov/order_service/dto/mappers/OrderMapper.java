package ru.otus.spring.belov.order_service.dto.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.belov.order_service.domain.Order;
import ru.otus.spring.belov.order_service.domain.OrderItem;
import ru.otus.spring.belov.order_service.dto.order.*;
import ru.otus.spring.belov.order_service.dto.user.UserInfoDto;
import ru.otus.spring.belov.order_service.feign.ProductServiceClient;
import ru.otus.spring.belov.order_service.feign.UserServiceClient;

import java.util.List;
import java.util.Map;

/**
 * Конвертер заказов
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class OrderMapper {

    @Autowired
    private ProductServiceClient productServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private ProductMapper productMapper;

    /**
     * Конвертирует заказ в dto
     * @param order заказ
     * @return заказ dto
     */
    @Mapping(target = "userInfo", source = "order", qualifiedByName = "setUserInfo")
    public abstract OrderDto orderToDtoWithUserInfo(Order order);

    /**
     * Конвертирует заказ в dto
     * @param order заказ
     * @return заказ dto
     */
    public abstract OrderDto orderToDto(Order order);

    /**
     * Конвертирует товар заказа в dto
     * @param order заказ
     * @return товар заказа dto
     */
    public abstract OrderItemDto orderToDto(OrderItem order);

    /**
     * Обновляет сущность на основе DTO
     * @param source     dto
     * @param order    обновляемая сущность
     */
    @Mapping(target = "items", ignore = true)
    public abstract void updateOrderFromDto(UpdateOrderRequest source, @MappingTarget Order order);

    /**
     * Создает обхект заказа на основе запроса
     * @param request запрос
     * @param userId  идентификатор пользователя
     * @return объект заказа
     */
    @Mapping(target = "items", source = "request.productIds", qualifiedByName = "itemsFromProductIds")
    @Mapping(target = "created", expression = "java(java.time.LocalDateTime.now())")
    public abstract Order createRequestToOrder(CreateOrderRequest request, String userId);

    /**
     * Формирует продукты заказа на основе идентификаторов
     * @param productMap карта идентификаторов продуктов и количества в заказе
     * @return продукты заказа на основе идентификаторов
     */
    @Named("itemsFromProductIds")
    List<OrderItem> itemsFromProductIds(Map<Long, Integer> productMap) {
        return productServiceClient.getProductsByIds(productMap.keySet()).stream()
                .map(product -> productMapper.orderToDto(product, productMap.get(product.getId())))
                .toList();
    }

    /**
     * Устанавливает обратную связь заказов и продуктов
     * @param order заказ
     */
    @AfterMapping
    void afterMapping(@MappingTarget Order order) {
        order.getItems().forEach(item ->  item.setOrder(order));
    }

    @Named("setUserInfo")
    UserInfoDto setUserInfo(Order order) {
        return userServiceClient.getUserInfo(order.getUserId());
    }
}
