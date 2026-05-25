package com.example.taskservice.dto;

import com.example.taskservice.entity.Priority;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateTaskRequest {

	@NotBlank
	private String title;

	private String description;

	private Priority priority;
}