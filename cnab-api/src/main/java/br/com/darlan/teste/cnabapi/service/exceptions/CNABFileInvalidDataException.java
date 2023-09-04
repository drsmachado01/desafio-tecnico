package br.com.darlan.teste.cnabapi.service.exceptions;

import java.util.List;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileValidationErrorDTO;
import lombok.Getter;

@Getter
public class CNABFileInvalidDataException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5171878217981594870L;
	
	private final List<CNABFileValidationErrorDTO> errors;

	public CNABFileInvalidDataException(String message, List<CNABFileValidationErrorDTO> errors) {
		super(message);
		this.errors = errors;
	}
}
