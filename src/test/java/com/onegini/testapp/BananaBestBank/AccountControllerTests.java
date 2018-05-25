package com.onegini.testapp.BananaBestBank;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onegini.testapp.BananaBestBank.domain.RequestData;
import com.onegini.testapp.BananaBestBank.domain.TransactionType;
import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;
import com.onegini.testapp.BananaBestBank.service.AccountService;
import com.onegini.testapp.BananaBestBank.service.TokenService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BananaBestBankApplication.class)
@WebAppConfiguration
public class AccountControllerTests {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	AccountService accountService;

	@Autowired
	TokenService tokenService;

	@Before
	public void setup() throws BananaBankBusinessException {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getCurrentBalanceExistingUserTest() throws Exception {

		accountService.increaseUsersBalance(new RequestData(100d, null), 1111l);
		accountService.increaseUsersBalance(new RequestData(200d, null), 1111l);

		mockMvc.perform(get("/balance/user/1111"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(100000)))
				.andExpect(jsonPath("$.balance", is(800d)));
	}

	@Test
	public void increaseUserBalanceTest() throws Exception {

		String json = convertToJSON(new RequestData(300d, null));

		mockMvc.perform(post("/balance/increase/user/1111")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void decreaseUserBalanceTest() throws Exception {

		String generatedToken = tokenService.getGeneratedToken(1111l);
		String json = convertToJSON(new RequestData(400d, generatedToken));

		mockMvc.perform(post("/balance/decrease/user/1111")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	public void getTransactionHistoryTest() throws Exception {

		accountService.increaseUsersBalance(new RequestData(500d, null), 1111l);
		accountService.increaseUsersBalance(new RequestData(600d, null), 1111l);
		String generatedToken = tokenService.getGeneratedToken(1111l);
		accountService.decreaseUsersBalance(new RequestData(700d, generatedToken), 1111l);

		mockMvc.perform(get("/balance/history/user/1111"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]value", is(String.valueOf(500d))))
				.andExpect(jsonPath("$[0]type", is(TransactionType.INCREASE.toString())))
				.andExpect(jsonPath("$[1]value", is(String.valueOf(600d))))
				.andExpect(jsonPath("$[1]type", is(TransactionType.INCREASE.toString())))
				.andExpect(jsonPath("$[2]value", is(String.valueOf(700d))))
				.andExpect(jsonPath("$[2]type", is(TransactionType.DECREASE.toString())));
	}













//	@Test
//	public void getBalance() throws Exception {
//
//		Account account = new Account();
//		account.setBalance(300);
//		String json = convertToJSON(account);
//
//		mockMvc.perform(post("/balance/increase/user/1111").content(json).contentType(MediaType.APPLICATION_JSON_UTF8));
//
//		mockMvc.perform(get("/balance/user/1111"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.balance", is(800d)));
//	}
//
//	@Test
//	public void getBalanceNonExistingUser() throws Exception {
//
//		mockMvc.perform(get("/balance/user/1112"));
//	}
//
//	@Test
//	public void getToken() throws Exception {
//
//		mockMvc.perform(post("/balance/tokens/user/1111"))
//				.andExpect(status().isOk());
//	}
//
	private String convertToJSON(Object o) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(o);
	}

}
