# Task Management Platform

Microservices-based backend system using Java Spring Boot.

## Services

- auth-service (Port 8081)
- task-service (Port 8082)

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- PostgreSQL
- Maven
- JWT Authentication

## Databases

Create databases manually:

CREATE DATABASE authdb;
CREATE DATABASE taskdb;

## Run Auth Service

cd auth-service
mvn spring-boot:run

## Run Task Service

cd task-service
mvn spring-boot:run