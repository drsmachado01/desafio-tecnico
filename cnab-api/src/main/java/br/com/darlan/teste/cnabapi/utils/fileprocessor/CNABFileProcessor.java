package br.com.darlan.teste.cnabapi.utils.fileprocessor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.darlan.teste.cnabapi.utils.constants.LineParts;
import br.com.darlan.teste.cnabapi.utils.constants.TransactionTypes;
import br.com.darlan.teste.cnabapi.utils.validator.CNABFileDataValidator;

@Component
public class CNABFileProcessor {

	@Autowired
	private CNABFileDataValidator validator;
	
	public String readCNABFile(MultipartFile file) throws IOException {
		return new String(file.getBytes());
	}
	
	public Map<Integer, String> transformFileToMap(String fileAsString) {
		Map<Integer, String> mapOfLines = new HashMap<>();
       
		List<String> lines = Arrays.asList(fileAsString.split("\n"));
        ListIterator<String> lit = lines.listIterator();
        
        while(lit.hasNext()) {
        	int lineNumber = lit.nextIndex();
        	String line = lit.next();
        	mapOfLines.put(lineNumber, line);
        }
        
        return mapOfLines;
	}
	
	public Map<String, String> readLine(String line) {
		validator.validateSize(line);
		if(line.startsWith(TransactionTypes.HEADER)) {
			return readHeaderLine(line);
		}
		
		if(line.startsWith(TransactionTypes.TRANSACTION)) {
			return readTransactionLine(line);
		}
		
		if(line.startsWith(TransactionTypes.FOOTER)) {
			return readFooterLine(line);
		}
		return new HashMap<>();
	}

	private Map<String, String> readHeaderLine(String line) {
		Map<String, String> header = new HashMap<>();
		header.put(LineParts.REGISTRY_TYPE, line.substring(0, 3));
		header.put(LineParts.BUSINESS_NAME, line.substring(3, 33));
		header.put(LineParts.COMPANY_ID, line.substring(33, 47));
		header.put(LineParts.FUTURE_USE, line.substring(47));
		return header;
	}

	private Map<String, String> readTransactionLine(String line) {
		Map<String, String> transaction = new HashMap<>();

		transaction.put(LineParts.REGISTRY_TYPE, line.substring(0, 3));
		transaction.put(LineParts.TRANSACTION_TYPE, line.substring(3, 4));
		transaction.put(LineParts.TRANSACTION_VALUE, line.substring(4, 19));
		transaction.put(LineParts.ORIGIN_ACCOUNT, line.substring(19, 34));
		transaction.put(LineParts.DESTINATION_ACCOUNT, line.substring(34));
		
		return transaction;
	}

	private Map<String, String> readFooterLine(String line) {
		Map<String, String> footer = new HashMap<>();
		footer.put(LineParts.REGISTRY_TYPE, line.substring(0, 3));
		footer.put(LineParts.TOTAL_CTRL_INFORMATION, line.substring(3));
		return footer;
	}
}
