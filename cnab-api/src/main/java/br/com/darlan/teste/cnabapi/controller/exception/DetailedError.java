package br.com.darlan.teste.cnabapi.controller.exception;

import java.util.List;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileValidationErrorDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DetailedError {
	private String status;
	private String message;
	private List<CNABFileValidationErrorDTO> errors;

}
