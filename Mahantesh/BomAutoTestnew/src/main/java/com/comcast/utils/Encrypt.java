package com.comcast.utils;


import javax.swing.*;

//import org.apache.commons.net.util.Base64;

import org.openqa.selenium.internal.Base64Encoder;

public class Encrypt {

	/* The method EncodePassword() will take the password as input in Message Dialog box and 
	 * encode the String using Base64 and output the encoded string.
	 * Copy the Encoded String and paste it in the password fields in .properties and .xls files.
	 */
/*
	@Test
	public void EncodePassword() {
		String pwd = JOptionPane.showInputDialog("Enter Your Password");
		if (pwd != null && !pwd.equals("")) {
			byte[]  bytesEncoded=Base64.encodeBase64(pwd.getBytes());
			JTextArea ta = new JTextArea(10, 10);
			String encpwd = new String(bytesEncoded);
			ta.setText(encpwd);
			ta.setOpaque(false);
			ta.setEditable(false);
			JOptionPane.showMessageDialog(null,ta);
		} else {
			JOptionPane.showMessageDialog(null,"Password cannot be NULL. Enter a Passord");
		}
	}

	public static void main (String args[]) {
		Encrypt e= new Encrypt();
		e.EncodeString();
	}

	@Test
	public void EncodeString() {
		String input = "Monday@321";
		if (input != null && !input.equals("")) {
			byte[] bytesEncoded=Base64.encodeBase64(input.getBytes());
			System.out.println("encoded value is "+new String(bytesEncoded));
		}
	}

	@Test
	public void DecodeString(String input) {
		if (input != null && !input.equals(""))
			System.out.println(new String(new Base64Encoder().decode(input)));
	}

	public static String decodeString(String input) throws NullPointerException {
		try {
			if (input != null && !input.equals(""))
				return new String(new Base64Encoder().decode(input));
			else
				throw new NullPointerException();
		} catch(Exception e) {
			throw e;
		}
	}*/
}