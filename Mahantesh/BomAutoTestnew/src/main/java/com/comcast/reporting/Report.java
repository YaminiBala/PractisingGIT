package com.comcast.reporting;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.testng.Assert;

import com.comcast.reporting.HtmlReport;
import com.comcast.reporting.Util;
import com.comcast.utils.Constant;

/**
 * Class to encapsulate all the reporting features of the framework
 */
public class Report {
	private ReportSettings reportSettings;
	private ReportTheme reportTheme;
	
	private int stepNumber = 1;
	private int nStepsPassed = 0, nStepsFailed = 0;
	private int nTestsPassed = 0, nTestsFailed = 0;
	
	private List<ReportType> reportTypes = new ArrayList<ReportType>();
	
	private String testStatus = "Passed";
	/**
	 * Function to get the current status of the test being executed
	 * @return the current status of the test being executed
	 */
	public String getTestStatus()
	{
		return testStatus;
	}
	public String getTestCaseName()
	{
		return this.reportSettings.getReportName();
	}
	
	private String failureDescription;
	/**
	 * Function to get the decription of any failure that may occur during the script execution
	 * @return The failure description (relevant only if the test fails)
	 */
	public String getFailureDescription()
	{
		return failureDescription;
	}
	
	
	/**
	 * Constructor to initialize the Report
	 * @param reportSettings The {@link ReportSettings} object
	 * @param reportTheme The {@link ReportTheme} object
	 */
	public Report(ReportSettings reportSettings, ReportTheme reportTheme)
	{
		this.reportSettings = reportSettings;
		this.reportTheme = reportTheme;
	}
	

	
	/* TEST LOG FUNCTIONS*/
	
	/**
	 * Function to initialize the test log
	 */
	public void initializeTestLog()
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).initializeTestLog();
		}
	}
	
	/**
	 * Function to add a heading to the test log
	 * @param heading The heading to be added
	 */
	public void addTestLogHeading(String heading)
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addTestLogHeading(heading);
		}
	}
	
	/**
	 * Function to add sub-headings to the test log
	 * (4 sub-headings present per test log row)
	 * @param subHeading1 The first sub-heading to be added
	 * @param subHeading2 The second sub-heading to be added
	 * @param subHeading3 The third sub-heading to be added
	 * @param subHeading4 The fourth sub-heading to be added
	 */
	public void addTestLogSubHeading(String subHeading1, String subHeading2,
										String subHeading3)
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addTestLogSubHeading(subHeading1, subHeading2,
														subHeading3);
		}
	}
	
	/**
	 * Function to add the overall table headings to the test log
	 * (should be called first before adding the actual content into the test log;
	 * headings and sub-heading should be added before this)
	 */
	public void addTestLogTableHeadings()
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addTestLogTableHeadings();
		}
	}
	
	/**
	 * Function to add a section to the test log
	 * @param section The section to be added
	 */
	public void addTestLogSection(String section)
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addTestLogSection(section);
		}
		
		stepNumber = 1;
	}
	
	/**
	 * Function to add a sub-section to the test log
	 * (should be called only within a previously created section)
	 * @param subSection The sub-section to be added
	 */
	public void addTestLogSubSection(String subSection)
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addTestLogSubSection(subSection);
		}
	}
	
	/**
	 * Function to update the test log with the details of a particular test step
	 * @param stepName The test step name
	 * @param stepDescription The description of what the test step does
	 * @param stepStatus The status of the test step
	 */
	public void updateTestLog(String stepName, String stepDescription, Status stepStatus) {
		if(stepStatus.equals(Status.FAIL)) {
			testStatus = "Failed";
			
			if (stepDescription == null) {
				stepDescription = "";
			}
			int iPos = stepDescription.toLowerCase().indexOf("build info:");
			if (iPos > 0) {
				stepDescription = stepDescription.toLowerCase().substring(0, iPos);
			}
			iPos = stepDescription.toLowerCase().indexOf("(session info:");
			if (iPos > 0) {
				stepDescription = stepDescription.toLowerCase().substring(0, iPos);
			}
			iPos = stepDescription.toLowerCase().indexOf("(warning: the server did not provide any stacktrace");
			if (iPos > 0) {
				stepDescription = stepDescription.toLowerCase().substring(0, iPos);
			}
			
			if(failureDescription == null) {
				failureDescription = stepDescription;
			} else {
				failureDescription = failureDescription + "; " + stepDescription;
			}
			nStepsFailed++;
		}
		
		if(stepStatus.equals(Status.PASS)) {
			nStepsPassed++;
		}
		
		if(stepStatus.ordinal() <= reportSettings.getLogLevel()) {
			String screenshotName = null;
			if((stepStatus.equals(Status.FAIL) && reportSettings.takeScreenshotFailedStep) ||
					(stepStatus.equals(Status.PASS) && reportSettings.takeScreenshotPassedStep) ||
					(stepStatus.equals(Status.SCREENSHOT))) {
				if (!stepName.equalsIgnoreCase("Overall Failed")) {
					screenshotName = reportSettings.getReportName() + "_" + Util.getCurrentFormattedTime(reportSettings.getDateFormatString()).replace(" ", "_").replace(":", "-") + ".png";
					takeScreenshot(reportSettings.getReportPath() + Util.getFileSeparator() + "Screenshots" + Util.getFileSeparator() + screenshotName);
				}
			}

			for(int i=0; i < reportTypes.size();i++) {
				reportTypes.get(i).updateTestLog(Integer.toString(stepNumber), stepName, stepDescription, stepStatus, screenshotName);
			}
			
			stepNumber++;
		}
		
		if(stepStatus.equals(Status.FAIL)) {
			boolean blnFail = true;
			if (stepName.equalsIgnoreCase("Overall Failed")) {
				blnFail = false;
			}
			String componentName = "";
			if(componentName.equalsIgnoreCase("Account Load")) {
				blnFail = false;
			}
			if(componentName.equalsIgnoreCase("Profinity Check")) {
				blnFail = false;
			}
			if(componentName.equalsIgnoreCase("Truck Roll")) {
				blnFail = false;
			}
			if (blnFail) {
				Assert.fail(stepDescription);
			}
		}
	}
	
	/**
	 * Function to take a screenshot
	 * @param screenshotPath The path where the screenshot should be saved
	 */
	protected void takeScreenshot(String screenshotPath)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle rectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);
		Robot robot = null;
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while creating Robot object (for taking screenshot)");
		}
		
		BufferedImage screenshotImage = robot.createScreenCapture(rectangle);
		File screenshotFile = new File (screenshotPath);
		
		try {
			ImageIO.write(screenshotImage, "jpg", screenshotFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while writing screenshot to .jpg file");
		}
	}
	
	/**
	 * Function to add a footer to the test log
	 * (The footer format is pre-defined - it contains the execution time and the number of passed/failed steps)
	 * @param executionTime The time taken to execute the test case
	 */
	public void addTestLogFooter(String executionTime)
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addTestLogFooter(executionTime, nStepsPassed, nStepsFailed);
		}
	}
	
	
	/* RESULT SUMMARY FUNCTIONS */
	
	/**
	 * Function to initialize the result summary
	 */
	public void initializeResultSummary()
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).initializeResultSummary();
		}
	}
	
	/**
	 * Function to add a heading to the result summary
	 * @param heading The heading to be added
	 */
	public void addResultSummaryHeading(String heading)
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addResultSummaryHeading(heading);
		}
	}
	
	/**
	 * Function to add sub-headings to the result summary
	 * (4 sub-headings present per result summary row)
	 * @param subHeading1 The first sub-heading to be added
	 * @param subHeading2 The second sub-heading to be added
	 * @param subHeading3 The third sub-heading to be added
	 * @param subHeading4 The fourth sub-heading to be added
	 * @param subHeading5 The fifth sub-heading to be added
	 */
	public void addResultSummarySubHeading(String subHeading1, String subHeading2, String subHeading3, 
											String subHeading4, String subHeading5, String subHeading6, String subHeading7)
	{
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addResultSummarySubHeading(subHeading1, subHeading2, subHeading3, 
															subHeading4, subHeading5, subHeading6, subHeading7);
		}
	}
	
	/**
	 * Function to add the results summary with the status of the executing test case
	 * @param iSno Serial number of the test case
	 * @param componentName The name of the test scenario
	 * @param testcaseName The name of the test case
	 * @param executionTime The time taken to execute the test case
	 * @param testStatus The status of the test case
	 * @param iteration Total number of times the test case is executed
	 */
	public void addResultSummary(String componentName, String testcaseName, String executionTime, String testStatus, int iteration) {
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addResultSummary(componentName, testcaseName, executionTime, testStatus, iteration);
		}
	}

	/**
	 * Function to update the iteration for executing test case
	 * @param testcaseName The name of the test case
	 * @param iteration Total number of times the test case is executed
	 */
	public void updateIterationSummary(String testcaseName, int iteration) {
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).updateIterationSummary(testcaseName, iteration);
		}
	}

	/**
	 * Function to update the results summary with the status of the executed test case
	 * @param testcaseName The name of the test case
	 * @param executionTime The time taken to execute the test case
	 * @param testStatus The status of the test case
	 * @param iteration Total number of times the test case is executed
	 * @param failureReason The reason for failure of the test case
	 */
	public void updateResultSummary(String currentTestcase, String executionTime, String testStatus, int iteration, String failureReason) {
		if(testStatus.equalsIgnoreCase("failed")) {
			nTestsFailed++;
		} else if (testStatus.equalsIgnoreCase("passed")) {
			nTestsPassed++;
		}
		
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).updateResultSummary(currentTestcase, executionTime, testStatus, iteration, failureReason);
		}
	}
	
	/**
	 * Function to add result to the result summary
	 * (The footer format is pre-defined - it contains the total execution time and the number of passed/failed tests)
	 * @param strBrowser The Browser in which the execution is happening
	 * @param strEnv The environment in which the execution is happening
	 * @param strUrl The application URL used for execution
	 */
	public void addResultSummaryStatus(String strBrowser, String strEnv, String strUrl) {
		for(int i=0; i < reportTypes.size(); i++) {
			reportTypes.get(i).addResultSummaryStatus(strBrowser, strEnv, strUrl);
		}
	}
	
	/**
	 * Function to initialize the different types of reports
	 */
	public void initializeReportTypes()
	{

		if(reportSettings.generateHtmlReports) {
			new File(reportSettings.getReportPath() +
						Util.getFileSeparator() + "HTML Results").mkdir();
			
			HtmlReport htmlReport = new HtmlReport(reportSettings, reportTheme);
			reportTypes.add(htmlReport);
		}
		
		if(reportSettings.includeTestDataInReport) {
			new File(reportSettings.getReportPath() +
						Util.getFileSeparator() + "Datatables").mkdir();
		}
	}
	
}