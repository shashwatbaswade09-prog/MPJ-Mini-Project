package com.bank.controller;

import com.bank.model.Account;
import com.bank.service.BankService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BankController {

    @Autowired
    private BankService bankService;

    // View: Login Page
    @GetMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("account") != null) return "redirect:/dashboard";
        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/";
    }

    // Action: Login
    @PostMapping("/login")
    public String login(@RequestParam int accountId, @RequestParam String password, 
                        HttpSession session, RedirectAttributes ra) {
        try {
            Account account = bankService.login(accountId, password);
            session.setAttribute("account", account);
            return "redirect:/dashboard";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }

    // Action: Register
    @PostMapping("/register")
    public String createAccount(@RequestParam int accountId, @RequestParam int customerId, 
                               @RequestParam double balance, @RequestParam String password,
                               RedirectAttributes ra) {
        try {
            bankService.createAccount(accountId, customerId, balance, password);
            ra.addFlashAttribute("message", "Account created successfully! Please login.");
            return "redirect:/";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            ra.addFlashAttribute("isRegister", true);
            return "redirect:/";
        }
    }

    // View: Dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Account sessionAcc = (Account) session.getAttribute("account");
        if (sessionAcc == null) return "redirect:/";

        try {
            // Get fresh data
            Account account = bankService.getAccount(sessionAcc.getAccountId());
            model.addAttribute("account", account);
            model.addAttribute("transactions", bankService.getHistory(account.getAccountId()));
            return "dashboard";
        } catch (Exception e) {
            return "redirect:/logout";
        }
    }

    // Action: Transactions
    @PostMapping("/transactions/deposit")
    public String deposit(@RequestParam int accountId, @RequestParam double amount, RedirectAttributes ra) {
        try {
            bankService.deposit(accountId, amount);
            ra.addFlashAttribute("message", "Deposit of $" + amount + " successful!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/transactions/withdraw")
    public String withdraw(@RequestParam int accountId, @RequestParam double amount, RedirectAttributes ra) {
        try {
            bankService.withdraw(accountId, amount);
            ra.addFlashAttribute("message", "Withdrawal of $" + amount + " successful!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/transactions/transfer")
    public String transfer(@RequestParam int fromAccount, @RequestParam int toAccount, 
                           @RequestParam double amount, RedirectAttributes ra) {
        try {
            bankService.transfer(fromAccount, toAccount, amount);
            ra.addFlashAttribute("message", "Transfer of $" + amount + " to #" + toAccount + " successful!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
