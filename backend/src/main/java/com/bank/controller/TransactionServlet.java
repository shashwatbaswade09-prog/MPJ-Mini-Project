package com.bank.controller;

import com.bank.model.Account;
import com.bank.repository.BankingDAO;
import com.bank.thread.TransactionLoggerThread;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TransactionServlet extends HttpServlet {
    private BankingDAO bankingDAO = new BankingDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        Account account = (Account) session.getAttribute("account");
        String path = request.getPathInfo();

        try {
            if ("/deposit".equals(path)) {
                double amount = Double.parseDouble(request.getParameter("amount"));
                bankingDAO.updateBalance(account.getAccountId(), amount);
                bankingDAO.addTransaction(account.getAccountId(), "Deposit", amount);
                logTransactionAsync(account.getAccountId(), "DEPOSIT", amount);
                session.setAttribute("message", "Deposit successful!");

            } else if ("/withdraw".equals(path)) {
                double amount = Double.parseDouble(request.getParameter("amount"));
                Account freshAcc = bankingDAO.getAccount(account.getAccountId());
                if (freshAcc.getBalance() >= amount) {
                    bankingDAO.updateBalance(account.getAccountId(), -amount);
                    bankingDAO.addTransaction(account.getAccountId(), "Withdraw", amount);
                    logTransactionAsync(account.getAccountId(), "WITHDRAWAL", amount);
                    session.setAttribute("message", "Withdrawal successful!");
                } else {
                    session.setAttribute("error", "Insufficient balance!");
                }

            } else if ("/transfer".equals(path)) {
                int toAccount = Integer.parseInt(request.getParameter("toAccount"));
                double amount = Double.parseDouble(request.getParameter("amount"));
                Account freshSender = bankingDAO.getAccount(account.getAccountId());
                Account freshReceiver = bankingDAO.getAccount(toAccount);

                if (freshReceiver == null) {
                    session.setAttribute("error", "Target account not found!");
                } else if (freshSender.getBalance() < amount) {
                    session.setAttribute("error", "Insufficient balance!");
                } else {
                    bankingDAO.updateBalance(account.getAccountId(), -amount);
                    bankingDAO.updateBalance(toAccount, amount);
                    bankingDAO.addTransaction(account.getAccountId(), "Transfer Out to #" + toAccount, amount);
                    bankingDAO.addTransaction(toAccount, "Transfer In from #" + account.getAccountId(), amount);
                    logTransactionAsync(account.getAccountId(), "TRANSFER_OUT", amount);
                    session.setAttribute("message", "Transfer successful!");
                }
            }
        } catch (Exception e) {
            session.setAttribute("error", "Transaction failed: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private void logTransactionAsync(int accountId, String type, double amount) {
        // Multithreading: Log transaction in a background thread
        new Thread(new TransactionLoggerThread(accountId, type, amount)).start();
    }
}
