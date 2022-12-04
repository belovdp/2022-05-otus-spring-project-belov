package ru.otus.spring.belov.order_service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@TestConfiguration
public class WireMockConfig {

    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new TestServiceInstanceListSupplier(Map.of("product-service", 9561, "user-service", 9562));
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockProductService() {
        return new WireMockServer(9561);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockUserService() {
        return new WireMockServer(9562);
    }
}
