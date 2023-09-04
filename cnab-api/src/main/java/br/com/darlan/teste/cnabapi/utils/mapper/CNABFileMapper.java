package br.com.darlan.teste.cnabapi.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileDTO;
import br.com.darlan.teste.cnabapi.dto.response.CNABFileFooterDTO;
import br.com.darlan.teste.cnabapi.dto.response.CNABFileHeaderDTO;
import br.com.darlan.teste.cnabapi.dto.response.CNABFileTransactionDTO;
import br.com.darlan.teste.cnabapi.entity.CNABFile;
import br.com.darlan.teste.cnabapi.entity.CNABFileFooter;
import br.com.darlan.teste.cnabapi.entity.CNABFileHeader;
import br.com.darlan.teste.cnabapi.entity.CNABFileTransaction;

@Component
public class CNABFileMapper implements ObjectMapper<CNABFileDTO, CNABFile> {

	@Autowired
	private ObjectMapper<CNABFileHeaderDTO, CNABFileHeader> headerMapper;

	@Autowired
	private ObjectMapper<CNABFileTransactionDTO, CNABFileTransaction> transactionMapper;

	@Autowired
	private ObjectMapper<CNABFileFooterDTO, CNABFileFooter> footerMapper;
	
	@Override
	public CNABFileDTO fromEntity(CNABFile entity) {
		return CNABFileDTO.builder()
				.id(entity.getIdCNABFile())
				.checksum(entity.getChecksum())
				.registrationDate(entity.getRegistrationDate())
				.header(headerMapper.fromEntity(entity.getHeader()))
				.transactions(transactionMapper.fromEntityList(entity.getTransactions()))
				.footer(footerMapper.fromEntity(entity.getFooter()))
				.build();
	}

	@Override
	public List<CNABFileDTO> fromEntityList(List<CNABFile> entities) {
		return entities.stream().map(this::fromEntity).collect(Collectors.toList());
	}
}
