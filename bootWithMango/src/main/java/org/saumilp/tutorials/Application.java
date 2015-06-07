package org.saumilp.tutorials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Main Application class for Boot
 */
@ComponentScan
@EnableAutoConfiguration
@PropertySource(value = "classpath:/app-config.properties")
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
