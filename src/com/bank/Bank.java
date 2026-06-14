package com.bank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, BankAccount> accounts;
    private static final String FILE_NAME = "bank_data.dat";
    private int nextAccountNumberSequence = 1001;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public String createAccount(String type, String name, double initialDeposit) {
        String accountNumber = "ACC" + nextAccountNumberSequence;
        BankAccount account;

        if (type.equalsIgnoreCase("savings")) {
            if (initialDeposit < 1000.0) {
                System.out.println("Error: Initial deposit of at least 1000 required for Savings.");
                return null;
            }
            account = new SavingsAccount(accountNumber, name, initialDeposit);
        } else {
            if (initialDeposit < 0) {
                System.out.println("Error: Initial deposit cannot be negative.");
                return null;
            }
            account = new CurrentAccount(accountNumber, name, initialDeposit);
        }

        accounts.put(accountNumber, account);
        nextAccountNumberSequence++;
        return accountNumber;
    }

    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber.toUpperCase().trim());
    }

    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts registered in the system.");
            return;
        }
        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-12s | %-20s | %-10s | %-10s%n", "Acc Number", "Holder Name", "Type", "Balance");
        System.out.println("-------------------------------------------------------------");
        for (BankAccount acc : accounts.values()) {
            System.out.printf("%-12s | %-20s | %-10s | %-10.2f%n", 
                    acc.getAccountNumber(), acc.getAccountHolderName(), acc.getAccountType(), acc.getBalance());
        }
        System.out.println("-------------------------------------------------------------");
    }

    public void printAccountStatement(String accountNumber) {
        BankAccount acc = findAccount(accountNumber);
        if (acc == null) {
            System.out.println("Error: Account not found.");
            return;
        }
        System.out.println("\n==================================================");
        System.out.println("                ACCOUNT STATEMENT                 ");
        System.out.println("==================================================");
        System.out.println("Account Number: " + acc.getAccountNumber());
        System.out.println("Account Holder: " + acc.getAccountHolderName());
        System.out.println("Account Type  : " + acc.getAccountType());
        System.out.printf("Current Balance: %.2f%n", acc.getBalance());
        System.out.println("--------------------------------------------------");
        System.out.println("Transaction History:");
        for (Transaction t : acc.getTransactions()) {
            System.out.println(t);
        }
        System.out.println("==================================================");
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
            oos.writeInt(nextAccountNumberSequence);
            System.out.println("Data successfully saved to disk.");
        } catch (IOException e) {
            System.out.println("Error: Could not save banking database: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.accounts = (Map<String, BankAccount>) ois.readObject();
            this.nextAccountNumberSequence = ois.readInt();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Warning: Failed to parse save state. Starting with empty dataset.");
        }
    }
}