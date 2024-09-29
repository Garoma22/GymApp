//package com.example.gymApp.authentification;
//
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class SessionAuthenticationFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//        throws IOException, ServletException {
//      HttpServletRequest httpRequest = (HttpServletRequest) request;
//      HttpServletResponse httpResponse = (HttpServletResponse) response;
//      String uri = httpRequest.getRequestURI();
//
////
////      if (uri.equals("/auth/login") || uri.equals("/auth/logout")) {
////        chain.doFilter(request, response);
////        return;
////      }
////
//
//
//      HttpSession session = httpRequest.getSession(false);
//
//      System.out.println("Filter triggered for URI: " + httpRequest.getRequestURI());
//
//      if (session == null || session.getAttribute("user") == null) {
//        System.out.println("Unauthorized access attempt to: " + httpRequest.getRequestURI());
//        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        httpResponse.getWriter().write("Unauthorized: Please log in.");
//        return;
//      }
//
//      System.out.println("Session found for user: " + session.getAttribute("user"));
//      chain.doFilter(request, response);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//    @Override
//    public void destroy() {}
//  }
