package com.example.gymApp.config;


import com.example.gymApp.authentification.SessionAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy
@Configuration
@ComponentScan(basePackages = "com.example.gymApp")
public class AppConfig {


  @Bean
  public FilterRegistrationBean<SessionAuthenticationFilter> sessionFilter() {
    FilterRegistrationBean<SessionAuthenticationFilter> registrationBean =
        new FilterRegistrationBean<>();

    registrationBean.setFilter(new SessionAuthenticationFilter());
    registrationBean.addUrlPatterns("/protected/*" );
//    ( "/trainee/*", "trainer/*", "user/*");

    return registrationBean;
  }


}


