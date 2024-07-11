# Project Task Tracker

## Overview
This Spring Boot application provides a RESTful API for managing team members and tasks within a project. It demonstrates the implementation of HATEOAS (Hypermedia as the Engine of Application State) for improved API discoverability and navigation.

## Features
- CRUD operations for team members and tasks
- HATEOAS implementation for RESTful navigation
- Custom exception handling
- JPA integration for data persistence
- Basic security configuration
- Enum-based status management for tasks

## Technologies
- Java 17 or later
- Spring Boot 3
- Spring Data JPA
- Spring HATEOAS
- Spring Security
- H2 Database (for development)

### Prerequisites
- JDK 17 or later
- Maven 3.6 or later

### Running the Application
1. Clone the repository:
   git clone https://github.com/Gensys09/project-task-tracker.git
2. Navigate to the project directory:
   cd project-task-tracker
3. Build the project:
   mvn clean install
4. Run the application:
   mvn spring-boot:run

The application will start running at `http://localhost:8080`.

## API Endpoints

### Team Members
- GET `/team-members`: Retrieve all team members
- GET `/team-members/{id}`: Retrieve a specific team member
- POST `/team-members`: Create a new team member
- PUT `/team-members/{id}`: Update an existing team member
- DELETE `/team-members/{id}`: Delete a team member

### Tasks
- GET `/tasks`: Retrieve all tasks
- GET `/tasks/{id}`: Retrieve a specific task
- POST `/tasks`: Create a new task
- PUT `/tasks/{id}/complete`: Mark a task as completed
- PUT `/tasks/{id}/cancel`: Cancel a task

## Security
The application currently has a basic security configuration that permits all requests. In a production environment, proper authentication and authorization should be implemented.
