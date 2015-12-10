package com.mm.base.config;

import com.mm.base.controller.ExceptionHandlerControllerAdvice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger log = LogManager.getLogger();

    @Bean
    ExceptionHandlerControllerAdvice advice() {
        return new ExceptionHandlerControllerAdvice();
    }

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("images*//**").addResourceLocations("images/");
    }*/
}