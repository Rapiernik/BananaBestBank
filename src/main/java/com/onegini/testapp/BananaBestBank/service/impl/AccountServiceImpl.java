package com.onegini.testapp.BananaBestBank.service.impl;

import com.onegini.testapp.BananaBestBank.domain.Account;
import com.onegini.testapp.BananaBestBank.domain.RequestData;
import com.onegini.testapp.BananaBestBank.domain.Token;
import com.onegini.testapp.BananaBestBank.domain.User;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;
import com.onegini.testapp.BananaBestBank.repository.AccountRepository;
import com.onegini.testapp.BananaBestBank.repository.TokenRepository;
import com.onegini.testapp.BananaBestBank.repository.UserRepository;
import com.onegini.testapp.BananaBestBank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Account checkUsersBalance(Long userId) throws BananaBankBusinessException {

        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            return userById.get().getAccount();
        } else {
            throw new BananaBankBusinessException("User with id: " + userId + " does not exist!");
        }
    }

    @Override
    @Transactional
    public void increaseUsersBalance(RequestData requestData, Long userId) throws BananaBankBusinessException {

        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            Account accountById = accountRepository.getOne(userById.get().getAccount().getId());
            Account updatedAccount = accountById.increase(requestData.getValue());
            accountRepository.save(updatedAccount);
        } else {
            throw new BananaBankBusinessException("User with id: " + userId + " does not exist!");
        }
    }

    @Override
    @Transactional
    public void decreaseUsersBalance(RequestData requestData, Long userId) throws BananaBankBusinessException {

        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            Account accountById = accountRepository.getOne(userById.get().getAccount().getId());
            if (accountById.getBalance() < requestData.getValue()) {
                throw new BananaBankBusinessException("You do not have enough funds in your account!");
            }

            Token tokenByUser = tokenRepository.findByUserId(userById.get().getId());
            if (tokenByUser != null) {
                if (tokenByUser.getCurrentValue().equals(requestData.getToken())
                        && tokenByUser.getExpirationDateTime().isAfter(LocalDateTime.now())) {
                    Account updatedAccount = accountById.decrease(requestData.getValue());
                    accountRepository.save(updatedAccount);
                    tokenRepository.deleteAll();
                } else {
                    throw new BananaBankBusinessException("Invalid token or expiration time has passed!");
                }
            } else {
                throw new BananaBankBusinessException("You must generate token first!");
            }
        } else {
            throw new BananaBankBusinessException("User with id: " + userId + " does not exist!");
        }
    }
}
