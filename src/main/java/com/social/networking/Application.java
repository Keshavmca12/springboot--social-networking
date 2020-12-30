package com.social.networking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 
 * @author kesha
 *  
 *  Codegen Command C:\Users\kesha\Downloads>java -jar swagger-codegen-cli-2.4.10.jar generate 
 *  -i http://localhost:8080/v2/api-docs -l typescript-angular -o testCocdegen
 */

/***
 * 
 * @author kesha
 * Swagger : http://localhost:8080/swagger-ui.html
 */
/**
 *Flyway command for command line execution 
 * 
 * For execution of flyway through command line maven plegin is required with db configuration
 * 
 *mvn flyway:info
 *
 *For exection profiles
 *
 *mvn flyway:info@EXECUTION_ID
 *
 *mvn flyway:info@replica-migrate
 *
 *mvn flyway:info@db-migrate
 *
 *mvn flyway:migrate
 *
 *mvn flyway:migrate@EXECUTION_ID
 *
 *mvn flyway:migrate@replica-migrate
 *
 */
@EnableJpaRepositories("com.social.networking.repository")
@EntityScan("com.social.networking.entity")
@SpringBootApplication
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
}

