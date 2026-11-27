package edu.lakshaned.presenceme_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;

    // Token validity: 1 hour
    private static final long JWT_EXPIRATION = 1000 * 60 * 60;

    public JwtService(@Value("${jwt.secret}") String secret) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate JWT Token
    public String generateToken(String userId, String role) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + JWT_EXPIRATION
        );

        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    // Validate JWT Token
    public boolean validateToken(String token) {

        try {
            getClaims(token);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract User ID from Token
    public String extractUserId(String token) {

        return getClaims(token).getSubject();
    }

    // Extract Role from Token
    public String extractRole(String token) {

        return getClaims(token).get("role", String.class);
    }

    // Read and Verify Token Claims
    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}