package com.salapp.bank.accountservice;

import com.netflix.discovery.EurekaClientConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;


@Import(TestcontainersConfiguration.class)
@SpringBootTest
@EnableAutoConfiguration
class AccountServiceApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
