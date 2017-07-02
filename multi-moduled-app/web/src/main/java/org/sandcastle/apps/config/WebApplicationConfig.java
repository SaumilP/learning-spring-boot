package org.sandcastle.apps.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.sandcastle.apps")
@Import({
        PropertyPlaceholderConfig.class,
        SwaggerConfig.class
})
public class WebApplicationConfig {
}
