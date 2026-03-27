package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.BankService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, Object> payload) {
        try {
            int accId = Integer.parseInt(payload.get("accountId").toString());
            int custId = Integer.parseInt(payload.get("customerId").toString());
            double balance = Double.parseDouble(payload.get("balance").toString());
            
            bankService.createAccount(accId, custId, balance);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Account Created Successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccount(@PathVariable int id) {
        Account acc = bankService.getAccount(id);
        if (acc != null) {
            return ResponseEntity.ok(acc);
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Account not found"));
        }
    }

    @PostMapping("/transactions/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, Object> payload) {
        try {
            int accId = Integer.parseInt(payload.get("accountId").toString());
            double amount = Double.parseDouble(payload.get("amount").toString());
            bankService.deposit(accId, amount);
            return ResponseEntity.ok(Map.of("message", "Deposit Successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/transactions/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Map<String, Object> payload) {
        try {
            int accId = Integer.parseInt(payload.get("accountId").toString());
            double amount = Double.parseDouble(payload.get("amount").toString());
            bankService.withdraw(accId, amount);
            return ResponseEntity.ok(Map.of("message", "Withdrawal Successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/transactions/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> payload) {
        try {
            int fromId = Integer.parseInt(payload.get("fromAccount").toString());
            int toId = Integer.parseInt(payload.get("toAccount").toString());
            double amount = Double.parseDouble(payload.get("amount").toString());
            bankService.transfer(fromId, toId, amount);
            return ResponseEntity.ok(Map.of("message", "Transfer Successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/accounts/{id}/history")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable int id) {
        return ResponseEntity.ok(bankService.getHistory(id));
    }
}
