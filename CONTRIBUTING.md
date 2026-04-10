# 🤝 Team Collaboration Guide: Work Division

To demonstrate a structured engineering workflow, this project is designed to be developed by a team of 4 people. Below is the division of work and the integration roadmap.

---

## 🏗️ Roles & Responsibilities

### 1. Frontend Architect (Dev A)
**Primary Focus**: Visual Interface & User Experience
- Design the responsive Dashboard using Tailwind CSS.
- Manage global state and component lifecycle in React.
- Implement Lucide icons and micro-animations for a premium feel.
- **Key Modules**: `frontend/src/App.jsx`, `frontend/src/components/*`

### 2. Backend Engineer (Dev B)
**Primary Focus**: Core API & Business Logic
- Implement the Service layer for deposits, withdrawals, and transfers.
- Manage ACID compliance in transactions.
- Design and optimize the JPA Repository layer.
- **Key Modules**: `backend/src/main/java/com/bank/service/*`, `backend/src/main/java/com/bank/repository/*`

### 3. Security & Validation Specialist (Dev C)
**Primary Focus**: Data Integrity & Authentication
- Implement the Login/Registration logic with password verification.
- Enforce API validation rules (e.g., preventing negative balance/amounts).
- Secure the backend endpoints and manage Cross-Origin Resource Sharing (CORS).
- **Key Modules**: `backend/src/main/java/com/bank/controller/BankController.java`, `backend/src/main/java/com/bank/model/Account.java`

### 4. Integration & DevOps Lead (Dev D)
**Primary Focus**: End-to-End Connectivity & Infrastructure
- Configure the Vite proxy for seamless Frontend-Backend communication.
- Manage project build settings (`pom.xml`, `vite.config.js`).
- Document API endpoints and maintain the repository.
- **Key Modules**: `frontend/vite.config.js`, `README.md`, `backend/src/main/resources/application.properties`

---

## 🔄 Integration Workflow

1. **Sprint 1 (Infrastructure)**: Dev D sets up the repository and proxy config. Dev B creates the Database Schema.
2. **Sprint 2 (Core Features)**: Dev B builds the Transaction Service. Dev A designs the Dashboard shell.
3. **Sprint 3 (Security)**: Dev C implements Authentication. Dev A connects the Login forms.
4. **Sprint 4 (Refinement)**: Dev D handles documentation and QA. Dev A/B/C fix bugs discovered in integration.

---

## 🛠️ Contribution Rules

- Always create a feature branch (e.g., `feat-auth` or `fix-UI`).
- Use descriptive commit messages.
- Ensure the backend compiles (`mvn compile`) before pushing.
- Maintain code consistency (Tailwind utilities only, standard Java naming conventions).
