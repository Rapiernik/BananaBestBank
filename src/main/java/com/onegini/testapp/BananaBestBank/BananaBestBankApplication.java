package com.onegini.testapp.BananaBestBank;

import com.onegini.testapp.BananaBestBank.domain.Account;
import com.onegini.testapp.BananaBestBank.domain.User;
import com.onegini.testapp.BananaBestBank.repository.AccountRepository;
import com.onegini.testapp.BananaBestBank.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BananaBestBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BananaBestBankApplication.class, args);
	}

	@Bean
	CommandLineRunner init(final UserRepository userRepository, final AccountRepository accountRepository) {

		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {

				//Entry data
				Account account = new Account();
				account.setBalance(500l);
				accountRepository.save(account);

				User user = new User();
				user.setAccount(account);

				userRepository.save(user);
			}
		};
	}
}
