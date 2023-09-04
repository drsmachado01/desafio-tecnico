package br.com.darlan.teste.cnabapi.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CNABFileValidationErrorDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3232145217694236923L;

	/**
     * Integer - identifies the line where the error occurs
     * */
    private Integer line;

    /**
     * String - indicates the error in a specific line
     * */
    private String error;
}
