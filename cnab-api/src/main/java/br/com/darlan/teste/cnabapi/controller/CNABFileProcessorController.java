package br.com.darlan.teste.cnabapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileValidationDTO;
import br.com.darlan.teste.cnabapi.service.CNABFileProcessorService;

@RestController
@RequestMapping("/CNABFileUpload")
public class CNABFileProcessorController {
	@Autowired
	private CNABFileProcessorService service;
	
	@PostMapping("")
	public ResponseEntity<CNABFileValidationDTO> process(@RequestParam MultipartFile cnabFile) throws IOException {
		return ResponseEntity.ok().body(service.process(cnabFile));
	}
}
