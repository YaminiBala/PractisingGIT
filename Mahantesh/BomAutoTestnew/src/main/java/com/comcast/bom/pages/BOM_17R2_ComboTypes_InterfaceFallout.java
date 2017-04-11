package com.comcast.bom.pages;
import java.awt.AWTException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.bson.BasicBSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.cognizant.framework.Status;
import com.comcast.reporting.*;
import com.comcast.bom.data.CustomerDetails;
import com.comcast.bom.data.LoginDetails;
import com.comcast.bom.data.Mongoconnect;
import com.comcast.utils.ComcastTest;
import com.comcast.utils.SeleniumReport;
import com.comcast.utils.TestSettings;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.ServerAddress;
public class BOM_17R2_ComboTypes_InterfaceFallout extends Page{
	public CustomerDetails customerDetails;
protected SeleniumReport report;
	


	//public WebDriver driver;
	//public WebDriver browser;
	public Mongoconnect Mc;
	
	
	public BOM_17R2_ComboTypes_InterfaceFallout(WebDriver browser,SeleniumReport report)
	{
		super(browser,report);
		PageFactory.initElements(browser, this);
		this.report=report;
		this.browser=browser;
	}
	
	@FindBy(partialLinkText="Account")
	public WebElement Account;
	
	@FindBy(partialLinkText="Bulk Uploads")
	public WebElement BulkUploads;
	
	@FindBy(partialLinkText="Bulk Upload Utility")
	public WebElement BulkUploadUtility;
	
	@FindBy(xpath=".//*[@id='uploadCategory']/option[4]")
	public WebElement uploadCategory;
	
	@FindBy(xpath=".//*[@id='swap']/div[2]/form/button[2]")
	public WebElement CheckEligibility;
	
	@FindBy(xpath="html/body/div/div[2]/div/div/div/div[2]/div[3]/input")
	public WebElement Browse;
	
	@FindBy(xpath=".//*[@id='swap']/div[2]/form/div[9]/div[3]/table/tbody/tr[1]/th[1]/div")
	public WebElement ValidateEligible;
	
	@FindBy(xpath=".//*[@id='comboOptions']/input[2]")
	public WebElement selectMpeg4Checkbox;
	
	@FindBy(xpath="	.//*[@id='comboOptions']/input[3]")
	public WebElement selectEOLCheckbox;
	
	
	@FindBy(xpath=".//*[@id='comboOptions']/input[1]")
	public WebElement selectX1Checkbox;
	
	
	@FindBy(xpath=".//*[@id='comboOptions']/input[4]")
	public WebElement selectCMEMTACheckbox;
	
	
	@FindBy(partialLinkText="Equipment Update Xchange tool")
	public WebElement Enhanced;
	
	@FindBy(xpath=".//*[@id='swap']/div[2]/form/div[1]/input")
	public WebElement accountno;
	
	@FindBy(xpath=".//*[@id='swapType']/option[2]")
	public WebElement swapType;
	
	@FindBy(xpath=".//*[@id='swapType']/option[7]")
	public WebElement swapTypecm;
	
	@FindBy(xpath=".//*[@id='swapType']/option[4]")
	public WebElement swapTypeCOM;
	
	@FindBy(xpath=".//*[@id='swapType']/option[3]")
	public WebElement swapTypeX1;
	
	@FindBy(xpath=".//*[@id='swapType']/option[8]")
	public WebElement swapTypeXR11;
	
	@FindBy(xpath=".//*[@id='swapType']/option[5]")
	public WebElement swapTypeEOL;
	
	@FindBy(xpath=".//*[@id='swapType']/option[6]")
	public WebElement swapTypeWFE;
	
	//added REDRNGswap Xpath on 1/20/2017 by dnages002c
	@FindBy(xpath=".//*[@id='swapType']/option[9]")
	public WebElement swapTypeREDRNG150;
	
	@FindBy(xpath="html/body/div/div[2]/div/div/div/div[2]/div[3]/input")
	public WebElement browse;
	
	@FindBy(xpath="html/body/div/div[2]/div/div/div/div[2]/div[3]/button")
	public WebElement uploadbutton;
	
	@FindBy(xpath="html/body/div/div[2]/div/div/div/div[2]/div[3]/button")
	public WebElement Upload;
	
	@FindBy(xpath=".//*[@id='swap']/div[2]/form/button[1]")
	public WebElement CreateSikOrder;
	
	
	@FindBy(xpath="//*[text()='Customer has declined to OPT IN']//preceding-sibling::input[1]")
	public WebElement optout;
	

	@FindBy(xpath=".//*[@id='swap']/div[2]/div[1]/label[2]")
	public WebElement optout1;

		public BOM_17R2_ComboTypes_InterfaceFallout enterAccountCOMBO(CustomerDetails customerDetails) throws Exception, AWTException, IOException {
			// TODO Auto-generated method stub
			waitForElement(Account);
			Account.click();
			report.updateTestLog("Navigate to Account Tab","Selection successful", Status.DONE);
			
			waitForElement(Enhanced);
			Enhanced.click();
			report.updateTestLog("Click Element: Equipment Update Xchange tool link","Selection successful", Status.DONE);
			
			waitForElement(accountno);
			accountno.sendKeys(customerDetails.accountNumber);
			report.updateTestLog("Enter Account Number","Account :-"+customerDetails.accountNumber, Status.DONE);
			
			
			waitForElement(swapTypeCOM);
			swapTypeCOM.click();
			report.updateTestLog("Select from Swap type dropdown","COMBO swap selected successfully ", Status.DONE);
			
			
			waitForElement(selectMpeg4Checkbox);
			selectX1Checkbox.click();
			
			waitForElement(selectCMEMTACheckbox);
			selectCMEMTACheckbox.click();
			Thread.sleep(10000);
			
			
			optout.click();
			Thread.sleep(10000);
			
			
		
			CreateSikOrder.click();
			report.updateTestLog("Click Element: Create SIK Order button ","Clicked successfully ", Status.DONE);
			
			
			
			Thread.sleep(4000);
			Alert alt=browser.switchTo().alert();
			
			alt.accept();
			waitForElement(Account);
			return new BOM_17R2_ComboTypes_InterfaceFallout(browser,report);
			
		}




		public BOM_17R2_ComboTypes_InterfaceFallout enterAccountCOMBO_EOLCM(CustomerDetails customerDetails) throws Exception, AWTException, IOException {
			// TODO Auto-generated method stub
			waitForElement(Account);
			Account.click();
			report.updateTestLog("Navigate to Account Tab","Selection successful", Status.DONE);
			
			waitForElement(Enhanced);
			Enhanced.click();
			report.updateTestLog("Click Element: Equipment Update Xchange tool link","Selection successful", Status.DONE);
			
			waitForElement(accountno);
			accountno.sendKeys(customerDetails.accountNumber);
			report.updateTestLog("Enter Account Number","Account :-"+customerDetails.accountNumber, Status.DONE);
			
			
			waitForElement(swapTypeCOM);
			swapTypeCOM.click();
			report.updateTestLog("Select from Swap type dropdown","COMBO swap selected successfully ", Status.DONE);
			
			
			waitForElement(selectMpeg4Checkbox);
			selectEOLCheckbox.click();
			
			waitForElement(selectCMEMTACheckbox);
			selectCMEMTACheckbox.click();
			Thread.sleep(10000);
			
			
			optout.click();
			Thread.sleep(10000);
			
			
		
			CreateSikOrder.click();
			report.updateTestLog("Click Element: Create SIK Order button ","Clicked successfully ", Status.DONE);
			
			
			
			Thread.sleep(4000);
			Alert alt=browser.switchTo().alert();
			
			alt.accept();
			waitForElement(Account);
			return new BOM_17R2_ComboTypes_InterfaceFallout(browser,report);
			
		}




		public BOM_17R2_ComboTypes_InterfaceFallout enterAccountCOMBO_MPEG4CM(CustomerDetails customerDetails) throws Exception, AWTException, IOException {
			// TODO Auto-generated method stub
			waitForElement(Account);
			Account.click();
			report.updateTestLog("Navigate to Account Tab","Selection successful", Status.DONE);
			
			waitForElement(Enhanced);
			Enhanced.click();
			report.updateTestLog("Click Element: Equipment Update Xchange tool link","Selection successful", Status.DONE);
			
			waitForElement(accountno);
			accountno.sendKeys(customerDetails.accountNumber);
			report.updateTestLog("Enter Account Number","Account :-"+customerDetails.accountNumber, Status.DONE);
			
			
			waitForElement(swapTypeCOM);
			swapTypeCOM.click();
			report.updateTestLog("Select from Swap type dropdown","COMBO swap selected successfully ", Status.DONE);
			
			
			waitForElement(selectMpeg4Checkbox);
			selectEOLCheckbox.click();
			
			waitForElement(selectCMEMTACheckbox);
			selectCMEMTACheckbox.click();
			Thread.sleep(10000);
			
			
			optout.click();
			Thread.sleep(10000);
			
			
		
			CreateSikOrder.click();
			report.updateTestLog("Click Element: Create SIK Order button ","Clicked successfully ", Status.DONE);
			
			
			
			Thread.sleep(4000);
			Alert alt=browser.switchTo().alert();
			
			alt.accept();
			waitForElement(Account);
			return new BOM_17R2_ComboTypes_InterfaceFallout(browser,report);
			
		}




		public BOM_17R2_ComboTypes_InterfaceFallout enterAccountX1(CustomerDetails customerDetails) throws Exception, AWTException, IOException {
			// TODO Auto-generated method stub
			waitForElement(Account);
			Account.click();
			report.updateTestLog("Navigate to Account Tab","Selection successful", Status.DONE);
			
			waitForElement(Enhanced);
			Enhanced.click();
			report.updateTestLog("Click Element: Equipment Update Xchange tool link","Selection successful", Status.DONE);
			
			waitForElement(accountno);
			accountno.sendKeys(customerDetails.accountNumber);
			report.updateTestLog("Enter Account Number","Account :-"+customerDetails.accountNumber, Status.DONE);
			
			
			waitForElement(swapTypeX1);
			swapTypeX1.click();
			report.updateTestLog("Select from Swap type dropdown","X1 swap selected successfully ", Status.DONE);
			
			
			Thread.sleep(10000);
			
			
			optout.click();
			Thread.sleep(10000);
			
			
		
			CreateSikOrder.click();
			report.updateTestLog("Click Element: Create SIK Order button ","Clicked successfully ", Status.DONE);
			
			
			
			Thread.sleep(4000);
			Alert alt=browser.switchTo().alert();
			
			alt.accept();
			waitForElement(Account);
			return new BOM_17R2_ComboTypes_InterfaceFallout(browser,report);
			
		}

		
			
		}


		




		



		
	

