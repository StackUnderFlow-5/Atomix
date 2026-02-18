Atomix — Distributed Ledger Module
📌 Overview

A high-concurrency ledger system built for the Atomix ecosystem using:

Java 21 & Spring Boot 3

JPA / Hibernate for persistent audit trails

Pessimistic Locking for thread-safe transfers

Swagger / OpenAPI 3 for interactive documentation

This project implements a robust backend for financial transactions, ensuring absolute data integrity even under heavy concurrent load.

✨ Features

Concurrent Fund Transfers: Uses database-level locking to prevent race conditions.

Double-Entry Validation: Ensures transaction blocks always balance to zero.

Automated Audit Logging: Every movement of funds is recorded with a debit/credit pair.

Professional Error Mapping: Custom exception hierarchy mapped to HTTP status codes (e.g., 400 for Insufficient Funds).

Interactive API Sandbox: Fully documented REST endpoints via Swagger UI.

🧠 Backend Principles Applied
Concurrency Control

Implemented Pessimistic Write Locking on the Account repository to ensure balance consistency during simultaneous requests.

Transactional Integrity

Utilized Spring's @Transactional to ensure that fund transfers and audit log creations succeed or fail as a single atomic unit.

API Documentation

Integrated Springdoc-OpenAPI to provide a standardized interface for frontend consumption and manual testing.

Clean Exception Handling

Decoupled business logic from HTTP responses using @RestControllerAdvice to maintain clean service layers.

🧠 Design Decisions
Why Pessimistic Locking?

To ensure that Alice cannot spend the same 100 rupees twice if two requests hit the server at the exact same millisecond.

Why H2 In-Memory Database?

Chosen for rapid development and testing of the ledger logic without requiring a complex external DB setup.

Why Swagger over Postman?

To provide built-in, live documentation that stays updated automatically as the code changes.

▶ How To Run
Prerequisites

Java 21

Maven (included via mvnw)

Steps

Clone repository:

git clone https://github.com/sahil/Atomix.git


Run Application:

.\mvnw.cmd spring-boot:run


Open Swagger:
Navigate to

http://localhost:8080/swagger-ui/index.html


H2 Console:
Access

http://localhost:8080/h2-console


with JDBC URL

jdbc:h2:mem:ledgerdb

🚀 Future Scope

PostgreSQL Integration: Transitioning to persistent storage for production use.

Microservices Deployment: Dockerizing the module for scaling within the Atomix system.
