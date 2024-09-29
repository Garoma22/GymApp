package com.example.gymApp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/protected")
public class TestController {
  @GetMapping("/hello")
  public String hello() {
    return "Hello, protected resource!";
  }
}

