package com.example.taskservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.taskservice.dto.AssignTaskRequest;
import com.example.taskservice.dto.CreateTaskRequest;
import com.example.taskservice.dto.TaskResponse;
import com.example.taskservice.dto.UpdateTaskRequest;
import com.example.taskservice.dto.UpdateTaskStatusRequest;
import com.example.taskservice.entity.Project;
import com.example.taskservice.entity.Task;
import com.example.taskservice.entity.TaskStatus;
import com.example.taskservice.repository.ProjectRepository;
import com.example.taskservice.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;
	private final ProjectRepository projectRepository;

	public TaskResponse create(UUID projectId, CreateTaskRequest request) {

		Project project = getProject(projectId);

		Task task = Task.builder().project(project).title(request.getTitle()).description(request.getDescription())
				.priority(request.getPriority()).assigneeUserId(request.getAssigneeUserId()).build();

		return map(taskRepository.save(task));
	}

	public List<TaskResponse> getAll(UUID projectId, TaskStatus status) {

		List<Task> tasks;

		if (status != null) {
			tasks = taskRepository.findByProjectIdAndStatus(projectId, status);
		} else {
			tasks = taskRepository.findByProjectId(projectId);
		}

		return tasks.stream().map(this::map).toList();
	}

	public TaskResponse getById(UUID projectId, UUID taskId) {

		Task task = getTask(projectId, taskId);

		return map(task);
	}

	public TaskResponse update(UUID projectId, UUID taskId, UpdateTaskRequest request) {

		Task task = getTask(projectId, taskId);

		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setPriority(request.getPriority());

		return map(taskRepository.save(task));
	}

	public TaskResponse assign(UUID projectId, UUID taskId, AssignTaskRequest request) {

		Task task = getTask(projectId, taskId);

		task.setAssigneeUserId(request.getAssigneeUserId());

		return map(taskRepository.save(task));
	}

	public TaskResponse updateStatus(UUID projectId, UUID taskId, UpdateTaskStatusRequest request) {

		Task task = getTask(projectId, taskId);

		validateStatusTransition(task.getStatus(), request.getStatus());

		task.setStatus(request.getStatus());

		return map(taskRepository.save(task));
	}

	public void delete(UUID projectId, UUID taskId) {

		Task task = getTask(projectId, taskId);

		taskRepository.delete(task);
	}

	public List<TaskResponse> myTasks() {

		String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

		return taskRepository.findByAssigneeUserId(UUID.fromString(userId)).stream().map(this::map).toList();
	}

	private void validateStatusTransition(TaskStatus current, TaskStatus next) {

		boolean valid = (current == TaskStatus.TODO && next == TaskStatus.IN_PROGRESS)
				|| (current == TaskStatus.IN_PROGRESS && next == TaskStatus.DONE)
				|| (current == TaskStatus.DONE && next == TaskStatus.IN_PROGRESS);

		if (!valid) {
			throw new RuntimeException("Invalid task status transition");
		}
	}

	private Project getProject(UUID projectId) {

		return projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
	}

	private Task getTask(UUID projectId, UUID taskId) {

		getProject(projectId);

		return taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
	}

	private TaskResponse map(Task task) {

		return TaskResponse.builder().id(task.getId()).projectId(task.getProject().getId()).title(task.getTitle())
				.description(task.getDescription()).status(task.getStatus()).priority(task.getPriority())
				.assigneeUserId(task.getAssigneeUserId()).createdAt(task.getCreatedAt()).updatedAt(task.getUpdatedAt())
				.build();
	}
}