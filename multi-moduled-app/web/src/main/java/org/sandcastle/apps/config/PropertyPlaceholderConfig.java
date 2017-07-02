package org.sandcastle.apps.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableAutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PropertyPlaceholderConfig {

    @Bean
    public PropertyPlaceholderConfigurer mainPropertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setOrder(1);
        configurer.setLocations(
                new ClassPathResource("org/sandcastle/app/multi-module-app.properties"),
                new ClassPathResource("org/sandcastle/module/external-system-client.properties")
        );
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}
