package com.example.taskservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.taskservice.entity.Priority;
import com.example.taskservice.entity.TaskStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {

	private UUID id;

	private UUID projectId;

	private String title;

	private String description;

	private TaskStatus status;

	private Priority priority;

	private UUID assigneeUserId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}