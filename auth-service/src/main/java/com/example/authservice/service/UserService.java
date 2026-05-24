package com.example.authservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.authservice.dto.UserResponse;
import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserResponse getCurrentUser() {

		String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

		User user = userRepository.findById(UUID.fromString(userId))
				.orElseThrow(() -> new RuntimeException("User not found"));

		return map(user);
	}

	public UserResponse getUserById(UUID id) {

		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		return map(user);
	}

	public List<UserResponse> getAllUsers() {

		return userRepository.findAll().stream().map(this::map).toList();
	}

	private UserResponse map(User user) {

		return UserResponse.builder().id(user.getId()).email(user.getEmail()).fullName(user.getFullName())
				.role(user.getRole()).isActive(user.getIsActive()).build();
	}
}