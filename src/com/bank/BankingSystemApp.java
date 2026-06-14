package com.bank;

import java.util.Scanner;

public class BankingSystemApp {
    private static Bank bank = new Bank();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        bank.loadFromFile();
        int choice = -1;

        while (choice != 7) {
            System.out.println("\n=================================");
            System.out.println("       CORE BANKING SYSTEM       ");
            System.out.println("=================================");
            System.out.println("1. Create New Bank Account");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Registered Accounts");
            System.out.println("6. Print Account Statement");
            System.out.println("7. Exit Application");
            System.out.print("Select operational choice (1-7): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        executeAccountCreation();
                        break;
                    case 2:
                        executeDeposit();
                        break;
                    case 3:
                        executeWithdrawal();
                        break;
                    case 4:
                        executeTransfer();
                        break;
                    case 5:
                        bank.displayAllAccounts();
                        break;
                    case 6:
                        executeStatementPrint();
                        break;
                    case 7:
                        bank.saveToFile();
                        System.out.println("\nApplication terminated. Goodbye.");
                        break;
                    default:
                        System.out.println("Invalid option. Please input a value between 1 and 7.");
                }
            } else {
                System.out.println("Input Error: Please enter a numeric option.");
                scanner.nextLine();
            }
        }
    }

    private static void executeAccountCreation() {
        System.out.println("\n--- Create Bank Account ---");
        System.out.print("Select Account Type (1 for Savings, 2 for Current): ");
        String selection = scanner.nextLine().trim();
        String type = selection.equals("1") ? "savings" : "current";

        String name = "";
        while (true) {
            System.out.print("Enter Account Holder Full Name: ");
            name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("Error: Holder name cannot be left blank.");
            } else if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Error: Name must contain alphabetic letters and spaces only.");
            } else {
                break; // Input is valid, exit the loop
            }
        }

        System.out.print("Enter Initial Deposit Amount: ");
        if (!scanner.hasNextDouble()) {
            System.out.println("Error: Invalid numerical format.");
            scanner.nextLine();
            return;
        }
        double deposit = scanner.nextDouble();
        scanner.nextLine();

        String generatedId = bank.createAccount(type, name, deposit);
        if (generatedId != null) {
            System.out.println("Success: Account created. Assigned Identifier: " + generatedId);
        }
    }
    private static void executeDeposit() {
        System.out.print("\nEnter Target Account Number: ");
        String accNum = scanner.nextLine();
        BankAccount acc = bank.findAccount(accNum);

        if (acc == null) {
            System.out.println("Error: Destination account record not found.");
            return;
        }

        System.out.print("Enter Deposit Amount: ");
        if (scanner.hasNextDouble()) {
            double amt = scanner.nextDouble();
            scanner.nextLine();
            acc.deposit(amt);
        } else {
            System.out.println("Error: Decimal point entry format required.");
            scanner.nextLine();
        }
    }

    private static void executeWithdrawal() {
        System.out.print("\nEnter Source Account Number: ");
        String accNum = scanner.nextLine();
        BankAccount acc = bank.findAccount(accNum);

        if (acc == null) {
            System.out.println("Error: Source account record not found.");
            return;
        }

        System.out.print("Enter Withdrawal Amount: ");
        if (scanner.hasNextDouble()) {
            double amt = scanner.nextDouble();
            scanner.nextLine();
            acc.withdraw(amt);
        } else {
            System.out.println("Error: Decimal point entry format required.");
            scanner.nextLine();
        }
    }

    private static void executeTransfer() {
        System.out.print("\nEnter Originating Account Number: ");
        String sourceId = scanner.nextLine();
        BankAccount sourceAcc = bank.findAccount(sourceId);

        if (sourceAcc == null) {
            System.out.println("Error: Source account record not found.");
            return;
        }

        System.out.print("Enter Recipient Account Number: ");
        String targetId = scanner.nextLine();
        BankAccount targetAcc = bank.findAccount(targetId);

        if (targetAcc == null) {
            System.out.println("Error: Recipient account record not found.");
            return;
        }

        if (sourceAcc.getAccountNumber().equals(targetAcc.getAccountNumber())) {
            System.out.println("Error: Intrabank transfers require two distinct routing paths.");
            return;
        }

        System.out.print("Enter Financial Remittance Value: ");
        if (scanner.hasNextDouble()) {
            double amt = scanner.nextDouble();
            scanner.nextLine();
            sourceAcc.transfer(targetAcc, amt);
        } else {
            System.out.println("Error: Value parsing format error.");
            scanner.nextLine();
        }
    }

    private static void executeStatementPrint() {
        System.out.print("\nEnter Requested Account Number: ");
        String accNum = scanner.nextLine();
        bank.printAccountStatement(accNum);
    }
}