package com.deliver.users.service

import com.deliver.users.domain.PeopleType
import com.deliver.users.dto.ClientRequest
import com.deliver.users.dto.PeopleDTO
import java.util.*

interface UserService {
    fun createUser(request: ClientRequest, type: PeopleType): PeopleDTO
    fun deactivateUser(id: UUID): Boolean
    fun getUser(userId: UUID): Optional<PeopleDTO>
}