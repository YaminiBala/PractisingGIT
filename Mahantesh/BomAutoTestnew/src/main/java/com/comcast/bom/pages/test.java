package com.comcast.bom.pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.Select;

import com.comcast.utils.SeleniumReport;
import com.comcast.utils.TestSettings;
import com.comcast.utils.TestUtils;
import com.google.common.io.FileWriteMode;

public class test extends Page {
String id ="";
	
	public test(WebDriver browser, SeleniumReport report) {
		super(browser, report);
		
	}

	public static void main(String[] args) {
		SeleniumReport report = null;
		final ThreadLocal<WebDriver> ThreadDriver = new ThreadLocal<WebDriver>();
		//WebDriver driver= ThreadDriver.get();
		WebDriver driver= null;
		//System.setProperty("webdriver.chrome.driver", TestUtils.getRelativePath()+ File.separator + "BrowserSpecificDrivers"+ File.separator + "chromedriver.exe");
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments("disable-popup-blocking");
		//options.addArguments("chrome.switches", "--disable-extensions");
		//DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		//capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		//capabilities.setBrowserName("chrome");
		//driver=ThreadGuard.protect(new ChromeDriver(capabilities));
		driver= new FirefoxDriver();
		ThreadDriver.set(driver);
		test b = new test(driver,report);
		b.loginSymComm();
	}
	
	@FindBy(xpath ="//td[text()='ID']/following-sibling::td[@class='CwfFTDF']/input")
	private WebElement userName;
	
	@FindBy(xpath ="//td[text()='Password']/following-sibling::td[@class='CwfFTDF']/input")
	private WebElement passwordComm;
	
	@FindBy(xpath ="//input[@value='Login']")
	private WebElement btnLogin;
	//*[@id="CwSearch"]/div/table/tbody/tr[11]/td[5]/input
	
	public void pageSourceInaFile(String content){
		try {
			FileWriter writer = new FileWriter(TestUtils.getRelativePath() + "\\src\\test\\resources\\EligiliblityFeedFiles\\pageSourceFile");
			BufferedWriter buffWr = new BufferedWriter(writer);
			buffWr.write(content);
			buffWr.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
	
	public void loginSymComm(){
		try{
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
		}catch(Exception ex) {
			System.out.println();
		}
	}
	
	
	

}