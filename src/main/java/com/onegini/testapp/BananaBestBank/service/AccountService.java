package com.onegini.testapp.BananaBestBank.service;

import com.onegini.testapp.BananaBestBank.domain.Account;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;

public interface AccountService {

    Account checkUsersBalance(Long userId) throws BananaBankBusinessException;
}
