package com.example.gymApp.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public Health health() {
    try {
      jdbcTemplate.execute("SELECT 1");
      return Health.up().withDetail("Database", "Available").build();
    } catch (Exception e) {
      return Health.down().withDetail("Database", "Unavailable")
          .withDetail("Error", e.getMessage()).build();
    }
  }
}
