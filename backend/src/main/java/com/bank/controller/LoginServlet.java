package com.bank.controller;

import com.bank.model.Account;
import com.bank.repository.BankingDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private BankingDAO bankingDAO = new BankingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            String password = request.getParameter("password");

            Account account = bankingDAO.login(accountId, password);

            if (account != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                response.sendRedirect("dashboard");
            } else {
                request.setAttribute("error", "Invalid Account ID or Password");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred during login.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }
}
