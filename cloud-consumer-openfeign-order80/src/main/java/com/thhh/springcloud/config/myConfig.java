package com.thhh.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class myConfig {
    @Bean
    Logger.Level getLoggerLevel(){
        return Logger.Level.FULL;
    }
}
