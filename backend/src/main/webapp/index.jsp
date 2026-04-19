<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>MPJ Vault - Login</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body { display: flex; align-items: center; justify-content: center; height: 100vh; background: linear-gradient(135deg, #2c3e50, #3498db); }
        .login-card { width: 400px; text-align: center; }
        .logo { font-size: 2.5rem; font-weight: 700; color: var(--accent); margin-bottom: 30px; }
        .logo span { color: var(--primary); }
    </style>
</head>
<body>
    <div class="card login-card">
        <div class="logo">MPJ<span>Vault</span></div>
        <h3>Secure Banking Login</h3>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>

        <form action="login" method="post">
            <input type="number" name="accountId" placeholder="Account Number" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit" class="btn btn-primary">Login to My Vault</button>
        </form>
        
        <p style="margin-top: 20px; color: #666; font-size: 0.9rem;">
            Default Admin: 100 / admin123<br>
            Default User: 101 / user123
        </p>
    </div>
</body>
</html>
