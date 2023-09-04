package br.com.darlan.teste.cnabapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_CNAB_TRANSACTION")
@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CNABFileTransaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4512614596410360649L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CNAB_TRANSACTION")
	private Long idCNABFileTransaction;
	@Column(name = "REGISTRY_TYPE")
	private String registryType;
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	@Column(name = "TRANSACTION_VALUE")
	private BigDecimal transactionValue;
	@Column(name = "ORIGIN_ACCOUNT")
	private String originAccount;
	@Column(name = "DESTINATION_ACCOUNT")
	private String destinationAccount;
}
