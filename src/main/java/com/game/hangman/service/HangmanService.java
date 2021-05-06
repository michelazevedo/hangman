package com.game.hangman.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.game.hangman.data.GuessResult;
import com.game.hangman.exception.GameLoadException;
import com.game.hangman.exception.GameNotStardedEsception;

import lombok.Data;

@Service
@SessionScope
@Data
public class HangmanService {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private Environment env;
	
	private List<String> words = new ArrayList<>();
	private List<String> guessedChars = new ArrayList<>();
	
	private int attempts = 0;
	private int maxAttempts = 6;
	private String selectedWord = "";
	
	private void loadWordsFromXMLResource() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(
				HangmanService.class.getClassLoader().getResourceAsStream( env.getProperty("words.file.name")));
			
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("word");
			for (int i = 0; i < nodeList.getLength(); i++) {
				words.add(nodeList.item(i).getTextContent());				
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			throw new GameLoadException();
		}
	}
	
	public GuessResult start() {
		loadWordsFromXMLResource();

		Random random = new Random();
		int idx = random.nextInt(words.size());
		selectedWord = words.get(idx).toUpperCase();
		attempts = 0;

		GuessResult result = new GuessResult();
		result.setMaxAttempts(maxAttempts);
		result.setRemaingAttempts(maxAttempts);
		result.setWordLength(selectedWord.length());
		result.setGameOver(false);
		
		guessedChars.removeIf((a) -> true);
		for (int i = 0; i < selectedWord.length(); i++) {
			guessedChars.add("");
		}
		return result;
	}

	public GuessResult guess(String ch) {
		String chToLookup = ch.toUpperCase();
		if (selectedWord == "") {
			throw new GameNotStardedEsception();
		} else {
			GuessResult result = new GuessResult();
			result.setMaxAttempts(maxAttempts);

			boolean wrongGuess = true;
			for (int i = 0; i < selectedWord.length(); i++) {
				if (selectedWord.charAt(i) == chToLookup.charAt(0)) {
					guessedChars.set(i, chToLookup);
					wrongGuess = false;
				}
			}
			result.setGuessedChars(guessedChars);

			if (wrongGuess) {
				attempts++;
			} 
			
			result.setRemaingAttempts(getRemainingAttempts());			
			result.setGameOver(getGameOver());
			return result;
		}
	}

	public int getRemainingAttempts() {
		return attempts < maxAttempts ? maxAttempts - attempts: 0;
	}
	
	public boolean getGameOver() {
		
		StringBuilder guessedCharsSoFar = new StringBuilder();
		guessedChars.forEach(guessedCharsSoFar::append);
		
		return getRemainingAttempts() == 0 || guessedCharsSoFar.toString().equalsIgnoreCase(selectedWord);	
	}
}
