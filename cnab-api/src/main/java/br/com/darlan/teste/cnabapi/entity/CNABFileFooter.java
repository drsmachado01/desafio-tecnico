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
@Table(name = "TB_CNAB_FOOTER")
@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CNABFileFooter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7379080247751593002L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CNAB_FOOTER")
	private Long idCNABFileFooter;
	@Column(name = "REGISTRY_TYPE")
	private String registryType;
	@Column(name = "TTL_CTRL_INFO")
	private String totalControlInformation;
}
