package com.deliver.order.web.controller;


import com.deliver.order.bpm.OrderState;
import com.deliver.order.dto.CreateOrderRequest;
import com.deliver.order.dto.OrderDTO;
import com.deliver.order.security.SecurityConstants;
import com.deliver.order.security.SecurityHandler;
import com.deliver.order.service.OrderService;
import com.deliver.order.web.API;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = API.V1 + "/order", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public List<OrderDTO> getAllForCurrentUser(@AuthenticationPrincipal User user) {
        return orderService.getAllForCurrentUser(user.getUsername());
    }

    @GetMapping("/all-admin")
    @PreAuthorize(SecurityConstants.IS_ADMIN)
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }

    @PostMapping
    @PreAuthorize(SecurityConstants.NOT_COURIER)
    public OrderDTO createOrder(@RequestBody CreateOrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> changeTargetAddress(
            @PathVariable("id") UUID orderId,
            @RequestParam("dest") String dest) {

        Optional<OrderDTO> dto = orderService.getOrder(orderId)
                .map(order -> {
                    if (!securityHandler.hasPermission(order)) {
                        throw new AccessDeniedException("403 returned");
                    }
                    return order.getId();
                })
                .flatMap(id -> orderService.setTarget(id, dest));

        return ResponseEntity.of(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> cancelOrderByUser(@PathVariable("id") UUID orderId) {
        return ResponseEntity.of(orderService.setOrderState(orderId, OrderState.CANCELLED));
    }

}
