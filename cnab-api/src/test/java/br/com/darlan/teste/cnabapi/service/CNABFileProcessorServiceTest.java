package br.com.darlan.teste.cnabapi.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileValidationDTO;
import br.com.darlan.teste.cnabapi.repository.CNABFileFooterRepository;
import br.com.darlan.teste.cnabapi.repository.CNABFileHeaderRepository;
import br.com.darlan.teste.cnabapi.repository.CNABFileRepository;
import br.com.darlan.teste.cnabapi.repository.CNABFileTransactionRepository;
import br.com.darlan.teste.cnabapi.utils.MockFileProvider;
import br.com.darlan.teste.cnabapi.utils.constants.LineParts;
import br.com.darlan.teste.cnabapi.utils.fileprocessor.CNABFileProcessor;
import br.com.darlan.teste.cnabapi.utils.fileprocessor.ChecksumGenerator;
import br.com.darlan.teste.cnabapi.utils.validator.CNABFileDataValidator;

@ExtendWith(MockitoExtension.class)
class CNABFileProcessorServiceTest {
	private static final String LINHA_1_OK = "001Empresa A              1900010000010000          Empresa A";
	private static final String LINHA_2_OK = "002C10000000010000000001234560000012345";
	private static final String LINHA_3_OK = "002D200000000200000000012345612345600";
	private static final String LINHA_4_OK = "002T300000000300000000012345623456789";
	private static final String LINHA_5_OK = "003";

	@InjectMocks
	private CNABFileProcessorService service;
	
	@Mock
	private CNABFileProcessor processor;
	
	@Mock
	private CNABFileDataValidator validator;
	
	@Mock
	private ChecksumGenerator checksumGenerator;
	
	@Mock
	private CNABFileRepository fileRepository;
	
	@Mock
	private CNABFileHeaderRepository fileHeaderRepository;
	
	@Mock
	private CNABFileTransactionRepository fileTransactionRepository;
	
	@Mock
	private CNABFileFooterRepository fileFooterRepository;
	
	@Test
	void testProcessSuccess() throws IOException {
		MockMultipartFile file = new MockMultipartFile("cnabFile", MockFileProvider.getFileAsByteArray(true));
		
		when(processor.readCNABFile(any())).thenReturn(theFileContent());
		when(processor.transformFileToMap(anyString())).thenReturn(generateFileMap());
		when(validator.validateNotNullField(anyString())).thenReturn(true);
		addWhenToReadLines();
		
		try {
			CNABFileValidationDTO dto = service.process(file);
			assertNotNull(dto);
			
			assertEquals("success", dto.getStatus());
			assertEquals("Arquivo CNAB enviado e processado com sucesso.", dto.getMessage());
			assertNotNull(dto.getData());
			assertNotNull(dto.getData().getTransactions());
			assertEquals(3, dto.getData().getTransactions().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addWhenToReadLines() {
		when(processor.readLine(LINHA_1_OK)).thenReturn(generateMapLinha1());
		when(processor.readLine(LINHA_2_OK)).thenReturn(generateMapLinha2());
		when(processor.readLine(LINHA_3_OK)).thenReturn(generateMapLinha3());
		when(processor.readLine(LINHA_4_OK)).thenReturn(generateMapLinha4());
		when(processor.readLine(LINHA_5_OK)).thenReturn(generateMapLinha5());
	}

	@Test
	void testProcessError() {
		MockMultipartFile file = new MockMultipartFile("cnabFile", MockFileProvider.getFileAsByteArray(false));
		
		try {
			CNABFileValidationDTO dto = service.process(file);
			assertNotNull(dto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Map<Integer, String> generateFileMap() {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, LINHA_1_OK);
		map.put(2, LINHA_2_OK);
		map.put(3, LINHA_3_OK);
		map.put(4, LINHA_4_OK);
		map.put(5, LINHA_5_OK);
		return map;
	}

	private Map<String, String> generateMapLinha1() {
		Map<String, String> fields = new HashMap<>();
		fields.put(LineParts.REGISTRY_TYPE, "001");
		fields.put(LineParts.BUSINESS_NAME, "Empresa A              1900010");
		fields.put(LineParts.COMPANY_ID, "000010000     ");
		fields.put(LineParts.FUTURE_USE, "     Empresa A");
		return fields;
	}

	private Map<String, String> generateMapLinha2() {
		Map<String, String> fields = new HashMap<>();
		fields.put(LineParts.REGISTRY_TYPE, "002");
		fields.put(LineParts.TRANSACTION_TYPE, "C");
		fields.put(LineParts.TRANSACTION_VALUE, "100000000100000");
		fields.put(LineParts.ORIGIN_ACCOUNT, "000012345600000");
		fields.put(LineParts.DESTINATION_ACCOUNT, "12345");
		return fields;
	}

	private Map<String, String> generateMapLinha3() {
		Map<String, String> fields = new HashMap<>();
		fields.put(LineParts.REGISTRY_TYPE, "002");
		fields.put(LineParts.TRANSACTION_TYPE, "D");
		fields.put(LineParts.TRANSACTION_VALUE, "200000000200000");
		fields.put(LineParts.ORIGIN_ACCOUNT, "000012345612345");
		fields.put(LineParts.DESTINATION_ACCOUNT, "600");
		return fields;
	}

	private Map<String, String> generateMapLinha4() {
		Map<String, String> fields = new HashMap<>();
		fields.put(LineParts.REGISTRY_TYPE, "002");
		fields.put(LineParts.TRANSACTION_TYPE, "T");
		fields.put(LineParts.TRANSACTION_VALUE, "300000000300000");
		fields.put(LineParts.ORIGIN_ACCOUNT, "000012345623456");
		fields.put(LineParts.DESTINATION_ACCOUNT, "789");
		return fields;
	}

	private Map<String, String> generateMapLinha5() {
		Map<String, String> fields = new HashMap<>();
		fields.put(LineParts.REGISTRY_TYPE, "003");
		fields.put(LineParts.TOTAL_CTRL_INFORMATION, "");
		return fields;
	}

	private String theFileContent() {
		return MockFileProvider.getTheFileContent(true);
	}
}
