package com.scentify.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Generate key yang aman (bisa juga di-generate sekali dan disimpan di konfigurasi)
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // pakai SecretKey langsung
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate token dengan username dan role di dalam claims
    // Generate token dengan username dan role di dalam claims
    public String generateToken(String username, String rawRole) {
        // Ubah role ke uppercase untuk konsistensi, dan validasi agar tidak kosong
        String role = (rawRole != null) ? rawRole.toUpperCase() : "BUYER";

        if (!role.equals("ADMIN") && !role.equals("BUYER")) {
            role = "BUYER"; // fallback default
        }

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)  // simpan role string yang valid
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 jam
                .signWith(SECRET_KEY) // pakai SecretKey langsung
                .compact();
    }


    // Validasi token berdasarkan username dan expiration
    public Boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    // Extract role dari token (jika diperlukan)
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
}
