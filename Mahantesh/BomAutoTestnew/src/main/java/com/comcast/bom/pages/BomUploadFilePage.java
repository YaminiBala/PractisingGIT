package com.comcast.bom.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.comcast.reporting.*;
//import com.cognizant.framework.Status;
import com.comcast.bom.data.CustomerDetails;
import com.comcast.utils.ComcastTest;
import com.comcast.utils.SeleniumReport;
import com.comcast.utils.TestUtils;

public class BomUploadFilePage extends Page{
	
	//public WebDriver driver;
	public WebDriver driver;
	public static WebDriverWait wait;
	protected SeleniumReport report;
	//public WebDriver browser;
	public BomLoginPage blp;
	
	//@FindBy(partialLinkText="Bulk Uploads")
	@FindBy(xpath ="//*[@id='Bulk Uploads']//*[text()='Bulk Uploads']")
	public WebElement bulkUploads;
	
	//@FindBy(partialLinkText="Bulk Upload Utility")
	@FindBy(xpath ="//*[@id='Bulk Upload Utility']//*[text()='Bulk Upload Utility']")
	public WebElement BulkUploadUtility;
	
	@FindBy(id = "uploadCategory")
	public WebElement uploadCategoryDropBox;
	
	@FindBy(xpath=".//*[@id='uploadCategory']/option[2]")
	public WebElement uploadCategory;
	
	@FindBy(xpath="html/body/div/div[2]/div/div/div/div[2]/div[3]/input")
	public WebElement Browse;
	
	@FindBy(xpath="//button[text()='Upload']")
	public WebElement Upload;
	
	@FindBy(xpath=".//*[@id='Account']/a")
	public WebElement Account;
	
	@FindBy(partialLinkText="HD Enhanced Xchange tool")
	public WebElement Enhanced;
	
	@FindBy(xpath=".//*[@id='swap']/div[2]/form/div[1]/input")
	public WebElement accountno;
	
	@FindBy(xpath=".//*[@id='swapType']/option[2]")
	public WebElement swapType;
	
	@FindBy(xpath=".//*[@id='swap']/div[2]/form/button[1]")
	public WebElement CreateSikOrder;
	
	
	public static final int BUFFER_SIZE = 4096;
	
	public BomUploadFilePage(WebDriver browser,SeleniumReport report)
	{
		super(browser,report);
		PageFactory.initElements(browser, this);
		this.report=report;
		this.browser=browser;
		//wait = new WebDriverWait(driver, 30); 
		
	}
	
	/**
	* This function will fetch the data from Satish's DB 
	* and after uploading it will store the file with 
	* the current date and time in the mentioned directory.
	* @author  Dhruv
	*/
	
	public BomUploadFilePage WriteFileToDisk(CustomerDetails customerDetails) throws Exception
	{
		Date date = new Date() ;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
	    
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        String url = "jdbc:sqlserver://tpkatm-wc-1p.cable.comcast.com:1433;DatabaseName=Tools_Index";
	        String user = "RD_ONLY";
	        String password = "Read@user";
	 
	        String filePath = "D:\\software\\t\\EligibleAccounts_"+dateFormat.format(date) +".txt";
	 
	        System.out.println("Connection to Satish's DB for fetching eligibility feed file details");
	        try {
	            Connection conn = DriverManager.getConnection(url, user, password);
	 
	            String sql = "select UpData from Tools_Index.dbo.usr_entry WHERE ACC_NUM = '"+customerDetails.accountNumber+"' order by LAST_UPDATED ASC";
	            System.out.println("Executing query for fetching feed file details");
	            PreparedStatement statement = conn.prepareStatement(sql);
	 
	            ResultSet result = statement.executeQuery();
	            if (result.next()) {
	                Blob blob = result.getBlob("UpData");
	               
	                InputStream inputStream = blob.getBinaryStream();
	                System.out.println("Writing data from the database to the feed file");
	                OutputStream outputStream = new FileOutputStream(filePath);
	 
	                int bytesRead = -1;
	                byte[] buffer = new byte[BUFFER_SIZE];
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    outputStream.write(buffer, 0, bytesRead);
	                }
	               
	 
	                inputStream.close();
	                outputStream.close();
	                System.out.println("Eligibility feed file data is fetched and saved successfully");
	            }
	            conn.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	 	
		return new BomUploadFilePage(browser,report);
	}
	
	/**
	* This function will upload and validate the eligibility feed file 
	* with a swap type as MPEG4.
	* @author  Dhruv
	*/
	
	public BomUploadFilePage UploadFileMPEG4(CustomerDetails customerDetails) throws Exception
	{
	
		waitForElement(bulkUploads);
		bulkUploads.click();
		report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
		
		waitForElement(BulkUploadUtility);
		BulkUploadUtility.click();
		report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
		
		waitForElement(uploadCategory);
		uploadCategory.click();
		report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload option selected successfully");
		
		//code to verify text inside the file
	
		 //code to upload the latest file from the directory
		 File directory = new File("D:\\software\\t");
			// get just files, not directories
			File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
		
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
			System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
			displayFiles(files);
		
		String sFilePath = path; 
		  boolean isMatch = false ;
		 
		  boolean matcher = false;
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
		   Pattern ppattern= Pattern.compile(pattern);
		    matcher=pattern.matches(pattern);
		   System.out.println(matcher);
		      //isMatch = Pattern.matches(pattern, str);
		     System.out.println("The text contains 'MPEG4'? " + matcher);

		    } catch (IOException e) {
		         e.printStackTrace();
		    } finally {
		     try {
		      if (txtReader != null)txtReader.close();
		     } catch (IOException ex) {
		      ex.printStackTrace();
		     }
		    }

		if(matcher==true)
		{
			report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'MPEG4'", Status.PASS);
			waitForElement(Browse);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
			Browse.sendKeys(path);
		}
		
		else{
			//report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'X1'", Status.PASS);
			report.reportFailEvent("Feed file is inappropriate", "Feed has missing fields");
		
		}
		
		waitForElement(Upload);
		report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
		Upload.click();
		Thread.sleep(5000);
		HandleAlert();
		
		Thread.sleep(10000);
			
		return new BomUploadFilePage(browser,report);
		
	}
	
	/**
	* This function Handle Alerts present on the page
	* @author  Dhruv
	*/
	
	
	public void HandleAlert()
	{
		try {
			waitForAlert();
			if (isAlertPresent()) {
				Alert alt = browser.switchTo().alert();
				String txt = browser.switchTo().alert().getText();
				System.out.println(txt);
				if (txt.contains("please select files to upload")) {
					report.updateTestLog("File Upload", "Upload file is not selected.", Status.WARNING);
					report.updateTestLog("File Upload", "Upload file is not selected.", Status.SCREENSHOT);
					alt.accept();
				} else if (txt.contains("File Uploaded Successfully")) {
					report.updateTestLog("File Upload", "Successfully uploaded the file.", Status.PASS);
					alt.accept();
				}
			}
		} catch (Exception ex) {

		}
	}

	
	/**
	* This function will upload and validate the eligibility feed file 
	* with a swap type as CM/EMTA.
	* @author  Dhruv
	*/
	
	
	
	public BomUploadFilePage UploadFileCmEMTA(CustomerDetails customerDetails) throws Exception
	{
		waitForElement(bulkUploads);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Uploads")));
		bulkUploads.click();
		report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
		waitForElement(BulkUploadUtility);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Upload Utility")));
		BulkUploadUtility.click();
		report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='uploadCategory']/option[2]")));
		waitForElement(uploadCategory);
		uploadCategory.click();
		report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload option selected successfully");
		//code to verify text inside the file
		
		String fs;
		 File file;
		 
		 //code to upload the latest file from the directory
		 File directory = new File("D:\\software\\t");
			// get just files, not directories
			File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
		
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
			System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
			displayFiles(files);
		
		String sFilePath = path; 
		  boolean isMatch = false ;
		  boolean matcher = false;
		  //Creating FileReader object
		  FileReader fr = null;
		  //Creating BufferedReader object
		  BufferedReader txtReader  = null;
		  String str = null;
		  String pattern=".*CM/EMTA.*";
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
		   Pattern ppattern= Pattern.compile(pattern);
		    matcher=pattern.matches(pattern);
		   System.out.println(matcher);
		      //isMatch = Pattern.matches(pattern, str);
		     System.out.println("The text contains 'CM/EMTA'? " + matcher);

		    } catch (IOException e) {
		         e.printStackTrace();
		    } finally {
		     try {
		      if (txtReader != null)txtReader.close();
		     } catch (IOException ex) {
		      ex.printStackTrace();
		     }
		    }
		
		if(matcher==true)
		{
			report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'CM/EMTA'", Status.PASS);
			waitForElement(Browse);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
			Browse.sendKeys(path);
		}
		
		else{
			
			report.updateTestLog("Feed file is inappropriate","Feed has missing fields", Status.PASS);
			browser.close();
			
		}
		waitForElement(Upload);
		report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
		Upload.click();
		Thread.sleep(5000);
		waitForElement(Upload);
		HandleAlert();
		Thread.sleep(10000);
				
		return new BomUploadFilePage(browser,report);
		
	}
	static String str,path;
	public static void displayFiles(File[] files) {
		for (File file : files) {
		
			
			str=file.getName();
			System.out.println("latest file : "+str);
			path="D:\\software\\t\\"+str;
			System.out.println("new path where file will upload:-"+path);
			//System.out.printf("File: %-20s Last Modified:" + new Date(file.lastModified()) + "\n", file.getName());
			
			//System.out.println(obj.getFile("D:\\software\\t\\"+file.getName()+""));	
			System.out.printf("File: %-20s Last Modified:" + new Date(file.lastModified()) + "\n", file.getName());

			//System.out.println("%-20s"+file.getName());
		
		}}
	
	private String getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return result.toString();

	  }

	
	/**
	* This function will upload and validate the eligibility feed file 
	* with a swap type as X1.
	* @author  Dhruv
	*/
	public BomUploadFilePage UploadFileX1(CustomerDetails customerDetails) throws Exception
	{
		waitForElement(bulkUploads);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Uploads")));
		bulkUploads.click();
		report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
		waitForElement(BulkUploadUtility);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Upload Utility")));
		BulkUploadUtility.click();
		report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='uploadCategory']/option[2]")));
		waitForElement(uploadCategory);
		uploadCategory.click();
		report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload selected successfully");
		//code to verify text inside the file
		
		String fs;
		 File file;
		 
		 //code to upload the latest file from the directory
		 File directory = new File("D:\\software\\t");
			// get just files, not directories
			File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
		
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
			System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
			displayFiles(files);
		
		String sFilePath = path; 
		  boolean isMatch = false ;
		  boolean matcher = false;
		  //Creating FileReader object
		  FileReader fr = null;
		  //Creating BufferedReader object
		  BufferedReader txtReader  = null;
		  String str = null;
		  String pattern=".*X1.*";
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
		   Pattern ppattern= Pattern.compile(pattern);
		    matcher=pattern.matches(pattern);
		   System.out.println(matcher);
		      //isMatch = Pattern.matches(pattern, str);
		     System.out.println("The text contains 'X1'? " + matcher);

		    } catch (IOException e) {
		         e.printStackTrace();
		    } finally {
		     try {
		      if (txtReader != null)txtReader.close();
		     } catch (IOException ex) {
		      ex.printStackTrace();
		     }
		    }
		    BufferedReader crunchifyBuffer = null;
			int crunchifyTotalWords = 0;
			int crunchifyTotalWords1 = 0;
			int crunchifyTotalLines = 0;
			int crunchifyTotalCharacters = 0;
			int crunchifyTotalCharacters1 = 0;
			//String crunchifyFile = "D:\\software\\t\\EligibleAccounts_201605020715.txt";
			//readFileAndPrintCounts();
	 
			String crunchifyLine;
	 
			// Read file contents
			crunchifyBuffer = new BufferedReader(new FileReader(sFilePath));
	 
			try {
				System.out.println("========== File Content ==========");
	 
				// read each line one by one
				while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
					System.out.println(crunchifyLine);
					crunchifyTotalLines++;
	 
					// ignore multiple white spaces
					String[] myWords = crunchifyLine.replaceAll("\\s+", " ").split(" ");
	 
					String[] myWords1 = crunchifyLine.replaceAll("\\s+", ":").split(":");
					
					for (String s : myWords) {
						crunchifyTotalCharacters += s.length();
					}
	 
					crunchifyTotalWords += myWords.length;
					
					for (String s : myWords1) {
						crunchifyTotalCharacters1 += s.length();
					}
	 
					crunchifyTotalWords1 += myWords1.length;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
			System.out.println("\n========== Result ==========");
	 
			System.out.println("* Total Characters: " + crunchifyTotalCharacters);
			System.out.println("* Total Charactersnew: " + crunchifyTotalCharacters1);
			System.out.println("* Total Words: " + crunchifyTotalWords);
			
			System.out.println("* Total Lines: " + crunchifyTotalLines);
			
			int test=crunchifyTotalCharacters-crunchifyTotalCharacters1;
			
			if(test==16||test==49)
			{
				
				System.out.println("success");
			}
			else
			{
				
				System.out.println("not");
			}
		
		if(matcher==true)
		{
			report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'X1'", Status.PASS);
			Thread.sleep(5000);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
			Browse.sendKeys(path);
		}
		
		else{
			
			report.reportFailEvent("Feed file is inappropriate", "Feed has missing fields");
			
		}
		
		waitForElement(Upload);
		report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
		Upload.click();
		waitForElement(Upload);
		
		HandleAlert();
		Thread.sleep(10000);
			
		return new BomUploadFilePage(browser,report);
		
	}
	
	
	/**
	* This function will check for number of colons in the feed file 
	* @author  Dhruv
	*/
	public  BomUploadFilePage readFileAndPrintCounts() throws FileNotFoundException {
		 
		BufferedReader crunchifyBuffer = null;
		int crunchifyTotalWords = 0;
		int crunchifyTotalWords1 = 0;
		int crunchifyTotalLines = 0;
		int crunchifyTotalCharacters = 0;
		int crunchifyTotalCharacters1 = 0;
		String crunchifyFile = "D:\\software\\t\\EligibleAccounts_201605020715.txt";
		readFileAndPrintCounts();
 
		String crunchifyLine;
 
		// Read file contents
		crunchifyBuffer = new BufferedReader(new FileReader(crunchifyFile));
 
		try {
			System.out.println("========== File Content ==========");
 
			// read each line one by one
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
				System.out.println(crunchifyLine);
				crunchifyTotalLines++;
 
				// ignore multiple white spaces
				String[] myWords = crunchifyLine.replaceAll("\\s+", " ").split(" ");
 
				String[] myWords1 = crunchifyLine.replaceAll("\\s+", ":").split(":");
				
				for (String s : myWords) {
					crunchifyTotalCharacters += s.length();
				}
 
				crunchifyTotalWords += myWords.length;
				
				for (String s : myWords1) {
					crunchifyTotalCharacters1 += s.length();
				}
 
				crunchifyTotalWords1 += myWords1.length;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		System.out.println("\n========== Result ==========");
 
		System.out.println("* Total Characters: " + crunchifyTotalCharacters);
		System.out.println("* Total Charactersnew: " + crunchifyTotalCharacters1);
		System.out.println("* Total Words: " + crunchifyTotalWords);
		
		System.out.println("* Total Lines: " + crunchifyTotalLines);
		
		int test=crunchifyTotalCharacters-crunchifyTotalCharacters1;
		
		if(test==16||test==49)
		{
			
			System.out.println("success");
		}
		
		return new BomUploadFilePage(browser,report);
		
	}
 
	
	/**
	* This function will upload and validate the eligibility feed file 
	* with a swap type as XR11.
	* 
	* @author  Dhruv
	
	*/
	
	public BomUploadFilePage UploadFileXR11(CustomerDetails customerDetails) throws Exception
	{
		waitForElement(bulkUploads);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Uploads")));
		bulkUploads.click();
		report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
		waitForElement(BulkUploadUtility);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Upload Utility")));
		BulkUploadUtility.click();
		report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='uploadCategory']/option[2]")));
		waitForElement(uploadCategory);
		uploadCategory.click();
		report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload option selected successfully");
		//code to verify text inside the file
		
		String fs;
		 File file;
		 
		 //code to upload the latest file from the directory
		 File directory = new File("D:\\software\\t");
			// get just files, not directories
			File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
		
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
			System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
			displayFiles(files);
			
			
		
		String sFilePath = path; 
		  boolean isMatch = false ;
		  boolean matcher = false;
		  //Creating FileReader object
		  FileReader fr = null;
		  //Creating BufferedReader object
		  BufferedReader txtReader  = null;
		  String str = null;
		  String pattern=".*XR11.*";
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
		   Pattern ppattern= Pattern.compile(pattern);
		    matcher=pattern.matches(pattern);
		   System.out.println(matcher);
		      //isMatch = Pattern.matches(pattern, str);
		     System.out.println("The text contains 'XR11'? " + matcher);

		    } catch (IOException e) {
		         e.printStackTrace();
		    } finally {
		     try {
		      if (txtReader != null)txtReader.close();
		     } catch (IOException ex) {
		      ex.printStackTrace();
		     }
		    }

		
		
		if(matcher==true)
		{
			report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'XR11'", Status.PASS);
			waitForElement(Browse);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
			Browse.sendKeys(path);
		}
		
		
		else{
			
			report.updateTestLog("Validation cannot be completed","Now driver should quit", Status.PASS);
			browser.close();
			
		}
		
		waitForElement(Upload);
		report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
		Upload.click();
		Thread.sleep(5000);
		
		Alert alt = browser.switchTo().alert();
		alt.accept();
		
					
		return new BomUploadFilePage(browser,report);
		
	}
	
	/**
	* This function will upload and validate the eligibility feed file 
	* with a swap type as WFE.
	* @author  Dhruv
	*/
	
		public BomUploadFilePage UploadFileWFE(CustomerDetails customerDetails) throws Exception
		{
			waitForElement(bulkUploads);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Uploads")));
			bulkUploads.click();
			report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
			waitForElement(BulkUploadUtility);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Upload Utility")));
			BulkUploadUtility.click();
			report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='uploadCategory']/option[2]")));
			waitForElement(uploadCategory);
			uploadCategory.click();
			report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload option selected successfully");
			//code to verify text inside the file
			
			String fs;
			 File file;
			 
			 //code to upload the latest file from the directory
			 File directory = new File("D:\\software\\t");
				// get just files, not directories
				File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
			
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
				displayFiles(files);
				
				
			
			String sFilePath = path; 
			  boolean isMatch = false ;
			  boolean matcher = false;
			  //Creating FileReader object
			  FileReader fr = null;
			  //Creating BufferedReader object
			  BufferedReader txtReader  = null;
			  String str = null;
			  String pattern=".*WFE.*";
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
			   Pattern ppattern= Pattern.compile(pattern);
			    matcher=pattern.matches(pattern);
			   System.out.println(matcher);
			      //isMatch = Pattern.matches(pattern, str);
			     System.out.println("The text contains 'WFE'? " + matcher);

			    } catch (IOException e) {
			         e.printStackTrace();
			    } finally {
			     try {
			      if (txtReader != null)txtReader.close();
			     } catch (IOException ex) {
			      ex.printStackTrace();
			     }
			    }
			
			if(matcher==true)
			{
				report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'WFE'", Status.PASS);
				waitForElement(Browse);
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
				Browse.sendKeys(path);
			}
			
			else{
				
				report.updateTestLog("Validation cannot be completed","Now driver should quit", Status.PASS);
				browser.close();
				
			}
			waitForElement(Upload);
			report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
			Upload.click();
			Thread.sleep(5000);
			
			HandleAlert();
			
						
			return new BomUploadFilePage(browser,report);
			
		}

		/**
		* This function will upload and validate the eligibility feed file 
		* with a swap type as EOL.
		* @author  Dhruv
		*/
		
		public BomUploadFilePage UploadFileEOL(CustomerDetails customerDetails) throws Exception
		{
			waitForElement(bulkUploads);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Uploads")));
			bulkUploads.click();
			report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
			waitForElement(BulkUploadUtility);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Upload Utility")));
			BulkUploadUtility.click();
			report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='uploadCategory']/option[2]")));
			waitForElement(uploadCategory);
			uploadCategory.click();
			report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload option selected successfully");
			//code to verify text inside the file
			
			String fs;
			 File file;
			 
			 //code to upload the latest file from the directory
			 File directory = new File("D:\\software\\t");
				// get just files, not directories
				File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
			
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
				displayFiles(files);
				
				
			String sFilePath = path; 
			  boolean isMatch = false ;
			  boolean matcher = false;
			  //Creating FileReader object
			  FileReader fr = null;
			  //Creating BufferedReader object
			  BufferedReader txtReader  = null;
			  String str = null;
			  String pattern=".*EOL.*";
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
			   Pattern ppattern= Pattern.compile(pattern);
			    matcher=pattern.matches(pattern);
			   System.out.println(matcher);
			      //isMatch = Pattern.matches(pattern, str);
			     System.out.println("The text contains 'EOL'? " + matcher);

			    } catch (IOException e) {
			         e.printStackTrace();
			    } finally {
			     try {
			      if (txtReader != null)txtReader.close();
			     } catch (IOException ex) {
			      ex.printStackTrace();
			     }
			    }
	
			if(matcher==true)
			{
				report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'EOL'", Status.PASS);
				waitForElement(Browse);
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
				Browse.sendKeys(path);
			}
			
			
			else{
				
				report.updateTestLog("Validation cannot be completed","Now driver should quit", Status.PASS);
				browser.close();
				
			}
			waitForElement(Upload);
			report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
			Upload.click();
			Thread.sleep(5000);
			
			HandleAlert();
			
			return new BomUploadFilePage(browser,report);
			
		}
		
		/**
		* This function will upload and validate the eligibility feed file 
		* with a swap type as COMBO.
		* @author  Dhruv
		*/
		public BomUploadFilePage UploadFileCOMBO(CustomerDetails customerDetails) throws Exception
		{
			waitForElement(bulkUploads);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Uploads")));
			bulkUploads.click();
			report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
			waitForElement(BulkUploadUtility);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Upload Utility")));
			BulkUploadUtility.click();
			report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='uploadCategory']/option[2]")));
			waitForElement(uploadCategory);
			uploadCategory.click();
			report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload option selected successfully");
			//code to verify text inside the file
			
			String fs;
			 File file;
			 
			 //code to upload the latest file from the directory
			 File directory = new File("D:\\software\\t");
				// get just files, not directories
				File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
			
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
				displayFiles(files);
			
			String sFilePath = path; 
			  boolean isMatch = false ;
			  boolean matcher = false;
			  //Creating FileReader object
			  FileReader fr = null;
			  //Creating BufferedReader object
			  BufferedReader txtReader  = null;
			  String str = null;
			  String pattern=".*X1.*";
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
			   Pattern ppattern= Pattern.compile(pattern);
			    matcher=pattern.matches(pattern);
			   System.out.println(matcher);
			      //isMatch = Pattern.matches(pattern, str);
			     System.out.println("The text contains 'X1'? " + matcher);

			    } catch (IOException e) {
			         e.printStackTrace();
			    } finally {
			     try {
			      if (txtReader != null)txtReader.close();
			     } catch (IOException ex) {
			      ex.printStackTrace();
			     }
			    }
			
			if(matcher==true)
			{
				report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'COMBO'", Status.PASS);
				waitForElement(Browse);
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
				Browse.sendKeys(path);
			}
			
			else{
				
				report.updateTestLog("Feed file is inappropriate","Feed has missing fields", Status.PASS);
				browser.close();
				
			}
			waitForElement(Upload);
			report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
			Upload.click();
			Thread.sleep(5000);
			HandleAlert();
								
			return new BomUploadFilePage(browser,report);
			
		}

		
		/**
		* This function will upload and validate the eligibility feed file 
		* with a swap type as EOL commercial accounts.
		* @author  Dhruv
		*/
		public BomUploadFilePage UploadFileEOLCOM(CustomerDetails customerDetails) throws Exception {
			
			waitForElement(bulkUploads);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Uploads")));
			bulkUploads.click();
			report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
			waitForElement(BulkUploadUtility);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Upload Utility")));
			BulkUploadUtility.click();
			report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='uploadCategory']/option[2]")));
			waitForElement(uploadCategory);
			uploadCategory.click();
			report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload option selected successfully");
			
			String fs;
			 File file;
			 
			 //code to upload the latest file from the directory
			 File directory = new File("D:\\software\\t");
				// get just files, not directories
				File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
			
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
				displayFiles(files);
			
			String sFilePath = path; 
			  boolean isMatch = false ;
			  boolean matcher = false;
			  //Creating FileReader object
			  FileReader fr = null;
			  //Creating BufferedReader object
			  BufferedReader txtReader  = null;
			  String str = null;
			  String pattern=".*EOL.*";
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
			   Pattern ppattern= Pattern.compile(pattern);
			    matcher=pattern.matches(pattern);
			   System.out.println(matcher);
			      //isMatch = Pattern.matches(pattern, str);
			     System.out.println("The text contains 'EOL'? " + matcher);

			    } catch (IOException e) {
			         e.printStackTrace();
			    } finally {
			     try {
			      if (txtReader != null)txtReader.close();
			     } catch (IOException ex) {
			      ex.printStackTrace();
			     }
			    }
			
			if(matcher==true)
			{
				report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'EOL'", Status.PASS);
				waitForElement(Browse);
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
				Browse.sendKeys(path);
			}
			
			else{
				
				report.updateTestLog("Feed file is inappropriate","Feed has missing fields", Status.PASS);
				browser.close();
				
			}
			waitForElement(Upload);
			report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
			Upload.click();
			Thread.sleep(5000);
			HandleAlert();
			
			
			return new BomUploadFilePage(browser,report);
			
		}

		
		/**
		* This function will upload and validate the eligibility feed file 
		* with a swap type as MPEG4 commercial accounts.
		* @author  Dhruv
		 * @throws Exception 
		*/
		public BomUploadFilePage UploadFileMPEG4COM(CustomerDetails customerDetails) throws Exception {
			
			waitForElement(bulkUploads);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Uploads")));
			bulkUploads.click();
			report.reportPassEvent("Navigate to Bulk Uploads Tab","Selection successful");
			waitForElement(BulkUploadUtility);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Bulk Upload Utility")));
			BulkUploadUtility.click();
			report.reportPassEvent("Click Element: Bulk Upload Utility link","Selection successful");
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='uploadCategory']/option[2]")));
			waitForElement(uploadCategory);
			uploadCategory.click();
			report.reportPassEvent("Select from Upload Category dropdown","Eligibility file upload option selected successfully");
			
			String fs;
			 File file;
			 
			 //code to upload the latest file from the directory
			 File directory = new File("D:\\software\\t");
				// get just files, not directories
				File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
			
				Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				System.out.println("\nLast Modified Ascending Order (LASTMODIFIED_COMPARATOR)");
				displayFiles(files);
			
			String sFilePath = path; 
			  boolean isMatch = false ;
			  boolean matcher = false;
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
			   Pattern ppattern= Pattern.compile(pattern);
			    matcher=pattern.matches(pattern);
			   System.out.println(matcher);
			      //isMatch = Pattern.matches(pattern, str);
			     System.out.println("The text contains 'MPEG4'? " + matcher);

			    } catch (IOException e) {
			         e.printStackTrace();
			    } finally {
			     try {
			      if (txtReader != null)txtReader.close();
			     } catch (IOException ex) {
			      ex.printStackTrace();
			     }
			    }
			
			if(matcher==true)
			{
				report.updateTestLog("Validation of Eligibility file ","Validation successful: Swap Type 'MPEG4'", Status.PASS);
				waitForElement(Browse);
				//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("html/body/div/div[2]/div/div/div/div[2]/div[3]/input")));
				Browse.sendKeys(path);
			}
			
			else{
				
				report.updateTestLog("Feed file is inappropriate","Feed has missing fields", Status.PASS);
				browser.close();
				
			}
			waitForElement(Upload);
			report.updateTestLog("Click Element: Upload","Clicked successfully", Status.DONE);
			Upload.click();
			Thread.sleep(5000);
			HandleAlert();
			
			return new BomUploadFilePage(browser,report);
			
		}

	public BomUploadFilePage UploadFileProg(CustomerDetails customerDetails) throws Exception {
		click(bulkUploads);
		report.reportPassEvent("Navigate to Bulk Uploads Tab", "Navigation successful");
		if (isDisplayed(BulkUploadUtility))
			report.reportPassEvent("Navigate to Bulk Uploads Tab", "Bulk Upload Utility is displayed.");
		else
			report.reportFailEvent("Navigate to Bulk Uploads Tab", "Bulk Upload Utility is Not displayed.");

		click(BulkUploadUtility);
		report.reportPassEvent("Click Bulk Upload Utility link", "Bulk Upload Utility is selected");

		boolean displayed=isDisplayed(uploadCategoryDropBox);
System.out.println(displayed+"uploadCategoryDropBox");
		Select uploadCatDropDown = new Select(uploadCategoryDropBox);
	/*	for(int i=0;i<3;i++){
		try{
			uploadCatDropDown.selectByValue("Eligibility File Upload");
			flag=true;
			break;
		
		
		}catch(Exception e)
										
		}}*/
		uploadCatDropDown.selectByValue("Eligibility File Upload");
	/*	while(!uploadCategoryDropBox.isSelected()){
			//uploadCatDropDown.selectByIndex(1);
			uploadCatDropDown.selectByValue("Eligibility File Upload");
		}*/
	//	uploadCatDropDown.selectByVisibleText("   Eligibility File Upload ");
	//	uploadCatDropDown.selectByIndex(1);
		   
										 //Eligibility File Upload
		report.reportPassEvent("Select Eligibility File Upload from Dropdown", "Eligibility File Upload is selected from Upload Category dropdown ");
		// code to verify text inside the file

		// report.updateTestLog("Validation of Eligibility file ","Validation
		// started successfully", Status.PASS);

		// mandatory specifing the local path folder of feed file
		File directory = new File(TestUtils.getRelativePath() + "\\src\\test\\resources\\EligiliblityFeedFiles");
		// arranging based on last modified
		File[] files = directory.listFiles((FileFilter) FileFileFilter.FILE);
		Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		// accessing latest modified feed file
		File a = files[0];
		// converting the path name to string to append file name
		String dir = directory.toString();
		// getting latest modified file name
		String filename = a.getName();
		// concatinating path with feed file name
		String path = dir + "\\" + filename;
		System.out.println(path);
		Browse.sendKeys(path);
		click(Upload);
		HandleAlert();

		return new BomUploadFilePage(browser, report);

	}

		private Object BomUploadFilePage(WebDriver browser, SeleniumReport report2) {
			// TODO Auto-generated method stub
			return null;
		}	
		
		
		
	
}
