package com.example.gymApp.loggingAOP;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class RestLoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(RestLoggingAspect.class);

  @Before("execution(* com.example.gymApp.controller.*.*(..))")
  public void logBefore(JoinPoint joinPoint) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    // Логируем информацию о вызове конечной точки и параметрах запроса
    logger.info("Incoming request: {} {} from IP: {}",
        request.getMethod(),
        request.getRequestURI(),
        request.getRemoteAddr());
  }

  @AfterReturning(pointcut = "execution(* com.example.gymApp.controller.*.*(..))", returning = "response")
  public void logAfterReturning(JoinPoint joinPoint, ResponseEntity<?> response) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    // Логируем успешный ответ
    logger.info("Request to {} completed successfully with status: {} and response: {}",
        request.getRequestURI(),
        response.getStatusCode(),
        response.getBody());
  }

  @AfterThrowing(pointcut = "execution(* com.example.gymApp.controller.*.*(..))", throwing = "error")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    // Логируем ошибку, которая произошла при обработке запроса
    logger.error("Request to {} failed with error: {}",
        request.getRequestURI(),
        error.getMessage());
  }
}
