package org.example.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${APP_URL}")
    private String appURL;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        //.requestMatchers("/api/user-update").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/products").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/grocery-list").authenticated()
                        .requestMatchers("/api/products").authenticated()
                        //.requestMatchers("/api/book").permitAll()
                        .requestMatchers("/api/grocery-list").authenticated()
                        .anyRequest().permitAll())
                .logout(l -> l.logoutSuccessUrl(appURL))
                .oauth2Login(o -> o.defaultSuccessUrl(appURL));

        return http.build();
    }
}
