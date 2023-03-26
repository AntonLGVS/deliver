package com.deliver.order.web.controller;


import com.deliver.order.dto.OrderDTO;
import com.deliver.order.security.SecurityConstants;
import com.deliver.order.service.OrderService;
import com.deliver.order.web.API;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = API.V1 + "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    @PostAuthorize(SecurityConstants.CHECK_PERMISSION)
    public ResponseEntity<OrderDTO> getOrderOne(@PathVariable("id") UUID orderUUID) {
        return ResponseEntity.ok(orderService.getOrder(orderUUID));
    }
}
