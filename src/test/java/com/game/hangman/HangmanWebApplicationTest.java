package com.game.hangman;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.game.hangman.service.HangmanService;

@SpringBootTest
@AutoConfigureMockMvc
public class HangmanWebApplicationTest {
	
	@Autowired
	private HangmanService service;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getHTTPAtStartShouldReturnOKAndDefaultValues() throws Exception {
		this.mockMvc.perform(get("/hangman/start"))
		  .andDo(print())
		  .andExpect(status().isOk())
		  .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  .andExpect(jsonPath("$.maxAttempts", is( this.service.getMaxAttempts())))
		  .andExpect(jsonPath("$.remaingAttempts", is(this.service.getMaxAttempts())))
		  .andExpect(jsonPath("$.gameOver", is(false)));
	}
}
