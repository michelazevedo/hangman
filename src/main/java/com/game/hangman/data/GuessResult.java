package com.game.hangman.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class GuessResult {
	private List<String> guessedChars = new ArrayList<>();
	private int remaingAttempts = 0;
	private int maxAttempts = 0;
	private int wordLength = 0;
	private boolean gameOver = false;	
}
