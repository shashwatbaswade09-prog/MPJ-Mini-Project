package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankService {
    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    public BankService(AccountRepository accountRepo, TransactionRepository transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    public Account getAccount(int accId) {
        return accountRepo.findById(accId).orElse(null);
    }

    @Transactional
    public void createAccount(int accId, int custId, double balance, String password) {
        if (accountRepo.existsById(accId)) {
            throw new IllegalArgumentException("Account with ID " + accId + " already exists.");
        }
        Account account = new Account(accId, custId, balance, password);
        accountRepo.save(account);
    }

    public Account login(int accountId, String password) {
        Account acc = accountRepo.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (acc.getPassword().equals(password)) {
            return acc;
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    @Transactional
    public void deposit(int accId, double amount) {
        Account acc = accountRepo.findById(accId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account"));
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        acc.setBalance(acc.getBalance() + amount);
        accountRepo.save(acc);
        
        Transaction tx = new Transaction(0, accId, "Deposit", amount);
        transactionRepo.save(tx);
    }

    @Transactional
    public void withdraw(int accId, double amount) {
        Account acc = accountRepo.findById(accId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        if (acc.getBalance() >= amount) {
            acc.setBalance(acc.getBalance() - amount);
            accountRepo.save(acc);
            
            Transaction tx = new Transaction(0, accId, "Withdraw", amount);
            transactionRepo.save(tx);
        } else {
            throw new IllegalArgumentException("Insufficient Balance!");
        }
    }

    @Transactional
    public void transfer(int from, int to, double amount) {
        Account sender = accountRepo.findById(from)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender account"));
        Account receiver = accountRepo.findById(to)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver account"));

        if (sender.getBalance() >= amount) {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            
            accountRepo.save(sender);
            accountRepo.save(receiver);

            Transaction txDebit = new Transaction(0, from, "Transfer Debit", amount);
            Transaction txCredit = new Transaction(0, to, "Transfer Credit", amount);
            transactionRepo.save(txDebit);
            transactionRepo.save(txCredit);
        } else {
            throw new IllegalArgumentException("Insufficient Funds");
        }
    }

    public List<Transaction> getHistory(int accId) {
        return transactionRepo.findByAccountId(accId);
    }
}
