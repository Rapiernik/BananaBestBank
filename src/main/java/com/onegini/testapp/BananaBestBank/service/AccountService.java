package com.onegini.testapp.BananaBestBank.service;

import com.onegini.testapp.BananaBestBank.domain.Account;
import com.onegini.testapp.BananaBestBank.domain.RequestData;
import com.onegini.testapp.BananaBestBank.dto.TransactionDto;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;

import java.util.List;

public interface AccountService {

    Account checkUsersBalance(Long userId) throws BananaBankBusinessException;

    void increaseUsersBalance(RequestData requestData, Long userId) throws BananaBankBusinessException;

    void decreaseUsersBalance(RequestData requestData, Long userId) throws BananaBankBusinessException;

    List<TransactionDto> getAllUsersTransaction(Long userId) throws BananaBankBusinessException;
}
