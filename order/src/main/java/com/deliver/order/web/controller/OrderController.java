package com.deliver.order.web.controller;


import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;
import com.deliver.order.security.SecurityConstants;
import com.deliver.order.security.SecurityHandler;
import com.deliver.order.service.order.OrderService;
import com.deliver.order.web.API;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
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
@Tag(name = "order", description = "Order service")
//@Operation
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final SecurityHandler securityHandler;

    @GetMapping("/{id}")
    @PostAuthorize(SecurityConstants.CHECK_PERMISSION)
    public ResponseEntity<OrderDTO> getOrderOne(@PathVariable("id") UUID orderUUID) {
        return orderService.getOrder(orderUUID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<OrderDTO> getAllForCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return orderService.getAllForUser(user.getUsername());
    }

    @GetMapping("/admin/all")
    @PreAuthorize(SecurityConstants.IS_ADMIN)
    public List<OrderDTO> getAll(@RequestParam(value = "user", required = false) String userId) {
        if (StringUtils.hasText(userId)) {
            return orderService.getAllForUser(userId);
        } else {
            return orderService.getAll();
        }

    }

    @PostMapping
    @PreAuthorize(SecurityConstants.NOT_COURIER)
    public Mono<ResponseEntity<OrderDTO>> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        return orderService.createOrder(orderRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize(SecurityConstants.NOT_COURIER)
    public Mono<ResponseEntity<OrderDTO>> changeTargetAddress(
            @PathVariable("id") UUID orderId,
            @RequestParam("dest") String dest) {

        // TODO: security by user
        return orderService.editTargetAddress(orderId, dest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(SecurityConstants.NOT_COURIER)
    public Mono<ResponseEntity<Void>> cancelOrderByUser(@PathVariable("id") UUID orderId) {

        // TODO: security by user
        return orderService.cancelOrder(orderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
