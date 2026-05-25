package com.example.taskservice.dto;

import com.example.taskservice.entity.TaskStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskStatusRequest {

	@NotNull
	private TaskStatus status;
}