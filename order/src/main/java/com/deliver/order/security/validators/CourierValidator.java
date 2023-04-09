package com.deliver.order.security.validators;

import com.deliver.order.dto.OrderDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CourierValidator implements SecurityValidator<OrderDTO> {
    @Override
    public boolean isSupport(Object object) {
        return object instanceof OrderDTO;
    }

    @Override
    public boolean check(OrderDTO object) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails principal) {
            return Objects.equals(principal.getUsername(), object.getCourier());
        }
        return false;
    }
}
