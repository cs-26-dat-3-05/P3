package com.place2book.internal.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Bean(defaultCandidate = false)
    @Qualifier("place2book")
    public DataSource place2bookDataSource(
            @Value("${place2book.datasource.url}") String url,
            @Value("${place2book.datasource.username}") String username,
            @Value("${place2book.datasource.password}") String password) {
        return org.springframework.boot.jdbc.DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(defaultCandidate = false)
    @Qualifier("place2book")
    public JdbcClient place2bookJdbcClient(@Qualifier("place2book") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }
}
