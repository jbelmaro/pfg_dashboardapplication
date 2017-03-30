package es.tid.bluevia.m2mservice.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:spring-security.xml"})
@ComponentScan("es.tid.bluevia.m2mservice.security")
public class SecSecurityConfig {

    public SecSecurityConfig() {
        super();
    }

}