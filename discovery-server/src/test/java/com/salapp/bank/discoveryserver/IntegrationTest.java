package com.salapp.bank.discoveryserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;

/*@SpringBootTest
@EnableEurekaServer*/
public class IntegrationTest {

    /*public static GenericContainer<?> discoveryServer = new GenericContainer<>("springcloud/eureka:latest")
            .withExposedPorts(8761);

    @BeforeAll
    static void setup() {
        // Start the Eureka container before running tests
        discoveryServer.start();
    }

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("eureka.client.service-url.defaultZone",
                () -> "http://" + discoveryServer.getHost() + ":" + discoveryServer.getFirstMappedPort() + "/eureka/");
    }

    @Test
    void testEurekaServerIsRunning() {
        Assertions.assertTrue(discoveryServer.isRunning());
    }*/
}
