package com.salapp.bank.userservice.test;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class TestDatabaseConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.testcontainers.jdbc.ContainerDatabaseDriver")
                .url("jdbc:tc:postgresql:14-alpine:///userservice")
                .username("postgres")
                .password("postgres")
                .build();
    }
}
