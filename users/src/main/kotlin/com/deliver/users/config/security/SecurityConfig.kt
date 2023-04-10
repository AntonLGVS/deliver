package com.deliver.users.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableMethodSecurity
@Configuration(proxyBeanMethods = false)
class SecurityConfig {

    @Bean
    fun configHttp(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors().disable()
            .httpBasic()
            .and()
            .build()
    }

    @Bean
    fun manager(): InMemoryUserDetailsManager {
        val user = User.withUsername("admin")
            .password("{noop}admin")
            .roles("ADMIN")
        return InMemoryUserDetailsManager(user.build())
    }

//    @Bean
//    fun keycloak() = KeycloakBuilder.builder()
//        .grantType("password")
}