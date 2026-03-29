# MPJ Vault - Fullstack Banking Application

A modern, fullstack banking dashboard and management system combining a responsive **React (Vite + Tailwind CSS)** frontend with a robust **Java (Spring Boot + Spring Data JPA + H2 Database)** backend API.

## Project Structure

The project is split into two independent domains: `frontend` and `backend`.

```text
MPJ-Mini-Project/
├── frontend/                     # React application
│   ├── index.html
│   ├── package.json              # Node.js dependencies
│   ├── src/
│   │   ├── components/           # React Views (Login, Dashboard)
│   │   ├── App.jsx               # React Router / Auth boundaries
│   │   └── index.css             # Tailwind Directives
│   └── vite.config.js
│
└── backend/                      # Java Spring Boot API
    ├── pom.xml                   # Maven dependencies (JPA, Web, H2)
    └── src/main/java/com/bank/
        ├── BankApplication.java  # Spring Boot Main Application
        ├── controller/           # REST endpoints mapping
        ├── model/                # JPA Database Entities (@Entity)
        ├── repository/           # Spring Data JPA Interfaces
        └── service/              # Core business logic
```

## Running the Application Locally

You will need two terminal windows to run both services simultaneously.

### 1. Spring Boot Backend
The backend utilizes a **MySQL Database**. Every time the server is launched, the database schema is automatically updated via `hibernate.ddl-auto=update`.

1. Open a terminal and navigate to the `backend` folder.
2. Ensure you have an active local **MySQL server** running on Port `3306` with a database named `mpj_vault`.
3. Run the application utilizing Maven:
   ```bash
   mvn clean spring-boot:run
   ```
4. The backend will become accessible at `http://localhost:8080`.
   - Healthcheck & Endpoints: `/api/accounts`, `/api/transactions/*`

### 2. React Frontend
The frontend uses Vite as a development server and Tailwind for UI styling.

1. Open a new terminal and navigate to the `frontend` folder.
2. Install all Node package dependencies (first time only):
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
4. The React dashboard will be accessible at: `http://localhost:5173/MPJ-Mini-Project/`.

---

## Core Features
- **Instant Account Creation:** Create a new checking/savings account with an initial balance.
- **Deposit / Withdrawal:** Mutate your persistent balance utilizing transactional database queries.
- **Transfers:** Send money seamlessly to another Account ID, with full error handling for insufficient funds.
- **Transaction History:** Real-time visibility of your debits and credits synchronized via the Database.

## API Endpoints List

**Accounts:**
- `POST /api/accounts` - Registers a new account and sets a starting balance.
- `GET /api/accounts/{id}` - Retrieves account details and verifies sign-in.
- `GET /api/accounts/{id}/history` - Fetches the transactional ledger for an account.

**Transactions:**
- `POST /api/transactions/deposit` - Adds funds.
- `POST /api/transactions/withdraw` - Removes funds safely.
- `POST /api/transactions/transfer` - Moves funds from one distinct account to another internally.
