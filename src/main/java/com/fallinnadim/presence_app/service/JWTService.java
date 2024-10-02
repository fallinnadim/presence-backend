package com.fallinnadim.presence_app.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface JWTService {
        String generateToken(UserDetails userDetails, Date expirationDate, Map<String, Object> additionalClaims);

        String extractUsername(String token);

        boolean isExpired(String token);

        boolean isValid(String token, UserDetails userDetails);

}
