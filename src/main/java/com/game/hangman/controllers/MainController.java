package com.game.hangman.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.hangman.data.GuessResult;
import com.game.hangman.service.HangmanService;

@Controller
@RequestMapping("/hangman")
public class MainController {
	
	private final HangmanService service;
	public MainController(HangmanService service) {
		this.service = service;
	}
	
	@RequestMapping("/guess/{ch}")
	public @ResponseBody ResponseEntity<GuessResult> guess(@PathVariable String ch) {
		return ResponseEntity.status(HttpStatus.OK).body(service.guess(ch));
	}
	
	@RequestMapping("/start")
	public @ResponseBody ResponseEntity<GuessResult> start() {
		return ResponseEntity.status(HttpStatus.OK).body(service.start());
	}
}
