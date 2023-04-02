package com.deliver.order.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain httpSettings(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager manager() {
        User.UserBuilder user = User.withUsername("admin")
                .password("{noop}admin")
                .roles("USER");
        return new InMemoryUserDetailsManager(user.build());
    }
}
