package com.deliver.users.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity
data class People (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var login: String,
    var userName: String,
    var type: PeopleType,
    var active: Boolean
)

enum class PeopleType {
    USER, ADMIN, COURIER
}
