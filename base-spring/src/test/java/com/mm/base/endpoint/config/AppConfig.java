package com.mm.base.endpoint.config;

import com.mm.base.controller.StatusController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class AppConfig {
    @Bean
    public StatusController statusController() {
        return new StatusController();
    }
}