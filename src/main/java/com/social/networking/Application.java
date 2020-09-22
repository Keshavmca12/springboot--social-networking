package com.social.networking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

@EnableJpaRepositories("com.social.networking.repository")
@EntityScan("com.social.networking.entity")
@SpringBootApplication
public
class Application extends org.springframework.boot.web.support.SpringBootServletInitializer {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
}

