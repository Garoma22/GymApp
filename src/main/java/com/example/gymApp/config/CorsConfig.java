package com.example.gymApp.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  public static final String GET = "GET";
  public static final String POST = "POST";
  public static final String PUT = "PUT";
  public static final String DELETE = "DELETE";
  public static final String OPTIONS = "OPTIONS";
  public static final String PATCH = "PATCH";
  public static final String HEAD = "HEAD";

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(List.of(
        "http://localhost:8080",
        "http://localhost:8081",
        "http://localhost:8082",
        "http://localhost:8083",
        "http://localhost:8084"));
//    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
    config.setAllowedMethods(List.of(GET,POST,PUT,DELETE,OPTIONS,PATCH,HEAD));
    config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));


    //this is global CORS -Filter
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}