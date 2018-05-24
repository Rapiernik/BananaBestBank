package com.onegini.testapp.BananaBestBank.controller;

import com.onegini.testapp.BananaBestBank.domain.Account;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;
import com.onegini.testapp.BananaBestBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/balance/user/{userId}", method = RequestMethod.GET)
    public Account checkBalanceByUserId(@PathVariable("userId") Long id) throws BananaBankBusinessException {

        return accountService.checkUsersBalance(id);
    }
}
