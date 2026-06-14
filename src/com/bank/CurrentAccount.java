package com.bank;

public class CurrentAccount extends BankAccount {
    private static final long serialVersionUID = 1L;
    private static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(String accountNumber, String accountHolderName, double initialBalance) {
        super(accountNumber, accountHolderName, initialBalance);
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return false;
        }
        if (balance - amount < -OVERDRAFT_LIMIT) {
            System.out.println("Error: Overdraft limit exceeded. Maximum overdraft allowed is " + OVERDRAFT_LIMIT);
            return false;
        }
        balance -= amount;
        logTransaction("WITHDRAWAL", amount);
        System.out.println("Success: Withdrawal completed.");
        return true;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }
}