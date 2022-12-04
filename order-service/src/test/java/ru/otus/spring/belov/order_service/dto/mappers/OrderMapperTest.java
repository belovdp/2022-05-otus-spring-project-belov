package ru.otus.spring.belov.order_service.dto.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import ru.otus.spring.belov.order_service.WireMockConfig;
import ru.otus.spring.belov.order_service.domain.Order;
import ru.otus.spring.belov.order_service.domain.OrderItem;
import ru.otus.spring.belov.order_service.dto.order.CreateOrderRequest;
import ru.otus.spring.belov.order_service.dto.order.UpdateOrderRequest;
import ru.otus.spring.belov.order_service.dto.product.ProductItem;
import ru.otus.spring.belov.order_service.dto.user.UserInfoDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Тест маппера с интеграцией с другими сервисами")
@Import(WireMockConfig.class)
class OrderMapperTest {

    private static final String USER_ID = "userId";

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private WireMockServer mockProductService;
    @Autowired
    private WireMockServer mockUserService;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Тестирует маппинг заказа в dto, обогащая информацией о пользователях из другого сервиса")
    @Test
    void orderToDtoWithUserInfo() throws JsonProcessingException {
        var userInfo = mockUserService();
        var order = getOrder();
        var dto = orderMapper.orderToDtoWithUserInfo(order);
        assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("items.order", "userInfo")
                .isEqualTo(dto);
        assertThat(userInfo)
                .usingRecursiveComparison()
                .isEqualTo(dto.getUserInfo());
    }

    @DisplayName("Тестирует маппинг заказа в dto")
    @Test
    void orderToDto() {
        var order = getOrder();
        var dto = orderMapper.orderToDto(order);
        assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("items.order")
                .isEqualTo(dto);
    }

    @DisplayName("Тестирует модификацию заказа на основе dto")
    @Test
    void updateOrderFromDto() {
        var updatedOrder = getOrder();
        var request = new UpdateOrderRequest();
        request.setPhone("123");
        request.setAddress("address");
        request.setEmail("email");
        assertThat(request)
                .usingRecursiveComparison()
                .ignoringFields("items")
                .isNotEqualTo(updatedOrder);
        orderMapper.updateOrderFromDto(request, updatedOrder);
        assertThat(request)
                .usingRecursiveComparison()
                .ignoringFields("items")
                .isEqualTo(updatedOrder);
    }

    @DisplayName("Тестирует создание заказа, обогащая информацией о продукте из сервиса продуктов")
    @Test
    void createRequestToOrder() throws JsonProcessingException {
        mockProducts();
        var request = new CreateOrderRequest("email", "+799", null, "address", "username", Map.of(1L, 2, 2L, 3));
        var order = orderMapper.createRequestToOrder(request, "user-id");
        assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("id", "created", "items", "userId")
                .isEqualTo(request);
        assertEquals("user-id", order.getUserId());
        assertNotNull(order.getCreated());
        assertEquals(2, order.getItems().size());
        assertEquals(order, order.getItems().get(0).getOrder());
        assertEquals(2, order.getItems().get(0).getCount());
        assertEquals(order, order.getItems().get(1).getOrder());
        assertEquals(3, order.getItems().get(1).getCount());
    }

    private List<ProductItem> mockProducts() throws JsonProcessingException {
        var products = List.of(ProductItem.builder()
                        .deleted(false)
                        .price(new BigDecimal(100))
                        .id(1L)
                        .title("Продукт 1")
                        .build(),
                ProductItem.builder()
                        .deleted(false)
                        .price(new BigDecimal(400))
                        .id(2L)
                        .title("Продукт 2")
                        .build()
        );
        mockProductService.stubFor(WireMock.get(urlMatching("/products/list\\?(.*)"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(products))));
        return products;
    }

    private UserInfoDto mockUserService() throws JsonProcessingException {
        var userInfo = UserInfoDto.builder()
                .username("userFromService")
                .email("userFromService@test.ru")
                .id(USER_ID)
                .lastName("dsf")
                .firstName("fds")
                .build();
        mockUserService.stubFor(WireMock.get(urlMatching("/admin/users/" + USER_ID))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(userInfo))));
        return userInfo;
    }

    private Order getOrder() {
        var order = new Order();
        order.setId(1L);
        order.setAddress("address");
        order.setEmail("email");
        order.setPhone("phone");
        order.setUsername("username");
        order.setUserId(USER_ID);
        order.setCreated(LocalDateTime.now());
        order.setItems(List.of(OrderItem.builder()
                        .count(2)
                        .id(1L)
                        .productId(4L)
                        .price(new BigDecimal(100))
                        .title("Продукт 1")
                .build()));
        return order;
    }
}