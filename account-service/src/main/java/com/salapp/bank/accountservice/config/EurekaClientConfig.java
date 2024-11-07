package com.salapp.bank.accountservice.config;

import com.salapp.bank.accountservice.annotations.ProfileDevelopment;
import com.salapp.bank.accountservice.annotations.ProfileProduction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
@Slf4j
@Configuration
@ProfileDevelopment
@EnableDiscoveryClient*/
public class EurekaClientConfig {


    /*@Bean
    String executedOnProduction(String data) {
        log.info("Executed on production {}", data);
        return "production";
    }*/

}
