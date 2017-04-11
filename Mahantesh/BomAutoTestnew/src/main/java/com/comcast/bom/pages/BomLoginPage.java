package com.comcast.bom.pages;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.comcast.reporting.Status;
import com.comcast.bom.data.CustomerDetails;
import com.comcast.bom.data.LoginDetails;
import com.comcast.utils.ComcastTest;
import com.comcast.utils.SeleniumReport;
import com.comcast.utils.TestSettings;
import com.comcast.utils.TestUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


public class BomLoginPage extends Page{
	
	
	private LoginDetails loginInfo;
	//public Webbrowser browser;
	private  WebDriverWait wait;
	
	public BomLoginPage(WebDriver browser,SeleniumReport report)
	{
		super(browser,report);
		PageFactory.initElements(browser, this);
		this.report=report;
		 //wait = new WebbrowserWait(browser, 30); 
	}
	//loginInfo = LoginDetails.loadFromDatatable(dataTable);
	@FindBy(xpath="//input[@name='login']")
	public WebElement username;

	@FindBy(id ="password")
	private WebElement password;
	
	@FindBy(id = "login")	
	private WebElement Submit;
	
	@FindBy(id = "globalHeader")
	private WebElement globalHeader;
	
	
	protected SeleniumReport report;
	
	/**
	* This function will take username and password from framework file
	* and login into BOM. 
	* 
	* @author  Dhruv
	
	*/
	
	public void handleLoginAlert() {
		try {
			waitForAlert();
			if (isAlertPresent()) {
				Alert alt = browser.switchTo().alert();
				String txt = browser.switchTo().alert().getText();
				System.out.println(txt);
				if (txt.contains("Invalid Username")) {
					alt.accept();
					report.reportFailEvent("Login into BOM UI", "Login is not Successful.");
				}
				Thread.sleep(2000);
				alt.accept();
			}
		} catch (Exception ex) {

		}
	}
	
	public BomLoginPage LoginToBom(LoginDetails loginInfo) throws Exception
	{
		isDisplayed(username);
		sendKeys(username, loginInfo.userID);
		//report.updateTestLog("BOM UI Login: User Name","User Name: '"+ testSettings.getSettings("bomUserName")+"' ", Status.SCREENSHOT);
		report.reportPassEvent("BOM UI Login: User Name", "User Name: '"+ loginInfo.userID +"' ");
	
		isDisplayed(password);
		sendKeys(password,  loginInfo.password);
		report.reportPassEvent("BOM UI Login: Password","Password entered successfully ");
		
		click(Submit);
		handleLoginAlert();
		
		if (isDisplayed(globalHeader))
			report.reportPassEvent("BOM UI Login ", "Successfully Logged in and DashBord is Displayed");
		else
			report.reportFailEvent("BOM UI Login ", "Log in is not successful.");
		return new BomLoginPage(browser,report);
	}

	
	@FindBy(xpath ="//td[text()='ID']/following-sibling::td[@class='CwfFTDF']/input")
	private WebElement userName;
	
	@FindBy(xpath ="//td[text()='Password']/following-sibling::td[@class='CwfFTDF']/input")
	private WebElement passwordComm;
	
	@FindBy(xpath ="//input[@value='Login']")
	private WebElement btnLogin;
	
	
	
	
	public void loginSymComm(String accountNumber){
		try{
			TestSettings ts = new TestSettings();
			browser.get("http://omapp-dt-a1q.ula.comcast.net:7401/cwf");
			Thread.sleep(4000);
			browser.switchTo().frame("cwf_5");
			WebElement userName = browser.findElement(By.xpath("//td[text()='ID']/following-sibling::td[@class='CwfFTDF']/input"));
			sendKeys(userName, "upadmin");
			sendKeys(passwordComm,"upadmin");
			click(btnLogin);
			Thread.sleep(6000);
			try{
			browser.switchTo().frame("cwf_7");
			}catch(Exception ex){
		//	browser.switchTo().frame("cwf_H");
			}
			
			Select dropdown = new Select(browser.findElement(By.xpath("//select[@name='f.0100']")));
			dropdown.selectByValue("dtawlext:dtaWLApp");
			click(browser.findElement(By.xpath("//input[@name='f.0200']")));
			Thread.sleep(6000);
		//pageSourceInaFile(browser.getPageSource());
			
			click(browser.findElement(By.xpath("//span[contains(text(),'Worklist')]")));
			click(browser.findElement(By.xpath("//span[contains(text(),'Worklist Manager')]")));
			
		//	sendKeys(browser.findElement(By.xpath("//td[@class ='CwfFTDF']/input[@name='f.0A00']")),"1234567789");
			
		//	sendKeys(passwordComm,"upadmin");
		//	click(btnLogin);
			
			// System.out.println("Page Source "+browser.getPageSource());
			// String str1 = browser.getPageSource();
			// System.out.println("page source is"+str1);
			browser.findElement(By.xpath("//*[@id='CwSearch']/div/table/tbody/tr[33]/td[1]/select/option[8]")).click();
			browser.findElement(By.xpath("//input[@class='CwFOB']")).click();
			browser.switchTo().defaultContent();
			Thread.sleep(3000);
			System.out.println("Hello " + browser.findElements(By.xpath("//iframe")).size());
			// browser.findElement(By.xpath(".//*[@id='cws0_K']"));
			System.out.println("Hi");

			Actions action = new Actions(browser);
			action.clickAndHold(browser.findElement(By.xpath("(//span[contains(text(),'Task')])[3]"))).build().perform();

			String winHandleBefore = browser.getWindowHandle();
			action.moveToElement(browser.findElement(By.xpath("//span[contains(text(),'Get Available')]"))).click().build().perform();
			Thread.sleep(20000);

			/*
			 * String whs = browser.getWindowHandle(); browser.switchTo().window(whs);
			 * System.out.println("current URL is "+browser.getCurrentUrl());
			 */ Set<String> handle = browser.getWindowHandles();
			System.out.println("size of window " + handle.size());
			for (String winHandle : handle) {

				browser.switchTo().window(winHandle);
				System.out.println(browser.getCurrentUrl());
				System.out.println("current title is " + browser.getTitle());
				System.out.println("Window is changed");
			}
			System.out.println("size of window " + handle.size());
			Thread.sleep(10000);
			// System.out.println(browser.getCurrentUrl());
			// System.out.println(browser.getCurrentUrl());
			System.out.println("current URL2 is " + browser.getCurrentUrl());
			System.out.println("No of frames in new window " + browser.findElements(By.xpath("//iframe")).size());
			Thread.sleep(10000);
			browser.switchTo().frame(2);
			browser.findElement(By.xpath("(.//input[@class='CwfFF'])[3]")).sendKeys(accountNumber);
			browser.switchTo().defaultContent();
			action.moveToElement(browser.findElement(By.xpath(".//span[contains(text(),'Search')]"))).click().build().perform();

			Thread.sleep(5000);
			browser.switchTo().frame(2);
			action.moveToElement(browser.findElement(By.xpath(".//td[contains(text(),'" + accountNumber + "')]/.."))).click().build().perform();
			browser.switchTo().defaultContent();
			action.moveToElement(browser.findElement(By.xpath("//span[contains(text(),'Select Task(s)')]"))).click().build().perform();

			Thread.sleep(3000);
			browser.switchTo().window(winHandleBefore);
			WebElement el = browser.findElement(By.xpath("//td[contains(text(),'" + accountNumber + "')]/parent::tr[1]"));
			el.click();
			System.out.println("clicked on the account number");
			Thread.sleep(5000);
			browser.findElement(By.xpath("(//span[contains(text(),'Task')])[3]")).click();

			// action.clickAndHold(browser.findElement(By.xpath("(//span[contains(text(),'Task')])[3]"))).build().perform();

			// action.moveToElement(browser.findElement(By.xpath("//span[contains(text(),'Start
			// Work')]"))).click().build().perform();
			action.moveToElement(browser.findElement(By.xpath(".//span[contains(text(),'Actions')]"))).click().build().perform();
			action.moveToElement(browser.findElement(By.xpath(".//span[contains(text(),'" + ts.getSettings("userAction") + "')]"))).click().build().perform();
			
			
			
			}catch(Exception ex) {
				System.out.println();
			}
	}
	
	/*public static void main(String[] args) {
		SeleniumReport report = null;
		final ThreadLocal<WebDriver> Threadbrowser = new ThreadLocal<WebDriver>();
		WebDriver browser= Threadbrowser.get();
		System.setProperty("webbrowser.chrome.browser", TestUtils.getRelativePath()+ File.separator + "BrowserSpecificbrowsers"+ File.separator + "chromebrowser.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-popup-blocking");
		options.addArguments("chrome.switches", "--disable-extensions");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setBrowserName("chrome");
		browser=ThreadGuard.protect(new ChromeDriver(capabilities));
		Threadbrowser.set(browser);
		BomLoginPage b = new BomLoginPage(browser,report);
		b.loginSymComm();
	}
	*/

}
