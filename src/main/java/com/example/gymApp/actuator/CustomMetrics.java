package com.example.gymApp.actuator;


import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

  public CustomMetrics(MeterRegistry registry) {
    registry.counter("custom_metric_total", "status", "success");
  }
}
