package com.place2book.internal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {

    private static final String[] HEALTH = {
        "/actuator/health",
        "/actuator/health/**"
    };

    private static final String[] SWAGGER = {
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    };

    private static final String[] API = {
        "/api/**"
    };

    /**
     * The development security filter chain
     * Health, Swagger and API are open. CSRF is disabled for API endpoints
     * All other endpoints require authentication
     * !! NOT TO BE USED IN PRODUCTION !!
     */
    @Bean 
    @Profile("dev") // The profile is configured in the compose.yaml file in root.
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(HEALTH).permitAll()
                .requestMatchers(SWAGGER).permitAll()
                .requestMatchers(API).permitAll()
                .anyRequest().authenticated()
            )
            .csrf((csrf) -> csrf.ignoringRequestMatchers(API))
            .formLogin(Customizer.withDefaults());
        return http.build();
    }

    /**
     * The production security filter chain
     * Health and Swagger are open - not the API endpoints.
     * TODO: Add oauth2 login for API endpoints when SSO is configured
     */

    @Bean 
    @Profile("prod")
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(HEALTH).permitAll()
                .requestMatchers(SWAGGER).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults());
        return http.build();
    }
}
