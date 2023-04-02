package com.deliver.users.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table
data class People (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    var type: PeopleType,
    var active: Boolean
)

enum class PeopleType {
    USER, ADMIN, COURIER
}
