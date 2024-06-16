package com.uyghurjava.book;

import com.uyghurjava.book.user.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CustomAuditAware implements AuditorAware<Integer> {
    @Override
    @Nonnull
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        User userAuthenticated = (User) authentication.getPrincipal();

        return Optional.ofNullable(userAuthenticated.getId());
    }
}
