package br.com.darlan.teste.cnabapi.dto.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CNABFileValidationTransactionDTO {
	private String type;
	private BigDecimal value;
	private String accountOrigin;
	private String accountDestination;
}
