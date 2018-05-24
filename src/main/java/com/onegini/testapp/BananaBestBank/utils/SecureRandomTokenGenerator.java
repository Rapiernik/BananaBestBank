package com.onegini.testapp.BananaBestBank.utils;

import java.security.SecureRandom;

public class SecureRandomTokenGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SECURE_TOKEN_LENGTH = 15;

    private static final SecureRandom random = new SecureRandom();

    private static final char[] symbols = CHARACTERS.toCharArray();
    private static final char[] buf = new char[SECURE_TOKEN_LENGTH];

    public static String generateNextToken() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}
