package br.com.darlan.teste.cnabapi.utils.constants;

public class CNABFileValidationErrors {
	private CNABFileValidationErrors() {}
	
	public static final String MSG_INVALID_TRANSACTION_TYPE = "Tipo de transação inválido.";
	public static final String MSG_TRANSACTION_VALUE_FORMAT_INVALID = "Valor da transação está fora do formato válido.";
	public static final String MSG_ORIGIN_ACCOUNT_INVALID_FORMAT = "Conta origem não está no formato correto.";
	public static final String MSG_DESTINATION_ACCOUNT_INVALID_FORMAT = "Conta destino não está no formato correto.";
	public static final String MSG_TRANSACTION_VALUE_CANNOT_BE_NULL = "Valor da transação não pode ser nulo.";
	public static final String MSG_ORIGIN_ACCOUNT_MANDATORY = "Conta origem é obrigatória.";
	public static final String MSG_DESTINATION_ACCOUNT_MANDATORY = "Conta destino é obrigatória.";

	public static final String MSG_INVALID_FILE_FORMAT = "Erro ao processar o arquivo CNAB. "
			+ "Certifique-se de que o arquivo esteja no formato posicional correto.";
	public static final String MSG_FILE_INVALID_DATA = "O arquivo CNAB possui formato inválido.";
}
