package br.com.darlan.teste.cnabapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_CNAB_FILE")
@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CNABFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7990738493600187860L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CNAB_FILE")
	private Long idCNABFile;
	@Column(name = "REGISTRATION_DATE")
	private LocalDateTime registrationDate;
	@Column(name = "CHECKSUM")
	private Long checksum;
	@OneToOne
	private CNABFileHeader header;
	@OneToMany
	private List<CNABFileTransaction> transactions;
	@OneToOne
	private CNABFileFooter footer;
}
