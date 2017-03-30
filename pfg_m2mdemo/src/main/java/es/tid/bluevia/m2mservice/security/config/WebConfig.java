package es.tid.bluevia.m2mservice.security.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("es.tid.bluevia.m2mservice.rest")
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    public WebConfig() {
        super();
    }

}