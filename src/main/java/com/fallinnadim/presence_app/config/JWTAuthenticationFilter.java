package com.fallinnadim.presence_app.config;

import com.fallinnadim.presence_app.service.JWTService;
import com.fallinnadim.presence_app.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTService jwtService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    public JWTAuthenticationFilter(final UserDetailsServiceImpl userDetailsService,
                                   final JWTService jwtService,
                                   final HandlerExceptionResolver handlerExceptionResolver) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (doesNotContainBearerToken(authHeader)) {
                filterChain.doFilter(request, response);
                return;
            }
            String jwtToken = extractTokenValue(authHeader);
            String username = jwtService.extractUsername(jwtToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails foundUser = userDetailsService.loadUserByUsername(username);
                if (jwtService.isValid(jwtToken, foundUser)) {
                    updateContext(foundUser, request);
                }
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    private void updateContext(UserDetails foundUser, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                foundUser,
                null,
                foundUser.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String extractTokenValue(String authHeader) {
        return authHeader.substring(7);
    }

    private boolean doesNotContainBearerToken(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }
}

