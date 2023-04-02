package com.deliver.users.config.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@EnableMethodSecurity
@Configuration(proxyBeanMethods = false)
class SecurityConfig {
}