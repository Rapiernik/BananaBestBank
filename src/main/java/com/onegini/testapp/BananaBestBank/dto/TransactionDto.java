package com.onegini.testapp.BananaBestBank.dto;

import java.io.Serializable;

public class TransactionDto implements Serializable{

    private String type;
    private String value;

    public TransactionDto(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public TransactionDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
