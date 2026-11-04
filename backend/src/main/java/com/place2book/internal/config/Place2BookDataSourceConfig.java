package com.place2book.internal.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

/**
 * This is the internal Place2Book database.
 * We don't need a DataSource for the backend DB, because Spring automatically creates this one
 * Inject with @Qualifier("place2book")
 */
@Configuration(proxyBeanMethods = false)
public class Place2BookDataSourceConfig {
    @Qualifier("place2book")
    @Bean(defaultCandidate = false)
    @ConfigurationProperties("place2book.datasource")
    public DataSource place2bookDataSource() {
        return DataSourceBuilder.create().build();
    }
}
