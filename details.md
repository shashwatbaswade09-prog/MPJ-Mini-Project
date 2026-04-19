# Technical Report: MPJ Vault Banking System

## 1. Project Overview
MPJ Vault is a comprehensive banking platform designed to demonstrate the implementation of **Jakarta EE (Java EE 8)** technologies. The system transitions from a framework-heavy approach (Spring Boot) to a foundationally strong architecture using **Servlets, JSP, JDBC, and PostgreSQL**. 

This transition highlights a deep understanding of the request-response lifecycle, database persistence without ORM abstraction, and manual memory/thread management.

---

## 2. System Architecture (MVC Pattern)
The application strictly follows the **Model-View-Controller (MVC)** design pattern to ensure separation of concerns:

- **Model**: Java POJOs (`Account`, `Transaction`, `Loan`) representing the data layer.
- **View**: JSP files situated in `WEB-INF` (to prevent direct browser access) using JSTL and EL for dynamic rendering.
- **Controller**: Java Servlets that intercept HTTP requests, process business logic, and determine the view to be rendered.

---

## 3. Technical Depth & Component Analysis

### A. Persistence Layer: The DAO Pattern
Instead of using automated JPA/Hibernate, this project implements the **Data Access Object (DAO)** pattern.
- **Why?**: It provides absolute control over SQL execution, allowing for optimized queries and a thorough understanding of the JDBC bridge.
- **How it works**: The `BankingDAO` uses `PreparedStatement` to prevent **SQL Injection** attacks and manages the mapping between `ResultSet` and Java Objects manually.

### B. Security: Servlet Filters & RBAC
Authentication and **Role-Based Access Control (RBAC)** are implemented using a `Filter`.
- **Why?**: Filters act as a "Global Gatekeeper." By intercepting every request before it reaches a Servlet, we centralize security logic.
- **How it works**: The `AuthFilter` checks the `HttpSession` for a valid `Account` object. If the user attempts to access `/admin` without an `ADMIN` role, it returns a `403 Forbidden` error.

### C. Concurrency: Multithreaded Background Logging
The project demonstrates advanced concurrency by logging every financial transaction to a text file in a separate thread.
- **Why?**: To prevent "I/O Blocking." Writing to a file is slow; by using a background thread (`TransactionLoggerThread`), the user receives a "Success" message immediately while the logging happens in the background.
- **How it works**: The `TransactionServlet` instantiates a new `Thread` object, passing the transaction data to the `Runnable` implementation.

### D. Automated Lifecycle Management
The system uses a `ServletContextListener` (`DatabaseInitializer`).
- **Why?**: To handle **Auto-Configuration**. In a production environment, the DB schema must be ready before the first request of the user.
- **How it works**: When the server boots up, `contextInitialized()` is triggered. It checks for the existence of PostgreSQL tables and inserts "Seed Data" (Admin and Sample Users) if the database is empty.

---

## 4. Technical Workflow
1. **Request Interception**: User submits a form (e.g., Deposit).
2. **Filtering**: `AuthFilter` verifies the user's session is active.
3. **Dispatching**: The `web.xml` maps the request to `TransactionServlet`.
4. **Processing**: Servlet calls `BankingDAO` to update the balance in PostgreSQL.
5. **Concurrency**: Servlet triggers a background thread to log the action to `banking_logs.txt`.
6. **Navigation**: Servlet updates the session with a success message and redirects to `DashboardServlet`.
7. **Rendering**: `DashboardServlet` fetches fresh data and forwards the user to `dashboard.jsp`.

---

## 5. Deployment & Execution Guide

### Prerequisites
1. **JDK 17 or 21+**: Installed and verified via `java -version`.
2. **PostgreSQL**: Running on port `5432`.
   - Database name: `mpj_vault`
   - User: `postgres`
   - Password: `$Sahil1205`

### Running the Application (Full Process)

#### Option 1: Using the Portable Runner (Recommended)
This script handles the Maven environment and JVM settings for you.
1. Open PowerShell in the project root.
2. Run:
   ```powershell
   .\run.ps1
   ```

#### Option 2: Manual Terminal Commands
If you have Maven installed globally:
1. Create the database:
   ```bash
   psql -U postgres -c "CREATE DATABASE mpj_vault;"
   ```
2. Navigate to the backend:
   ```bash
   cd backend
   ```
3. Run with necessary JVM flags (for Java 17+ compatibility):
   ```bash
   mvn tomcat7:run -Dmaven.test.skip=true
   ```

### Default "Report Ready" User Accounts
- **Master Admin**: ID: `100` | Pass: `admin123`
- **Standard User**: ID: `101` | Pass: `user123`
- **Standard User 2**: ID: `102` | Pass: `user456`
