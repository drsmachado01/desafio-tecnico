package br.com.darlan.teste.cnabapi.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CNABFileTransactionDTO {
	private Long idCNABFileTransaction;
	private String registryType;
	private String transactionType;
	private BigDecimal transactionValue;
	private String originAccount;
	private String destinationAccount;
}
