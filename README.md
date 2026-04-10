# 💳 Vault Pro: Modern Fullstack Banking Platform

[![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)

**Vault Pro** is a high-performance, secure banking dashboard designed for the modern era. It combines a sleek, responsive React frontend with a robust Spring Boot backend to provide a seamless financial management experience.

---

## 🎯 Key Features

- **🚀 Instant Account Creation**: Seamlessly register new banking accounts with initial balances.
- **🔒 Secure Authentication**: Multi-layered security with password hashing and session-based access.
- **💸 Real-time Transactions**: Perform deposits, withdrawals, and peer-to-peer transfers with ACID compliance.
- **📊 Financial Analytics**: Comprehensive transaction ledger with real-time history and balance tracking.
- **🎨 Premium UI/UX**: Aesthetic dashboard built with Tailwind CSS and Lucide icons.

---

## 👥 Team & Work Division

To demonstrate modular development, this project is structured for a 4-person engineering team:

| Team Member | Role | Key Responsibilities |
| :--- | :--- | :--- |
| **Developer 1** | **Frontend Architect** | UI/UX design, Dashboard components, and Global State Management. |
| **Developer 2** | **Backend Lead** | Core API development, Business Logic (Service Layer), and Database Schema. |
| **Developer 3** | **Security Specialist** | Authentication flow, Password encryption, and API Data Validation. |
| **Developer 4** | **QA & DevOps** | Integration testing, Database consistency, and Repository Management. |

---

## 🛠️ Technology Stack

- **Frontend**: React (Vite), Axios, Tailwind CSS, Lucide-React.
- **Backend**: Java 17, Spring Boot 3.x, Spring Data JPA, Spring Web.
- **Database**: MySQL 8.0+.
- **Build Tools**: Maven 3.9+, NPM.

---

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 17+
- MySQL Server 8.0+
- Node.js & NPM

### Setup Database
1. Create a MySQL database named `mpj_vault`.
2. Configure your credentials in `backend/src/main/resources/application.properties`.

### Launch Backend
```bash
cd backend
mvn spring-boot:run
```
*Accessible at: `http://localhost:8081`*

### Launch Frontend
```bash
cd frontend
npm install
npm run dev
```
*Accessible at: `http://localhost:5173/MPJ-Mini-Project/`*

---

## 📑 API Endpoints

### Accounts
- `POST /api/accounts`: Register a new account.
- `POST /api/accounts/login`: Authenticate and access dashboard.
- `GET /api/accounts/{id}`: Fetch account profile.

### Transactions
- `POST /api/transactions/deposit`: Add funds.
- `POST /api/transactions/withdraw`: Remove funds safely.
- `POST /api/transactions/transfer`: In-system peer-to-peer transfer.
- `GET /api/accounts/{id}/history`: Retrieve full transaction ledger.

---

## 📜 License
*Demonstration project for Advanced Fullstack Banking Architecture.*
