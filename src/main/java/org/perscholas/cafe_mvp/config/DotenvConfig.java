package org.perscholas.cafe_mvp.config;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DotenvConfig {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(Dotenv dotenv, ConfigurableEnvironment env) {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        Map<String, Object> dotenvMap = new HashMap<>();
        dotenv.entries().forEach(entry -> dotenvMap.put(entry.getKey(), entry.getValue()));
        env.getPropertySources().addLast(new MapPropertySource("dotenvProperties", dotenvMap));
        return propertyConfigurer;
    }
}
