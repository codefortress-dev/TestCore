package dev.codefortress.core.easy_security_core_jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtTokenUtil {

    private final Key key;
    private final long expirationSeconds;

    public JwtTokenUtil(String secretKey, long expirationSeconds) {
        if (!StringUtils.hasText(secretKey)) {
            throw new IllegalArgumentException("JWT secret key must not be empty");
        }
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = parseToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    public String extractSubject(String token) {
        return parseToken(token).getSubject();
    }
}