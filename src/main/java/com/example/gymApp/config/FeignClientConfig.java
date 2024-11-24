package com.example.gymApp.config;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class FeignClientConfig {

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      String jwtToken = JwtStorage.getJwt();  //here we get jwt from temporary storage!!!
      if (jwtToken != null) {
        requestTemplate.header("Authorization", "Bearer " + jwtToken);
        log.info("Added Authorization header with TOKEN to the request :" + jwtToken);

      }
    };

  }@Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
