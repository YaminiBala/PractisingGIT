package com.comcast.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.comcast.reporting.*;

public class SeleniumReport extends Report {
	private WebDriver driver;
	private String hostName;
	private String localHostName;
	
	/**
	 * Function to set the {@link WebDriver} object
	 * 
	 * @param driver
	 *            The {@link WebDriver} object
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setHostName(String strHost) {
		this.hostName = strHost;
	}

	public String getHostName() {
		return hostName;
	}

	public void setLocalHostName(String strHost) {
		this.localHostName = strHost;
	}

	public String getLocalHostName() {
		return localHostName;
	}

	/**
	 * Constructor to initialize the Report
	 * 
	 * @param reportSettings
	 *            The {@link ReportSettings} object
	 * @param reportTheme
	 *            The {@link ReportTheme} object
	 */
	public SeleniumReport(ReportSettings reportSettings, ReportTheme reportTheme) {
		super(reportSettings, reportTheme);
	}

	/*@Override
	protected void takeScreenshot(String screenshotPath) {
		if (driver == null) {
			throw new FrameworkException("Report.driver is not initialized!");
		}
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(screenshotPath), true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException(
					"Error while writing screenshot to file");
		}
	}
	*/
	
	@Override
	protected void takeScreenshot(String screenshotPath) {
		if (driver == null) {
			//throw new FrameworkException("Report.driver is not initialized!");
			System.out.println("Report.driver is not initialized!");
		}

		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(screenshotPath), true);
		} catch (Exception e) {
			//e.printStackTrace();
			//throw new FrameworkException("Error while writing screenshot to file");
			System.out.println("Error while writing screenshot to file");
		}
	}
	
	
	/*
	 * 
	 */
	public void reportDoneEvent(String stepName,String description) {
		this.updateTestLog(stepName, description, Status.DONE);
	}	
	
	/**
	 * Add a Passed step to the test report
	 * @param stepName
	 * @param description
	 */
	public void reportPassEvent(String stepName,String description){
		this.updateTestLog(stepName, description, Status.PASS);
	}
	
	/**
	 * Add a failed report to the test report
	 * @param stepName
	 * @param description
	 */
	public void reportFailEvent(String stepName,String description){
		this.updateTestLog(stepName, description, Status.FAIL);
	}

	/**
	 * Add a warning report to the test report
	 * @param stepName
	 * @param description
	 */
	public void reportWarningEvent(String stepName,String description){
		this.updateTestLog(stepName, description, Status.WARNING);
	}
}