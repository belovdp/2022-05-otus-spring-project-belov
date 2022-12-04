package ru.otus.spring.belov.order_service.feign;

import org.springframework.stereotype.Component;
import ru.otus.spring.belov.order_service.dto.user.UserInfoDto;

@Component
public class UserServiceClientFallback implements UserServiceClient {

    @Override
    public UserInfoDto getUserInfo(String id) {
        return UserInfoDto.builder()
                .email("N/A")
                .firstName("N/A")
                .lastName("N/A")
                .username("N/A")
                .id(id)
                .build();
    }
}
