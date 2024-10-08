package org.earlspilner.auth.dto;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Alexander Dudkin
 */
public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_MODERATOR, ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
