package com.salapp.bank.discoveryserver;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DiscoveryServerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verifies that the application context loads successfully.
        Assertions.assertNotNull(applicationContext, "The application context should have loaded.");
    }

    @Test
    void eurekaServerIsEnabled() {
        // Verifies that the application is configured as a Eureka Server.
        boolean isEurekaServerEnabled = !applicationContext.getBeansWithAnnotation(EnableEurekaServer.class).isEmpty();
        Assertions.assertTrue(isEurekaServerEnabled, "Eureka Server should be enabled in this application.");
    }

}
