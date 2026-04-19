<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>MPJ Vault - Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <nav class="navbar">
        <div style="font-size: 1.5rem; font-weight: 700;">MPJ Vault</div>
        <div>
            <span>Welcome, Account #${sessionScope.account.accountId}</span>
            <a href="logout">Logout</a>
        </div>
    </nav>

    <div class="container">
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-success">${sessionScope.message}</div>
            <% session.removeAttribute("message"); %>
        </c:if>
        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-danger">${sessionScope.error}</div>
            <% session.removeAttribute("error"); %>
        </c:if>

        <div class="stats-grid">
            <div class="card stat-card">
                <h3>Current Balance</h3>
                <div class="stat-value">$<fmt:formatNumber value="${sessionScope.account.balance}" minFractionDigits="2" /></div>
            </div>
            <div class="card stat-card">
                <h3>Customer ID</h3>
                <div class="stat-value">${sessionScope.account.customerId}</div>
            </div>
            <div class="card stat-card">
                <h3>Account Type</h3>
                <div class="stat-value">${sessionScope.account.role}</div>
            </div>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 25px;">
            <!-- Left Side: Actions -->
            <div>
                <div class="card">
                    <h3>Quick Actions</h3>
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                        <button onclick="showForm('depositForm')" class="btn btn-primary">Deposit</button>
                        <button onclick="showForm('withdrawForm')" class="btn btn-primary">Withdraw</button>
                        <button onclick="showForm('transferForm')" class="btn btn-primary">Transfer</button>
                        <button onclick="showForm('loanForm')" class="btn btn-primary">Apply Loan</button>
                    </div>

                    <!-- Deposit Form -->
                    <div id="depositForm" class="action-form" style="display:none; margin-top:20px;">
                        <h4>Deposit Funds</h4>
                        <form action="transactions/deposit" method="post">
                            <input type="number" name="amount" step="0.01" placeholder="Amount to Deposit" required>
                            <button type="submit" class="btn btn-success">Confirm Deposit</button>
                        </form>
                    </div>

                    <!-- Withdraw Form -->
                    <div id="withdrawForm" class="action-form" style="display:none; margin-top:20px;">
                        <h4>Withdraw Funds</h4>
                        <form action="transactions/withdraw" method="post">
                            <input type="number" name="amount" step="0.01" placeholder="Amount to Withdraw" required>
                            <button type="submit" class="btn btn-danger">Confirm Withdrawal</button>
                        </form>
                    </div>

                    <!-- Transfer Form -->
                    <div id="transferForm" class="action-form" style="display:none; margin-top:20px;">
                        <h4>Fund Transfer</h4>
                        <form action="transactions/transfer" method="post">
                            <input type="number" name="toAccount" placeholder="Recipient Account ID" required>
                            <input type="number" name="amount" step="0.01" placeholder="Amount to Transfer" required>
                            <button type="submit" class="btn btn-primary">Send Funds</button>
                        </form>
                    </div>

                    <!-- Loan Form -->
                    <div id="loanForm" class="action-form" style="display:none; margin-top:20px;">
                        <h4>Apply for Loan</h4>
                        <form action="loans/apply" method="post">
                            <input type="number" name="amount" step="0.01" placeholder="Loan Amount" required>
                            <button type="submit" class="btn btn-success">Submit Request</button>
                        </form>
                    </div>
                </div>

                <div class="card">
                    <h3>My Loan Requests</h3>
                    <table>
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="loan" items="${loans}">
                                <tr>
                                    <td>${loan.requestDate}</td>
                                    <td>$<fmt:formatNumber value="${loan.amount}" minFractionDigits="2" /></td>
                                    <td>
                                        <span class="badge ${loan.status == 'APPROVED' ? 'alert-success' : (loan.status == 'REJECTED' ? 'alert-danger' : '')}">
                                            ${loan.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Right Side: History -->
            <div class="card">
                <h3>Recent Transactions</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Type</th>
                            <th>Amount</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tx" items="${transactions}">
                            <tr>
                                <td>${tx.type}</td>
                                <td style="color: ${tx.type.contains('Withdraw') || tx.type.contains('Debit') ? 'var(--danger)' : 'var(--success)'}">
                                    ${tx.type.contains('Withdraw') || tx.type.contains('Debit') ? '-' : '+'}$<fmt:formatNumber value="${tx.amount}" minFractionDigits="2" />
                                </td>
                                <td style="font-size: 0.8rem; color: #666;">${tx.date}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        function showForm(formId) {
            document.querySelectorAll('.action-form').forEach(f => f.style.display = 'none');
            document.getElementById(formId).style.display = 'block';
        }
    </script>
</body>
</html>
