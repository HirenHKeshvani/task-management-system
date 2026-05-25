package com.example.taskservice.dto;

import java.util.UUID;

import com.example.taskservice.entity.Priority;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTaskRequest {

	@NotBlank
	private String title;

	private String description;

	private Priority priority;

	private UUID assigneeUserId;
}