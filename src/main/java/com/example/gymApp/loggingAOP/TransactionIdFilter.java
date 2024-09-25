package com.example.gymApp.loggingAOP;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;



import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;



@Slf4j
public class TransactionIdFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.info("Entering TransactionIdFilter");
    // Генерируем уникальный идентификатор для каждого запроса
    String transactionId = UUID.randomUUID().toString();
    System.out.println("Generated transactionId: " + transactionId);  // Логирование в консоль

    // Сохраняем его в MDC (Mapped Diagnostic Context), чтобы он был доступен в логах
    MDC.put("transactionId", transactionId);

    try {
      // Продолжаем выполнение запроса
      chain.doFilter(request, response);
    } finally {
      // Удаляем transactionId из MDC после завершения обработки запроса
      MDC.remove("transactionId");
    }
  }

  @Override
  public void destroy() {
    // Clean up (optional)
  }


}
