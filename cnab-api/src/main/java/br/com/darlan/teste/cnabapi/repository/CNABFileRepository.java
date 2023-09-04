package br.com.darlan.teste.cnabapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.darlan.teste.cnabapi.entity.CNABFile;

@Repository
public interface CNABFileRepository extends JpaRepository<CNABFile, Long> {

}
