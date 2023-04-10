package com.deliver.users.repository

import com.deliver.users.domain.Courier
import com.deliver.users.domain.CourierStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CourierRepository : JpaRepository<Courier, UUID> {
    fun findByStatus(status: CourierStatus): List<Courier>
}