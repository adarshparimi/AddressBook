package com.cfm.AddressBook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class myMvcConfig {

    @Bean
    public WebMvcConfigurer myMVCConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET","POST").allowedHeaders("*");
                WebMvcConfigurer.super.addCorsMappings(registry);
            }
        };

    }
}
