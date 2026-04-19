package com.bank.model;

import java.time.LocalDateTime;

public class Loan {
    private int id;
    private int accountId;
    private double amount;
    private String status; // PENDING, APPROVED, REJECTED
    private LocalDateTime requestDate;

    public Loan() {}

    public Loan(int id, int accountId, double amount, String status, LocalDateTime requestDate) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.status = status;
        this.requestDate = requestDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
}
