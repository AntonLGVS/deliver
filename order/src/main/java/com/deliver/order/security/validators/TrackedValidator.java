package com.deliver.order.security.validators;

import com.deliver.order.dto.TrackedDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TrackedValidator implements SecurityValidator<TrackedDTO> {
    @Override
    public boolean isSupport(Object object) {
        return object instanceof TrackedDTO;
    }

    @Override
    public boolean check(TrackedDTO object) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return isOwner(object, auth) || isAdmin(auth);
    }

    private static boolean isOwner(TrackedDTO object, Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails principal) {
            return Objects.equals(principal.getUsername(), object.getCreatedBy());
        }
        return false;
    }

    private static boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
