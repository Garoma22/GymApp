package com.example.gymApp.loggingAOP;

//import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect  //works with old versions of spring-aop (5.3.22), aspectjweaver (1.9.7)
@Component
public class LoggingAspect {

  @Before("execution(* com.example.gymApp.controller.*.*(..)) || execution(* com.example.gymApp.service.*.*(..))")
  public void logBeforeMethod(JoinPoint joinPoint) {
    String transactionId = MDC.get("transactionId"); // Получаем transactionId из MDC
    if (transactionId == null) {
      transactionId = "N/A"; // Если транзакции нет, добавляем значение по умолчанию
    }

    // Логируем информацию о методе и транзакции
    System.out.println("Executing method: " + joinPoint.getSignature().getName() +
        " in class: " + joinPoint.getTarget().getClass().getSimpleName() +
        " [transactionId=" + transactionId + "]");
  }
}
