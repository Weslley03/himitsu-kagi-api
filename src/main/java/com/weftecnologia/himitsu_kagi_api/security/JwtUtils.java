package com.weftecnologia.himitsu_kagi_api.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import com.weftecnologia.himitsu_kagi_api.configs.EnvVariables;
import com.weftecnologia.himitsu_kagi_api.exceptions.customExceptions.JwtValidationException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {
  private static final Key key = Keys.hmacShaKeyFor(EnvVariables.getJWTSecretKey().getBytes(StandardCharsets.UTF_8));

  public static String generateToken(String userId, String email) {
    long expirationTime = 1000 * 60 * 60; // 1 hour
    return Jwts.builder()
        .setSubject(userId)
        .claim("email", email)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(key)
        .compact();
  }

  public static boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static String getUserIdFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  /* TO USE IN CONTROLLER */

  public static String JwtValidationMiddleware(String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new JwtValidationException("Invalid Authorization header");
    }

    String token = authHeader.substring(7);

    if (!validateToken(token)) {
      throw new JwtValidationException("Invalid JWT token");
    }

    return getUserIdFromToken(token);
  }
}
