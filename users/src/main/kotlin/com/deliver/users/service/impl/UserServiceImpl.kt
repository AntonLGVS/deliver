package com.deliver.users.service.impl

import com.deliver.users.domain.PeopleType
import com.deliver.users.dto.ClientRequest
import com.deliver.users.dto.PeopleDTO
import com.deliver.users.mapper.PeopleMapper
import com.deliver.users.repository.PeopleRepository
import com.deliver.users.service.CourierService
import com.deliver.users.service.KeycloakService
import com.deliver.users.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val courierService: CourierService,
    private val keycloakService: KeycloakService,
    private val peopleRepository: PeopleRepository,
    private val peopleMapper: PeopleMapper
) : UserService {

    @Transactional
    override fun createUser(request: ClientRequest, type: PeopleType): PeopleDTO {
        val savedUser = peopleMapper.toPeople(request)
            .apply { this.type = type }
            .let { peopleRepository.save(it) }

        if (type == PeopleType.COURIER) {
            courierService.createCourier(savedUser)
        }

        keycloakService.createKeycloakUser(savedUser)
        return peopleMapper.toPeopleDto(savedUser)
    }

    @Transactional
    override fun deactivateUser(id: UUID): Boolean {
        return peopleRepository.findById(id)
            .map {
                it.active = false
                peopleRepository.save(it)
                keycloakService.deactivateKeycloakUser(it)
            }.isPresent
    }

    override fun getUser(userId: UUID): Optional<PeopleDTO> = peopleRepository.findById(userId).map(peopleMapper::toPeopleDto)
}