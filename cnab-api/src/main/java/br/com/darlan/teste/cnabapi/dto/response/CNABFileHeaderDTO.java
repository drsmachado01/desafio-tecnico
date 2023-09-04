package br.com.darlan.teste.cnabapi.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CNABFileHeaderDTO {
	private Long idCNABHeader;
	private String registryType;
	private String businessName;
	private String companyId;
	private String futureUse;
}
