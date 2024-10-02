package com.fallinnadim.presence_app.service.impl;

import com.fallinnadim.presence_app.config.JWTProperties;
import com.fallinnadim.presence_app.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService {
    private final JWTProperties jwtProperties;

    @Autowired
    public JWTServiceImpl(final JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String generateToken(UserDetails userDetails, Date expirationDate, Map<String, Object> additionalClaims) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes());
        return Jwts.builder()
                .claims()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .add(additionalClaims)
                .and()
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    @Override
    public boolean isExpired(String token) {
        return getAllClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    @Override
    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return userDetails.getUsername().equals(username) && !isExpired(token);

    }

    private Claims getAllClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes());
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        return parser.parseSignedClaims(token).getPayload();
    }

}
