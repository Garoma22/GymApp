package com.example.gymApp;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy
@Configuration
@ComponentScan(basePackages = "com.example.gymApp")
public class AppConfig {


}


