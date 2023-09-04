package br.com.darlan.teste.cnabapi.utils.processor;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import br.com.darlan.teste.cnabapi.utils.MockFileProvider;
import br.com.darlan.teste.cnabapi.utils.constants.LineParts;
import br.com.darlan.teste.cnabapi.utils.fileprocessor.CNABFileProcessor;
import br.com.darlan.teste.cnabapi.utils.validator.CNABFileDataValidator;

@ExtendWith(MockitoExtension.class)
class CNABFileProcessorTest {

	@InjectMocks
	private CNABFileProcessor processor;

	@Mock
	private CNABFileDataValidator validator;

	@Test
	void testGetFileContentValidCNABFile() {
		String theFile = MockFileProvider.getTheFileContent(true);
		Map<Integer, String> lines = processor.transformFileToMap(theFile);
		assertNotNull(lines);
	}

	@Test
	void testGetFileContentInvalidCNABFile() {
		String theFile = MockFileProvider.getTheFileContent(false);
		Map<Integer, String> lines = processor.transformFileToMap(theFile);
		assertNotNull(lines);
	}

	@Test
	void testReadCHABFileValidCNABFile() {
		try {
			MultipartFile theFile = new MockMultipartFile("cnabFile", MockFileProvider.getFileAsByteArray(true));
			String file = processor.readCNABFile(theFile);
			assertNotNull(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testReadCNABFileInvalidCNABFile() {
		try {
			MultipartFile theFile = new MockMultipartFile("cnabFile", MockFileProvider.getFileAsByteArray(false));
			String file = processor.readCNABFile(theFile);
			assertNotNull(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testReadLineHeaderError() {
		Map<String, String> parts = processor
				.readLine("001Empresa B 1900010000020000 Empresa B                                          ");
		assertNotNull(parts);
		assertEquals("001", parts.get(LineParts.REGISTRY_TYPE));
		assertNotEquals("Empresa B              1900010", parts.get(LineParts.BUSINESS_NAME));
		assertNotEquals("000010000     ", parts.get(LineParts.COMPANY_ID));
		assertNotEquals("     Empresa B", parts.get(LineParts.FUTURE_USE));
	}

	@Test
	void testReadLineHeaderSuccess() {
		Map<String, String> parts = processor.readLine("001Empresa A              1900010000010000          Empresa A");
		assertNotNull(parts);
		assertEquals("001", parts.get(LineParts.REGISTRY_TYPE));
		assertEquals("Empresa A              1900010", parts.get(LineParts.BUSINESS_NAME));
		assertEquals("000010000     ", parts.get(LineParts.COMPANY_ID));
		assertEquals("     Empresa A", parts.get(LineParts.FUTURE_USE));
	}

	@Test
	void testReadLineTransactionSuccess() {
		Map<String, String> parts = processor.readLine("002C10000000010000000001234560000012345");
		assertNotNull(parts);
		assertEquals("002", parts.get(LineParts.REGISTRY_TYPE));
		assertEquals("C", parts.get(LineParts.TRANSACTION_TYPE));
		assertEquals("100000000100000", parts.get(LineParts.TRANSACTION_VALUE));
		assertEquals("000012345600000", parts.get(LineParts.ORIGIN_ACCOUNT));
		assertEquals("12345", parts.get(LineParts.DESTINATION_ACCOUNT));
	}

	@Test
	void testReadLineFooterSuccess() {
		Map<String, String> parts = processor.readLine("003");
		assertNotNull(parts);
		assertEquals("003", parts.get(LineParts.REGISTRY_TYPE));
		assertEquals("", parts.get(LineParts.TOTAL_CTRL_INFORMATION));
	}
}
