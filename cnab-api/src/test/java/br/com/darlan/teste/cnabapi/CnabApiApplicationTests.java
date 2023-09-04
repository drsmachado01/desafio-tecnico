package br.com.darlan.teste.cnabapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.darlan.teste.cnabapi.controller.CNABFileProcessorController;

@SpringBootTest
class CnabApiApplicationTests {
	@Autowired
	private CNABFileProcessorController validationController;
	
	@Test
	void contextLoads() {
		assertThat(validationController).isNotNull();
	}

}
