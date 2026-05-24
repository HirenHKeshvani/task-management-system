package com.example.authservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authservice.dto.UserResponse;
import com.example.authservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/me")
	public UserResponse me() {
		return userService.getCurrentUser();
	}

	@GetMapping("/{id}")
	public UserResponse getById(@PathVariable UUID id) {
		return userService.getUserById(id);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getAllUsers() {
		return userService.getAllUsers();
	}
}