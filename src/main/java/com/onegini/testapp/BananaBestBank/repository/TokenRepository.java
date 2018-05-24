package com.onegini.testapp.BananaBestBank.repository;

import com.onegini.testapp.BananaBestBank.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByUserId(Long userId);
}
