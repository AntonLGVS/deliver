package com.deliver.users.web.controller

import com.deliver.users.domain.PeopleType
import com.deliver.users.dto.ClientRequest
import com.deliver.users.dto.PeopleDTO
import com.deliver.users.service.UserService
import com.deliver.users.web.API
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["${API.V1}/public"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Public", description = "Public CRUD operations with user API")
class PublicUserController(private val userService: UserService) {

    @Operation(summary = "Create new user")
    @PostMapping("/client")
    fun createClient(@ParameterObject @RequestBody clientRequest: ClientRequest): PeopleDTO {
        return userService.createUser(clientRequest, PeopleType.USER)
    }
}