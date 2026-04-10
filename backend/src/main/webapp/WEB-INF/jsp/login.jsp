<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vault Pro - Login</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-slate-50 min-h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full bg-white p-8 rounded-2xl shadow-xl shadow-slate-200 border border-slate-100">
        <div class="text-center mb-8">
            <div class="mx-auto w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center mb-4">
                <i class="fa-solid fa-credit-card text-blue-600 text-xl"></i>
            </div>
            <h2 class="text-2xl font-bold text-slate-800" id="formTitle">
                ${isRegister ? 'Create Account' : 'Welcome Back'}
            </h2>
            <p class="text-slate-500 mt-2" id="formSubtitle">
                ${isRegister ? 'Sign up to start saving' : 'Enter your Account ID to continue'}
            </p>
        </div>

        <c:if test="${not empty error}">
            <div class="mb-4 p-3 bg-red-50 text-red-600 rounded-lg text-sm border border-red-100">
                ${error}
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="mb-4 p-3 bg-green-50 text-green-700 rounded-lg text-sm border border-green-100">
                ${message}
            </div>
        </c:if>

        <div id="loginFormContainer" class="${isRegister ? 'hidden' : ''}">
            <form action="/login" method="post" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Account ID</label>
                    <input type="number" name="accountId" required placeholder="e.g. 1001"
                        class="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Password</label>
                    <input type="password" name="password" required placeholder="Enter your password"
                        class="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all">
                </div>
                <button type="submit"
                    class="w-full bg-slate-900 hover:bg-slate-800 text-white font-medium py-2.5 rounded-lg transition-colors flex items-center justify-center gap-2">
                    Access Dashboard <i class="fa-solid fa-arrow-right text-xs"></i>
                </button>
            </form>
        </div>

        <div id="registerFormContainer" class="${isRegister ? '' : 'hidden'}">
            <form action="/register" method="post" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Account ID</label>
                    <input type="number" name="accountId" required
                        class="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Customer ID</label>
                    <input type="number" name="customerId" required
                        class="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Initial Balance ($)</label>
                    <input type="number" name="balance" required step="0.01"
                        class="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Password</label>
                    <input type="password" name="password" required
                        class="w-full px-4 py-2 border border-slate-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all">
                </div>
                <button type="submit"
                    class="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2.5 rounded-lg transition-colors flex items-center justify-center gap-2">
                    Create Account <i class="fa-solid fa-user-plus text-xs"></i>
                </button>
            </form>
        </div>

        <div class="mt-6 text-center">
            <button onclick="toggleForm()" id="toggleBtn"
                class="text-sm font-medium text-blue-600 hover:text-blue-700">
                ${isRegister ? 'Already have an account? Login' : 'Need an account? Register'}
            </button>
        </div>
    </div>

    <script>
        function toggleForm() {
            const loginForm = document.getElementById('loginFormContainer');
            const registerForm = document.getElementById('registerFormContainer');
            const title = document.getElementById('formTitle');
            const subtitle = document.getElementById('formSubtitle');
            const btn = document.getElementById('toggleBtn');

            if (loginForm.classList.contains('hidden')) {
                loginForm.classList.remove('hidden');
                registerForm.classList.add('hidden');
                title.innerText = 'Welcome Back';
                subtitle.innerText = 'Enter your Account ID to continue';
                btn.innerText = 'Need an account? Register';
            } else {
                loginForm.classList.add('hidden');
                registerForm.classList.remove('hidden');
                title.innerText = 'Create Account';
                subtitle.innerText = 'Sign up to start saving';
                btn.innerText = 'Already have an account? Login';
            }
        }
    </script>
</body>
</html>
