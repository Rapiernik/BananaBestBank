package com.onegini.testapp.BananaBestBank.service.impl;

import com.onegini.testapp.BananaBestBank.domain.Token;
import com.onegini.testapp.BananaBestBank.domain.User;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;
import com.onegini.testapp.BananaBestBank.repository.TokenRepository;
import com.onegini.testapp.BananaBestBank.repository.UserRepository;
import com.onegini.testapp.BananaBestBank.service.TokenService;
import com.onegini.testapp.BananaBestBank.utils.SecureRandomTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SecureRandomTokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String getGeneratedToken(Long userId) throws BananaBankBusinessException {

        String tokenValue = SecureRandomTokenGenerator.generateNextToken();

        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            Token token = new Token();
            token.setCurrentValue(tokenValue);
            token.setUser(userById.get());
            token.setExpirationDateTime(LocalDateTime.now().plusSeconds(90));
            tokenRepository.deleteAll();
            tokenRepository.save(token);
        } else {
            throw new BananaBankBusinessException("User with id: " + userId + " does not exist!");
        }

        return tokenValue;
    }
}
