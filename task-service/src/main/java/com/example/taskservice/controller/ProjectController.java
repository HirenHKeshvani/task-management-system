package com.example.taskservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskservice.dto.CreateProjectRequest;
import com.example.taskservice.dto.ProjectResponse;
import com.example.taskservice.dto.UpdateProjectRequest;
import com.example.taskservice.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProjectResponse> create(@Valid @RequestBody CreateProjectRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(request));
	}

	@GetMapping
	public List<ProjectResponse> getAll() {
		return projectService.getAll();
	}

	@GetMapping("/{id}")
	public ProjectResponse getById(@PathVariable UUID id) {
		return projectService.getById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ProjectResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateProjectRequest request) {

		return projectService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {

		projectService.delete(id);

		return ResponseEntity.noContent().build();
	}
}