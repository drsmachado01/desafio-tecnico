package br.com.darlan.teste.cnabapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileDTO;
import br.com.darlan.teste.cnabapi.dto.response.CNABFileValidationDTO;
import br.com.darlan.teste.cnabapi.dto.response.CNABFileValidationDataDTO;
import br.com.darlan.teste.cnabapi.dto.response.CNABFileValidationErrorDTO;
import br.com.darlan.teste.cnabapi.dto.response.CNABFileValidationTransactionDTO;
import br.com.darlan.teste.cnabapi.entity.CNABFile;
import br.com.darlan.teste.cnabapi.entity.CNABFileFooter;
import br.com.darlan.teste.cnabapi.entity.CNABFileHeader;
import br.com.darlan.teste.cnabapi.entity.CNABFileTransaction;
import br.com.darlan.teste.cnabapi.repository.CNABFileFooterRepository;
import br.com.darlan.teste.cnabapi.repository.CNABFileHeaderRepository;
import br.com.darlan.teste.cnabapi.repository.CNABFileRepository;
import br.com.darlan.teste.cnabapi.repository.CNABFileTransactionRepository;
import br.com.darlan.teste.cnabapi.service.exceptions.CNABFileInvalidDataException;
import br.com.darlan.teste.cnabapi.utils.constants.CNABFileValidationErrors;
import br.com.darlan.teste.cnabapi.utils.constants.LineParts;
import br.com.darlan.teste.cnabapi.utils.fileprocessor.CNABFileProcessor;
import br.com.darlan.teste.cnabapi.utils.fileprocessor.ChecksumGenerator;
import br.com.darlan.teste.cnabapi.utils.mapper.ObjectMapper;
import br.com.darlan.teste.cnabapi.utils.validator.CNABFileDataValidator;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CNABFileProcessorService {
	@Autowired
	private CNABFileProcessor processor;

	@Autowired
	private CNABFileDataValidator validator;
	
	@Autowired
	private CNABFileRepository fileRepository;
	
	@Autowired
	private CNABFileHeaderRepository fileHeaderRepository;
	
	@Autowired
	private CNABFileTransactionRepository fileTransactionRepository;
	
	@Autowired
	private CNABFileFooterRepository fileFooterRepository;
	
	@Autowired
	private ChecksumGenerator checksumGenerator;
	
	@Autowired
	private ObjectMapper<CNABFileDTO, CNABFile> mapper;
	
	private String theCNABFile;

	private CNABFileValidationDataDTO dataDTO;
	
	private CNABFileHeader header;
	private List<CNABFileTransaction> transactions;
	private CNABFileFooter footer;

	public CNABFileValidationDTO process(MultipartFile file) throws IOException {
		this.transactions = new ArrayList<>();
		this.theCNABFile = processor.readCNABFile(file);
		Map<Integer, String> fileLines = processor.transformFileToMap(this.theCNABFile);
		dataDTO = new CNABFileValidationDataDTO();
		dataDTO.setTransactions(new ArrayList<>());
		processTransactions(fileLines);
		
		transformAndPersistCNABFile();

		return CNABFileValidationDTO.builder().status("success")
				.message("Arquivo CNAB enviado e processado com sucesso.").data(this.dataDTO).build();
	}

	private void transformAndPersistCNABFile() {
		fileRepository.save(CNABFile.builder()
				.registrationDate(LocalDateTime.now())
				.checksum(checksumGenerator.generateChecksum(theCNABFile))
				.header(this.header)
				.transactions(this.transactions)
				.footer(this.footer)
				.build());
	}

	private void persistHeader(Map<String, String> headerMap) {
		this.header = fileHeaderRepository.save(CNABFileHeader.builder()
				.registryType(headerMap.get(LineParts.REGISTRY_TYPE))
				.businessName(headerMap.get(LineParts.BUSINESS_NAME))
				.companyId(headerMap.get(LineParts.COMPANY_ID))
				.futureUse(headerMap.get(LineParts.FUTURE_USE))
				.build());
	}

	private void persistTransaction(Map<String, String> transactionMap) {
		this.transactions.add(fileTransactionRepository.save(CNABFileTransaction.builder()
			.registryType(transactionMap.get(LineParts.REGISTRY_TYPE))
			.transactionType(transactionMap.get(LineParts.TRANSACTION_TYPE))
			.transactionValue(convertToBigDecimal(transactionMap.get(LineParts.TRANSACTION_VALUE)))
			.originAccount(transactionMap.get(LineParts.ORIGIN_ACCOUNT))
			.destinationAccount(transactionMap.get(LineParts.DESTINATION_ACCOUNT))
			.build()));
	}

	private void processTransactions(Map<Integer, String> fileLines) {
		Iterator<Integer> it = fileLines.keySet().iterator();
		while (it.hasNext()) {
			int key = it.next();
			String line = fileLines.get(key);
			processFields(key, processor.readLine(line));
		}
	}

	private void persistFooter(Map<String, String> footerMap) {
		this.footer = fileFooterRepository.save(CNABFileFooter.builder()
				.registryType(footerMap.get(LineParts.REGISTRY_TYPE))
				.totalControlInformation(footerMap.get(LineParts.TOTAL_CTRL_INFORMATION))
				.build());
	}

	private void processFields(int lineNumber, Map<String, String> fields) {
		Iterator<String> it = fields.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String field = fields.get(key);
			if (!validator.validateNotNullField(field) && !(LineParts.TOTAL_CTRL_INFORMATION.equals(key))) {
				List<CNABFileValidationErrorDTO> errors = additionalErrorIsNeeded(key, lineNumber);
				errors.add(CNABFileValidationErrorDTO.builder().line(lineNumber).error(getErrorByField(key)).build());
				
				throw new CNABFileInvalidDataException(
						CNABFileValidationErrors.MSG_FILE_INVALID_DATA, errors);
			}
			
			validateAndPersistField(field, fields);
		}

		if ("002".equals(fields.get(LineParts.REGISTRY_TYPE))) {
			processTransaction(fields);
		}
	}

	private void validateAndPersistField(String field, Map<String, String> fields) {
		if(field.startsWith("001")) {
			persistHeader(fields);
		}
		
		if(field.startsWith("002")) {
			persistTransaction(fields);
		}
		
		if(field.startsWith("003")) {
			persistFooter(fields);
		}
	}

	private void processTransaction(Map<String, String> fields) {
		this.dataDTO.getTransactions()
				.add(CNABFileValidationTransactionDTO.builder().type(fields.get(LineParts.TRANSACTION_TYPE))
						.value(convertToBigDecimal(fields.get(LineParts.TRANSACTION_VALUE)))
						.accountOrigin(fields.get(LineParts.ORIGIN_ACCOUNT))
						.accountDestination(fields.get(LineParts.DESTINATION_ACCOUNT)).build());
	}

	private BigDecimal convertToBigDecimal(String part) {
		StringBuilder sb = new StringBuilder(part);
		sb.insert(part.length() - 2, ".");
		return new BigDecimal(sb.toString());
	}

	private List<CNABFileValidationErrorDTO> additionalErrorIsNeeded(String key, int lineNumber) {
		List<CNABFileValidationErrorDTO> errors = new ArrayList<>();
		if (Objects.equals(key, LineParts.ORIGIN_ACCOUNT)) {
			errors.add(CNABFileValidationErrorDTO.builder().line(lineNumber)
					.error(getErrorByField(LineParts.ORIGIN_ACCOUNT_MANDATORY)).build());
		}
		if (Objects.equals(key, LineParts.DESTINATION_ACCOUNT)) {
			errors.add(CNABFileValidationErrorDTO.builder().line(lineNumber)
					.error(getErrorByField(LineParts.DESTINATION_ACCOUNT_MANDATORY)).build());
		}
		if (Objects.equals(key, LineParts.TRANSACTION_VALUE)) {
			errors.add(CNABFileValidationErrorDTO.builder().line(lineNumber)
					.error(getErrorByField(LineParts.TRANSACTION_VALUE_NULL)).build());
		}
		return errors;
	}

	private String getErrorByField(String field) {
		return LineParts.getErrorFromField(field);
	}
	
	public List<CNABFileDTO> listAllProcessedFiles() {
		return mapper.fromEntityList(fileRepository.findAll());
	}
}
