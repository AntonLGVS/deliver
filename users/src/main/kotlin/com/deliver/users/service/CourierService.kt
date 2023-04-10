package com.deliver.users.service

import com.deliver.users.domain.Courier
import com.deliver.users.domain.CourierStatus
import com.deliver.users.domain.People
import java.util.*

interface CourierService {
    fun changeCourierStatus(id: UUID, status: CourierStatus): Boolean
    fun getAllCouriersWithStatus(status: CourierStatus): List<Courier>
    fun getAllCouriers() : List<Courier>
    fun createCourier(user: People)
}