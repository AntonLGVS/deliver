package com.deliver.users.kafka.consumer

import com.deliver.users.domain.CourierStatus
import com.deliver.users.service.CourierService
import org.deliver.courier.CourierManagementContract
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class DirectCourierConsumer(private val courierService: CourierService): CourierConsumer {
        private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${topic.courier.courier-management}"])
    fun engageCourier(management: CourierManagementContract) {
        log.info("Consume Courier Management: Courier ID = ${management.courierId}, Status = ${management.status}")

        val status = when(management.status) {
            CourierManagementContract.Status.ACQUIRED -> CourierStatus.BUSY
            CourierManagementContract.Status.RELEASED -> CourierStatus.FREE
            else -> CourierStatus.FREE
        }
        courierService.changeCourierStatus(management.courierId, status)
    }
}