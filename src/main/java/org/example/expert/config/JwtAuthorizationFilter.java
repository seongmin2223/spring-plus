package org.example.expert.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
           String bearerToken = request.getHeader("Authorization");

           if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
               String token = jwtUtil.substringToken(bearerToken);
               Claims claims = jwtUtil.extractClaims(token);

               setAuthentication(claims);
           }
           filterChain.doFilter(request,response);
    }

    private void setAuthentication(Claims claims) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        AuthUser authUser = AuthUser.fromClaims(claims);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authUser, null, authUser.getAuthorities());

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

}
