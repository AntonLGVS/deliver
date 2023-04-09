package com.deliver.order.web.controller;

import com.deliver.order.dto.OrderDTO;
import com.deliver.order.security.SecurityConstants;
import com.deliver.order.service.order.OrderService;
import com.deliver.order.web.API;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize(SecurityConstants.IS_ADMIN)
@RequestMapping(path = API.V1 + "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin", description = "Admin API")
@RequiredArgsConstructor
public class AdminController {
    private final OrderService orderService;


    @Operation(summary = "Get all available order",
            description = "Get all available orders, also if the user ID is specified, it will return only orders of this user.")
    @GetMapping("/order/all")
    public List<OrderDTO> getAll(
            @Parameter(description = "User ID") @RequestParam(value = "user", required = false) String userId) {

        if (StringUtils.hasText(userId)) {
            return orderService.getAllForUser(userId);
        } else {
            return orderService.getAll();
        }
    }

    @Operation(summary = "Assign courier to order")
    @PutMapping("/order/assign")
    public Mono<Boolean> assignCourier(
            @Parameter(description = "Order ID") @RequestParam("order") UUID orderId,
            @Parameter(description = "Courier ID") @RequestParam("courier") String courierId) {

        return orderService.assignCourier(orderId, courierId);
    }
}
