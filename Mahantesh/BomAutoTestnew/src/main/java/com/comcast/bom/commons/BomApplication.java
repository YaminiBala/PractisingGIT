package com.comcast.bom.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.comcast.reporting.*;
//import com.cognizant.framework.Status;
import com.comcast.bom.data.LoginDetails;
import com.comcast.bom.pages.BomHomePage;
import com.comcast.bom.pages.BomLoginPage;

import com.comcast.utils.SeleniumReport;
import com.comcast.utils.TestSettings;
import com.comcast.utils.TestUtils;

public class BomApplication {

	public WebDriver browser;
	public SeleniumReport report;
	public String url;
	public TestSettings testSettings;
	public String env;

	public BomApplication(WebDriver browser, SeleniumReport report) {
		this.browser = browser;
		testSettings = new TestSettings();
		this.url = testSettings.getApplicationURL();
		this.report = report;
		this.env = testSettings.getEnvironmentToTest();
	}

	public BomHomePage openRelevantApplication() throws IOException {

		if (env.equals("UAT")) {
			System.out.println("testying");
			// openApplication().applicationLogin(loginInfo);
			openUATapplication();
			/*
			 * String strBrowser = (new TestSettings()).getBrowser();
			 * if(strBrowser.contains("ie")){
			 * openUATapplicationBasicURL(loginInfo); }else{
			 * openUATapplication(loginInfo); }
			 */
			return new BomHomePage(browser, report);
		} else {
			// openApplication().applicationLogin(loginInfo);
			return new BomHomePage(browser, report);

		}

	}

	public BomLoginPage openApplication() {
		try {
			browser.get(url);
			// handleIECertificateErrorAndSafariCertificatePopup(browser);
			//browser.manage().window().maximize();
			report.updateTestLog("Open Application", "Application has been successfully launched", Status.DONE);
			return new BomLoginPage(browser, report);
		} catch (UnreachableBrowserException bx) {

			try {
				report.updateTestLog("Open Application", "Application has been successfully launched", Status.DONE);
				return new BomLoginPage(browser, report);
			} catch (RuntimeException ex) {
			browser.close();
				report.updateTestLog("Open Application", ex.getMessage(), Status.FAIL);
				throw ex;
			}
		} catch (RuntimeException ex) {
			report.updateTestLog("Open Application", ex.getMessage(), Status.FAIL);
			throw ex;
		}
	}

	public BomHomePage openUATapplication() {
		try {

			// String[] filepath=new String[]
			// {TestUtils.getRelativePath()+"\\src\\AutoIt\\QALoginAll.exe",testSettings.getBrowser()};
			// try {
			// Runtime.getRuntime().exec(filepath);

			// } catch (IOException e) {
			// System.out.println("Unable to close");
			// }
			browser.get(url);
			System.out.println("testing : " + url);

			// report.reportDoneEvent("Enter UN and PWD", "UserName
			// is->"+loginInfo.userID +" Password
			// is->"+loginInfo.password.replaceAll(".", "*"));
			//browser.manage().window().maximize();
			// report.updateTestLog("Open UAT Application", "Application has
			// been successfully launched", Status.DONE);
			return new BomHomePage(browser, report);
		} catch (RuntimeException ex) {
			report.updateTestLog("Open Application", ex.getMessage(), Status.FAIL);
			System.out.println("exception " + ex.toString());
			throw ex;

		}

	}

	public void close() throws IOException, Exception {

		//browser.quit();

		Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");

		/*
		 * //browser.quit();
		 * 
		 * //sleep(2500); if(isProcessRunging("iexplore.exe")) {
		 * killProcess("iexplore.exe"); System.out.println("killing iexplore");
		 * } if(isProcessRunging("IEDriverServer.exe")) {
		 * killProcess("IEDriverServer.exe"); System.out.println(
		 * "killing iedriverserver.exe"); } if(isProcessRunging("WerFault.exe"))
		 * { killProcess("WerFault.exe"); System.out.println(
		 * "killing iedriverserver.exe"); } if(isProcessRunging("chrome.exe")) {
		 * killProcess("chrome.exe"); System.out.println("killing chrome.exe");
		 * } if(isProcessRunging("chromedriver.exe")) {
		 * killProcess("chromedriver.exe"); System.out.println(
		 * "killing chromedriver.exe"); } String
		 * filepath1=TestUtils.getRelativePath()+"\\src\\AutoIt\\clear.bat"; try
		 * { Runtime.getRuntime().exec(filepath1); //sleep(2000); } catch
		 * (IOException e) { System.out.println("Unable to close");; }
		 */
	}

	public static void killProcess(String serviceName) throws Exception {

		Runtime.getRuntime().exec(KILL + serviceName);

	}

	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /IM ";

	public static boolean isProcessRunging(String serviceName) throws Exception {

		Process p = Runtime.getRuntime().exec(TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {

			// System.out.println(line);
			if (line.contains(serviceName)) {
				return true;
			}
		}

		return false;

	}

	private void deleteAllCookies() {
		try {
			browser.manage().deleteAllCookies();
		} catch (SessionNotFoundException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
