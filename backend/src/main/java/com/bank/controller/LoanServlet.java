package com.bank.controller;

import com.bank.model.Account;
import com.bank.repository.BankingDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoanServlet extends HttpServlet {
    private BankingDAO bankingDAO = new BankingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Account account = (Account) session.getAttribute("account");
        String path = request.getPathInfo();

        try {
            if ("/apply".equals(path)) {
                double amount = Double.parseDouble(request.getParameter("amount"));
                bankingDAO.requestLoan(account.getAccountId(), amount);
                session.setAttribute("message", "Loan application submitted!");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Loan application failed: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
