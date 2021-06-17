package com.crewmeister.cmcodingchallenge.currency;

import com.crewmeister.cmcodingchallenge.currency.CurrencyController;
import com.crewmeister.cmcodingchallenge.service.CurrencyInformationService;


import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
@SpringBootTest
@AutoConfigureMockMvc
class CmCodingChallengeApplicationTests {

	@MockBean
	private CurrencyInformationService currencyInformationService;
	@Autowired
	private CurrencyController currencyController;
	@Autowired
	private MockMvc mockMvc;
	@Test
	void contextLoads() {
		assertThat(currencyController).isNotNull();
	}
	@Test
	public  void CheckFor404InCaseOfNoCurrencyConversionValue() throws Exception {
		when(currencyInformationService.getCurrencyConversionValueOnADay(Mockito.anyString(),Mockito.anyString())).thenReturn("");
		mockMvc.perform( get("/api/currencies/AUD/2021-05-21")).andDo(print()).andExpect(status().isNotFound()).andExpect(content().string(containsString("")));
	}

}
