package com.example.taskservice.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignTaskRequest {

	@NotNull
	private UUID assigneeUserId;
}