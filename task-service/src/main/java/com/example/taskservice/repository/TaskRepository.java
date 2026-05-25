package com.example.taskservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskservice.entity.Task;
import com.example.taskservice.entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, UUID> {

	List<Task> findByProjectId(UUID projectId);

	List<Task> findByProjectIdAndStatus(UUID projectId, TaskStatus status);

	List<Task> findByAssigneeUserId(UUID assigneeUserId);
}