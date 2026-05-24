package com.example.authservice.dto;

import java.util.UUID;

import com.example.authservice.entity.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private UUID id;

	private String email;

	private String fullName;

	private Role role;

	private Boolean isActive;
}