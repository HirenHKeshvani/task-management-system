package com.example.authservice.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.authservice.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateToken(User user) {

		return Jwts.builder().subject(user.getId().toString()).claim("role", user.getRole().name()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSigningKey()).compact();
	}

	public Claims extractClaims(String token) {

		return Jwts.parser().verifyWith((javax.crypto.SecretKey) getSigningKey()).build().parseSignedClaims(token)
				.getPayload();
	}

	public boolean isTokenValid(String token) {
		try {
			extractClaims(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
