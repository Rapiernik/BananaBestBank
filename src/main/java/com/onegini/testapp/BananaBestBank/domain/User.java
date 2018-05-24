package com.onegini.testapp.BananaBestBank.domain;

import javax.persistence.*;

@Entity
public class User {

    private long id;
    private Account account;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq", sequenceName = "userSeq", initialValue=1111, allocationSize=100)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account accounts) {
        this.account = accounts;
    }
}
