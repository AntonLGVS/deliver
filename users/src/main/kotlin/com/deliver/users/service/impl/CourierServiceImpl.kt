package com.deliver.users.service.impl

import com.deliver.users.domain.Courier
import com.deliver.users.domain.CourierStatus
import com.deliver.users.domain.People
import com.deliver.users.repository.CourierRepository
import com.deliver.users.service.CourierService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourierServiceImpl(private val courierRepository: CourierRepository) : CourierService {

    @Transactional
    override fun createCourier(user: People) {
        val courier = Courier(user.id, CourierStatus.FREE)
        courierRepository.save(courier)
    }

    @Transactional
    override fun changeCourierStatus(id: UUID, status: CourierStatus): Boolean {
        return courierRepository.findById(id)
            .map {
                it.status = status
                courierRepository.save(it)
            }
            .isPresent
    }

    override fun getAllCouriersWithStatus(status: CourierStatus): List<Courier> = courierRepository.findByStatus(status)

    override fun getAllCouriers(): List<Courier> = courierRepository.findAll()
}