package br.com.darlan.teste.cnabapi.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileHeaderDTO;
import br.com.darlan.teste.cnabapi.entity.CNABFileHeader;

@Component
public class CNABFileHeaderMapper implements ObjectMapper<CNABFileHeaderDTO, CNABFileHeader> {
	
	@Override
	public CNABFileHeaderDTO fromEntity(CNABFileHeader entity) {
		return CNABFileHeaderDTO.builder()
				.idCNABHeader(entity.getIdCNABHeader())
				.registryType(entity.getRegistryType())
				.businessName(entity.getBusinessName())
				.companyId(entity.getCompanyId())
				.futureUse(entity.getFutureUse())
				.build();
	}

	@Override
	public List<CNABFileHeaderDTO> fromEntityList(List<CNABFileHeader> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}

}
