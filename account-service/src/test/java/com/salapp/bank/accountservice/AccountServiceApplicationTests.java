package com.salapp.bank.accountservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AccountServiceApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
