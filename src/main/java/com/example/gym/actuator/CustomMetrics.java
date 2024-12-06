package com.example.gym.actuator;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Timer;

import java.util.concurrent.TimeUnit;

@Component
public class CustomMetrics {

  private final Counter successCounter;
  private final Counter failureCounter;
  private final Timer operationTimer;

  public CustomMetrics(MeterRegistry registry) {

    this.successCounter = registry.counter("login_operations_success_total", "status", "success");
    this.failureCounter = registry.counter("login_operations_failure_total", "status", "failure");

    this.operationTimer = registry.timer("custom_operations_duration_seconds");
  }

  public void recordSuccess() {

    successCounter.increment();
  }

  public void recordFailure() {

    failureCounter.increment();
  }

  public void recordOperationDuration(long duration) {

    operationTimer.record(duration, TimeUnit.MILLISECONDS);
  }
}

