package com.decathlon.ecolededev.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandleException {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity notFound(EntityNotFoundException enfe) {

		return new ResponseEntity<>(enfe.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotCorrectSlotException.class)
	public ResponseEntity incorrect(EntityNotFoundException enfe) {
		return new ResponseEntity<>(enfe.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(NotAvailableSlotException.class)
	public ResponseEntity conflict(NotAvailableSlotException enfe) {
		return new ResponseEntity<>(enfe.getMessage(), HttpStatus.CONFLICT);
	}
}