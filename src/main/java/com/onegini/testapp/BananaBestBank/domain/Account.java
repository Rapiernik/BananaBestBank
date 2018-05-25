package com.onegini.testapp.BananaBestBank.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class Account {

    private long id;
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public Account() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq", sequenceName = "accountSeq", initialValue=100000, allocationSize=100)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account increase(Double amount) {

        return getConvertedAmount(this.balance + amount);
    }

    public Account decrease(Double amount) {

        return getConvertedAmount(this.balance - amount);
    }

    private Account getConvertedAmount(double v) {
        BigDecimal bd = new BigDecimal(Double.toString(v));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        this.balance = bd.doubleValue();
        return this;
    }
}
