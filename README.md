# Task Management Platform

Backend microservices-based task management platform built using Java Spring Boot.

---

# Architecture

The system contains two independent microservices:

| Service | Port | Responsibility |
|---|---|---|
| auth-service | 8081 | Authentication & user management |
| task-service | 8082 | Project & task management |

Both services use PostgreSQL and JWT-based authentication.

---

# Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication
- Maven

---

# Project Structure

```text
task-management-platform/
│
├── auth-service/
├── task-service/
├── postman/
├── README.md
└── .gitignore
```

---

# Database Setup

Login to PostgreSQL:

```bash
psql -U postgres
```

Create databases:

```sql
CREATE DATABASE authdb;
CREATE DATABASE taskdb;
```

---

# Application Properties

## auth-service

Location:

```text
auth-service/src/main/resources/application.properties
```

Example:

```properties
spring.application.name=auth-service

server.port=8081

spring.datasource.url=jdbc:postgresql://localhost:5432/authdb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

---

## task-service

Location:

```text
task-service/src/main/resources/application.properties
```

Example:

```properties
spring.application.name=task-service

server.port=8082

spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

Both services must use the same JWT secret.

---

# Build Instructions

## Build auth-service

```bash
cd auth-service
mvn clean install
```

---

## Build task-service

```bash
cd task-service
mvn clean install
```

---

# Run Instructions

## Start auth-service

```bash
cd auth-service
mvn spring-boot:run
```

Runs on:

```text
http://localhost:8081
```

---

## Start task-service

```bash
cd task-service
mvn spring-boot:run
```

Runs on:

```text
http://localhost:8082
```

---

# Authentication Flow

1. Register user
2. Login user
3. Copy JWT token
4. Pass JWT token in Authorization header

Example:

```text
Authorization: Bearer YOUR_TOKEN
```

---

# API Summary

---

# Auth Service APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/v1/auth/register | Register user |
| POST | /api/v1/auth/login | Login user |
| GET | /api/v1/users/me | Current user |
| GET | /api/v1/users/{id} | Get user by id |
| GET | /api/v1/users | Get all users |

---

# Project APIs

| Method | Endpoint |
|---|---|
| POST | /api/v1/projects |
| GET | /api/v1/projects |
| GET | /api/v1/projects/{id} |
| PUT | /api/v1/projects/{id} |
| DELETE | /api/v1/projects/{id} |

---

# Task APIs

| Method | Endpoint |
|---|---|
| POST | /api/v1/projects/{pid}/tasks |
| GET | /api/v1/projects/{pid}/tasks |
| GET | /api/v1/projects/{pid}/tasks/{tid} |
| PUT | /api/v1/projects/{pid}/tasks/{tid} |
| PATCH | /api/v1/projects/{pid}/tasks/{tid}/assign |
| PATCH | /api/v1/projects/{pid}/tasks/{tid}/status |
| DELETE | /api/v1/projects/{pid}/tasks/{tid} |
| GET | /api/v1/tasks/my-tasks |

---

# Roles & Permissions

| Action | USER | ADMIN |
|---|---|---|
| View Projects | ✅ | ✅ |
| Create Project | ❌ | ✅ |
| Update Project | ❌ | ✅ |
| Delete Project | ❌ | ✅ |
| Create Task | ✅ | ✅ |
| Update Task | ✅ | ✅ |
| Assign Task | ❌ | ✅ |
| Delete Task | ❌ | ✅ |

---

# Status Transition Rules

Allowed transitions:

```text
TODO -> IN_PROGRESS
IN_PROGRESS -> DONE
DONE -> IN_PROGRESS
```

Any other transition returns HTTP 400.

---

# Security Features

- JWT stateless authentication
- BCrypt password hashing
- Role-based authorization
- Input validation using @Valid
- Global exception handling
- Protected APIs using Spring Security

---

# Postman Collection

Postman collection is available inside:

```text
postman/
```

Import collection into Postman to test APIs.

---

# Known Limitations

The following optional features are not implemented yet:

- Flyway migrations
- Docker Compose
- Swagger/OpenAPI
- Refresh tokens
- Cross-service assignee validation

These can be added later if required.

---

# Future Improvements

Potential improvements:

- Centralized API gateway
- Service discovery
- Refresh token support
- Dockerized deployment
- Flyway migration versioning
- OpenAPI documentation
