package br.com.darlan.teste.cnabapi.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.darlan.teste.cnabapi.service.exceptions.CNABFileInvalidDataException;
import br.com.darlan.teste.cnabapi.service.exceptions.CNABFileValidationException;

@ControllerAdvice
public class CNABFileValidationExceptionHandler {

	@ExceptionHandler(CNABFileValidationException.class)
	public ResponseEntity<StandardError> incorrectFileFormat(CNABFileValidationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(StandardError.builder().status("error").message(ex.getMessage()).build());
	}
	
	@ExceptionHandler(CNABFileInvalidDataException.class)
	public ResponseEntity<DetailedError> fileWithIncorrectData(CNABFileInvalidDataException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(DetailedError.builder().status("error").message(ex.getMessage())
						.errors(ex.getErrors()).build());
	}
}
