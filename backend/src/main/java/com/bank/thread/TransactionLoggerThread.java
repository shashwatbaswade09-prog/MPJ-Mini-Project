package com.bank.thread;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class TransactionLoggerThread implements Runnable {
    private int accountId;
    private String type;
    private double amount;

    public TransactionLoggerThread(int accountId, String type, double amount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public void run() {
        // Simulate background processing (e.g., logging to a file)
        try (PrintWriter out = new PrintWriter(new FileWriter("banking_logs.txt", true))) {
            out.println("[" + LocalDateTime.now() + "] Account " + accountId + ": " + type + " - Amount: $" + amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
