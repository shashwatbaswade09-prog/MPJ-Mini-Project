<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>MPJ Vault - Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <nav class="navbar">
        <div style="font-size: 1.5rem; font-weight: 700;">MPJ Vault | Admin</div>
        <div>
            <span>Welcome, Admin #${sessionScope.account.accountId}</span>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
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

        <div class="card">
            <h2>Pending Loan Requests</h2>
            <table>
                <thead>
                    <tr>
                        <th>Loan ID</th>
                        <th>Account ID</th>
                        <th>Amount</th>
                        <th>Request Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="loan" items="${pendingLoans}">
                        <tr>
                            <td>${loan.id}</td>
                            <td>${loan.accountId}</td>
                            <td>$<fmt:formatNumber value="${loan.amount}" minFractionDigits="2" /></td>
                            <td>${loan.requestDate}</td>
                            <td>
                                <div style="display: flex; gap: 5px;">
                                    <form action="${pageContext.request.contextPath}/admin/approveLoan" method="post" style="display:inline;">
                                        <input type="hidden" name="loanId" value="${loan.id}">
                                        <input type="hidden" name="accountId" value="${loan.accountId}">
                                        <input type="hidden" name="amount" value="${loan.amount}">
                                        <button type="submit" class="btn btn-success" style="padding: 5px 10px; font-size: 0.8rem;">Approve</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/admin/rejectLoan" method="post" style="display:inline;">
                                        <input type="hidden" name="loanId" value="${loan.id}">
                                        <button type="submit" class="btn btn-danger" style="padding: 5px 10px; font-size: 0.8rem;">Reject</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty pendingLoans}">
                        <tr><td colspan="5" style="text-align:center;">No pending loan requests.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <div class="card">
            <h2>System Accounts Overview</h2>
            <table>
                <thead>
                    <tr>
                        <th>Account ID</th>
                        <th>Customer ID</th>
                        <th>Balance</th>
                        <th>Role</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="acc" items="${allAccounts}">
                        <tr>
                            <td>${acc.accountId}</td>
                            <td>${acc.customerId}</td>
                            <td>$<fmt:formatNumber value="${acc.balance}" minFractionDigits="2" /></td>
                            <td>
                                <span style="background: ${acc.role == 'ADMIN' ? '#2c3e50' : '#3498db'}; color: white; padding: 2px 8px; border-radius: 4px; font-size: 0.8rem;">
                                    ${acc.role}
                                </span>
                            </td>
                            <td style="color: var(--success); font-weight: 600;">ACTIVE</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
