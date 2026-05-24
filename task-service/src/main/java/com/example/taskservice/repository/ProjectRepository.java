package com.example.taskservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskservice.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}