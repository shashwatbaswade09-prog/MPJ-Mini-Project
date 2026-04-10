<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vault Pro - Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-slate-50 min-h-screen">
    <!-- Header -->
    <nav class="bg-white border-b border-slate-100 sticky top-0 z-50">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
            <div class="flex items-center gap-2">
                <i class="fa-solid fa-credit-card text-blue-600 text-xl"></i>
                <span class="text-xl font-bold text-slate-800 tracking-tight">MPJ Vault</span>
            </div>
            <div class="flex items-center gap-6">
                <span class="text-sm font-medium text-slate-600 hidden md:block">Welcome, ${account.customerId}</span>
                <a href="/logout" class="text-sm font-medium text-slate-500 hover:text-slate-900 transition-colors">Sign Out</a>
            </div>
        </div>
    </nav>

    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
        
        <!-- Welcome Message -->
        <h1 class="text-2xl font-bold text-slate-800">Your Financial Overview</h1>

        <c:if test="${not empty error}">
            <div class="p-4 bg-red-50 text-red-600 rounded-xl text-sm border border-red-100 flex items-center gap-3">
                <i class="fa-solid fa-circle-exclamation"></i> ${error}
            </div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="p-4 bg-green-50 text-green-700 rounded-xl text-sm border border-green-100 flex items-center gap-3">
                <i class="fa-solid fa-circle-check"></i> ${message}
            </div>
        </c:if>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <!-- Balance Card -->
            <div class="lg:col-span-2 bg-slate-900 rounded-3xl p-8 text-white shadow-2xl relative overflow-hidden group">
                <div class="absolute top-0 right-0 -mr-16 -mt-16 w-64 h-64 bg-slate-800 rounded-full opacity-20 transform group-hover:scale-110 transition-transform duration-700"></div>
                <div class="relative z-10">
                    <div class="flex justify-between items-start mb-12">
                        <div>
                            <h3 class="text-slate-400 font-medium mb-1">Available Balance</h3>
                            <div class="text-6xl font-bold tracking-tight">
                                $<fmt:formatNumber type="number" minFractionDigits="2" value="${account.balance}" />
                            </div>
                        </div>
                        <div class="bg-blue-600/20 text-blue-400 p-3 rounded-2xl border border-blue-500/20 backdrop-blur-sm">
                            <i class="fa-solid fa-shield-halved text-2xl"></i>
                        </div>
                    </div>
                    <div class="flex items-center justify-between">
                        <div class="space-y-1">
                            <p class="text-slate-500 text-xs font-bold uppercase tracking-widest">Account Holder</p>
                            <p class="font-medium text-slate-300">Customer #${account.customerId}</p>
                        </div>
                        <div class="space-y-1 text-right">
                            <p class="text-slate-500 text-xs font-bold uppercase tracking-widest">Account Number</p>
                            <p class="font-medium text-slate-300">#${account.accountId}</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Quick Actions -->
            <div class="bg-white rounded-3xl p-6 shadow-sm border border-slate-100 flex flex-col justify-between">
                <h3 class="font-bold text-slate-800 mb-6 flex items-center gap-2">
                    <i class="fa-solid fa-bolt text-blue-500"></i> Quick Actions
                </h3>
                <div class="grid grid-cols-1 gap-3">
                    <button onclick="openModal('deposit')" 
                        class="w-full h-14 flex items-center justify-between px-5 rounded-2xl bg-blue-50 text-blue-700 hover:bg-blue-100 transition-all font-semibold group">
                        <span class="flex items-center gap-3"><i class="fa-solid fa-circle-up text-lg group-hover:scale-110 transition-transform"></i> Deposit</span>
                        <i class="fa-solid fa-chevron-right text-xs opacity-50"></i>
                    </button>
                    <button onclick="openModal('withdraw')" 
                        class="w-full h-14 flex items-center justify-between px-5 rounded-2xl bg-slate-50 text-slate-700 hover:bg-slate-100 transition-all font-semibold border border-slate-200 group">
                        <span class="flex items-center gap-3"><i class="fa-solid fa-circle-down text-lg group-hover:scale-110 transition-transform text-orange-500"></i> Withdraw</span>
                        <i class="fa-solid fa-chevron-right text-xs opacity-50"></i>
                    </button>
                    <button onclick="openModal('transfer')" 
                        class="w-full h-14 flex items-center justify-between px-5 rounded-2xl bg-slate-900 text-white hover:bg-slate-800 transition-all font-semibold group">
                        <span class="flex items-center gap-3"><i class="fa-solid fa-paper-plane text-lg group-hover:scale-110 transition-transform text-blue-400"></i> Transfer</span>
                        <i class="fa-solid fa-chevron-right text-xs opacity-50"></i>
                    </button>
                </div>
            </div>
        </div>

        <!-- History -->
        <div class="bg-white rounded-3xl shadow-sm border border-slate-100 overflow-hidden">
            <div class="px-8 py-6 border-b border-slate-100 flex items-center justify-between">
                <h3 class="font-bold text-slate-800 flex items-center gap-3">
                    <i class="fa-solid fa-clock-rotate-left text-slate-400"></i> Recent Activity
                </h3>
            </div>
            <div class="divide-y divide-slate-50">
                <c:choose>
                    <c:when test="${empty transactions}">
                        <div class="px-8 py-16 text-center text-slate-400 text-sm italic">
                            No transactions record found for this account.
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="tx" items="${transactions}">
                            <div class="px-8 py-5 flex items-center justify-between hover:bg-slate-50 transition-colors">
                                <div class="flex items-center gap-5">
                                    <div class="w-12 h-12 rounded-2xl flex items-center justify-center ${tx.type == 'Deposit' || tx.type == 'Transfer Credit' ? 'bg-green-100 text-green-600' : 'bg-red-50 text-red-500'}">
                                        <i class="fa-solid ${tx.type == 'Deposit' || tx.type == 'Transfer Credit' ? 'fa-arrow-down' : 'fa-arrow-up'} text-lg"></i>
                                    </div>
                                    <div>
                                        <div class="font-bold text-slate-800">${tx.type}</div>
                                        <div class="text-xs font-semibold text-slate-400 uppercase tracking-tighter">
                                            <fmt:parseDate value="${tx.date}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDate" type="both" />
                                            <fmt:formatDate value="${parsedDate}" pattern="MMM dd, yyyy" /> • <fmt:formatDate value="${parsedDate}" pattern="HH:mm" />
                                        </div>
                                    </div>
                                </div>
                                <div class="text-right">
                                    <div class="font-bold text-lg ${tx.type == 'Deposit' || tx.type == 'Transfer Credit' ? 'text-green-600' : 'text-slate-800'}">
                                        ${tx.type == 'Deposit' || tx.type == 'Transfer Credit' ? '+' : '-'}$<fmt:formatNumber value="${tx.amount}" minFractionDigits="2"/>
                                    </div>
                                    <div class="text-[10px] font-bold text-slate-300 uppercase">Settled</div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </main>

    <!-- Modal Base -->
    <div id="actionModal" class="hidden fixed inset-0 z-[100] flex items-center justify-center p-4">
        <div class="absolute inset-0 bg-slate-900/40 backdrop-blur-sm" onclick="closeModal()"></div>
        <div class="relative bg-white w-full max-w-md rounded-3xl p-8 shadow-2xl animate-in fade-in zoom-in duration-300">
            <h2 id="modalTitle" class="text-2xl font-bold text-slate-800 mb-6 flex items-center gap-3 capitalize"></h2>
            
            <form id="actionForm" action="" method="post" class="space-y-5">
                <input type="hidden" name="accountId" value="${account.accountId}">
                <div>
                    <label class="block text-sm font-bold text-slate-600 mb-2 uppercase tracking-widest text-[10px]">Enter Amount ($)</label>
                    <div class="relative">
                        <span class="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 font-bold">$</span>
                        <input type="number" name="amount" step="0.01" min="0.01" required placeholder="0.00"
                            class="w-full pl-8 pr-4 py-4 bg-slate-50 border border-slate-200 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all font-bold text-lg">
                    </div>
                </div>

                <div id="transferField" class="hidden">
                    <label class="block text-sm font-bold text-slate-600 mb-2 uppercase tracking-widest text-[10px]">To Account Number</label>
                    <input type="number" name="toAccount" placeholder="Recipient Account ID"
                        class="w-full px-4 py-4 bg-slate-50 border border-slate-200 rounded-2xl focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all font-bold">
                </div>

                <div class="flex gap-3 pt-4">
                    <button type="button" onclick="closeModal()" 
                        class="flex-1 px-4 py-4 rounded-2xl bg-slate-100 text-slate-600 font-bold hover:bg-slate-200 transition-colors uppercase tracking-widest text-xs">
                        Cancel
                    </button>
                    <button type="submit" 
                        class="flex-2 px-8 py-4 rounded-2xl bg-blue-600 text-white font-bold hover:bg-blue-700 shadow-lg shadow-blue-200 transition-all uppercase tracking-widest text-xs">
                        Confirm Transaction
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openModal(type) {
            const modal = document.getElementById('actionModal');
            const title = document.getElementById('modalTitle');
            const form = document.getElementById('actionForm');
            const transferField = document.getElementById('transferField');
            const toAccInput = transferField.querySelector('input');

            modal.classList.remove('hidden');
            title.innerText = type;
            
            if (type === 'transfer') {
                form.action = '/transactions/transfer';
                transferField.classList.remove('hidden');
                toAccInput.required = true;
                form.name = 'fromAccount'; // For transfer endpoint mapping
                // Map the input names for transfer
                document.querySelector('input[name="accountId"]').name = "fromAccount";
                document.querySelector('input[name="toAccount"]').name = "toAccount";
            } else {
                form.action = '/transactions/' + type;
                transferField.classList.add('hidden');
                toAccInput.required = false;
                document.querySelector('input[name="fromAccount"]')?.setAttribute('name', 'accountId');
            }
        }

        function closeModal() {
            document.getElementById('actionModal').classList.add('hidden');
        }
    </script>
</body>
</html>
