package com.onegini.testapp.BananaBestBank.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {

    private long id;
    private Account account;
    private TransactionType transactionType;
    private double value;
    private LocalDate transactionDate;

    public Transaction(Account account, TransactionType transactionType, double value, LocalDate transactionDate) {
        this.account = account;
        this.transactionType = transactionType;
        this.value = value;
        this.transactionDate = transactionDate;
    }

    public Transaction() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Enumerated(EnumType.STRING)
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
