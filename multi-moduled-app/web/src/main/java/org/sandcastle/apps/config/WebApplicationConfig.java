package org.sandcastle.apps.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:/org/sandcastle/apps/application-config.properties")
@Import({SwaggerConfig.class})
public class WebApplicationConfig {
}
