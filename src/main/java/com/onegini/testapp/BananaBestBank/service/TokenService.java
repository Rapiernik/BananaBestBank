package com.onegini.testapp.BananaBestBank.service;

import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;

public interface TokenService {

    String getGeneratedToken(Long userId) throws BananaBankBusinessException;
}
