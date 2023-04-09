package com.deliver.order.web.controller;


import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;
import com.deliver.order.security.SecurityConstants;
import com.deliver.order.security.SecurityHandler;
import com.deliver.order.service.order.OrderService;
import com.deliver.order.web.API;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = API.V1 + "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Order", description = "CRUD operations with the orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final SecurityHandler securityHandler;

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    @PostAuthorize(SecurityConstants.CHECK_PERMISSION)
    public ResponseEntity<OrderDTO> getOrderOne(
            @Parameter(description = "Order ID") @PathVariable("id") UUID orderUUID) {

        return orderService.getOrder(orderUUID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all orders", description = "API endpoint returns all orders related to current user")
    @GetMapping("/all")
    public List<OrderDTO> getAllForCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return orderService.getAllForUser(user.getUsername());
    }

    @Operation(summary = "Create new order")
    @PostMapping
    @PreAuthorize(SecurityConstants.NOT_COURIER)
    public Mono<ResponseEntity<OrderDTO>> createOrder(
            @ParameterObject @RequestBody CreateOrderRequest orderRequest) {

        return orderService.createOrder(orderRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Change the order destination",
            description = """
     API endpoint allows to change the order destination to new location.
     User can change destination only in NEW status.""")
    @PutMapping("/{id}")
    @PreAuthorize(SecurityConstants.NOT_COURIER)
    public Mono<ResponseEntity<OrderDTO>> changeTargetAddress(
            @Parameter(description = "Order ID") @PathVariable("id") UUID orderId,
            @Parameter(description = "New destination") @RequestParam("dest") String dest) {

        // TODO: security by user
        return orderService.editTargetAddress(orderId, dest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(tags = {"Order", "Courier"}, summary = "Cancel the order")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> cancelOrderByUser(
            @Parameter(description = "Order ID") @PathVariable("id") UUID orderId) {

        // TODO: need to implement security check by user and courier
        return orderService.cancelOrder(orderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
