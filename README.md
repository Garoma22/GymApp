# GymApp

Backend application for managing a gym domain: **Trainees**, **Trainers**, **Trainings**, and **Training Types**.

Built as a learning / portfolio project with a focus on clean REST API design, authentication, database migrations, and environment configuration.

## Features

- REST API for managing:
  - Trainees / Trainers profiles
  - Trainings
  - Training types
- Authentication (session-based / basic project auth logic)
- Database migrations with **Liquibase**
- Environment configuration via **Spring Profiles** (`local`, `dev`, `prod`)
- Unit testing (service layer)
- Local infrastructure via **Docker Compose**
- PostgreSQL as primary database

## Tech Stack

- Java
- Spring Boot
- PostgreSQL
- Liquibase
- Docker / Docker Compose
- JUnit 5 (tests)

## Project Structure (high level)

- `controller` — REST endpoints
- `service` — business logic
- `repository` — database access (JPA)
- `dto / mapper` — request/response models and mapping
- `config` — application configuration (profiles, security/auth, etc.)
- `resources/db/changelog` — Liquibase changelogs

## Getting Started

### Prerequisites
- Java (project uses modern Java; set up your JDK in IDE)
- Docker & Docker Compose
- (Optional) Maven / Gradle depending on the project build setup

### Run with Docker Compose (recommended)

1. Start infrastructure:
   ```bash
   docker compose up -d
