package com.audiotranscriber.Audio_Transcriber;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
     registry.addMapping("/**").allowedOrigins("localhost:5173/")
             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders().allowCredentials(true);
    }
}
