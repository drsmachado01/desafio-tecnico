package br.com.darlan.teste.cnabapi.utils.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CNABFileDataValidatorTest {

	@InjectMocks
	private CNABFileDataValidator validator;
	
	@Test
	void testValidateNotNullFieldWithNullField() {
		boolean returned = validator.validateNotNullField(null);
		assertEquals(false, returned);
	}
	
	@Test
	void testValidateNotNullFieldWithBlankField() {
		boolean returned = validator.validateNotNullField("");
		assertEquals(false, returned);
	}
	
	@Test
	void testValidateNotNullFieldWithZerosInField() {
		boolean returned = validator.validateNotNullField("0000000000000000000000");
		assertEquals(false, returned);
	}
	
	@Test
	void testValidateNotNullFieldWithValidInField() {
		boolean returned = validator.validateNotNullField("100000000100000");
		assertEquals(true, returned);
	}
}
