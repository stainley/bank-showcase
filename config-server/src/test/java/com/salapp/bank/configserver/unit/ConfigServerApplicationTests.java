package com.salapp.bank.configserver.unit;


import com.salapp.bank.configserver.ConfigServerApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class ConfigServerApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        Assertions.assertThat(applicationContext).isNotNull();
    }

    @Test
    void configServerApplicationBeanExists() {
        Assertions.assertThat(applicationContext.getBean(ConfigServerApplication.class)).isNotNull();
    }

}
