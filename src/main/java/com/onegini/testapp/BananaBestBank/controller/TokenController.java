package com.onegini.testapp.BananaBestBank.controller;

import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;
import com.onegini.testapp.BananaBestBank.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/balance/tokens/user/{userId}", method = RequestMethod.POST)
    public String getTokenOperation(@PathVariable("userId") Long id) throws BananaBankBusinessException {

        return tokenService.getGeneratedToken(id);
    }
}
