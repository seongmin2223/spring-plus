package org.example.expert.domain.common.dto;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;
    private final UserRole userRole;

    public AuthUser(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static AuthUser fromClaims(Claims claims) {
        Long id = Long.parseLong(claims.getSubject());
        String email = claims.get("email", String.class);
        UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));
        return new AuthUser(id, email, userRole);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.userRole.toString()));
    }

}
