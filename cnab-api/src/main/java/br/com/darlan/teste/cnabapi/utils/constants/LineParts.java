package br.com.darlan.teste.cnabapi.utils.constants;

import java.util.HashMap;
import java.util.Map;

public class LineParts {

	public static final String REGISTRY_TYPE = "REGISTRY_TYPE";
	public static final String BUSINESS_NAME = "BUSINESS_NAME";
	public static final String COMPANY_ID = "COMPANY_ID";
	public static final String FUTURE_USE = "FUTURE_USE";
	
	public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public static final String TRANSACTION_VALUE = "TRANSACTION_VALUE";
	public static final String ORIGIN_ACCOUNT = "ORIGIN_ACCOUNT";
	public static final String DESTINATION_ACCOUNT = "DESTINATION_ACCOUNT";
	public static final String TRANSACTION_VALUE_NULL = "TRANSACTION_VALUE_NULL";
	public static final String ORIGIN_ACCOUNT_MANDATORY = "ORIGIN_ACCOUNT_MANDATORY";
	public static final String DESTINATION_ACCOUNT_MANDATORY = "DESTINATION_ACCOUNT_MANDATORY";
	
	public static final String TOTAL_CTRL_INFORMATION = "TOTAL_CTRL_INFORMATION";
	
	private static final Map<String, String> mapOfErrors = new HashMap<>();
	
	static {
		mapOfErrors.put(TRANSACTION_TYPE, CNABFileValidationErrors.MSG_INVALID_TRANSACTION_TYPE);
		mapOfErrors.put(TRANSACTION_VALUE, CNABFileValidationErrors.MSG_TRANSACTION_VALUE_FORMAT_INVALID);
		mapOfErrors.put(ORIGIN_ACCOUNT, CNABFileValidationErrors.MSG_ORIGIN_ACCOUNT_INVALID_FORMAT);
		mapOfErrors.put(DESTINATION_ACCOUNT, CNABFileValidationErrors.MSG_DESTINATION_ACCOUNT_INVALID_FORMAT);
		mapOfErrors.put(TRANSACTION_VALUE_NULL, CNABFileValidationErrors.MSG_TRANSACTION_VALUE_CANNOT_BE_NULL);
		mapOfErrors.put(ORIGIN_ACCOUNT_MANDATORY, CNABFileValidationErrors.MSG_ORIGIN_ACCOUNT_MANDATORY);
		mapOfErrors.put(DESTINATION_ACCOUNT_MANDATORY, CNABFileValidationErrors.MSG_DESTINATION_ACCOUNT_MANDATORY);
	}

	private LineParts() {}
	
	public static String getErrorFromField(String field) {
		return mapOfErrors.get(field);
	}
}
