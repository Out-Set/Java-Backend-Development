package com.pk.development.app.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class CustomStringEncryptor implements StringEncryptor {

	private static String password = "Bits@123";
	private static String algorithm = "PBEWITHMD5ANDDES";
    private final StandardPBEStringEncryptor encryptor;
    
    public static void main (String[] args) throws IOException {

    	StringEncryptor encryptor = new CustomStringEncryptor();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    System.out.print("Please enter Option-> 1 for Encrypt & 2 for Decrypt:: ");
	    int choice  = Integer.parseInt(reader.readLine());
	    System.out.println("You entered : " + choice);
	    String text;
	    if(choice==1) {
	    	System.out.print("Pls enter text to Encrypt:: ");
	    	text = reader.readLine();
		    String encryptedText = encryptor.encrypt(text);
		    System.out.println("Encrypted text is: " + encryptedText);
	    }else if(choice==2) {
	    	System.out.print("Pls enter text to Decrypt:: ");
	    	text = reader.readLine();
		    String encryptedText = encryptor.decrypt(text);
		    System.out.println("Decrypted text is: " + encryptedText);
	    }
    }

    public CustomStringEncryptor() {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        // Configure any other encryption settings as needed
    }

    @Override
    public String encrypt(String value) {
        return encryptor.encrypt(value);
    }

    @Override
    public String decrypt(String encryptedValue) {
        return encryptor.decrypt(encryptedValue);
    }
}

