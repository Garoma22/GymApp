package com.example.gymApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {


  @Operation(summary = "Пример API", description = "Описание для метода hello")
  @GetMapping("/hello")
  public String hello() {
    return "Hello, Swagger!";
  }
}