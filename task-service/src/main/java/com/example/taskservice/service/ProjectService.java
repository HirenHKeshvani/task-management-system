package com.example.taskservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.taskservice.dto.CreateProjectRequest;
import com.example.taskservice.dto.ProjectResponse;
import com.example.taskservice.dto.UpdateProjectRequest;
import com.example.taskservice.entity.Project;
import com.example.taskservice.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;

	public ProjectResponse create(CreateProjectRequest request) {

		String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

		Project project = Project.builder().name(request.getName()).description(request.getDescription())
				.ownerUserId(UUID.fromString(userId)).build();

		return map(projectRepository.save(project));
	}

	public List<ProjectResponse> getAll() {

		return projectRepository.findAll().stream().map(this::map).toList();
	}

	public ProjectResponse getById(UUID id) {

		Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

		return map(project);
	}

	public ProjectResponse update(UUID id, UpdateProjectRequest request) {

		Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

		project.setName(request.getName());
		project.setDescription(request.getDescription());

		return map(projectRepository.save(project));
	}

	public void delete(UUID id) {

		Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

		projectRepository.delete(project);
	}

	private ProjectResponse map(Project project) {

		return ProjectResponse.builder().id(project.getId()).name(project.getName())
				.description(project.getDescription()).ownerUserId(project.getOwnerUserId())
				.createdAt(project.getCreatedAt()).updatedAt(project.getUpdatedAt()).build();
	}
}