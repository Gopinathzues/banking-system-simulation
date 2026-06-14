package com.bank;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date date;
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.date = new Date();
        this.type = type;
        this.amount = amount;
    }

    public Date getDate() { return date; }
    public String getType() { return type; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("%s | %-10s | Amt: %.2f", date.toString(), type, amount);
    }
}