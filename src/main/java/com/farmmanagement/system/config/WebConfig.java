package com.farmmanagement.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${api.version}")
    private String apiVersion;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix(
        "/api/" + apiVersion,
        handlerType -> handlerType.getPackageName().startsWith("com.farmmanagement.system.controller")
    );
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Áp dụng cho tất cả các endpoint bắt đầu bằng /api/
                // Allow the frontend running on localhost:3000 and common local variants
                .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000")
                // Allow origin patterns for other local dev ports if needed (keeps credentials allowed)
                .allowedOriginPatterns("http://localhost:*", "http://127.0.0.1:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Content-Disposition")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
