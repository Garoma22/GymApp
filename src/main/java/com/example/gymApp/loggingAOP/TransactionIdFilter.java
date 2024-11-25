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
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class TransactionIdFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.info("Entering TransactionIdFilter");

    String transactionId = UUID.randomUUID().toString();
    log.info("Generated transactionId: " + transactionId);
    MDC.put("transactionId", transactionId);

    try {

      chain.doFilter(request, response);
    } finally {

      MDC.remove("transactionId");
    }
  }

  @Override
  public void destroy() {

  }
}
