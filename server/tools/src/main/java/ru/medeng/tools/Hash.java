package ru.medeng.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	private static MessageDigest alg;
	private static String salt;
	private static int saltIndex;
	
	public static void init(String salt, int index) {
		try {
			alg = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		Hash.salt = salt;
		Hash.saltIndex = index;
	}
	
	private static byte[] hash(byte[] password) {
		return alg.digest(password);
	}
	
	public static String hexHash(String password) {
		var saltBytes = salt.getBytes();
		var simpleHash = hash(password.getBytes());
		var saltHash = new byte[simpleHash.length + saltBytes.length];
		
		System.arraycopy(simpleHash, 0, saltHash, 0, saltIndex);
		System.arraycopy(saltBytes, 0, saltHash, 0, saltBytes.length);
		System.arraycopy(simpleHash, saltIndex, saltHash, saltIndex + saltBytes.length, simpleHash.length - saltIndex);
		
		var builder = new StringBuilder();
		var hash = hash(saltHash);
		
		for (var b : hash) {
			builder.append("%02x".formatted(b));
		}
		
		return builder.toString();
	}
}
