package com.deliver.users.mapper

import com.deliver.users.domain.People
import com.deliver.users.dto.ClientRequest
import com.deliver.users.dto.PeopleDTO
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PeopleMapper {
    fun toPeople(request: ClientRequest): People
    fun toPeopleDto(people: People): PeopleDTO
}