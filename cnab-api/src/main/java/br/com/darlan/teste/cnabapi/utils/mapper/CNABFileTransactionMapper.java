package br.com.darlan.teste.cnabapi.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileTransactionDTO;
import br.com.darlan.teste.cnabapi.entity.CNABFileTransaction;

@Component
public class CNABFileTransactionMapper implements ObjectMapper<CNABFileTransactionDTO, CNABFileTransaction> {
	
	@Override
	public CNABFileTransactionDTO fromEntity(CNABFileTransaction entity) {
		return CNABFileTransactionDTO.builder()
				.idCNABFileTransaction(entity.getIdCNABFileTransaction())
				.registryType(entity.getRegistryType())
				.transactionType(entity.getTransactionType())
				.transactionValue(entity.getTransactionValue())
				.originAccount(entity.getOriginAccount())
				.destinationAccount(entity.getDestinationAccount())
				.build();
	}

	@Override
	public List<CNABFileTransactionDTO> fromEntityList(List<CNABFileTransaction> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}
}
