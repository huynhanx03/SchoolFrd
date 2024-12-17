# SchoolFrd Microservices Application

**SchoolFrd** is a robust microservices-based application built with **Spring Boot 3**, designed to support a scalable and efficient architecture for educational platforms. It enables functionalities such as account management, college exam score lookup, user notifications, content posting, and user profile management.

## Overview
The application leverages the power of microservices and modern tools for scalability, performance, and maintainability. 

### Key Features:
1. **API Gateway**: Centralized API entry point for routing and managing requests.
2. **College Exam Score Service**: Allows users to look up college exam scores for the year 2024, optimized for querying 1 million records.
3. **Identity Service**: Handles user authentication, account creation, role-based access control, and security.
4. **Notification Service**: Sends notifications via **Kafka** and **Brevo**.
5. **Post Service**: Allows users to post content.
6. **Profile Service**: Creates and manages user profiles using **Neo4j**.
7. **Eureka Server**: Service discovery for managing microservices.
8. **Monitor Admin**: Enables monitoring and management of microservice statuses.

---

### Table of Contents
1. [Architecture Overview](#architecture-overview)
2. [Technologies Used](#technologies-used)
3. [Microservices Description](#microservices-description)
4. [Key Features](#key-features)
5. [System Design](#system-design)
6. [Setup and Installation](#setup-and-installation)
7. [API Documentation](#api-documentation)
8. [Performance Optimization](#performance-optimization)
9. [Monitoring and Logging](#monitoring-and-logging)
10. [Future Improvements](#future-improvements)

---

### Architecture Overview

The **SchoolFrd** system adopts a microservices architecture where each service is designed to handle specific business functionalities independently. Key highlights include:

- **Spring Cloud Gateway** for request routing and filtering
- **Eureka Server** for service discovery to enable dynamic scaling
- **Kafka** for asynchronous event-driven communication
- **Brevo** for email notifications
- **Neo4j** for graph-based data storage (user relationships)
- **Monitoring Admin** for managing and monitoring services

The system uses **Docker** to containerize services, ensuring easy deployment and scaling.

![Architecture Diagram](architecture-diagram.png)
```
[API Gateway] → Routes → [College Exam Score Service]   (MongoDB)
                              [Identity Service]        (MySQL)
                              [Notification Service]    (Kafka + Brevo)
                              [Post Service]            (MongoDB)
                              [Profile Service]         (Neo4j)
```

---  
*Illustrative architecture of SchoolFrd system.*

---

## Technologies Used

| Technology            | Purpose                          |
|-----------------------|----------------------------------|
| **Spring Boot 3**     | Backend framework for services  |
| **Spring Cloud**      | Microservices infrastructure    |
| **Spring Gateway**    | API Gateway for routing         |
| **Eureka**            | Service discovery and registry  |
| **Kafka**             | Asynchronous messaging system   |
| **Brevo**             | Email and notification service  |
| **Neo4j**             | Graph database for user profiles|
| **mongoDB**           | NoSQL database for flexible and scalable data storage|
| **MySQL**             | Relational database (exam data) |
| **Prometheus & Grafana** | Monitoring and metrics       |
| **Docker**            | Containerization and deployment |
| **Swagger/OpenAPI**   | API documentation               |

---

## Microservices Description

The SchoolFrd system consists of the following services:

### 1. API Gateway
- **Purpose**: Routes external requests to appropriate microservices.
- **Technology**: Spring Cloud Gateway
- **Key Features**:
  - Centralized entry point for all requests.
  - Dynamic routing to microservices.
  - Integrated with Swagger UI for API documentation.

### 2. Eureka Server
- **Purpose**: Service discovery and registration.
- **Technology**: Spring Cloud Netflix Eureka
- **Key Features**:
  - Manages service registration and lookup.
  - Ensures system resilience with dynamic scaling.

### 3. Identity Service
- **Purpose**: Manages user accounts, authentication, and authorization.
- **Technology**: Spring Security, JWT, MySQL, Spring Data JPA
- **Key Features**:
  - Secure login and registration system.
  - Role-based access control (RBAC).
  - Password encryption using BCrypt.

### 4. College Exam Score Service
- **Purpose**: Provides access to college exam scores for 2024.
- **Technology**: MongoDB
- **Key Features**:
  - Optimized for querying 1 million+ records.
  - REST APIs for retrieving student scores.
  - Indexing and caching for fast response times.

### 5. Notification Service
- **Purpose**: Sends notifications via email.
- **Technology**: Kafka, Brevo
- **Key Features**:
  - Asynchronous notification processing with Kafka.
  - Email delivery through Brevo's SMTP API.

### 6. Post Service
- **Purpose**: Allows users to post and manage information.
- **Technology**: MongoDB
- **Key Features**:
  - CRUD operations for posts.
  - Supports tagging and categorization.

### 7. Profile Service
- **Purpose**: Creates and manages user profiles with relationship data.
- **Technology**: Neo4j (graph database)
- **Key Features**:
  - Stores user relationships and social connections.
  - Optimized queries for graph traversal.

### 8. Monitoring and Admin Service
- **Purpose**: Provides system health and performance monitoring.
- **Technology**: Spring Actuator, Prometheus, Grafana
- **Key Features**:
  - Monitor microservice health status.
  - View system metrics and logs through Grafana dashboards.

---

## Key Features

1. **Scalability**: Independent deployment and scaling of each microservice.
2. **Performance Optimization**: Fast querying of 1 million records using caching and indexing.
3. **Event-Driven Architecture**: Kafka ensures asynchronous and reliable communication between services.
4. **High Availability**: Eureka for dynamic discovery and fault tolerance.
5. **Security**:
   - JWT-based authentication.
   - Role-based access control.
6. **Monitoring**:
   - Prometheus and Grafana for real-time metrics.
   - Spring Boot Actuator for health checks.
7. **Extensibility**: Modular design allows for easy integration of new services.

---

## System Design

The system adopts the following design principles:
- **Separation of Concerns**: Each microservice handles a specific domain responsibility.
- **Resilience and Fault Tolerance**: Circuit breaker and retry mechanisms can be implemented using Resilience4j.
- **API Gateway**: Acts as a single entry point for all clients.
- **Load Balancing**: Integrated with Eureka for service load balancing.

---

## Setup and Installation

### Prerequisites
- **JDK 17**
- **Docker & Docker Compose**
- **Kafka** and **Zookeeper**
- **PostgreSQL** and **Neo4j**

### Steps to Run the Application
1. **Clone the repository**:
   ```bash
   git clone https://github.com/huynhanx03/SchoolFrd.git
   cd schoolfrd
   ```
2. **Start infrastructure services**:
   ```bash
   docker-compose up -d
   ```
3. **Run Eureka Server**:
   ```bash
   cd eureka-server
   ./mvnw spring-boot:run
   ```
4. **Run all microservices**:
   ```bash
   ./mvnw spring-boot:run
   ```
   Repeat this for each service: identity, notification, post, profile, score.
5. **Run API Gateway**:
   ```bash
   cd api-gateway
   ./mvnw spring-boot:run
   ```
6. **Configure the following environment variables for services**:
   - **MongoDB**: Host, port, database.
   - **MySQL**: Host, port, user, password.
   - **Kafka**: Broker URL.
   - **Brevo API Key** for email notifications.
7. **Start Eureka Server and other microservices**:
8. **Access the services**:
   - API Gateway: `http://localhost:8888`
   - Eureka Dashboard: `http://localhost:8761`
   - Swagger UI: `http://localhost:8888/swagger-ui.html`

---

## Endpoints
| Service                     | Endpoint                    | Description                     |
|-----------------------------|-----------------------------|---------------------------------|
| **API Gateway**             | `/api/v1/**`                | Routes all requests.            |
| **College Exam Score**      | `/college-exam-score/**`    | Exam score lookups.             |
| **Identity Service**        | `/identity/**`              | User authentication and roles.  |
| **Notification Service**    | `/notification/**`          | Notification APIs.              |
| **Post Service**            | `/post/**`                  | Content posting APIs.           |
| **Profile Service**         | `/profile/**`               | User profile management.        |

---

## API Documentation

The system provides a unified Swagger UI through API Gateway:
- **Swagger UI**: [http://localhost:8888/swagger-ui.html](http://localhost:8888/swagger-ui.html)
- Each microservice exposes its own OpenAPI documentation as well.

---

## Performance Optimization

### College Exam Score Service
- Indexed mongoDB tables for efficient queries.
- Caching strategies (e.g., Redis) for frequently accessed data.
- Pagination and filtering support for large datasets.

### Kafka Integration
- Ensures asynchronous and fast event delivery.
- Handles high message throughput without performance degradation.

---

## Monitoring and Logging

- **Spring Boot Actuator**: Provides endpoints for health checks and metrics.
- **Prometheus & Grafana**:
   - Collects and visualizes service metrics.
   - Pre-configured dashboards for monitoring.

---

## Future Improvements
- Implement Circuit Breaker using Resilience4j for better fault tolerance.
- Add rate-limiting for API Gateway.
- Use Elasticsearch for advanced searching capabilities.
- Improve security with OAuth2 and Keycloak integration.

---

## Contributors
- **huynhanx03** - [GitHub](https://github.com/huynhanx03)

---

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Acknowledgments
- Spring Boot and Spring Cloud Community
- Kafka and Neo4j Teams for robust tools
- Brevo for email delivery solutions
