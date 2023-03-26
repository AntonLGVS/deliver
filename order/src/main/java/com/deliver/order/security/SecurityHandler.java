package com.deliver.order.security;

import com.deliver.order.security.validators.SecurityValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("security")
@RequiredArgsConstructor
public class SecurityHandler {

    private static final boolean DEFAULT_PERMISSION = false;

    private final Set<SecurityValidator<?>> validators;

    @SuppressWarnings("unchecked")
    public boolean hasPermission(ResponseEntity<?> response) {
        for (SecurityValidator<Object> validator : (Set<SecurityValidator<Object>>) (Object) validators) {
            if (validator.isSupport(response)) {
                return validator.check(response);
            }
            if (validator.isSupport(response.getBody())) {
                return validator.check(response.getBody());
            }
        }
        return DEFAULT_PERMISSION;
    }
}
