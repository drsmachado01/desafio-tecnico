package br.com.darlan.teste.cnabapi.entity;

import java.io.Serializable;

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
@Table(name = "TB_CNAB_HEADER")
@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CNABFileHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3336999815819479868L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CNAB_HEADER")
	private Long idCNABHeader;
	@Column(name = "REGISTRY_TYPE")
	private String registryType;
	@Column(name = "BUSINESS_NAME")
	private String businessName;
	@Column(name = "COMPANY_ID")
	private String companyId;
	@Column(name = "FUTURE_USE")
	private String futureUse;
	
	
}
