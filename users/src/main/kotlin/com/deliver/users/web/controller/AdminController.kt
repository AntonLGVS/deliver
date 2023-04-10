package com.deliver.users.web.controller

import com.deliver.users.domain.Courier
import com.deliver.users.domain.CourierStatus
import com.deliver.users.domain.PeopleType
import com.deliver.users.dto.ClientRequest
import com.deliver.users.dto.PeopleDTO
import com.deliver.users.security.SecurityConstant
import com.deliver.users.service.CourierService
import com.deliver.users.service.UserService
import com.deliver.users.web.API
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(path = ["${API.V1}/admin"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Admin", description = "Admin API")
@PreAuthorize(SecurityConstant.IS_ADMIN)
class AdminController(private val userService: UserService, private val courierService: CourierService) {

    @Operation(summary = "Create courier account")
    @PostMapping("/courier")
    fun createCourier(@ParameterObject @RequestBody request: ClientRequest): PeopleDTO {
        return userService.createUser(request, PeopleType.COURIER)
    }

    @Operation(summary = "Create additional admin account")
    @PostMapping("/admin")
    fun createAdmin(@ParameterObject @RequestBody request: ClientRequest): PeopleDTO {
        return userService.createUser(request, PeopleType.ADMIN)
    }

    @Operation(summary = "Deactivate user account")
    @DeleteMapping("/deactivate/{id}")
    fun deactivateUser(@Parameter(description = "User ID") @PathVariable("id") userId: UUID): Boolean {
        return userService.deactivateUser(userId)
    }

    @Operation(summary = "Get user account")
    @GetMapping("/{id}")
    fun getUser(@Parameter(description = "User ID") @PathVariable userId: UUID): ResponseEntity<PeopleDTO> {
        return userService.getUser(userId)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "get all couriers")
    @GetMapping("/courier/all")
    fun getCouriers(
        @Parameter(description = "Courier status") @RequestParam(
            name = "status",
            required = false
        ) status: CourierStatus?
    ): List<Courier> {

        return status?.let { courierService.getAllCouriersWithStatus(it) }
            ?: courierService.getAllCouriers()
    }
}
