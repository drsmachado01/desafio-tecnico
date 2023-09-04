package br.com.darlan.teste.cnabapi.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileFooterDTO;
import br.com.darlan.teste.cnabapi.entity.CNABFileFooter;

@Component
public class CNABFileFooterMapper implements ObjectMapper<CNABFileFooterDTO, CNABFileFooter> {
	
	@Override
	public CNABFileFooterDTO fromEntity(CNABFileFooter entity) {
		return CNABFileFooterDTO.builder()
				.idCNABFileFooter(entity.getIdCNABFileFooter())
				.registryType(entity.getRegistryType())
				.totalControlInformation(entity.getTotalControlInformation())
				.build();
	}

	@Override
	public List<CNABFileFooterDTO> fromEntityList(List<CNABFileFooter> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}

}
