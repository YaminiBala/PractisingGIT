/**
 * 
 */
package com.comcast.bom.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author 494656
 *
 */
public class VerifyFile {

	/**
	 * @param args
	 */
	public boolean  Verify(String[] args) {
		// TODO Auto-generated method stub
		
		//here we are validating the content of the file which we will upload
		
		 //FilePath
		  String sFilePath = "D:\\software\\t\\EligibleAccounts_201605020715.txt"; 
		  boolean isMatch = false ;
		  //Creating FileReader object
		  FileReader fr = null;
		  //Creating BufferedReader object
		  BufferedReader txtReader  = null;
		  String str = null;
		  String pattern=".*MPEG4.*";
		  //Handling Exception using Try-Catch
		    try {
		     String sCurrentLine;
		     fr =  new FileReader(sFilePath);
		     txtReader = new BufferedReader(fr);
		     //Reading file until file is null
		     while ((sCurrentLine = txtReader.readLine()) != null) 
		     {
		    	  str=sCurrentLine;
		    	 
		       System.out.println(str);
		    	 
		     }
		      isMatch = Pattern.matches(pattern, str);
		     System.out.println("The text contains 'MPEG4'? " + isMatch);

		    } catch (IOException e) {
		         e.printStackTrace();
		    } finally {
		     try {
		      if (txtReader != null)txtReader.close();
		     } catch (IOException ex) {
		      ex.printStackTrace();
		     }
		    }
			return isMatch;

	}

}
