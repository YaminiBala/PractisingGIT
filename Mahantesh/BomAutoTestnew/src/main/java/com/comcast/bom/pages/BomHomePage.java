package com.comcast.bom.pages;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.Driver;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.comcast.reporting.*;
import com.comcast.bom.data.CustomerDetails;
import com.comcast.bom.data.LoginDetails;
import com.comcast.bom.data.Mongoconnect;
import com.comcast.utils.ComcastTest;
import com.comcast.utils.SeleniumReport;
import com.comcast.utils.TestSettings;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class BomHomePage extends Page {

	protected SeleniumReport report;

	// public WebDriver driver;
	// public WebDriver browser;
	public Mongoconnect Mc;

	public BomHomePage(WebDriver browser, SeleniumReport report) {
		super(browser, report);
		PageFactory.initElements(browser, this);
		this.report = report;
		this.browser = browser;

	}

	@FindBy(partialLinkText = "Account")
	public WebElement Account;

	@FindBy(partialLinkText = "Bulk Uploads")
	public WebElement BulkUploads;

	@FindBy(partialLinkText = "Bulk Upload Utility")
	public WebElement BulkUploadUtility;

	@FindBy(xpath = ".//*[@id='uploadCategory']/option[4]")
	public WebElement uploadCategory;

	@FindBy(xpath = ".//*[@id='swap']/div[2]/form/button[2]")
	public WebElement CheckEligibility;

	@FindBy(xpath = ".//*[@id='swap']/div[2]/form/div[9]/div[3]/table/tbody/tr[1]/th[1]/div")
	public WebElement ValidateEligible;

	@FindBy(xpath = ".//*[@id='comboOptions']/input[2]")
	public WebElement selectMpeg4Checkbox;

	@FindBy(xpath = "	.//*[@id='comboOptions']/input[3]")
	public WebElement selectEOLCheckbox;

	@FindBy(xpath = ".//*[@id='comboOptions']/input[1]")
	public WebElement selectX1Checkbox;

	@FindBy(xpath = ".//*[@id='comboOptions']/input[4]")
	public WebElement selectCMEMTACheckbox;

	@FindBy(partialLinkText = "Equipment Update Xchange tool")
	public WebElement Enhanced;

	@FindBy(xpath = ".//*[@id='swap']/div[2]/form/div[1]/input")
	public WebElement accountno;

	@FindBy(xpath = ".//*[@id='swapType']/option[2]")
	public WebElement swapType;

	@FindBy(xpath = ".//*[@id='swapType']/option[7]")
	public WebElement swapTypecm;

	@FindBy(xpath = ".//*[@id='swapType']/option[4]")
	public WebElement swapTypeCOM;

	@FindBy(xpath = ".//*[@id='swapType']/option[3]")
	public WebElement swapTypeX1;

	@FindBy(xpath = ".//*[@id='swapType']/option[8]")
	public WebElement swapTypeXR11;

	@FindBy(xpath = ".//*[@id='swapType']/option[5]")
	public WebElement swapTypeEOL;

	@FindBy(xpath = ".//*[@id='swapType']/option[6]")
	public WebElement swapTypeWFE;

	// added REDRNGswap Xpath on 1/20/2017 by dnages002c
	@FindBy(xpath = ".//*[@id='swapType']/option[9]")
	public WebElement swapTypeREDRNG150;

	@FindBy(xpath = "html/body/div/div[2]/div/div/div/div[2]/div[3]/input")
	public WebElement browse;

	@FindBy(xpath = "html/body/div/div[2]/div/div/div/div[2]/div[3]/button")
	public WebElement uploadbutton;

	@FindBy(xpath = ".//*[@id='swap']/div[2]/form/button[1]")
	public WebElement CreateSikOrder;

	@FindBy(xpath = "//*[text()='Customer has declined to OPT IN']//preceding-sibling::input[1]")
	public WebElement optout;

	@FindBy(xpath = ".//*[@id='swap']/div[2]/div[1]/label[2]")
	public WebElement optout1;

	@FindBy(xpath = "//button[contains(text(),'Eligibility')]")
	public WebElement btnCheckEligibility;

	@FindBy(xpath = "//*[@id='swap']/div[2]/form/div[10]")
	public WebElement tblCheckEligibility;
	
	@FindBy(xpath ="//*[@id='swap']/div[2]/form/div[10]/div[3]/table/tbody/tr[contains(@data-ng-repeat,'swapEligibility')]")
	public List<WebElement> chkEligilityRows;

	public BomHomePage enterAccountForX1(CustomerDetails customerDetails) throws Exception {
		waitForElement(Account);
		Account.click();
		report.reportPassEvent("Navigate to Account Tab", "Navigation successful");

		waitForElement(Enhanced);
		Enhanced.click();
		report.reportPassEvent("Click on Equipment Update Xchange Tool Link", "Link selected successfully");

		waitForElement(accountno);
		accountno.sendKeys(customerDetails.accountNumber);
		// report.updateTestLog("Enter Account Number", "Account: " +
		// customerDetails.accountNumber, Status.DONE);
		report.reportPassEvent("Enter Account Number", "Account: " + customerDetails.accountNumber);
		click(btnCheckEligibility);
		HandleAlertForCheckEligibility();
		
		
		if (isDisplayed(tblCheckEligibility)) {
			report.reportPassEvent("Validate Check Eligibility Grid", "Check Eligibility Grid is Successfully Displayed");
		} else {
				report.reportFailEvent("Validate Check Eligibility Grid", "Check Eligibility Grid is Failed");
		}
		
		if(chkEligilityRows.size() <=0){
			report.reportFailEvent("Validate Check Eligibility Grid", "There are No Check Eligilibilty Rows");
		}
		boolean flag = false;
		for(WebElement rows :chkEligilityRows ){
			WebElement isEligible  =rows.findElement(By.xpath("//td[contains(@ng-show,'swapEligibility.eligible') and not(@class='ng-hide')]/div")) ;
			WebElement marketEligible  = rows.findElement(By.xpath("//td[contains(@ng-show,'swapEligibility.marketEligibility') and not(@class='ng-hide')]/div")) ;
			if (getText(isEligible).equalsIgnoreCase("Yes") && getText(marketEligible).equalsIgnoreCase("Yes") )
				flag = true;
				else
				flag = false;
			}
			
			if (flag)
				report.reportPassEvent("Validate Check Eligibility Grid", "There are Check Eligilibilty Rows has Eligible and Market Eligble as 'YES'");
			else {
				report.reportFailEvent("Validate Check Eligibility Grid", "Check Eligilibilty Rows");
			}

		waitForElement(swapTypeX1);
		swapTypeX1.click();
		report.reportPassEvent("Select X1 from Swap type dropdown", "X1 swap selected successfully ");

		/* optout.click(); */
		click(optout);
		/* Thread.sleep(10000); */

		/* CreateSikOrder.click(); */
		click(CreateSikOrder);
		HandleAlertForSubmitSwap();
		report.reportPassEvent("Click on Create SIK Order button ", "Order submitted successfully from BOM UI");
		// Thread.sleep(6000);
		//browser.close();
		return new BomHomePage(browser, report);

	}

	public void HandleAlertForSubmitSwap() {
		try {
			waitForAlert();
			if (isAlertPresent()) {
				Alert alt = browser.switchTo().alert();
				String txt = browser.switchTo().alert().getText();
				System.out.println(txt);
				if (txt.contains("Swap Order Failed with ErrorMessage")) {
					alt.accept();
					report.reportFailEvent("File Upload", "Upload file is not selected.");
				}
				Thread.sleep(5000);
				alt.accept();
			}
		} catch (Exception ex) {

		}
	}

	public void HandleAlertForCheckEligibility() {
		try {
			Thread.sleep(2000);
			waitForAlert();
			if (isAlertPresent()) {
				Alert alt = browser.switchTo().alert();
				String txt = browser.switchTo().alert().getText();
				System.out.println(txt);
				if (txt.contains("The Customer is not SIK eligible")) {
					alt.accept();
					report.reportFailEvent("File Upload", "Check Eligibility failed");
				}
				Thread.sleep(5000);
				alt.accept();
			}
		} catch (Exception ex) {

		}

	}

}
