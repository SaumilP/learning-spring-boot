package org.sandcastle.apps;

import org.sandcastle.apps.config.WebApplicationConfig;
import org.sandcastle.apps.internal.config.ModuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class WebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebApplicationConfig.class, ModuleConfig.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
