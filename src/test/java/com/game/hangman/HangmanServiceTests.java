package com.game.hangman;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.game.hangman.exception.GameNotStardedEsception;
import com.game.hangman.service.HangmanService;

@SpringBootTest
class HangmanServiceTests {
	
	@Autowired
	private HangmanService service;
	
	@Test
	void startGameMustLoadAndPickAWordFromWordsList() {
		this.service.start();
		assertTrue(this.service.getWords().size() > 0);
		assertNotNull(this.service.getSelectedWord());
		assertTrue(this.service.getWords().contains(this.service.getSelectedWord()));
	}
	
	@Test
	void guessBeforeGameStartMustThrowException() {
		assertThrows(GameNotStardedEsception.class, () -> {
	        this.service.guess("X");
	    });
	}
	
	@Test
	void wrongGuessAfterMaxAttemptsMustGameOver() {
		this.service.start();
		for (int i = 0; i < this.service.getMaxAttempts(); i++) {
			this.service.guess("@");			
		}
		
		assertEquals(this.service.getRemainingAttempts(), 0);
		assertTrue(this.service.getGameOver());
	}
}
