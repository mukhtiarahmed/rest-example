package com.hackerrank.assignment.domain;

import com.hackerrank.assignment.dto.UserDTO;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * The UserAuditorAware.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
public class UserAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.ofNullable(null);
        }

        String userName = ((UserDTO) authentication.getPrincipal()).getUserName();
        return Optional.of(userName);
    }
}
