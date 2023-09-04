package br.com.darlan.teste.cnabapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.darlan.teste.cnabapi.entity.CNABFileHeader;

public interface CNABFileHeaderRepository extends JpaRepository<CNABFileHeader, Long> {

}
