package com.onegini.testapp.BananaBestBank.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onegini.testapp.BananaBestBank.domain.Account;
import com.onegini.testapp.BananaBestBank.domain.RequestData;
import com.onegini.testapp.BananaBestBank.domain.Token;
import com.onegini.testapp.BananaBestBank.domain.Transaction;
import com.onegini.testapp.BananaBestBank.domain.TransactionType;
import com.onegini.testapp.BananaBestBank.domain.User;
import com.onegini.testapp.BananaBestBank.dto.TransactionDto;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;
import com.onegini.testapp.BananaBestBank.exception.InvalidOrExpiredOrNoTokenException;
import com.onegini.testapp.BananaBestBank.repository.AccountRepository;
import com.onegini.testapp.BananaBestBank.repository.TokenRepository;
import com.onegini.testapp.BananaBestBank.repository.TransactionRepository;
import com.onegini.testapp.BananaBestBank.repository.UserRepository;
import com.onegini.testapp.BananaBestBank.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TransactionRepository transactionRepository;

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

            saveCurrentTransaction(updatedAccount, TransactionType.INCREASE, requestData.getValue());
        } else {
            throw new BananaBankBusinessException("User with id: " + userId + " does not exist!");
        }
    }

    @Override
    @Transactional
    public void decreaseUsersBalance(RequestData requestData, Long userId)
            throws BananaBankBusinessException, InvalidOrExpiredOrNoTokenException {

        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            Token tokenByUser = tokenRepository.findByUserId(userById.get().getId());
            if (tokenByUser != null) {
                if (tokenByUser.getCurrentValue().equals(requestData.getToken())
                        && tokenByUser.getExpirationDateTime().isAfter(LocalDateTime.now())) {
                    Account accountById = accountRepository.getOne(userById.get().getAccount().getId());
                    if (accountById.getBalance() < requestData.getValue()) {
                        throw new BananaBankBusinessException("You do not have enough funds in your account!");
                    }
                    Account updatedAccount = accountById.decrease(requestData.getValue());
                    accountRepository.save(updatedAccount);
                    tokenRepository.deleteAll();

                    saveCurrentTransaction(updatedAccount, TransactionType.DECREASE, requestData.getValue());
                } else {
                    throw new InvalidOrExpiredOrNoTokenException("Invalid token or expiration time has passed!");
                }
            } else {
                throw new InvalidOrExpiredOrNoTokenException("You must generate token first!");
            }
        } else {
            throw new BananaBankBusinessException("User with id: " + userId + " does not exist!");
        }
    }

    @Override
    public List<TransactionDto> getAllUsersTransaction(Long userId) throws BananaBankBusinessException {

        List<TransactionDto> transactionDtos = new ArrayList<>();

        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            List<Transaction> transactions = transactionRepository.findByAccountId(userById.get().getAccount().getId());

            for (Transaction transaction : transactions) {
                TransactionDto dto = new TransactionDto(transaction.getTransactionType().name(),
                        String.valueOf(transaction.getValue()));
                transactionDtos.add(dto);
            }
        } else {
            throw new BananaBankBusinessException("User with id: " + userId + " does not exist!");
        }
        return transactionDtos;
    }

    private void saveCurrentTransaction(Account updatedAccount, TransactionType transactionType, double value) {
        Transaction transaction = new Transaction(updatedAccount, transactionType, value, LocalDate.now());
        transactionRepository.save(transaction);
    }
}
