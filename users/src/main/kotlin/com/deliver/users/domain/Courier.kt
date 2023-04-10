package com.deliver.users.domain

import jakarta.persistence.*
import java.util.*

@Entity
data class Courier(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID,
    @Enumerated(EnumType.STRING)
    var status: CourierStatus
) {
    @OneToOne(optional = false)
    @JoinColumn(name = "people_id", nullable = false)
    lateinit var account: People
}

enum class CourierStatus {
    FREE, BUSY
}
