<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>MPJ Vault - Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body style="display: flex; align-items: center; justify-content: center; height: 100vh; text-align: center;">
    <div class="card" style="max-width: 500px;">
        <h1 style="color: var(--danger); font-size: 4rem;">Oops!</h1>
        <p>Something went wrong or the page you are looking for doesn't exist.</p>
        <p style="color: #666; margin-bottom: 30px;">Error Code: ${pageContext.errorData.statusCode}</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">Return to Dashboard</a>
    </div>
</body>
</html>
