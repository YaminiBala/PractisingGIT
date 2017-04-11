package com.comcast.bom.pages;

import java.awt.AWTException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.comcast.utils.DataTable;
import com.comcast.utils.SeleniumReport;
import com.comcast.utils.TestSettings;
import com.comcast.utils.TestUtils;
import com.google.common.base.Function;

/**
 * Base class for all the pages.
 * 
 */
public abstract class Page {
	protected WebDriver browser;
	protected SeleniumReport report;
	protected DataTable dataTable;
	protected TestSettings testSettings;
	protected String title;

	public static String currentWindow;

	long minimumTimeout = 2;
	long maximumTimeout = 60;

	/**
	 * Constructor for Page class
	 * 
	 * @param browser
	 * @param report
	 * 
	 */
	private void setTimeout() {
		TestSettings testSettings = new TestSettings();
		String strEnv = testSettings.getEnvironmentToTest();
		switch (strEnv.toUpperCase().trim()) {
		case "CI":
			maximumTimeout = 120;
			break;
		case "QA":
			maximumTimeout = 120;
			break;
		case "UAT":
			maximumTimeout = 60;
			break;
		default:
			maximumTimeout = 60;
			break;
		}
	}
	protected Page(SeleniumReport report) {
		setTimeout();
		this.report = report;

	}

	protected Page(WebDriver browser, SeleniumReport report) {
		setTimeout();
		this.browser = browser;
		this.report = report;
		this.testSettings = new TestSettings();
		PageFactory.initElements(browser, this);
	}

	protected Page(WebDriver browser, SeleniumReport report, TestSettings settings) {
		setTimeout();
		this.browser = browser;
		this.report = report;
		this.testSettings = settings;
		PageFactory.initElements(browser, this);
	}

	/**
	 * Check if the element is present in the page
	 * 
	 * @param element
	 *            WebElement need to check
	 * @return True if present
	 */
	public boolean isDisplayed(By by) {
		try {
			return browser.findElement(by).isDisplayed();
		} catch (NoSuchElementException ex) {
			return false;
		} catch (StaleElementReferenceException ex2) {
			return false;
		}
	}

	/**
	 * Check if the element is present in the page and report error
	 * 
	 * @param element
	 * @param errorMsg
	 *            error message need to report if the element not present
	 */
	protected void checkElementPresent(WebElement element, String value) {
		if (isDisplayed(element)) {
			report.reportPassEvent("VerifyPresenceof: " + value, "Element is present");
		} else {
			report.reportFailEvent("VerifyPresenceof: " + value, "Element is not present");
		}
	}

	/***
	 * Method to click on a link(WebElement button)
	 * 
	 * @param : Element Name
	 ***/
	public void clickOnButton(WebElement button) {
		if (isDisplayed(button)) {
			String btn = getText(button);
			click(button);
			waitForPageLoad();
			report.reportPassEvent("ClickOn" + btn, "Successfully clicked on button " + btn);
		} else {
			report.reportFailEvent("Click : " + button.toString(), button.toString() + " is NOT clicked successfully.");
		}
	}

	/**
	 * Method to click on a button(WebElement button)
	 * 
	 * @param : Element Name
	 */
	protected void jsClickButton(WebElement button) {
		String btnText = button.getText();
		try {
			if ((new TestSettings()).getBrowser().equalsIgnoreCase("iexplore")) {
				((JavascriptExecutor) browser).executeScript("arguments[0].click();", button);
				report.reportDoneEvent("ClickOnButton" + btnText, "Successfully clicked on button " + btnText);
			} else {
				button.click();
			}
		} catch (RuntimeException ex) {
			report.reportDoneEvent("Click" + btnText, btnText + " is NOT clicked successfully, EXCEPTION CAUGHT : " + ex.getMessage());
		}
	}

	// *****************************************************************************************************************//
	// TextBox
	// *****************************************************************************************************************//
	/***
	 * Method to enter text in a textbox
	 * 
	 * @param : Element Name
	 * @return :
	 * @author : Shilpa (sshank001c)
	 ***/
	public void enterText(WebElement element, String text) {
		if (isDisplayed(element)) {
			sendKeys(element, text);
			// element.sendKeys(Keys.ENTER);
			report.reportDoneEvent("Enter :" + text, "Successfully entered " + text);
		} else {
			report.reportFailEvent("Enter :" + text, "NOT able to enter text");
		}
	}

	// *****************************************************************************************************************//
	// Links
	// *****************************************************************************************************************//
	/***
	 * Method to click on a link(WebElement link)
	 * 
	 * @param : Element Name
	 * @author : Shilpa (sshank001c)
	 ***/
	public void clickOnLink(WebElement link) {
		try {
			isDisplayed(link);
			String linkText = link.getText();
			if (isDisplayed(link)) {
				// link.click();
				//jsClick(link);
				//click(link);
				javaScriptClick(link);
				report.reportDoneEvent("ClickOn" + linkText, "Successfully clicked on link " + linkText);
			}
		} catch (Exception ex) {
			report.reportFailEvent("ClickÖnLink", "Link is not displayed, EXCEPTION CAUGHT : " + ex.getMessage());
		}
	}

	public void clickOnElement(WebElement element, String reportStep) {
		try {
			click(element);
			report.reportDoneEvent(reportStep, "Clicked on element successfully");
		} catch (Exception ex) {
			report.reportFailEvent(reportStep, "Element is NOT clicked successfully, EXCEPTION CAUGHT : " + ex.getMessage());
		}
	}

	/**
	 * Method to click on a link(WebElement link)
	 * 
	 * @param : Element Name
	 */
	protected void jsClick(WebElement link) {
		try {
			String linkText = link.getText().trim();
			if ((new TestSettings()).getBrowser().equalsIgnoreCase("iexplore")) {
				((JavascriptExecutor) browser).executeScript("arguments[0].click();", link);
			} else if ((new TestSettings()).getBrowser().equalsIgnoreCase("chrome")) {
				((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", link);
				sleep(500);
				((JavascriptExecutor) browser).executeScript("arguments[0].click();", link);

			} else {
				link.click();

			}
		} catch (Exception ex) {
			report.reportFailEvent("ClickOnLink", "Unable to click on Link ->" + ex.getMessage());
		}
	}

	/**
	 * Method to sroll down to find element
	 * 
	 * @param : Element Name
	 */

	public Object scrollElementIntoView(WebElement element) {
		try {
			return ((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception Ex) {
			return null;
		}
	}
	
	public Object scrollElementIntoViewTop(WebElement element) {
		try {
			return ((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(false);", element);
		} catch (Exception Ex) {
			return null;
		}
	}

	/***
	 * Method to switch to child window
	 * 
	 * @param : parentWindow
	 ***/
	public String navigatoToWindow(String parentWindow, String pageName) {
		String pageTitle = null;
		try {
			Set<String> windows = browser.getWindowHandles();
			long lStartTimer = System.currentTimeMillis();
			while (windows.size() == 1) {
				windows = browser.getWindowHandles();
				long lEndTimer = System.currentTimeMillis();
				if (lEndTimer - lStartTimer > 20000) {
					break;
				}
			}
			for (String windowHandle : windows) {
				if (!windowHandle.equals(parentWindow)) {
					browser.switchTo().window(windowHandle);
					title = browser.getTitle();
					pageTitle = browser.getTitle();
					System.out.println("Page Title :" + title);
					browserMaximize();
					if (isAlertPresent()) {
						Alert alert = browser.switchTo().alert();
						alert.accept();
					}
					report.reportPassEvent("NavigateTo" + pageName, "Successfully navigated to " + pageName + " page");
					break;
				}
			}
		} catch (Exception ex) {
			browser.close();
			browser.switchTo().window(parentWindow);
		}
		return pageTitle;
	}
	
	public String navigatoToThirdWindow(String grandParentWindow,String parentWindow, String pageName) {
		String pageTitle = null;
		try {
			Set<String> windows = browser.getWindowHandles();
			long lStartTimer = System.currentTimeMillis();
			while (windows.size() == 2) {
				windows = browser.getWindowHandles();
				long lEndTimer = System.currentTimeMillis();
				if (lEndTimer - lStartTimer > 20000) {
					break;
				}
			}
			for (String windowHandle : windows) {
				if (!windowHandle.equals(parentWindow) && !windowHandle.equals(grandParentWindow)) {
					browser.switchTo().window(windowHandle);
					title = browser.getTitle();
					pageTitle = browser.getTitle();
					System.out.println("Page Title :" + title);
					browserMaximize();
					if (isAlertPresent()) {
						Alert alert = browser.switchTo().alert();
						alert.accept();
					}
					report.reportPassEvent("NavigateTo" + pageName, "Successfully navigated to " + pageName + " page");
					break;
				}
			}
		} catch (Exception ex) {
			browser.close();
			browser.switchTo().window(parentWindow);
			report.reportFailEvent("NavigateTo" + pageName,
					"Not able to navigate to " + pageName + " Exception caught :" + ex.getMessage());
		}
		return pageTitle;
	}
	

	/***
	 * Method to switch to child window
	 * 
	 * @param : parentWindow
	 ***/
	public void closeParentWindowNavigateToChildWindow(String parentWindow, String pageName) {
		try {
			sleep(5000);
			Set<String> handles = browser.getWindowHandles();
			browser.close();
			sleep(2000);
			for (String windowHandle : handles) {
				if (!windowHandle.equals(parentWindow)) {
					browser.switchTo().window(windowHandle);
					browserMaximize();
					sleep(5000);
					break;
				}
			}
		} catch (RuntimeException ex) {
		}
	}

	/***
	 * Method to switch to parent window
	 * 
	 * @param : parentWindow
	 ***/
	public void navigatoToParentWindow(String parentWindow) {
		browser.switchTo().window(parentWindow);
	}

	/***
	 * Method to navigate to default content
	 * 
	 * @param :
	 * @return :
	 * @author : Shabana (sshaik002c) Modified By :
	 ***/
	public void navigateToDefaultContent() {
		browser.switchTo().defaultContent();
	}

	// *****************************************************************************************************************//
	// Alert pop ups
	// *****************************************************************************************************************//
	/***
	 * Method to accept and close alert and return the text within the alert
	 * 
	 * @param :
	 * @return :
	 * @author : Shravanth (sarago001c) Modified By :
	 ***/
	public String closeAlertAndReturnText() {
		String alertMessage = null;
		try {
			if(waitForAlert()){
				int count =0;
				while(isAlertPresent()) {
					count++;
					alertMessage = alert.getText();
					System.out.print("\nMessage: " + alertMessage);
					report.reportPassEvent("Alert Pop up","Alert displayed with message : " + alertMessage);
					alert.accept();
					if(count > 5) {
						break;
					}
				}
			}
		} catch (Exception e) {
			report.reportFailEvent("Alert Pop up", "Authentication pop is not Identified");
		}
		return alertMessage;
	}

	private Alert alert;
	public boolean isAlertPresent(){
	    try{
		alert = browser.switchTo().alert();
//		report.updateTestLog("Alert","Alert",Status.SCREENSHOT);
		
		//		    a.sendKeys("1");
		return true;
	    }//try
	    catch(Exception e){
		return false;
	    }//catch
	}
	/***
	 * Method to accept and close alert and return the text within the alert
	 * 
	 * @param :
	 * @return :
	 * @author : Shravanth (sarago001c) Modified By :
	 ***/
	public String closeAlertAndReturnTextshort() {
		String alertMessage = null;
		try {
			WebDriverWait wait = new WebDriverWait(browser, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = browser.switchTo().alert();
			alertMessage = alert.getText();
			report.reportPassEvent("Alert Message Check", "Alert Message Successfully displayed as :'" + alertMessage+"'");
			alert.accept();
		} catch (Exception Ex) {

		}
		return alertMessage;
	}

	/***
	 * Method to accept and close alert and compare returned text within the
	 * alert
	 * 
	 * @param :
	 * @return :
	 * @author : Shilpa(266022) Modified By :
	 ***/
	public String closeAlertAndCompareReturnText(String expectedValue) {
		String alertMessage = null;
		WebDriverWait wait = new WebDriverWait(browser, 20);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = browser.switchTo().alert();
		alertMessage = alert.getText();
		if (expectedValue.contains(alertMessage)) {
			report.reportPassEvent("alertMessage", "alertMessage is displayed as expected :" + alertMessage);
		} else {
			report.reportFailEvent("alertMessage", "alertMessage is not displayed as expected :" + alertMessage);
		}
		alert.accept();
		return alertMessage;
	}

	// *****************************************************************************************************************//
	// waits
	// *****************************************************************************************************************//

	/**
	 * Method to wait for element to load in the page
	 * 
	 * @param WebElement
	 */
	protected Boolean waitForElement(By by) {
		try {
			new WebDriverWait(browser, 90).until(ExpectedConditions.presenceOfElementLocated(by));

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	protected void waitForNewSecondWindow() {
		try {
			int count = 0;
			Set<String> windows = browser.getWindowHandles();
			while (count < 40) {
				windows = browser.getWindowHandles();
				if (windows.size() > 1) {
					report.reportPassEvent("VerifyNewWindowOpened", "New Window opened successfully");
					break;
				} else {
					sleep(2000);
					count++;
				}
			}
			if (count >= 40) {
				report.reportFailEvent("VerifyNewWindowOpened", "New Window not opened");
			}
		} catch (Exception Ex) {
			report.reportFailEvent("VerifyNewWindowOpened", "New Window not opened");
		}
	}

	/**
	 * Method to wait for element to load in the page
	 * 
	 * @param WebElement
	 */

	protected Boolean waitForElement(WebElement we) {
		return isDisplayed(we);
	}

	/***
	 * Method to search customer with the invalid Combinations
	 * 
	 * @param :
	 * @return :
	 * @author : Kumar (kpokha001c)
	 * @throws
	 ***/
	protected void isPresent(WebElement we) {
		WebDriverWait wait = new WebDriverWait(browser, 10);
		try {
			wait.until(ExpectedConditions.visibilityOf(we));
			report.reportPassEvent("HomePage::" + we.getAttribute("innerHTML") + " Object Verification", "The object " + we.getAttribute("innerHTML") + " is present.");
		} catch (NoSuchElementException nse) {
			report.reportDoneEvent("HomePage::" + we.getAttribute("innerHTML") + " Object Verification", "The object " + we.getAttribute("innerHTML") + " is NOT present.");
			// System.out.println("" + we + " No such element");
		} catch (TimeoutException toe) {
			report.reportDoneEvent("HomePage::" + we.getAttribute("innerHTML") + " Object Verification", "The object " + we.getAttribute("innerHTML") + " is NOT present.");
			// System.out.println("" + we + " TimeOut");
		}

	}

	/**
	 * Method to wait for Alert present in the page
	 * 
	 * @param
	 */

	protected Boolean waitForAlert() {
		try {
			new WebDriverWait(browser, 20).until(ExpectedConditions.alertIsPresent());
		//	report.reportPassEvent("Alert Pop up","Alert displayed");
		//	report.updateTestLog("Alert","Alert",Status.SCREENSHOT);
			return true;
		} catch (Exception Ex) {
			return false;
		}
	}

	// *****************************************************************************************************************//
	// General
	// *****************************************************************************************************************//
	/***
	 * Method to check if element is enabled(element_name)
	 * 
	 * @param : Element Name
	 * @return :
	 * @author : Shabana (sshaik002c)
	 ***/
	public void isElementEnabled(WebElement element) {
		try {
			if (isDisplayed(element)) {
				if (element.isEnabled()) {
					report.reportPassEvent("IsElementEnabled", element.getText() + "is enabled");
				} else {
				}

			}
		} catch (RuntimeException ex) {

		}
	}

	/***
	 * Method to check if element is enabled(element_name) with a boolean return
	 * 
	 * @param : Element Name
	 * @return : Boolean
	 * @author : Shabana (sshaik002c) Parity for E360: Indu Murthy (imurth002c)
	 ***/
	public boolean isElementEnabledwithBooleanReturn(WebElement element) {
		waitForElement(element);
		if (element.isEnabled()) {
			report.reportDoneEvent("ElementIsEnabled", element.getText() + "is enabled");
			return true;
		} else {
			report.reportDoneEvent("ElementIsNotEnabled", element.getText() + "is not enabled");
			return false;
		}

	}

	/***
	 * Method to check if element is disabled(element_name)
	 * 
	 * @param : Element Name
	 * @return :
	 * @author : Shabana (sshaik002c) Modified By :
	 ***/
	public void isElementDisabled(WebElement element) {
		try {
			if (isDisplayed(element)) {
				if (element.isEnabled()) {
				} else {
					report.reportPassEvent("isElementDisabled", element.getText() + "is disabled");
				}

			}
		} catch (RuntimeException ex) {
		}
	}

	protected void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Method to Alter Today's date to specified date
	 * 
	 * @param : Element Name
	 * @return :
	 * @author : Omkar (oraghu001c) Modified By :
	 ***/
	public Date getAlteredDate(Integer NoOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, NoOfDays);
		return cal.getTime();
	}
	
	public String getAlteredDateByFromDate(Integer NoOfDays, String dateFromUI) {
		Calendar cal = null;
		String date3=null;
		try {
			DateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy");
			DateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			Date date2;

			date2 = sdf.parse(dateFromUI);
			System.out.println("date2::" + date2);
			cal = Calendar.getInstance();
			cal.setTime(date2);
			cal.add(Calendar.DATE, NoOfDays);
			Date date1 = cal.getTime();
			System.out.println("cal.getTime::" + date1);
			date3 = sdf1.format(date1);
			System.out.println("date3::" + date3);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date3;
	}

	/***
	 * Method to Switch window and return URL of window
	 * 
	 * @param : Element Name
	 * @return : Browser URL
	 * @author : Omkar (oraghu001c) Modified By :
	 ***/
	public String switchtowindow(String window) {
		browser.switchTo().window(window);
		return browser.getCurrentUrl();
	}

	/***
	 * Method to hover on a link(WebElement link)
	 * 
	 * @param : Element Name
	 * @author : Shilpa (sshank001c)
	 ***/
	public void hooverOnLink(WebElement link) {
		String linkText = link.getText();
		try {
			waitForElement(link);
			if (isDisplayed(link)) {
				Actions action = new Actions(browser);
				action.moveToElement(link);
				action.perform();
				this.sleep(2);
			}
		} catch (RuntimeException ex) {
			report.reportDoneEvent("HoverOn" + linkText, linkText + " is NOT hovered successfully, EXCEPTION CAUGHT : " + ex.getMessage());
		}
	}

	/**
	 * Check if the element is present in the page
	 * 
	 * @param element
	 *            WebElement need to check
	 * @return True if present
	 * @author : Shilpa (sshank001c)
	 */
	protected void isElementPresent_Report(WebElement element) {
		if (isDisplayed(element)) {
			report.reportPassEvent("CheckElementPresent", element.getText() + ": is present");
		} else {
			report.reportFailEvent("CheckElementPresent : " + element, "Element is not present");
		}
	}

	/***
	 * Method to Verify the selectComboOptions
	 * 
	 * @param :
	 * @return :
	 * @author : Shilpa (sshank001c) Modified By :
	 * @throws InterruptedException
	 ***/

	public void selectComboOptions(WebElement selObj, String[] expOption) {
		try {
			Select sel = new Select(selObj);
			String Flag = null;
			Flag = "true";
			for (int i = 0; i < sel.getOptions().size(); i++) {
				System.out.println(sel.getOptions().get(i).getText());
				if (sel.getOptions().get(i).getText().trim().equalsIgnoreCase(expOption[i])) {
					Flag = "true";
				} else {
					Flag = "False";
					report.reportDoneEvent("VerifyDropdown :" + expOption[i], "Dropdown does not have the value ->" + expOption[i]);
				}
			}

			if (Flag == "False") {
				report.reportDoneEvent("selectComboOptions :", "Verification Failed some values missing");
			}
		} catch (NoSuchElementException ex) {
			report.reportFailEvent("selectComboOptions :", ex.getMessage());

		}
	}

	/**
	 * To get the warning message and compare it with the actual message and
	 * close the warning
	 * 
	 * @param warningMsg
	 *            WebElement need to check and compare with String expectedMsg
	 * @return True if present
	 * @author : Namitha (Nnaray003c)
	 */
	protected void closeWarningAndCompareReturnText(WebElement warningMsg, String expectedMsg, WebElement warningMsgClose) {
		try {
			String actualWarningMsg = null;
			//waitForElement(warningMsg);
			isDisplayed(warningMsg);
			actualWarningMsg = getText(warningMsg);
			if (actualWarningMsg.contains(expectedMsg)) {
				report.reportPassEvent("WarningMsgDisplayedAsExpected", warningMsg.getText() + "Msg Displayed As Expected");
				//report.updateTestLog("WarningMsgDisplayedAsExpected", warningMsg.getText() + "Msg Displayed As Expected", Status.SCREENSHOT);
			} else {
			}
			click(warningMsgClose);

		} catch (NoSuchElementException ex) {
			report.reportFailEvent("WarningMsg_NotDisplayed", warningMsg.getText() + ": is not present" + ex.getMessage());

		}
	}

	/***
	 * Method to close tool kit
	 * 
	 * @param :
	 * @return :
	 * @author : Shabana Shaik(sshaik002c) Modified By :
	 ***/
	
	/***
	 * Method to close back end service name
	 * 
	 * @param :
	 * @return :
	 * @author : Shabana Shaik(sshaik002c) Modified By :
	 ***/
	public String closeBackEndServiceFrame() {
		String message = null;
		if (isDisplayed(browser.findElement(By.tagName("iframe")))) {
			String frameName = browser.findElement(By.tagName("iframe")).getAttribute("name");
			System.out.println(frameName);
			browser.switchTo().frame(frameName);
			isDisplayed(browser.findElement(By.id("lblMessage")));
			message = getText(browser.findElement(By.id("lblMessage")));
			System.out.println(message);
			do {
				browser.findElement(By.id("Button2")).sendKeys(Keys.ENTER);
			} while (isDisplayed(browser.findElement(By.xpath("//iframe[@name='" + frameName + "']"))));

			// browser.switchTo().frame(browser.findElement(By.xpath("//iframe[contains(@name,'RadWindowManager')]")));
			// browser.findElement(By.xpath("//*[@name='CloseBtn']")).click();
			browser.switchTo().defaultContent();
		}

		return message;
	}

	//The password for the selected user has been reset to
	@FindBy(id = "lblMessage")
	private WebElement lblMessage;
	
	@FindBy(id = "Button2")
	private WebElement button2;
	
	@FindBy(id = "CloseBtn")
	private WebElement CloseBtn;
	/***
	 * Method to close frame with a message and check the message
	 * 
	 * @param :
	 * @return :
	 * @author : Shravanth(sarago001) Modified By :
	 ***/
	@FindBy(tagName = "iframe")
	private WebElement iframe;
	
	//Maximum number of Password Changes has been Exceeded.
	@FindBy(id = "MessageLabel")
	private WebElement MessageLabel;
	
	
	@FindBy(xpath ="//iframe[contains(@name,'ResetDotcomPasswordRadWindowManager')]")
	private WebElement SuccessPasswordFrame;
	
	public void closeBackEndServiceFrameAndCheckMessage(String expMessage) {
		try
		{
		String message = null;
		if (isDisplayed(iframe)) {
			String frameName = iframe.getAttribute("name");
			System.out.println(frameName);
			browser.switchTo().frame(frameName);
			if (isDisappeared(lblMessage)) {
				isDisplayed(lblMessage);
			}
			int found = isDisplayed(lblMessage,MessageLabel);
			if(found ==1)
			message = getText(lblMessage);
			else
				message = getText(MessageLabel); 
			System.out.println(message);
			if (message.contains(expMessage) ) {
				click(button2);
				report.reportPassEvent("Check Message", "Messsage is as expected: actual Message:" + message);
			}else if (message.contains("Maximum number of password changes have been exceeded")) {
					click(CloseBtn);
					report.reportPassEvent("Check Message", "Messsage is as expected: actual Message:" + message);
			}else {
				report.reportFailEvent("Check Message", "Messsage is not as expected: actual Message:" + message);
			}
			report.reportPassEvent("CloseBackEndServicePop", "Successfully closed back end service pop up");
			browser.switchTo().defaultContent();
		} else
			report.reportFailEvent("Verify success message", "Success message is not displayed.");
		}catch(Exception ex)
		{
			System.out.println();
		}
		
	}

	/***
	 * Method to Enter text in the field specified
	 * 
	 * @param :
	 * @return :
	 * @author : Shravanth PAV (sarago001) Modified By :
	 ***/
	public void sendKeys(WebElement we, String fieldName, String textToEnter) {
		try {
			sendKeys(we, textToEnter);
			report.reportPassEvent("Enter data to field:" + fieldName, "Data entered:" + textToEnter);
		} catch (Exception re) {
			report.reportFailEvent("Enter data to field:" + fieldName, "Failed entering data." + re.getMessage());
		}

	}

	/***
	 * Method to click an element to open another element
	 * 
	 * @param : Element Name
	 * @return :
	 * @author : Shravanth (sarago001c) Modified By :
	 ***/
	public void clickToOpen(WebElement element, By elementToBeVisible) {
		try {
			waitForElement(element);
			int count = 0;
			do {
				if (count > 50)
					break;
				if (count % 2 == 0) {
					element.click();
					sleep(2500);
				} else {
					jsClick(element);
					sleep(2500);
				}
				count = count + 1;
			} while (!isDisplayed(elementToBeVisible));
		} catch (RuntimeException ex) {
			report.reportFailEvent("Click on element", "Cannot click on element");
		}
	}

	/***
	 * Method to click and close an element
	 * 
	 * @param : Element Name
	 * @return :
	 * @author : Shravanth (sarago001c) Modified By :
	 ***/
	public void clickToClose(WebElement element) {
		try {
			waitForElement(element);
			int count = 0;
			while (isDisplayed(element)) {
				if (count > 50) {
					break;
				} else {
					if (count % 2 == 0) {
						element.click();
						sleep(500);
					} else {
						jsClickButton(element);
						sleep(500);
					}
				}
				count = count + 1;
			}

		} catch (RuntimeException ex) {
			System.out.println("Cannot click on element");
		}
	}

	public String getXPath(WebElement webElement) {
		String jscript = "function getXPath(node) {" + "var comp, comps = [];" + "var parent = null;" + "var xpath = '';" + "var getPos = function(node) {" + "var position = 1, curNode;" + "if (node.nodeType == Node.ATTRIBUTE_NODE) {"
				+ "return null;" + "}" + "for (curNode = node.previousSibling; curNode; curNode = curNode.previousSibling) {" + "if (curNode.nodeName == node.nodeName) {" + "++position;" + "}" + "}" + "return position;" + "}"
				+ "if (node instanceof Document) {" + "return '/';" + "}" + "for (; node && !(node instanceof Document); node = node.nodeType == Node.ATTRIBUTE_NODE ? node.ownerElement : node.parentNode) {" + "comp = comps[comps.length] = {};"
				+ "switch (node.nodeType) {" + "case Node.TEXT_NODE:" + "comp.name = 'text()';" + "break;" + "case Node.ATTRIBUTE_NODE:" + "comp.name = '@' + node.nodeName;" + "break;" + "case Node.PROCESSING_INSTRUCTION_NODE:"
				+ "comp.name = 'processing-instruction()';" + "break;" + "case Node.COMMENT_NODE:" + "comp.name = 'comment()';" + "break;" + "case Node.ELEMENT_NODE:" + "comp.name = node.nodeName;" + "break;" + "}" + "comp.position = getPos(node);"
				+ "}" + "for (var i = comps.length - 1; i >= 0; i--) {" + "comp = comps[i];" + "xpath += '/' + comp.name;" + "if (comp.position != null) {" + "xpath += '[' + comp.position + ']';" + "}" + "}" + "return xpath;}";
		return (String) ((JavascriptExecutor) browser).executeScript(jscript, webElement);
	}

	/**
	 * Click On Help Button an any Page and verify its Content
	 * 
	 * @param HelpButton
	 *            to click, ContentVerification to get a Text form HelpPage,
	 *            ContentText to verify if text matches with text displayed in
	 *            HelpPage
	 * @return : void
	 * @author : Omkar (oraghu001c)
	 */

	
	/***
	 * Method to close frame with a message and check the message
	 * 
	 * @param :
	 * @return :
	 * @author : Shravanth(sarago001) Modified By :
	 ***/
	public void closeFrame() {

		String frameName = browser.findElement(By.tagName("iframe")).getAttribute("name");
		System.out.println(frameName);
		browser.switchTo().frame(frameName);
		do {
			jsClick(browser.findElement(By.id("CloseBtn")));
		} while (isDisplayed(By.xpath("//iframe[@name='" + frameName + "']")));

		// browser.switchTo().frame(browser.findElement(By.xpath("//iframe[contains(@name,'RadWindowManager')]")));
		// browser.findElement(By.xpath("//*[@name='CloseBtn']")).click();
		report.reportDoneEvent("CloseBackEndServicePop", "Successfully closed back end service pop up");
		browser.switchTo().defaultContent();

	}


	/***
	 * Method to handle multiple windows
	 * 
	 * @param :
	 * @return :
	 * @author : Shravanth(sarago001c) Modified By :
	 ***/
	public void handleMultipleWindowsNew(String windowTitle) {
		//sleep(5000);
		Set<String> windows = browser.getWindowHandles();
		for (String window : windows) {
			browser.switchTo().window(window);
			if (browser.getTitle().contains(windowTitle)) {
				//sleep(5000);
				System.out.print("\nWindow switched to " + browser.getTitle());
				report.reportPassEvent("Switch window", "Window switched to " + browser.getTitle());
				break;
			} // else report.reportFailEvent("Switch window",
				// "Problem opening new window");
		}
	}

	/***
	 * Method to compare arrays
	 * 
	 * @param :
	 * @return :
	 * @author : Facundo (ftolos001c) Modified By :
	 ***/

	public void compareArrays(List<String> A, List<String> B) {
		try {
			Assert.assertNotNull(A);
			Assert.assertNotNull(B);
			Assert.assertTrue(A.containsAll(B));
			Assert.assertTrue(A.size() == B.size());
			report.reportPassEvent("Compare elements", "All elements are correct");
		} catch (Throwable e) {
			report.reportFailEvent("Error Comparing the elements", "Error comparing elements: " + e + "Expected: " + B + " Actual: " + A);
		}

	}

	/***
	 * Method to compare arrays
	 * 
	 * @param :
	 * @return :
	 * @author : Facundo (ftolos001c) Modified By :
	 ***/

	public boolean compareStrings(String actual, String expected) {
		boolean result = false;
		try {
			if (actual.equalsIgnoreCase(expected)) {
				result = true;
			} else
				report.reportDoneEvent("Compare text", "Text displayed is not the expected. Expected: " + expected + " Actual: " + actual);
		} catch (RuntimeException e) {
			report.reportFailEvent("Problem during execution", "Exception found: " + e);
		}
		return result;
	}

	/***
	 * Method to get Current Date
	 * 
	 * @param :
	 * @return : Nill
	 * @author : kpokha001c Modified By :
	 * 
	 ***/
	public String getCurrentDate() {
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("MMM dd, yyyy");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			return strDate;
		} catch (Exception ex) {
			report.reportFailEvent("getCurrentDate", "Probelm during execution" + ex.getMessage());
		}
		return null;
	}

	/***
	 * Method to get Previous Date
	 * 
	 * @param : days to deduct from current date
	 * @return : Nill
	 * @author : kpokha001c Modified By :
	 * 
	 ***/
	public String getPreviousDaysDate(int minusDays) {
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("MMM dd, yyyy");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, Integer.parseInt("-" + minusDays));
			String strDate = sdfDate.format(cal.getTime());
			return strDate;
		} catch (Exception ex) {
			report.reportFailEvent("getCurrentDate", "Probelm during execution" + ex.getMessage());
		}
		return null;
	}

	public void selectElementsFromDd(WebElement element, String visibileText) {
		Select select = new Select(element);
		select.selectByVisibleText(visibileText);
	}

	
	/***
	 * Method to enterCredentialsThruRobotClass
	 * 
	 * @param :toolKitName
	 * @return :
	 * @author : kpokha001c
	 * @throws AWTException
	 ***/
	public void switchToNewWindow(String parentWindowHandle) throws AWTException {
		sleep(5000);
		boolean switched = false;
		Set<String> handles = browser.getWindowHandles();
		for (String windowHandle : handles) {
			if (!(windowHandle.equals(parentWindowHandle))) {
				browser.switchTo().window(windowHandle);
				browserMaximize();
				switched = true;
				System.out.println("Window Switched");
				break;
			}
		}
		if(!switched)
			report.reportFailEvent("Switch to new window", "Could not switch to new Window");
	}

	/***
	 * Method to enterCredentialsThruRobotClass
	 * 
	 * @param :toolKitName
	 * @return :
	 * @author : kpokha001c
	 * @throws AWTException
	 ***/
	public void closeChildAndSwitchToParentWindow(String parentWindowHandle) throws AWTException 
	{
		browser.close();
		Set<String> handles = browser.getWindowHandles();
		for (String windowHandle : handles) {
			if ((windowHandle.equals(parentWindowHandle))) {
				browser.switchTo().window(windowHandle);
				browserMaximize();
				break;
			}
		}
	}
	
	public boolean isWifiRouterPresent() {
		try {
			//WebDriverWait wait = new WebDriverWait(browser, 5);
			isDisplayed(browser.findElement(By.xpath("//*[@id='divAccountIndicators']/div[1]/span[2]")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Adding as part of clean up work --- starting from June 3rd 2016
	private WebElement fluentWait(final WebElement webElement, long lngTimeout) {
		WebElement element = null;
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(browser).withTimeout(lngTimeout, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS).ignoring(Exception.class);
//			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
//				public Boolean apply(WebDriver driver) {
//					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//				}
//			};

			try {
//				wait.until(pageLoadCondition);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				element = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						System.out.println("Waiting in fluentWait : " + webElement);
						if (webElement.isDisplayed())
							return webElement;
						else
							return null;
					}
				});
			} catch (Exception e) {
			}

			if (element != null) {
				/*((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(false);", element);
				sleep(500);*/
//				TestSettings testSettings1 = new TestSettings();
//				if(!testSettings1.getBrowser().equals("iexplore")){
//					((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(false);", element);
//					sleep(500);
//				}
				/*Actions actions = new Actions(browser);
				actions.moveToElement(element);
				actions.perform();*/
				JavascriptExecutor je = (JavascriptExecutor) browser;
				int clientHeight = browser.manage().window().getSize().height;
				int elementPos = element.getLocation().y + (element.getSize().height/2);
				int scrollTo = elementPos - (clientHeight / 2);
				je.executeScript("window.scrollTo(0, " + scrollTo + ");");
			}
		} catch (Exception e) {
			throw e;
		}
		return element;
	}

	public void click(WebElement element) {
		System.out.println("\n***********************************************");
		System.out.println("click");
		System.out.println("***********************************************");

		try {
			fluentWait(element, maximumTimeout).click();
		} catch (Exception e) {
			try {
				javaScriptClick(element);
			} catch (Exception e1) {
				throw e;
			}
		}
	}

	public void javaScriptClick(WebElement element) {
		System.out.println("\n***********************************************");
		System.out.println("javaScriptClick");
		System.out.println("***********************************************");

		String linkText = element.getText().trim();
		((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", element);
		sleep(500);
		((JavascriptExecutor) browser).executeScript("arguments[0].click();", element);
	}

	public boolean isEnabled(WebElement element) {
		System.out.println("\n***********************************************");
		System.out.println("isEnabled");
		System.out.println("***********************************************");

		boolean blnFlag = false;

		try {
			if (fluentWait(element, maximumTimeout).isEnabled() == true)
				blnFlag = true;
			else
				blnFlag = false;
		} catch (Exception e) {
			blnFlag = false;
		}
		return blnFlag;
	}

	public boolean isDisabled(WebElement element) {
		System.out.println("\n***********************************************");
		System.out.println("isDisabled");
		System.out.println("***********************************************");

		boolean blnFlag = false;

		try {
			if (fluentWait(element, maximumTimeout).isEnabled() == false)
				blnFlag = true;
			else
				blnFlag = false;
		} catch (Exception e) {
			blnFlag = false;
		}
		return blnFlag;
	}

	public boolean isDisplayed(WebElement element) {
		System.out.println("\n***********************************************");
		System.out.println("isDisplayed");
		System.out.println("***********************************************");

		boolean blnFound = false;

		try {
			fluentWait(element, maximumTimeout).isDisplayed();
			blnFound = true;
		} catch (Exception e) {
			blnFound = false;
		}
		return blnFound;
	}

	public int isDisplayed(WebElement element1, WebElement element2) {
		System.out.println("\n***********************************************");
		System.out.println("isDisplayed");
		System.out.println("***********************************************");

		int iFound = 0;

		try {
			long lngCurrentTime = System.currentTimeMillis();
			long lngEndTime = lngCurrentTime + (maximumTimeout * 1000);

			while (lngCurrentTime <= lngEndTime) {
				try {
					fluentWait(element1, minimumTimeout).isDisplayed();
					iFound = 1;
					break;
				} catch (Exception e1) {
					try {
						fluentWait(element2, minimumTimeout).isDisplayed();
						iFound = 2;
						break;
					} catch (Exception e2) {
						lngCurrentTime = System.currentTimeMillis();
						if (lngCurrentTime > lngEndTime) {
							throw e2;
						}
					}
				}
			}
		} catch (Exception e) {
			iFound = 0;
		}
		return iFound;
	}

	public int isDisplayed(WebElement element1, WebElement element2, WebElement element3) {
		System.out.println("\n***********************************************");
		System.out.println("isDisplayed");
		System.out.println("***********************************************");

		int iFound = 0;

		try {
			long lngCurrentTime = System.currentTimeMillis();
			long lngEndTime = lngCurrentTime + (maximumTimeout * 1000);

			while (lngCurrentTime <= lngEndTime) {
				try {
					fluentWait(element1, minimumTimeout).isDisplayed();
					iFound = 1;
					break;
				} catch (Exception e1) {
					try {
						fluentWait(element2, minimumTimeout).isDisplayed();
						iFound = 2;
						break;
					} catch (Exception e2) {
						try {
							fluentWait(element3, minimumTimeout).isDisplayed();
							iFound = 3;
							break;
						} catch (Exception e3) {
							lngCurrentTime = System.currentTimeMillis();
							if (lngCurrentTime > lngEndTime) {
								throw e3;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			iFound = 0;
		}
		return iFound;
	}

	public boolean isDisplayed(WebElement element, long lngTimeout) {
		System.out.println("\n***********************************************");
		System.out.println("isDisplayed");
		System.out.println("***********************************************");

		boolean blnFound = false;

		try {
			fluentWait(element, lngTimeout).isDisplayed();
			blnFound = true;
		} catch (Exception e) {
			blnFound = false;
		}
		return blnFound;
	}

	public boolean isDisappeared(WebElement element) {
		System.out.println("\n***********************************************");
		System.out.println("isDisappeared");
		System.out.println("***********************************************");

		boolean blnDisappeared = false;
		long lngCurrentTime = System.currentTimeMillis();
		long lngEndTime = lngCurrentTime + (maximumTimeout * 1000);

		while (lngCurrentTime <= lngEndTime) {
			if (isDisplayed(element, minimumTimeout)) {
				lngCurrentTime = System.currentTimeMillis();
			} else {
				blnDisappeared = true;
				break;
			}
		}
		return blnDisappeared;
	}

	public void sendKeys(WebElement element, String text) {
		System.out.println("\n***********************************************");
		System.out.println("sendKeys");
		System.out.println("***********************************************");

		try {
			element = fluentWait(element, maximumTimeout);
			element.clear();
			if (text.trim().length() > 0) {
				element.sendKeys(text);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void selectField(WebElement element, String Value) {
		System.out.println("\n***********************************************");
		System.out.println("selectField");
		System.out.println("***********************************************");

		try {
			WebElement selElement = fluentWait(element, maximumTimeout);
			Select obj = new Select(selElement);
			obj.selectByVisibleText(Value);
		} catch (Exception e) {
			throw e;
		}
	}

	public String getText(WebElement element) {
		System.out.println("\n***********************************************");
		System.out.println("getText");
		System.out.println("***********************************************");

		String strText = null;

		try {
			strText = fluentWait(element, maximumTimeout).getText();
		} catch (Exception e) {
			throw e;
			
		}
		return strText;
	}
	
	public HashMap<String,String> convertDatetoUTC(String UIDate) throws ParseException
    {
		
		HashMap<String, String> Utc = new HashMap<>();
		Date date = new Date();
		final String DATE_FORMAT = "MMM dd, yyyy hh:mm a zzz";
		final SimpleDateFormat cdtSdf = new SimpleDateFormat(DATE_FORMAT);
		date = cdtSdf.parse(UIDate);
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date);
		cal2.setTime(date);
		cal1.add(Calendar.MINUTE, 1);    //adding a minute to date to include boundary values
		cal2.add(Calendar.MINUTE, -1);		//Subtracting a minute to date to include boundary values
		final SimpleDateFormat utcSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		final TimeZone utc = TimeZone.getTimeZone("UTC");
		utcSdf.setTimeZone(utc);
		String dateutc1 = utcSdf.format(cal1.getTime());
		String dateutc2 = utcSdf.format(cal2.getTime());
		Utc.put("fromdate", dateutc2);
		Utc.put("todate", dateutc1);
		System.out.println(Utc.get("fromdate"));
		System.out.println(Utc.get("todate"));
		
		return Utc;
    }

	public int isEnabled(WebElement element1, WebElement element2) {
		System.out.println("\n***********************************************");
		System.out.println("isEnabled");
		System.out.println("***********************************************");

		int iFound = 0;

		try {
			long lngCurrentTime = System.currentTimeMillis();
			long lngEndTime = lngCurrentTime + (maximumTimeout * 1000);

			while (lngCurrentTime <= lngEndTime) {
				try {
					fluentWait(element1, minimumTimeout).isEnabled();
					iFound = 1;
					break;
				} catch (Exception e1) {
					try {
						fluentWait(element2, minimumTimeout).isEnabled();
						iFound = 2;
						break;
					} catch (Exception e2) {
						lngCurrentTime = System.currentTimeMillis();
						if (lngCurrentTime > lngEndTime) {
							throw e2;
						}
					}
				}
			}
		} catch (Exception e) {
			iFound = 0;
		}
		return iFound;
	}
	
	public void selectComboOptionstovalidateDate(List<WebElement> selObj, String[] expOption) {
		try {
			//Select sel = new Select(selObj);
			String Flag = null;
			Flag = "true";
			for (int i = 0; i < selObj.size(); i++) {
				System.out.println(selObj.get(i).getText());
				if (selObj.get(i).getText().trim().equalsIgnoreCase(expOption[i])) {
					Flag = "true";
				} else {
					Flag = "False";
					report.reportDoneEvent("VerifyDropdown :" + expOption[i], "Dropdown does not have the value ->" + expOption[i]);
				}
			}

			if (Flag == "False") {
				report.reportDoneEvent("selectComboOptions :", "Verification Failed some values missing");
			}
		} catch (NoSuchElementException ex) {
			report.reportFailEvent("selectComboOptions :", ex.getMessage());

		}
	}
	
	public static HashMap<String,String> convertUIDatetoUTC(String UIDate) throws ParseException
    {
		
		HashMap<String, String> Utc = new HashMap<>();
		Date date = new Date();
		final String DATE_FORMAT = "MMM dd, yyyy, hh:mm a zzz";
		final SimpleDateFormat cdtSdf = new SimpleDateFormat(DATE_FORMAT);
		date = cdtSdf.parse(UIDate);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		cal1.add(Calendar.MINUTE, 1);    //adding a minute to date to include boundary values
		
		final SimpleDateFormat utcSdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		final TimeZone utc = TimeZone.getTimeZone("UTC");
		utcSdf.setTimeZone(utc);
		String dateutc1 = utcSdf.format(cal1.getTime());
		Utc.put("todate", dateutc1);
		System.out.println(Utc.get("todate"));
		
		return Utc;
    }

	public String getAccountContextIDFromPageSource() {
		String strSource = browser.getPageSource();
		String strBeginText = "CPE.AccountContextID";
		String strEndText = ";";
		String strAccountContextID = "";
		if (strSource.contains(strBeginText)) {
			int iBegin = strSource.indexOf(strBeginText);
			int iEnd = strSource.indexOf(strEndText, iBegin);
			strAccountContextID = strSource.substring(iBegin + strBeginText.length(), iEnd);
			strAccountContextID = strAccountContextID.replace(" ", "");
			strAccountContextID = strAccountContextID.replace("=", "");
			strAccountContextID = strAccountContextID.replace("'", "");
		}
		return strAccountContextID;
	}

	
	@FindBy(xpath = "//*[contains(text(),'This page is used to hold your data while you are being authorized for your request.')]")
	private WebElement secondWindowAuthPopup;

	

	public void handleCertificateError() {
		try {
			testSettings = new TestSettings();
			String browserName = testSettings.getBrowser();
			if (browserName.equalsIgnoreCase("iexplore")) {
				browser.navigate().to("javascript:document.getElementById('overridelink').click()");
			} else if (browserName.equalsIgnoreCase("chrome")) {
				browser.navigate().to("javascript:document.getElementById('overridelink').click()");
			}
		} catch(Exception ex) {
		}
	}
	
	/***
	 * Method to enter credentials to window login popup
	 * 
	 * @param :loginInfo
	 * @author : kpokha001c
	 * @throws IOException
	 ***/
	
	
	@FindBy(xpath = "//*[@class='x2-loading-indicator']")
	private WebElement loadingIndicator_Dotted;

	@FindBy(xpath = "(//div[@class='lob-status LOB_status_LoadingImage'])[1]")
	private WebElement loadingIndicator_LOBs;

	@FindBy(xpath="//img[@alt='Loading...']")
	private WebElement loadingIndicator_Circle;
	
	public void waitForPageLoad() {
		for (int i = 0; i < 2; i++) {
			boolean load1 = isDisappeared(loadingIndicator_Dotted);
			boolean load2 = isDisappeared(loadingIndicator_LOBs);
			boolean load3 = isDisappeared(loadingIndicator_Circle);
			if (load1 && load2 && load3) {
				break;
			}
		}
	}

	public void browserMaximize() {
	//	browser.manage().window().maximize();
//		browser.manage().window().setPosition(new Point(0,0));
//		browser.manage().window().setSize(new Dimension(1440,900));
	}
	
	public boolean isDisplayedByXpath(WebElement w , String s){
		try{
			w.findElement(By.xpath(s));
		return true;
		}catch(Exception ex){
		return false;
		}
	}
}
