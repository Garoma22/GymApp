package com.example.gymApp;

import com.example.gymApp.Dao.InMemoryStorage;
import com.example.gymApp.Service.Facade;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class GymApplication {

    private static final Logger log = LoggerFactory.getLogger(GymApplication.class);

    public static void main(String[] args) throws Exception {
        // Инициализация контекста Spring с помощью конфигурационного класса для веб-приложений
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);


        Server server = new Server(8081);






        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        handler.addServlet(servletHolder, "/");




        try {
            server.start();
            log.info("Server started at {}", server);

            InMemoryStorage inMemoryStorage = new InMemoryStorage();
            inMemoryStorage.init();


            server.join();
        } catch (Exception e) {
            log.error("Server start error!");
            e.printStackTrace();
        } finally {
            if (!server.isStopped()) {
                log.error("Server is stopped");
                server.stop();
            }
            server.destroy();
        }
    }
}
