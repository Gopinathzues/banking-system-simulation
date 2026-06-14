package com.bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String accountHolderName;
    protected double balance;
    private List<Transaction> transactions;

    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        logTransaction("INITIAL DEPOSIT", initialBalance);
    }

    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Deposit amount must be positive.");
            return;
        }
        balance += amount;
        logTransaction("DEPOSIT", amount);
        System.out.println("Success: Deposited successfully.");
    }

    public abstract boolean withdraw(double amount);

    public void transfer(BankAccount targetAccount, double amount) {
        if (targetAccount == null) {
            System.out.println("Error: Target account does not exist.");
            return;
        }
        if (amount <= 0) {
            System.out.println("Error: Transfer amount must be positive.");
            return;
        }
        
        // Execute withdrawal from this account first
        if (this.withdraw(amount)) {
            // Modify transaction type label manually for clarity
            int lastIndex = this.transactions.size() - 1;
            this.transactions.remove(lastIndex);
            logTransaction("TRANSFER TO " + targetAccount.getAccountNumber(), amount);
            
            // Deposit to the recipient account
            targetAccount.balance += amount;
            targetAccount.logTransaction("TRANSFER FROM " + this.getAccountNumber(), amount);
            System.out.println("Success: Transfer completed successfully.");
        }
    }

    protected void logTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount));
    }

    public abstract String getAccountType();
}