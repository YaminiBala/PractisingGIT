package com.comcast.reporting;

import com.comcast.reporting.Status;


/**
 * Interface representing a type of report available with the framework
 */
public interface ReportType
{
	/**
	 * Function to initialize the test log
	 */
	public void initializeTestLog();
	
	/**
	 * Function to add a heading to the test log
	 * @param heading The heading to be added
	 */
	public void addTestLogHeading(String heading);
	
	/**
	 * Function to add sub-headings to the test log
	 * (4 sub-headings present per test log row)
	 * @param subHeading1 The first sub-heading to be added
	 * @param subHeading2 The second sub-heading to be added
	 * @param subHeading3 The third sub-heading to be added
	 */
	public void addTestLogSubHeading(String subHeading1, String subHeading2,
										String subHeading3);
	
	/**
	 * Function to add the overall table headings to the test log
	 * (should be called first before adding the actual content into the test log;
	 * headings and sub-heading should be added before this)
	 */
	public void addTestLogTableHeadings();
	
	/**
	 * Function to add a section to the test log
	 * @param section The section to be added
	 */
	public void addTestLogSection(String section);
	
	/**
	 * Function to add a sub-section to the test log
	 * (should be called only within a previously created section)
	 * @param subSection The sub-section to be added
	 */
	public void addTestLogSubSection(String subSection);
	
	/**
	 * Function to update the test log with the details of a particular test step
	 * @param stepNumber The current step number
	 * @param stepName The test step name
	 * @param stepDescription The description of what the test step does
	 * @param stepStatus The {@link Status} of the test step
	 * @param screenshotName The filename of the screenshot file (in case of failed step)
	 */
	public void updateTestLog(String stepNumber, String stepName,
								String stepDescription,	Status stepStatus,
								String screenshotName);
	
	/**
	 * Function to create a footer to close the test log
	 * @param executionTime The time taken to execute the test case
	 * @param nStepsPassed The number of test steps that passed
	 * @param nStepsFailed The number of test steps that failed 
	 */
	public void addTestLogFooter(String executionTime, int nStepsPassed, int nStepsFailed);
	
	
	public void initializeResultSummary();
	
	public void addResultSummaryHeading(String heading);
	
	public void addResultSummarySubHeading(String subHeading1, String subHeading2, String subHeading3, 
											String subHeading4, String subHeading5, String subHeading6, String subHeading7);
	
	/**
	 * Function to add the results summary with the status of the executing test case
	 * @param iSno Serial number of the test case
	 * @param componentName The name of the test scenario
	 * @param testcaseName The name of the test case
	 * @param executionTime The time taken to execute the test case
	 * @param testStatus The status of the test case
	 * @param iteration Total number of times the test case is executed
	 */
	public void addResultSummary(String componentName, String testcaseName, String executionTime, String testStatus, int iteration);

	/**
	 * Function to update the iteration for executing test case
	 * @param testcaseName The name of the test case
	 * @param iteration Total number of times the test case is executed
	 */
	public void updateIterationSummary(String testcaseName, int iteration);

	/**
	 * Function to update the results summary with the status of the executed test case
	 * @param testcaseName The name of the test case
	 * @param executionTime The time taken to execute the test case
	 * @param testStatus The status of the test case
	 * @param iteration Total number of times the test case is executed
	 * @param failureReason The reason for failure of the test case
	 */
	public void updateResultSummary(String testcaseName, String executionTime, String testStatus, int iteration, String failureReason);
	
	/**
	 * Function to create status count in results summary
	 * @param strBrowser The Browser in which the execution is happening
	 * @param strEnv The environment in which the execution is happening
	 * @param strUrl The application URL used for execution
	 */
	public void addResultSummaryStatus(String strBrowser, String strEnv, String strUrl);
}