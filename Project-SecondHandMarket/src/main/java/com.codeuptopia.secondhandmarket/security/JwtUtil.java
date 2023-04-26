
package com.codeuptopia.secondhandmarket.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtUtil {
    
    private static final String SECRET_KEY = "supersecretkeysupersecretkeysupersecretkeysupersecretkey";
    
    public static String generateToken(String username) {
        Instant now = Instant.now();
        Instant expiration = now.plus(1, ChronoUnit.DAYS);
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        String token = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expiration))
        .signWith(key)
        .compact();
        return token;
    }
    
    public static Claims decodeToken(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims;
    }
    
}
