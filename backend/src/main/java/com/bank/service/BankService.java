package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.BankDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private final BankDAO dao;

    public BankService(BankDAO dao) {
        this.dao = dao;
    }

    public Account getAccount(int accId) {
        return dao.getAccount(accId);
    }

    public void createAccount(int accId, int custId, double balance) {
        if (dao.getAccount(accId) != null) {
            throw new IllegalArgumentException("Account with ID " + accId + " already exists.");
        }
        dao.createAccount(accId, custId, balance);
    }

    public void deposit(int accId, double amount) {
        dao.deposit(accId, amount);
    }

    public void withdraw(int accId, double amount) {
        dao.withdraw(accId, amount);
    }

    public void transfer(int from, int to, double amount) {
        dao.transfer(from, to, amount);
    }

    public List<Transaction> getHistory(int accId) {
        return dao.getTransactions(accId);
    }
}
