package com.example.taskservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskservice.dto.AssignTaskRequest;
import com.example.taskservice.dto.CreateTaskRequest;
import com.example.taskservice.dto.TaskResponse;
import com.example.taskservice.dto.UpdateTaskRequest;
import com.example.taskservice.dto.UpdateTaskStatusRequest;
import com.example.taskservice.entity.TaskStatus;
import com.example.taskservice.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;

	@PostMapping("/api/v1/projects/{pid}/tasks")
	public ResponseEntity<TaskResponse> create(@PathVariable UUID pid, @Valid @RequestBody CreateTaskRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(pid, request));
	}

	@GetMapping("/api/v1/projects/{pid}/tasks")
	public List<TaskResponse> getAll(@PathVariable UUID pid, @RequestParam(required = false) TaskStatus status) {

		return taskService.getAll(pid, status);
	}

	@GetMapping("/api/v1/projects/{pid}/tasks/{tid}")
	public TaskResponse getById(@PathVariable UUID pid, @PathVariable UUID tid) {

		return taskService.getById(pid, tid);
	}

	@PutMapping("/api/v1/projects/{pid}/tasks/{tid}")
	public TaskResponse update(@PathVariable UUID pid, @PathVariable UUID tid,
			@Valid @RequestBody UpdateTaskRequest request) {

		return taskService.update(pid, tid, request);
	}

	@PatchMapping("/api/v1/projects/{pid}/tasks/{tid}/assign")
	@PreAuthorize("hasRole('ADMIN')")
	public TaskResponse assign(@PathVariable UUID pid, @PathVariable UUID tid,
			@Valid @RequestBody AssignTaskRequest request) {

		return taskService.assign(pid, tid, request);
	}

	@PatchMapping("/api/v1/projects/{pid}/tasks/{tid}/status")
	public TaskResponse updateStatus(@PathVariable UUID pid, @PathVariable UUID tid,
			@Valid @RequestBody UpdateTaskStatusRequest request) {

		return taskService.updateStatus(pid, tid, request);
	}

	@DeleteMapping("/api/v1/projects/{pid}/tasks/{tid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable UUID pid, @PathVariable UUID tid) {

		taskService.delete(pid, tid);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/api/v1/tasks/my-tasks")
	public List<TaskResponse> myTasks() {

		return taskService.myTasks();
	}
}