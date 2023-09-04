package br.com.darlan.teste.cnabapi.utils.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import br.com.darlan.teste.cnabapi.service.exceptions.CNABFileInvalidDataException;
import br.com.darlan.teste.cnabapi.utils.constants.CNABFileValidationErrors;
import br.com.darlan.teste.cnabapi.utils.constants.TransactionTypes;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CNABFileDataValidator {	
	private List<String> listOfTransactionTypes = new ArrayList<>();

	public CNABFileDataValidator() {
		listOfTransactionTypes.add("C");
		listOfTransactionTypes.add("D");
		listOfTransactionTypes.add("T");
	}
	
	public boolean validateNotNullField(String field) {
		log.info("Field " + field);
		return (Objects.nonNull(field) && !field.isBlank() && !field.isEmpty() && field.replace("0", "").length() >= 1);
	}
	
	public void validateSize(String line) {
		if(line.startsWith(TransactionTypes.HEADER) && (line.trim().length() < 48)) {
			throw new CNABFileInvalidDataException(CNABFileValidationErrors.MSG_INVALID_FILE_FORMAT, null);
		}
		
		if(line.startsWith(TransactionTypes.TRANSACTION) && (line.trim().length() < 37)) {
			throw new CNABFileInvalidDataException(CNABFileValidationErrors.MSG_INVALID_FILE_FORMAT, null);
		}
		
		if(line.startsWith(TransactionTypes.FOOTER) && (line.trim().length() < 3)) {
			throw new CNABFileInvalidDataException(CNABFileValidationErrors.MSG_INVALID_FILE_FORMAT, null);
		}
	}
	
	public void validateTransactionType(String transactionType) {
		if(listOfTransactionTypes.contains(transactionType)) {
			throw new CNABFileInvalidDataException(CNABFileValidationErrors.MSG_INVALID_TRANSACTION_TYPE, null);
		}
	}
}
