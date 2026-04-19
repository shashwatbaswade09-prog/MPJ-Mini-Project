package com.bank.model;

public class Account {
    private int accountId;
    private int customerId;
    private double balance;
    private String password;
    private String role; // "ADMIN" or "USER"

    public Account() {}

    public Account(int accountId, int customerId, double balance, String password, String role) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.balance = balance;
        this.password = password;
        this.role = role;
    }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
