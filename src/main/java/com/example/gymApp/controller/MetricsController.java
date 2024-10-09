package com.example.gymApp.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/metrics")
public class MetricsController {
  private final Counter customMetricCounter;

  public MetricsController(MeterRegistry meterRegistry) {
    this.customMetricCounter = meterRegistry.counter("custom_metric_total", "status", "success");
  }

  @GetMapping("/incrementMetric")
  public String incrementMetric() {
    customMetricCounter.increment();
    return "Metric incremented!";
  }
}

