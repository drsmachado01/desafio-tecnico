package br.com.darlan.teste.cnabapi.utils.fileprocessor;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.springframework.stereotype.Component;

@Component
public class ChecksumGenerator {
	public long generateChecksum(String file) {
		return processChecksum(file.getBytes());
	}
	
	public long processChecksum(byte[] bytes) {
	    Checksum crc32 = new CRC32();
	    crc32.update(bytes, 0, bytes.length);
	    return crc32.getValue();
	}
}
