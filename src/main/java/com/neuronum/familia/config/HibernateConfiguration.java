package com.neuronum.familia.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class HibernateConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.dbselenium")
    public DataSource seleniumDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.dbcalls")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}
