package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BankDAO {
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public BankDAO() {
        // Add a default account for easy testing
        accounts.put(1001, new Account(1001, 1, 5000.0));
    }

    public Account getAccount(int accId) {
        return accounts.get(accId);
    }

    public void createAccount(int accId, int custId, double balance) {
        accounts.put(accId, new Account(accId, custId, balance));
    }

    public void deposit(int accId, double amount) {
        Account acc = accounts.get(accId);
        if (acc != null && amount > 0) {
            acc.setBalance(acc.getBalance() + amount);
            transactions.add(new Transaction(transactions.size() + 1, accId, "Deposit", amount));
        } else {
            throw new IllegalArgumentException("Invalid account or amount");
        }
    }

    public void withdraw(int accId, double amount) {
        Account acc = accounts.get(accId);
        if (acc != null) {
            if (acc.getBalance() >= amount) {
                acc.setBalance(acc.getBalance() - amount);
                transactions.add(new Transaction(transactions.size() + 1, accId, "Withdraw", amount));
            } else {
                throw new IllegalArgumentException("Insufficient Balance!");
            }
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    public void transfer(int from, int to, double amount) {
        Account sender = accounts.get(from);
        Account receiver = accounts.get(to);

        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Invalid sender or receiver account");
        }

        if (sender.getBalance() >= amount) {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            transactions.add(new Transaction(transactions.size() + 1, from, "Transfer Debit", amount));
            transactions.add(new Transaction(transactions.size() + 1, to, "Transfer Credit", amount));
        } else {
            throw new IllegalArgumentException("Insufficient Funds");
        }
    }

    public List<Transaction> getTransactions(int accId) {
        return transactions.stream()
            .filter(t -> t.getAccountId() == accId)
            .collect(Collectors.toList());
    }
}
