package com.example.authservice.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authservice.dto.AuthResponse;
import com.example.authservice.dto.LoginRequest;
import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.entity.Role;
import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public void register(RegisterRequest request) {

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		Role role = request.getRole() != null ? request.getRole() : Role.USER;

		User user = User.builder().email(request.getEmail()).passwordHash(passwordEncoder.encode(request.getPassword()))
				.fullName(request.getFullName()).role(role).build();

		userRepository.save(user);
	}

	public AuthResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
			throw new BadCredentialsException("Invalid credentials");
		}

		String token = jwtService.generateToken(user);

		return new AuthResponse(token);
	}
}
