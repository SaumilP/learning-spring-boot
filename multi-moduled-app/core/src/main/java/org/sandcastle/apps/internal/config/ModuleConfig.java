package org.sandcastle.apps.internal.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan("org.sandcastle.apps.internal")
@PropertySource(value = "module.properties")
public class ModuleConfig {

}
