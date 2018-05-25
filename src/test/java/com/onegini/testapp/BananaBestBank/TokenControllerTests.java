package com.onegini.testapp.BananaBestBank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.onegini.testapp.BananaBestBank.exception.BananaBankBusinessException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BananaBestBankApplication.class)
@WebAppConfiguration
public class TokenControllerTests {

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws BananaBankBusinessException {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void getTokenOperationTest() throws Exception {

		mockMvc.perform(post("/balance/tokens/user/1111"))
				.andExpect(status().isCreated());
	}

	@Test
	public void getTokenOperationNonExistingUserTest() throws Exception {

		mockMvc.perform(post("/balance/tokens/user/1112"))
				.andExpect(status().isNotFound());
	}
}
