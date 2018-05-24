package com.onegini.testapp.BananaBestBank.service.impl;

import com.onegini.testapp.BananaBestBank.domain.Account;
import com.onegini.testapp.BananaBestBank.domain.User;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;
import com.onegini.testapp.BananaBestBank.repository.AccountRepository;
import com.onegini.testapp.BananaBestBank.repository.UserRepository;
import com.onegini.testapp.BananaBestBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account checkUsersBalance(Long userId) throws BananaBankBusinessException {

        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            return userById.get().getAccount();
        } else {
            throw new BananaBankBusinessException("User with id: " + userId + " does not exist!");
        }
    }
}
