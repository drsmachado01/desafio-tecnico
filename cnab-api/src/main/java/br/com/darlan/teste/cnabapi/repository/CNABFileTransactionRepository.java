package br.com.darlan.teste.cnabapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.darlan.teste.cnabapi.entity.CNABFileTransaction;

public interface CNABFileTransactionRepository extends JpaRepository<CNABFileTransaction, Long> {

}
