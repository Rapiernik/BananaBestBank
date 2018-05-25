package com.onegini.testapp.BananaBestBank.domain;

public class RequestData {

    private double value;
    private String token;

    public RequestData(double value, String token) {
        this.value = value;
        this.token = token;
    }

    public RequestData() {
    }

    public double getValue() {
        return value;
    }

    public String getToken() {
        return token;
    }

}
