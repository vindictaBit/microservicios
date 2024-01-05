package com.bancoa.bancoaservice.bancoA;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // Data access layer
@Table // Data access layer
public class Account {

    @Id // Data access layer
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Client client;

    @Lob
    private String contract; //podría ser byte[]

    private double balance;

    public Account(String accountNumber, String contract, double balance) {
        this.accountNumber = accountNumber;
        this.contract = contract;
        this.balance = balance;
    }

    /* public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void addTransaction(String transaction) {
        this.transactions.add(transaction);
    } */
}