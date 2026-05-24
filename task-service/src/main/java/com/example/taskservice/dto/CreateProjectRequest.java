package com.example.taskservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProjectRequest {

	@NotBlank
	private String name;

	private String description;
}