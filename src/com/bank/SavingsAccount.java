package com.bank;

public class SavingsAccount extends BankAccount {
    private static final long serialVersionUID = 1L;
    private static final double MINIMUM_BALANCE = 1000.0;

    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance) {
        super(accountNumber, accountHolderName, initialBalance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return false;
        }
        if (balance - amount < MINIMUM_BALANCE) {
            System.out.println("Error: Insufficient funds. Savings accounts must maintain a minimum balance of " + MINIMUM_BALANCE);
            return false;
        }
        balance -= amount;
        logTransaction("WITHDRAWAL", amount);
        System.out.println("Success: Withdrawal completed.");
        return true;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
}