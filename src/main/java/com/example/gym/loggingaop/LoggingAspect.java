package com.example.gym.loggingaop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect  //works with old versions of spring-aop (5.3.22), aspectjweaver (1.9.7)
@Component
public class LoggingAspect {

  @Before("execution(* com.example.gymApp.controller.*.*(..)) || execution(* com.example.gymApp.service.*.*(..))")
  public void logBeforeMethod(JoinPoint joinPoint) {
    String transactionId = MDC.get("transactionId");
    if (transactionId == null) {
      transactionId = "N/A";
    }
    log.info("Executing method: " + joinPoint.getSignature().getName() +
        " in class: " + joinPoint.getTarget().getClass().getSimpleName() +
        " [transactionId=" + transactionId + "]");
  }
}
