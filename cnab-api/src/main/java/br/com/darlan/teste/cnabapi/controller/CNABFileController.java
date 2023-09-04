package br.com.darlan.teste.cnabapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.darlan.teste.cnabapi.dto.response.CNABFileDTO;
import br.com.darlan.teste.cnabapi.service.CNABFileProcessorService;

@RestController
@RequestMapping("/cnabFile")
public class CNABFileController {
	@Autowired
	private CNABFileProcessorService service;
	
	@GetMapping("")
	public ResponseEntity<List<CNABFileDTO>> listAllProcessedFiles() {
		return ResponseEntity.ok().body(service.listAllProcessedFiles());
	}
}
