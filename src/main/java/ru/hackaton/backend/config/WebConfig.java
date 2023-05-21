package ru.hackaton.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry myCorsRegistry) {
        myCorsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .exposedHeaders("X-Total-Count", "Content-Range")
                .allowedMethods("GET", "POST", "PUT", "HEAD", "OPTIONS", "PATCH")
                .allowedHeaders("*");
    }
}
