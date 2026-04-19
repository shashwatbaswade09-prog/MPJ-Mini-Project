package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Loan;
import com.bank.model.Transaction;
import com.bank.repository.BankingDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class DashboardServlet extends HttpServlet {
    private BankingDAO bankingDAO = new BankingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Account account = (Account) session.getAttribute("account");

        try {
            // Refresh account data
            Account freshAccount = bankingDAO.getAccount(account.getAccountId());
            session.setAttribute("account", freshAccount);

            // Get transactions and loans
            List<Transaction> transactions = bankingDAO.getTransactions(account.getAccountId());
            List<Loan> loans = bankingDAO.getLoans(account.getAccountId());

            request.setAttribute("transactions", transactions);
            request.setAttribute("loans", loans);

            if ("ADMIN".equals(freshAccount.getRole())) {
                List<Account> allAccounts = bankingDAO.getAllAccounts();
                List<Loan> pendingLoans = bankingDAO.getAllPendingLoans();
                request.setAttribute("allAccounts", allAccounts);
                request.setAttribute("pendingLoans", pendingLoans);
                request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("logout");
        }
    }
}
