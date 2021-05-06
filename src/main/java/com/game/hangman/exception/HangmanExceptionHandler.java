package com.game.hangman.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.Data;

@ControllerAdvice
public class HangmanExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	
	@ExceptionHandler({GameNotStardedEsception.class})
	public ResponseEntity<Object> handleGameNotStarted(GameNotStardedEsception ex){
		String userMessage = messageSource.getMessage("game.not.started", null, LocaleContextHolder.getLocale());
		String causeMessage = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
		List<CustomError> erros = Arrays.asList(new CustomError(userMessage, causeMessage));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({GameLoadException.class})
	public ResponseEntity<Object> handleGameLoadFail(GameLoadException ex){
		String userMessage = messageSource.getMessage("game.load.fail", null, LocaleContextHolder.getLocale());
		String causeMessage = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
		List<CustomError> erros = Arrays.asList(new CustomError(userMessage, causeMessage));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<CustomError> errors = getErrors(ex.getBindingResult());
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}
		
	private List<CustomError> getErrors(BindingResult bindingResult){
		
		List<CustomError> erros = new ArrayList<CustomError>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String msgCause = fieldError.toString();
			String msgUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			erros.add(new CustomError(msgUser,msgCause));
		}
		return erros;
	}
	
	@Data
	public static class CustomError {
		private String userMessage;
		private String causeDetails;
		
		public CustomError(String userMessage, String causeDetails) {
			this.causeDetails = causeDetails;
			this.userMessage = userMessage;
		}
	}
}
