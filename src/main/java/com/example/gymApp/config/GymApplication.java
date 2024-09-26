//package com.example.gymApp;
//
//import com.example.gymApp.logging.MyWebAppInitializer;
//import com.example.gymApp.utils.ConsoleInputHandler;
//
//import jakarta.servlet.ServletContext;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.servlet.ServletContextHandler;
//import org.eclipse.jetty.servlet.ServletHolder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//
//public class GymApplication {
//
//  private static final Logger log = LoggerFactory.getLogger(GymApplication.class);
//
//  public static void main(String[] args) throws Exception {
//
//    Server server = new Server(8081);
//
//    ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
//    handler.setContextPath("/");
//    server.setHandler(handler);
//
//   AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//    context.register(AppConfig.class);
//
//
//
//    DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
//    ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
//    handler.addServlet(servletHolder, "/");
//
//    try {
//      server.start();
//      log.info("Server started at {}", server);
//
//      ConsoleInputHandler consoleInputHandler = context.getBean(ConsoleInputHandler.class);
//      consoleInputHandler.start();
//
//
//
//      server.join();
//    } catch (Exception e) {
//      log.error("Server start error!", e.getMessage());
//    } finally {
//      if (!server.isStopped()) {
//        log.error("Server is stopped");
//        server.stop();
//      }
//      server.destroy();
//    }
//  }
//}
//
//
//
//

package com.example.gymApp.config;

import com.example.gymApp.authentification.SessionAuthenticationFilter;
import com.example.gymApp.loggingAOP.TransactionIdFilter;
import com.example.gymApp.utils.ConsoleInputHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class GymApplication {

  private static final Logger log = LoggerFactory.getLogger(GymApplication.class);

  public static void main(String[] args) throws Exception {

    Server server = new Server(8081);

    ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    handler.setContextPath("/");
    server.setHandler(handler);

    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(AppConfig.class);


    DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
    ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
    handler.addServlet(servletHolder, "/");


    FilterHolder transactionFilterHolder = new FilterHolder(new TransactionIdFilter());
    handler.addFilter(transactionFilterHolder, "/*", null);


    FilterHolder sessionFilterHolder = new FilterHolder(new SessionAuthenticationFilter());
    handler.addFilter(sessionFilterHolder, "/protected/*", null); // Добавляем фильтр на маршруты с /protected


    try {
      server.start();
      log.info("Server started at {}", server);

      ConsoleInputHandler consoleInputHandler = context.getBean(ConsoleInputHandler.class);
      consoleInputHandler.start();

      server.join();
    } catch (Exception e) {
      log.error("Server start error!", e.getMessage());
    } finally {
      if (!server.isStopped()) {
        log.error("Server is stopped");
        server.stop();
      }
      server.destroy();
    }
  }
}

