package com.onegini.testapp.BananaBestBank.controller;

import com.onegini.testapp.BananaBestBank.domain.Account;
import com.onegini.testapp.BananaBestBank.domain.RequestData;
import com.onegini.testapp.BananaBestBank.dto.TransactionDto;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;
import com.onegini.testapp.BananaBestBank.exception.InvalidOrExpiredOrNoTokenException;
import com.onegini.testapp.BananaBestBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/balance/user/{userId}", method = RequestMethod.GET)
    public Account checkBalanceByUserId(@PathVariable("userId") Long id) throws BananaBankBusinessException {

        return accountService.checkUsersBalance(id);
    }

    @RequestMapping(value = "/balance/increase/user/{userId}", method = RequestMethod.POST)
    public void increaseUsersBalance(@RequestBody RequestData requestData, @PathVariable("userId") Long id)
            throws BananaBankBusinessException {

        accountService.increaseUsersBalance(requestData, id);
    }

    @RequestMapping(value = "/balance/decrease/user/{userId}", method = RequestMethod.POST)
    public void decreaseUsersBalance(@RequestBody RequestData requestData, @PathVariable("userId") Long id)
            throws BananaBankBusinessException, InvalidOrExpiredOrNoTokenException {

        accountService.decreaseUsersBalance(requestData, id);
    }

    @RequestMapping(value = "/balance/history/user/{userId}", method = RequestMethod.GET)
    public List<TransactionDto> getAllUsersTransaction(@PathVariable("userId") Long id) throws BananaBankBusinessException {

        return accountService.getAllUsersTransaction(id);
    }
}
