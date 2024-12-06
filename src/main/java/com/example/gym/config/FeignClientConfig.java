package com.example.gym.config;


import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
        FeignClientConfig.log.info(
            "Added Authorization header with TOKEN to the request :" + jwtToken);

        String transactionId = MDC.get("transactionId");
        if (transactionId != null) {

          requestTemplate.header("Transaction-ID", transactionId);
          log.info("Added Transaction-ID to the request: {}", transactionId);
        }
      }
    };
  }
      @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
