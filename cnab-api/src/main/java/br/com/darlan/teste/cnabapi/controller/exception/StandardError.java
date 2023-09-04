package br.com.darlan.teste.cnabapi.controller.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class StandardError {
	private String status;
	private String message;
}
