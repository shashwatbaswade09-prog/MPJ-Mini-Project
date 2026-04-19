package com.bank.controller;

import com.bank.repository.BankingDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    private BankingDAO bankingDAO = new BankingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String path = request.getPathInfo();

        try {
            if ("/approveLoan".equals(path)) {
                int loanId = Integer.parseInt(request.getParameter("loanId"));
                int accountId = Integer.parseInt(request.getParameter("accountId"));
                double amount = Double.parseDouble(request.getParameter("amount"));

                bankingDAO.updateLoanStatus(loanId, "APPROVED");
                bankingDAO.updateBalance(accountId, amount);
                bankingDAO.addTransaction(accountId, "Loan Disbursal", amount);
                session.setAttribute("message", "Loan approved and funds disbursed!");

            } else if ("/rejectLoan".equals(path)) {
                int loanId = Integer.parseInt(request.getParameter("loanId"));
                bankingDAO.updateLoanStatus(loanId, "REJECTED");
                session.setAttribute("message", "Loan rejected.");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Admin action failed: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}
