package com.example.gymApp.config;

import com.example.gymApp.utils.DataInitializer;
//import com.example.gymApp.utils.UserInitializationPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@ComponentScan(basePackages = "com.example.gymApp")
public class AppConfig {

//  @Bean
//  public static UserInitializationPostProcessor userInitializationPostProcessor() {
//    return new UserInitializationPostProcessor();
//  }

  @Bean
  public DataInitializer dataInitializer() {
    return new DataInitializer();
  }

}
