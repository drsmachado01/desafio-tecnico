package br.com.darlan.teste.cnabapi.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CNABFileValidationDTO {
	private String status;
	private String message;
	private CNABFileValidationDataDTO data;
	private List<CNABFileValidationErrorDTO> errors;
}
