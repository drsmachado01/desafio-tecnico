package br.com.darlan.teste.cnabapi.utils;

public class MockFileProvider {
	public static byte[] getFileAsByteArray(boolean valid) {
		return getTheFileContent(valid).getBytes();
	}
	
	public static String getTheFileContent(boolean valid) {
		if(valid) {
			return ("001Empresa A              1900010000010000          Empresa A\r\n"
					+ "002C10000000010000000001234560000012345\r\n"
					+ "002D200000000200000000012345612345600\r\n"
					+ "002T300000000300000000012345623456789\r\n"
					+ "003");
		}
		
		return ("001Empresa B 1900010000020000 Empresa B\r\n"
				+ "002C10000000010000000001234560000012345\r\n"
				+ "002D200000000200000000012345612345600\r\n"
				+ "002T300000000300000000012345623456789\r\n"
				+ "003XPTO");
	}
}
