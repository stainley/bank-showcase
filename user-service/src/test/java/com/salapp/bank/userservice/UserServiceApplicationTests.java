package com.salapp.bank.userservice;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@SpringBootTest
@Testcontainers
class UserServiceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataSource dataSource;


    @Test
    void contextLoads() {
        Assertions.assertThat(applicationContext).isNotNull();
        Assertions.assertThat(applicationContext.getBean(UserServiceApplication.class)).isNotNull();
        Assertions.assertThat(dataSource).isNotNull();
    }

}
