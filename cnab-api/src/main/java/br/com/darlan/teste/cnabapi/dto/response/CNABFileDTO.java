package br.com.darlan.teste.cnabapi.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class CNABFileDTO {
	private Long id;
	private Long checksum;
	private CNABFileHeaderDTO header;
	private List<CNABFileTransactionDTO> transactions;
	private CNABFileFooterDTO footer;
	private LocalDateTime registrationDate;
}
