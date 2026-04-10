package com.bank.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private int accountId;
    private int customerId;
    private double balance;
    private String password;

    public Account() {}

    public Account(int accountId, int customerId, double balance, String password) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.balance = balance;
        this.password = password;
    }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
