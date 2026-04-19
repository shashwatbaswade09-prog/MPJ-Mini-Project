package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Loan;
import com.bank.model.Transaction;
import com.bank.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BankingDAO {

    public Account login(int accountId, String password) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ? AND password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("account_id"),
                            rs.getInt("customer_id"),
                            rs.getDouble("balance"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        }
        return null;
    }

    public void createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_id, customer_id, balance, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, account.getAccountId());
            pstmt.setInt(2, account.getCustomerId());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getPassword());
            pstmt.setString(5, account.getRole());
            pstmt.executeUpdate();
        }
    }

    public Account getAccount(int accountId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getInt("account_id"),
                            rs.getInt("customer_id"),
                            rs.getDouble("balance"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        }
        return null;
    }

    public void updateBalance(int accountId, double amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
        }
    }

    public void addTransaction(int accountId, String type, double amount) throws SQLException {
        String sql = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
        }
    }

    public List<Transaction> getTransactions(int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE account_id = ? ORDER BY date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Transaction tx = new Transaction();
                    tx.setId(rs.getInt("id"));
                    tx.setAccountId(rs.getInt("account_id"));
                    tx.setType(rs.getString("type"));
                    tx.setAmount(rs.getDouble("amount"));
                    tx.setDate(rs.getTimestamp("date").toLocalDateTime());
                    transactions.add(tx);
                }
            }
        }
        return transactions;
    }

    // Loan Operations
    public void requestLoan(int accountId, double amount) throws SQLException {
        String sql = "INSERT INTO loans (account_id, amount, status) VALUES (?, ?, 'PENDING')";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
        }
    }

    public List<Loan> getLoans(int accountId) throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE account_id = ? ORDER BY request_date DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    loans.add(new Loan(
                            rs.getInt("id"),
                            rs.getInt("account_id"),
                            rs.getDouble("amount"),
                            rs.getString("status"),
                            rs.getTimestamp("request_date").toLocalDateTime()
                    ));
                }
            }
        }
        return loans;
    }

    // Admin Operations
    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accounts.add(new Account(
                        rs.getInt("account_id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("balance"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        }
        return accounts;
    }

    public List<Loan> getAllPendingLoans() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE status = 'PENDING'";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("id"),
                        rs.getInt("account_id"),
                        rs.getDouble("amount"),
                        rs.getString("status"),
                        rs.getTimestamp("request_date").toLocalDateTime()
                ));
            }
        }
        return loans;
    }

    public void updateLoanStatus(int loanId, String status) throws SQLException {
        String sql = "UPDATE loans SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, loanId);
            pstmt.executeUpdate();
        }
    }
}
