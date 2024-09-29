package com.example.gymApp.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {
  @Override
  public Health health() {

    boolean serviceUp = checkYourService(); // твоя логика
    if (serviceUp) {
      return Health.up().build();
    }
    return Health.down().withDetail("Error Code", 503).build();
  }

  private boolean checkYourService() {

    return true;
  }
}
