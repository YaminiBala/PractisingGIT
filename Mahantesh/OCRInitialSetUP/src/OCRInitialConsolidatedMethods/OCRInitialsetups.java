package OCRInitialConsolidatedMethods;
import QAEnvironmentValidate.HomeSecurityConfigValidation;
import QAEnvironmentValidate.SuspendGlobalProcess;
import QAEnvironmentValidate.myTestClass;
import RTPCancellingAccounts.RunningSoapProj;
import DBValidation.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import SIKCancellingAccounts.*;

public class OCRInitialsetups {
	
	static WebDriver driver = new FirefoxDriver();

	public static void main(String[] args) throws Exception
	{ 
		 myTestClass testobj=new myTestClass();
		 RunningSoapProj sop=new  RunningSoapProj();
		 SIKUICancelAccounts sik=new SIKUICancelAccounts();
		 DBValidate db=new DBValidate();
		 SuspendGlobalProcess sus=new SuspendGlobalProcess();
		 HomeSecurityConfigValidation hs=new HomeSecurityConfigValidation();
		 String dirname = "newDir";
		 File dir = new File("C:\\OCRscreenshots");
		 String newdir=dir.toString();
		 if(dir.exists())
		 {
			 deleteDirectories( dir,"CorpValidation");
			 deleteDirectories( dir,"HomeSecurityUIValidation");
			 deleteDirectories( dir,"SIKCancelAccounts");
			 deleteDirectories( dir,"SuspendProcessValidation");
			
			 dir.delete();
			 dir.mkdirs();
			
		 }
		 else
		 {
			 dir.mkdirs();
		 }
		testobj.EnvironmentCorpConfigValidate(driver);	
		hs.RTPValidationsHomesecurity();
		sus.suspendingGlobalProcess();
		sik.sikcancels();
		System.out.println("************Initializing Soap UI***********");
		System.out.println("************All OCRInitialSetUp's Validations are success***********");
		 System.out.println("**See the output in C:\\OCRscreenshots folder**");
		 db.DBValidations();
		sop.RTPCancels();
		//completion();
		
	} 
	//-----------
	public static void completion()
	{
		 System.out.println("************All OCRInitialSetUp's Validations are success***********");
		 System.out.println("**See the output in C:\\OCRscreenshots folder**");
	}
	//----------
	
public static void deleteDirectories( File dir,String path)
{
	String newDir=dir.toString().concat("\\").concat(path);
	System.out.println(newDir);
	File directory = new File(newDir);
	if(directory.exists())
	 {
		 System.out.println("It exists");
		 String[]entries = directory.list();
		 
		 for(String s: entries){
		 File currentFile = new File(directory.getPath(),s);
		 currentFile.delete();
		 }
		 directory.delete();
		// dir.mkdirs();
	 }
	else
	{
		System.out.println("It not exists");
	}
	 
}
}
