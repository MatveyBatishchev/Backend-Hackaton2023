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
                .allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Header", "Access-Control-Expose-Headers", "Content-Range", "Content-Length", "Connection", "Content-Type", "X-Total-Count", "X-Content-Type-Options", "Set-Cookies", "*")
                .allowedMethods("GET", "POST", "PUT", "HEAD", "OPTIONS", "PATCH");
    }
}
