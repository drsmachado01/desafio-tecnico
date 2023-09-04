package br.com.darlan.teste.cnabapi.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CNABFileValidationDataDTO {
	private List<CNABFileValidationTransactionDTO> transactions;
}
