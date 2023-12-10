package com.jasonhatfield.homeschoolinteractive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class represents the configuration for the web application.
 * It implements the WebMvcConfigurer interface to customize the behavior of Spring MVC.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS (Cross-Origin Resource Sharing) mappings for the web application.
     * CORS allows web applications to make requests to a different domain than the one it originated from.
     * In this method, we specify the allowed origins, methods, headers, and credentials for CORS.
     * 
     * @param registry the CorsRegistry object used to configure CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
