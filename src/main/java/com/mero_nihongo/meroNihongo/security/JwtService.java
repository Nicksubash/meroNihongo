package com.mero_nihongo.meroNihongo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return username.equals(extractedUsername) && !isTokenExpired(token);
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isTokenExpired(String token) {
        Claims claims = extractClaims(token);
        return claims == null || claims.getExpiration().before(new Date());
    }
}
