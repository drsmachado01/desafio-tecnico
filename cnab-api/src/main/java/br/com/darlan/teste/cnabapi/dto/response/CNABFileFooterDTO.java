package br.com.darlan.teste.cnabapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CNABFileFooterDTO {
	private Long idCNABFileFooter;
	private String registryType;
	private String totalControlInformation;
}
