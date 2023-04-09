package com.deliver.order.web.controller;

import com.deliver.order.domain.common.OrderBPM;
import com.deliver.order.dto.OrderDTO;
import com.deliver.order.service.order.OrderService;
import com.deliver.order.web.API;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = API.V1 + "/courier", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Courier", description = "Courier API")
@RequiredArgsConstructor
public class CourierController {

    private final OrderService orderService;

    @Operation(summary = "Get all orders for courier",
            description = "API endpoint returns all orders related to current courier")
    @GetMapping("/order/all")
    public List<OrderDTO> getAllForCurrentCourier(@AuthenticationPrincipal UserDetails user) {
        return orderService.getAllForCourier(user.getUsername());
    }

    @Operation(summary = "Send DELIVERED status to order")
    @PutMapping("/order/{id}/delivered")
    public Mono<ResponseEntity<Void>> deliveredOrder(
            @Parameter(description = "Order ID") @PathVariable("id") UUID orderId) {

        return orderService.sendAction(orderId, OrderBPM.Action.DELIVER)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
