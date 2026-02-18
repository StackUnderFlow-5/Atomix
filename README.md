# Atomix — Distributed Ledger Module

---

## 📌 Overview
Atomix is a specialized backend engine built to manage financial transactions between accounts while maintaining a strict audit trail. It functions as a "Double-Entry" system that automates the movement of funds and records every transaction in a permanent history table.

**Built using:**
* **Java 21 & Spring Boot 3**
* **JPA / Hibernate** for data persistence
* **Pessimistic Locking** for thread-safe operations
* **Swagger / OpenAPI 3** for manual API testing

---

## ✨ Features

* **Thread-Safe Transfers**: Uses database-level locking verified for concurrent user access.
* **Double-Entry Validation**: Checks that transaction blocks balance to zero before processing.
* **Audit Logging**: Generates debit/credit records for every successful movement of funds.
* **Error Mapping**: Maps logic errors, such as "Insufficient Funds," to clear 400 Bad Request responses.
* **API Documentation**: Includes an interactive Swagger UI for testing the backend logic.

---

## 🧠 Backend Principles Applied

* **Concurrency Control**: Implemented Pessimistic Write Locking to manage simultaneous requests and maintain balance integrity.
* **Transactional Integrity**: Ensures fund transfers and audit log entries are treated as a single atomic unit using `@Transactional`.
* **API Standardization**: Standardized endpoint documentation to simplify manual verification and future frontend integration.

---

## 🧠 Design Decisions

* **Why Pessimistic Locking?**: To prevent race conditions when multiple requests attempt to update the same account balance at the same time.
* **Why H2 In-Memory Database?**: Used for rapid prototyping and testing of ledger logic without persistent database overhead.
* **Why Swagger?**: To provide an easy way to test the logic manually and visualize the API structure.

---

## ▶ How To Run

### Prerequisites
* Java 21
* Maven

### Steps
1. **Clone repository**: `git clone https://github.com/sahil/Atomix.git`
2. **Run Application**: `.\mvnw.cmd spring-boot:run`
3. **Open Swagger**: `http://localhost:8080/swagger-ui/index.html`
4. **H2 Console**: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:ledgerdb`)

---

## 🚀 Future Scope
* **Scalability Testing**: Performing load tests to scale beyond current concurrency limits.
* **PostgreSQL Integration**: Moving to a persistent database for production-level storage.
* **Distributed Online Judge**: Integrating with the larger Atomix project to handle contest fees and rewards.
