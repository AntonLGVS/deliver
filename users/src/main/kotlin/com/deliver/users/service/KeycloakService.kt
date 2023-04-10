package com.deliver.users.service

import com.deliver.users.domain.People

interface KeycloakService {
    fun createKeycloakUser(user: People)
    fun deactivateKeycloakUser(user: People)
}