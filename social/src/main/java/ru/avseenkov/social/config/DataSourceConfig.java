package ru.avseenkov.social.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.base01")
    public DataSourceProperties base01DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.base02")
    public DataSourceProperties base02DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource base01DataSource() {
        return base01DataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public DataSource base02DataSource() {
        return base02DataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    public JdbcTemplate base01JdbcTemplate(@Qualifier("base01DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate base02JdbcTemplate(@Qualifier("base02DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
