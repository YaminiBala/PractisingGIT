package com.comcast.reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.comcast.utils.ComcastTest;
import com.comcast.utils.Constant;


/**
 * Class to encapsulate the HTML report generation functions
 */
public class HtmlReport implements ReportType
{
	private String testLogPath, resultSummaryPath, resultSummaryPathJenkins;
	private ReportSettings reportSettings;
	private ReportTheme reportTheme;
	
	private String overallSummaryReport;
	
	private Boolean isTestLogHeaderTableCreated = false;
	private Boolean isTestLogMainTableCreated = false;
	
	private String currentSection = "";
	private String currentSubSection = "";
	private int currentContentNumber = 1;
	
	private String jenkinsArchievePath = null;
	public static int iSno = 0;
	
	/**
	 * Constructor to initialize the HTML report
	 * @param reportSettings The {@link ReportSettings} object
	 * @param reportTheme The {@link ReportTheme} object
	 */
	public HtmlReport(ReportSettings reportSettings, ReportTheme reportTheme)
	{
		this.reportSettings = reportSettings;
		this.reportTheme = reportTheme;
		
		testLogPath = reportSettings.getReportPath() + Util.getFileSeparator() + "HTML Results"
							+ Util.getFileSeparator() + reportSettings.getReportName() + ".html";
		
		resultSummaryPath = reportSettings.getReportPath() + Util.getFileSeparator() +
								"HTML Results" + Util.getFileSeparator() + "Summary" + ".html";
		
		resultSummaryPathJenkins = reportSettings.getReportPath() + Util.getFileSeparator() +
				"HTML Results" + Util.getFileSeparator() + "JenkinsArchieveSummary" + ".html";
		
		String jenkinsURL = System.getenv("JENKINS_URL");
		String jenkinsJobName = System.getenv("JOB_NAME");
		String jenkinsBuildTag = System.getenv("BUILD_TAG");
		String jenkinsBuildNo = System.getenv("BUILD_NUMBER");
		if (jenkinsBuildTag == null) {
			jenkinsBuildTag = "";
		}
		if (jenkinsBuildTag.length() > 0) {
			jenkinsArchievePath = jenkinsURL + "job/" + jenkinsJobName + "/" + jenkinsBuildNo + "/artifact/Results/" + jenkinsBuildTag;
		}
	}
	
	private synchronized void writeToSummaryReport(String testcaseName) {
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPath, false));
		    bufferedWriter.write(overallSummaryReport);
		    bufferedWriter.close();
		    if (jenkinsArchievePath != null) {
		    	String overallSummaryReportJenkins = overallSummaryReport.replace("<!---JenkinsArchievePath---><a href='..", "<a href='" + jenkinsArchievePath);
		    	overallSummaryReportJenkins = overallSummaryReportJenkins.replace("<!---JenkinsArchievePath---><a href='", "<a href='" + jenkinsArchievePath + "/HTML Results/");
		    	bufferedWriter = new BufferedWriter(new FileWriter(resultSummaryPathJenkins, false));
			    bufferedWriter.write(overallSummaryReportJenkins);
			    bufferedWriter.close();
		    }
//		    Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException("Error while writing to HTML result summary");
		}
	}
	
	private String getThemeCss()
	{
		String themeCss =	"\t\t <style type='text/css'> \n" +
								"\t\t\t body { \n" +
									"\t\t\t\t background-color: " + reportTheme.getContentForeColor() +"; \n" +
									"\t\t\t\t font-family: Trebuchet MS, Helvetica, sans-serif; \n" +
									"\t\t\t\t text-align: center; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t small { \n" +
									"\t\t\t\t font-size: 0.7em; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t table { \n" +
									"\t\t\t\t border: 1px solid #4D7C7B; \n" +
									"\t\t\t\t border-collapse: collapse; \n" +
									"\t\t\t\t border-spacing: 0px; \n" +
									"\t\t\t\t width: 1250px; \n" +
									"\t\t\t\t margin-left: auto; \n" +
									"\t\t\t\t margin-right: auto; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t table.fixed { \n" +
									"\t\t\t\t table-layout: fixed; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t table.fixed td { \n" +
								"\t\t\t\t word-wrap: break-word; \n" +
								"\t\t\t } \n\n" +
							
								"\t\t\t tr.heading { \n" +
									"\t\t\t\t background-color: " + reportTheme.getHeadingBackColor() + "; \n" +
									"\t\t\t\t color: #000000; \n" +
									"\t\t\t\t font-size: 0.9em; \n" +
									"\t\t\t\t font-weight: bold; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t tr.subheading { \n" +
									"\t\t\t\t background-color: " + reportTheme.getHeadingForeColor() + "; \n" +
									"\t\t\t\t color: #FFFFFF ; \n" +
									"\t\t\t\t font-weight: bold; \n" +
									"\t\t\t\t font-size: 0.9em; \n" +
									"\t\t\t\t text-align: center; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t tr.section { \n" +
									"\t\t\t\t background-color: " + reportTheme.getSectionBackColor() + "; \n" +
									"\t\t\t\t color: " + reportTheme.getSectionForeColor() + "; \n" +
									"\t\t\t\t cursor: pointer; \n" +
									"\t\t\t\t font-weight: bold; \n" +
									"\t\t\t\t font-size: 0.9em; \n" +
									"\t\t\t\t text-align: justify; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t tr.subsection { \n" +
									"\t\t\t\t cursor: pointer; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t tr.content { \n" +
									"\t\t\t\t background-color: " + reportTheme.getContentBackColor() + "; \n" +
									"\t\t\t\t color: " + reportTheme.getContentForeColor() + "; \n" +
									"\t\t\t\t font-size: 0.8em; \n" +
									"\t\t\t\t display: table-row; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t td, th { \n" +
									"\t\t\t\t padding: 4px; \n" +
									"\t\t\t\t text-align: inherit\\0/; \n" +
								"\t\t\t } \n\n" +
									
								"\t\t\t th { \n" +
								"\t\t\t\t font-family: 'Comic Sans MS', cursive, sans-serif; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t td { \n" +
								"\t\t\t\t border: 1px solid #4D7C7B; \n" +
								"\t\t\t\t border-spacing: 1px; \n" +
								"\t\t\t } \n\n" +

								"\t\t\t td.left { \n" +
								"\t\t\t\t text-align: left; \n" +
								"\t\t\t } \n\n" +

								"\t\t\t td.center { \n" +
									"\t\t\t\t text-align: center; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t td.executed { \n" +
									"\t\t\t\t font-weight: bold; \n" +
									"\t\t\t\t color: black; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t td.pass { \n" +
								"\t\t\t\t font-weight: bold; \n" +
								"\t\t\t\t color: green; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t td.fail { \n" +
									"\t\t\t\t font-weight: bold; \n" +
									"\t\t\t\t color: red; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t td.done, td.screenshot { \n" +
									"\t\t\t\t font-weight: bold; \n" +
									"\t\t\t\t color: black; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t td.debug { \n" +
									"\t\t\t\t font-weight: bold; \n" +
									"\t\t\t\t color: blue; \n" +
								"\t\t\t } \n\n" +
								
								"\t\t\t td.warning { \n" +
									"\t\t\t\t font-weight: bold; \n" +
									"\t\t\t\t color: orange; \n" +
								"\t\t\t } \n" +
							 "\t\t </style> \n\n";
		
		return themeCss;
	}
	
	private String getJavascriptFunctions()
	{
		String javascriptFunctions =	"\t\t <script> \n" +
											"\t\t\t function toggleMenu(objID) { \n" +
												"\t\t\t\t if (!document.getElementById) return; \n" +
												"\t\t\t\t var ob = document.getElementById(objID).style; \n" +
												"\t\t\t\t if(ob.display === 'none') { \n" +
													"\t\t\t\t\t try { \n" +
														"\t\t\t\t\t\t ob.display='table-row-group'; \n" +
													"\t\t\t\t\t } catch(ex) { \n" +
														"\t\t\t\t\t\t ob.display='block'; \n" +
													"\t\t\t\t\t } \n" +
												"\t\t\t\t } \n" +
												"\t\t\t\t else { \n" +
													"\t\t\t\t\t ob.display='none'; \n" +
												"\t\t\t\t } \n" +
											"\t\t\t } \n" +
											
											"\t\t\t function toggleSubMenu(objId) { \n" +
												"\t\t\t\t for(i=1; i<10000; i++) { \n" +
													"\t\t\t\t\t var ob = document.getElementById(objId.concat(i)); \n" +
													"\t\t\t\t\t if(ob === null) { \n" +
														"\t\t\t\t\t\t break; \n" +
													"\t\t\t\t\t } \n" +
													"\t\t\t\t\t if(ob.style.display === 'none') { \n" +
														"\t\t\t\t\t\t try { \n" +
															"\t\t\t\t\t\t\t ob.style.display='table-row'; \n" +
														"\t\t\t\t\t\t } catch(ex) { \n" +
															"\t\t\t\t\t\t\t ob.style.display='block'; \n" +
														"\t\t\t\t\t\t } \n" +
													"\t\t\t\t\t } \n" +
													"\t\t\t\t\t else { \n" +
														"\t\t\t\t\t\t ob.style.display='none'; \n" +
													"\t\t\t\t\t } \n" +
												"\t\t\t\t } \n" +
											"\t\t\t } \n" +
										 "\t\t </script> \n";
		
		return javascriptFunctions;
	}
	
	
	/* TEST LOG FUNCTIONS*/
	
	@Override
	public void initializeTestLog()
	{
		File testLogFile = new File(testLogPath);
		try {
			testLogFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while creating HTML test log file");
		}
		
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(testLogFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("Cannot find HTML test log file");
		}
		PrintStream printStream = new PrintStream(outputStream);
		
		String testLogHeadSection;
		testLogHeadSection = 	"<!DOCTYPE html> \n" +
								"<html> \n" +
								"\t <head> \n" +
									"\t\t <meta charset='UTF-8'> \n" +
									"\t\t <title>" +
										reportSettings.getProjectName() +
										" - " +	reportSettings.getReportName() +
										" Automation Execution Results" +
									"</title> \n\n" +
									getThemeCss() +
									getJavascriptFunctions() +
								"\t </head> \n";
		
        printStream.println(testLogHeadSection);
        printStream.close();
	}
	
	private void createTestLogHeaderTable()
	{
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
		    String testLogHeaderTable =	"\t <body> \n" +
											"\t\t <table class='fixed' id='header'> \n" +
												"\t\t\t <colgroup> \n" +
													"\t\t\t\t <col style='width: 20%' /> \n" +
													"\t\t\t\t <col style='width: 20%' /> \n" +
													"\t\t\t\t <col style='width: 60%' /> \n" +
												"\t\t\t </colgroup> \n\n" +
												 
												"\t\t\t <thead> \n";
		    bufferedWriter.write(testLogHeaderTable);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding header table to HTML test log");
		}
	}
	
	@Override
	public void addTestLogHeading(String heading)
	{
		if(!isTestLogHeaderTableCreated) {
			createTestLogHeaderTable();
			isTestLogHeaderTableCreated = true;
		}
		
		addTestLogHeadingTQM("Bulk OM Automation Report");
		
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
		    String testLogHeading =	"\t\t\t\t <tr class='heading'> \n" +
										"\t\t\t\t\t <th colspan='3' style='font-family:Palatino Linotype, Book Antiqua, Palatino, serif; font-size:1.4em;'> \n" + 
											"\t\t\t\t\t\t " + heading + " \n" +
										"\t\t\t\t\t </th> \n" +
									"\t\t\t\t </tr> \n";
		    bufferedWriter.write(testLogHeading);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding heading to HTML test log");
		}
	}
	
	public void addTestLogHeadingTQM(String heading)
	{
		if(!isTestLogHeaderTableCreated) {
			createTestLogHeaderTable();
			isTestLogHeaderTableCreated = true;
		}
		
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
		    String testLogHeading =	"\t\t\t\t <tr class='heading'> \n" +
										"\t\t\t\t\t <th colspan='3' style='font-family:Palatino Linotype,Book Antiqua, Palatino, serif; font-size:2em;'> \n" + 
											"\t\t\t\t\t\t " + heading + " \n" +
										"\t\t\t\t\t </th> \n" +
									"\t\t\t\t </tr> \n";
		    bufferedWriter.write(testLogHeading);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding heading to HTML test log");
		}
	}
	
	@Override
	public void addTestLogSubHeading(String subHeading1, String subHeading2,
										String subHeading3)
	{
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
		    String testLogSubHeading =	"\t\t\t\t <tr class='subheading'> \n" +
											"\t\t\t\t\t <th>&nbsp;" + subHeading1.replace(" ", "&nbsp;") + "</th> \n" +
											"\t\t\t\t\t <th>&nbsp;" + subHeading2.replace(" ", "&nbsp;") + "</th> \n" +
											"\t\t\t\t\t <th>&nbsp;" + subHeading3.replace(" ", "&nbsp;") + "</th> \n" +
										"\t\t\t\t </tr> \n";
		    bufferedWriter.write(testLogSubHeading);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding sub-heading to HTML test log");
		}
	}
	
	private void createTestLogMainTable()
	{
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
		    String testLogMainTable =		"\t\t\t </thead> \n" +
										 "\t\t </table> \n\n" +
										 "\t\t <table class='fixed' id='main'> \n" + 
											"\t\t\t <colgroup> \n" +
												"\t\t\t\t <col style='width: 5%' /> \n" +
												"\t\t\t\t <col style='width: 20%' /> \n" +
												"\t\t\t\t <col style='width: 35%' /> \n" +
												"\t\t\t\t <col style='width: 10%' /> \n" +
												"\t\t\t\t <col style='width: 15%' /> \n" +
												"\t\t\t\t <col style='width: 15%' /> \n" +
											 "\t\t\t </colgroup> \n\n";
		    bufferedWriter.write(testLogMainTable);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding main table to HTML test log");
		}
	}
	
	@Override
	public void addTestLogTableHeadings()
	{
		if(!isTestLogMainTableCreated) {
			createTestLogMainTable();
			isTestLogMainTableCreated = true;
		}
		
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
		    String testLogTableHeading =	"\t\t\t <thead> \n" +
												"\t\t\t\t <tr class='heading'> \n" + 
													"\t\t\t\t\t <th>Step#</th> \n" +
													"\t\t\t\t\t <th>Action</th> \n" +
													"\t\t\t\t\t <th>Actual Result</th> \n" +
													"\t\t\t\t\t <th>Status</th> \n" +
													"\t\t\t\t\t <th>Screenshot</th> \n" +
													"\t\t\t\t\t <th>Time</th> \n" +
												"\t\t\t\t </tr> \n" +
											"\t\t\t </thead> \n\n";
		    bufferedWriter.write(testLogTableHeading);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding main table headings to HTML test log");
		}
	}
	
	@Override
	public void addTestLogSection(String section)
	{
		String testLogSection = "";
		if (!currentSection.equals("")) {
			testLogSection = "\t\t\t </tbody>";
		}
		
		currentSection = section.replaceAll("[^a-zA-Z0-9]", "");
		
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
		    testLogSection +=	"\t\t\t <tbody> \n" +
										"\t\t\t\t <tr class='section'> \n" +
											"\t\t\t\t\t <td colspan='5' onclick=\"toggleMenu('" + currentSection + "')\">+ " +
												section + "</td> \n" +
										"\t\t\t\t </tr> \n" +
									"\t\t\t </tbody> \n" +
									"\t\t\t <tbody id='" + currentSection + "' style='display:table-row-group'> \n";
		    bufferedWriter.write(testLogSection);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding section to HTML test log");
		}
	}
	
	@Override
	public void addTestLogSubSection(String subSection)
	{
		currentSubSection = subSection.replaceAll("[^a-zA-Z0-9]", "");
		currentContentNumber = 1;
		
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
		    String testLogSubSection =	"\t\t\t\t <tr class='subheading subsection'> \n" +
											"\t\t\t\t\t <td colspan='5' onclick=\"toggleSubMenu('" + currentSection + currentSubSection + "')\">&nbsp;+ " +
												subSection + "</td> \n" +
										"\t\t\t\t </tr> \n";
		    bufferedWriter.write(testLogSubSection);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding sub-section to HTML test log");
		}
	}
	
	public void reportStepNumber(String stepNumber, String stepName, String stepDescription, Status stepStatus, String strFullScreenShotFileName) {
		try {
			reportStepDescription(stepNumber, stepName, stepDescription, stepStatus, strFullScreenShotFileName);
			Path path = Paths.get(strFullScreenShotFileName);
			reportScreenshot(Files.readAllBytes(path));
		} catch (Exception e) {
			System.out.println("Error while reporting allure step.");
		}
	}
	
	public void reportStepDescription(String stepNumber, String stepName, String stepDescription, Status stepStatus, String strFullScreenShotFileName) {
	}
	
	public byte[] reportScreenshot(byte[] screenShot) {
	    return screenShot;
	}
	
	public void allureReport(String stepNumber, String stepName, String stepDescription, Status stepStatus, String strFullScreenShotFileName) {
		try {
			reportStepNumber(stepNumber, stepName, stepDescription, stepStatus, strFullScreenShotFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateTestLog(String stepNumber, String stepName, String stepDescription, Status stepStatus, String screenShotName)
	{
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
			String strFileName = "..\\Screenshots\\" + screenShotName;
			String sspath = "<a href='" + strFileName + "'><img alt='No screenshot available.' src='" + strFileName + "' style='width:125px;height:75px;'></a>";
			
			String strFullScreenShotFileName = reportSettings.getReportPath() + File.separator + "Screenshots" + File.separator + screenShotName;
			File fileName = new File(strFullScreenShotFileName);
			if (!fileName.exists()) {
				sspath = "No screenshot";
			}
			
			allureReport(stepNumber, stepName, stepDescription, stepStatus, strFullScreenShotFileName);
			
			String testStepRow = "\t\t\t\t <tr class='content' id='" + currentSection + currentSubSection + currentContentNumber + "'> \n" +
									"\t\t\t\t\t <td>" + stepNumber + "</td> \n" +
									"\t\t\t\t\t <td class='left'>" + stepName + "</td> \n" +
									"\t\t\t\t\t <td class='left'>" + stepDescription + "</td> \n";
			currentContentNumber++;
			
			if(stepStatus.equals(Status.FAIL) || stepStatus.equals(Status.WARNING)) {
				if(reportSettings.takeScreenshotFailedStep) {
					 testStepRow += "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" +
							 											   					   stepStatus +
							 											   					   	"</td> \n";
					 testStepRow += "\t\t\t\t\t <td class='" + Status.SCREENSHOT.toString().toLowerCase() + "'>" +
																				  						  sspath +
																										"</td> \n";
				} else {
					testStepRow += 	"\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" +
																							   stepStatus +
																							   	"</td> \n";
					testStepRow += "\t\t\t\t\t <td class='" + Status.SCREENSHOT.toString().toLowerCase() + "'>" +
																								"No screenshot" +
																									  "</td> \n";
				}
			} else if(stepStatus.equals(Status.PASS)) {
				if(reportSettings.takeScreenshotPassedStep) {
					 testStepRow += "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" +
							 											   					   stepStatus +
							 											   					   	"</td> \n";
					 testStepRow += "\t\t\t\t\t <td class='" + Status.SCREENSHOT.toString().toLowerCase() + "'>" +
	  						  																			  sspath +
							   																			"</td> \n";
				} else {
					testStepRow += 	"\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" +
																							   stepStatus +
																							   	"</td> \n";
					testStepRow += "\t\t\t\t\t <td class='" + Status.SCREENSHOT.toString().toLowerCase() + "'>" +
																								"No screenshot" +
																									  "</td> \n";
				}
			} else if(stepStatus.equals(Status.SCREENSHOT)) {
				testStepRow += "\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" +
	   					   																  stepStatus +
	   					   																  	"</td> \n";
				
				 testStepRow += "\t\t\t\t\t <td class='" + Status.SCREENSHOT.toString().toLowerCase() + "'>" +
 						  																			  sspath +
																									"</td> \n";
			} else {
				testStepRow += 	"\t\t\t\t\t <td class='" + stepStatus.toString().toLowerCase() + "'>" +
										   													stepStatus +
										   													"</td> \n";
				testStepRow += "\t\t\t\t\t <td class='" + Status.SCREENSHOT.toString().toLowerCase() + "'>" +
																							"No screenshot" +
																									"</td> \n";
	       	}
			
	       	testStepRow += 		"\t\t\t\t\t <td>" +
       									"<small>" + Util.getCurrentFormattedTime(reportSettings.getDateFormatString()) + "</small>" +
       							"</td> \n" +
       						"\t\t\t\t </tr> \n";
	       	
		    bufferedWriter.write(testStepRow);
		    bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException("Error while updating HTML test log");
		}
	}
	
	@Override
	public void addTestLogFooter(String executionTime, int nStepsPassed, int nStepsFailed)
	{
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testLogPath, true));
			
			String testLogFooter =	"\t\t\t </tbody> \n" +
								"\t\t </table> \n\n" +
								
								"\t\t <table class='fixed' id='footer'> \n" +
									"\t\t\t <colgroup> \n" +
										"\t\t\t\t <col style='width: 25%' /> \n" +
										"\t\t\t\t <col style='width: 25%' /> \n" +
										"\t\t\t\t <col style='width: 25%' /> \n" +
										"\t\t\t\t <col style='width: 25%' /> \n" +
									"\t\t\t </colgroup> \n\n" +
									
									"\t\t\t <tfoot> \n" +
										"\t\t\t\t <tr class='heading'> \n" + 
											"\t\t\t\t\t <th colspan='4'>Execution Duration: " + executionTime + "</th> \n" + 
										"\t\t\t\t </tr> \n" +
										"\t\t\t\t <tr class='subheading'> \n" + 
											"\t\t\t\t\t <td class='pass'>&nbsp;Steps passed</td> \n" + 
											"\t\t\t\t\t <td class='pass'>&nbsp;: " + nStepsPassed + "</td> \n" +
											"\t\t\t\t\t <td class='fail'>&nbsp;Steps failed</td> \n" + 
											"\t\t\t\t\t <td class='fail'>&nbsp;: " + nStepsFailed + "</td> \n" +
										"\t\t\t\t </tr> \n" +
									"\t\t\t </tfoot> \n" +
								"\t\t </table> \n" +
							"\t </body> \n" +
						"</html>";
		    
		    bufferedWriter.write(testLogFooter);
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while adding footer to HTML test log");
		}
	}
	
	
	/* RESULT SUMMARY FUNCTIONS*/
	
	@Override
	public synchronized void initializeResultSummary() {
		File resultSummaryFile = new File(resultSummaryPath);

		try {
			resultSummaryFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while creating HTML result summary file");
		}

		overallSummaryReport = 	"<!DOCTYPE html> \n" +
								"<html> \n" +
								"\t <head> \n" +
									"\t\t <meta charset='UTF-8'> \n" +
									"\t\t <title> \n" + 
										"\t\t\t " + reportSettings.getProjectName() + " - Automation Execution Results Summary \n" +
									"\t\t </title> \n\n" +
									getThemeCss() +
									getJavascriptFunctions() +
								"\t </head> \n";
		writeToSummaryReport("initializeResultSummary");
	}
	
	@Override
	public synchronized void addResultSummaryHeading(String heading) {
	    String resultSummaryHeading =	"\t <body> \n" +
											"\t\t <table class='fixed' id='header'> \n" +
												"\t\t\t <thead> \n" +
													"\t\t\t\t <tr class='heading'> \n" +
														"\t\t\t\t\t <th colspan='16' style='font-family:Copperplate Gothic Bold; font-size:1.4em;'> \n" + 
															"\t\t\t\t\t\t " + heading + " \n" +
														"\t\t\t\t\t </th> \n" +
													"\t\t\t\t </tr> \n" + 
												"\t\t\t </thead> \n\n";
	    overallSummaryReport = overallSummaryReport + resultSummaryHeading;
	    writeToSummaryReport("addResultSummaryHeading");
	}
	
	@Override
	public synchronized void addResultSummarySubHeading(String subHeading1, String subHeading2, String subHeading3, String subHeading4, String subHeading5, String subHeading6, String subHeading7) {
	    String resultSummarySubHeading =		"\t\t\t <tr class='subheading'> \n" +
													"\t\t\t\t <th colspan='1'>" + subHeading1.replace(" ", "&nbsp;") + "</th> \n" +
													"\t\t\t\t <th colspan='2'>" + subHeading2.replace(" ", "&nbsp;") + "</th> \n" +
													"\t\t\t\t <th colspan='5'>" + subHeading3.replace(" ", "&nbsp;") + "</th> \n" +
													"\t\t\t\t <th colspan='2'>" + subHeading4.replace(" ", "&nbsp;") + "</th> \n" +
													"\t\t\t\t <th colspan='1'>" + subHeading5.replace(" ", "&nbsp;") + "</th> \n" +
													"\t\t\t\t <th colspan='1'>" + subHeading6.replace(" ", "&nbsp;") + "</th> \n" +
													"\t\t\t\t <th colspan='4'>" + subHeading7.replace(" ", "&nbsp;") + "</th> \n" +
												"\t\t\t </tr> \n";
	    overallSummaryReport = overallSummaryReport + resultSummarySubHeading;
	    writeToSummaryReport("addResultSummarySubHeading");
	}
	
	@Override
	public synchronized void addResultSummary(String componentName, String testcaseName, String executionTime, String testStatus, int iteration) {
		iSno++;
		String testcaseRow = "\t\t\t <tr class='content'> \n" +
								"\t\t\t\t <td colspan='1' class='center'>" + iSno + "</td> \n" +
								"\t\t\t\t <td colspan='2' class='center'>" + componentName + "</td> \n" +
								"\t\t\t\t <td colspan='5' class='left'><!---JenkinsArchievePath---><a href='" + testcaseName + ".html'>" + testcaseName + "</a></td> \n" +
								"\t\t\t\t <td colspan='2' class='center'>" + "<!---STARTTIME_" + testcaseName + "--->" + executionTime + "<!---ENDTIME_" + testcaseName + "--->" + "</td> \n" +
								"\t\t\t\t <!---STARTSTATUS_" + testcaseName + "---><td colspan='1' class='center'>" + testStatus + "</td><!---ENDSTATUS_" + testcaseName + "--->" + " \n" +
								"\t\t\t\t <td colspan='1' class='center'>" + "<!---STARTITERATION_" + testcaseName + "--->" + iteration + "<!---ENDITERATION_" + testcaseName + "--->" + "</td> \n" +
								"\t\t\t\t <td colspan='4' class='left'>" + "<!---STARTREASON_" + testcaseName + "---><!---ENDREASON_" + testcaseName + "--->" + "</td> \n" +
							"\t\t\t </tr> \n";
		overallSummaryReport = overallSummaryReport + testcaseRow;
		updateResultSummaryStatus(testcaseName);
	    writeToSummaryReport(testcaseName);
	}

	@Override
	public synchronized void updateIterationSummary(String testcaseName, int iteration) {
		String strStartText = "<!---STARTITERATION_" + testcaseName + "--->";
		String strEndText = "<!---ENDITERATION_" + testcaseName + "--->";
		int iStart = overallSummaryReport.indexOf(strStartText);
		int iEnd = overallSummaryReport.indexOf(strEndText);
		String strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		String strNewText = strStartText + Integer.toString(iteration) + strEndText;
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		updateResultSummaryStatus(testcaseName);
		writeToSummaryReport(testcaseName);
	}

	@Override
	public synchronized void updateResultSummary(String testcaseName, String executionTime, String testStatus, int iteration, String failureReason) {
		String strStartText = "<!---STARTTIME_" + testcaseName + "--->";
		String strEndText = "<!---ENDTIME_" + testcaseName + "--->";
		int iStart = overallSummaryReport.indexOf(strStartText);
		int iEnd = overallSummaryReport.indexOf(strEndText);
		String strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		String strNewText = executionTime;
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		strStartText = "<!---STARTSTATUS_" + testcaseName + "--->";
		strEndText = "<!---ENDSTATUS_" + testcaseName + "--->";
		iStart = overallSummaryReport.indexOf(strStartText);
		iEnd = overallSummaryReport.indexOf(strEndText);
		strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		if(testStatus.equalsIgnoreCase("passed")) {
			strNewText = "<td class='pass'>" + testStatus + "</td>";
		} else {
			strNewText = "<td class='fail'>" + testStatus + "</td>";
		}
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		strStartText = "<!---STARTITERATION_" + testcaseName + "--->";
		strEndText = "<!---ENDITERATION_" + testcaseName + "--->";
		iStart = overallSummaryReport.indexOf(strStartText);
		iEnd = overallSummaryReport.indexOf(strEndText);
		strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		strNewText = Integer.toString(iteration);
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		strStartText = "<!---STARTREASON_" + testcaseName + "--->";
		strEndText = "<!---ENDREASON_" + testcaseName + "--->";
		iStart = overallSummaryReport.indexOf(strStartText);
		iEnd = overallSummaryReport.indexOf(strEndText);
		strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		strNewText = failureReason;
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		updateResultSummaryStatus(testcaseName);
		writeToSummaryReport(testcaseName);
	}
	
	@Override
	public synchronized void addResultSummaryStatus(String strBrowser, String strEnv, String strUrl) {
	    String resultSummaryStatus =	"\t\t\t <colgroup> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
											"\t\t\t\t <col style='width: 6.25%' /> \n" +
										"\t\t\t </colgroup> \n\n" +
										"\t\t\t <tr class='subheading'> \n" +
												"\t\t\t\t <th colspan='3'>Browser : " + strBrowser.replace(" ", "&nbsp;") + "</th> \n" +
												"\t\t\t\t <th colspan='3'>ENV : " + strEnv.replace(" ", "&nbsp;") + "</th> \n" +
												"\t\t\t\t <th colspan='10'>URL : <a href='" + strUrl.replace(" ", "&nbsp;") + "'>" + strUrl.replace(" ", "&nbsp;") + "</a></td> \n" +
										"\t\t\t </tr> \n" +
										"\t\t\t <tr class='subsection'> \n" + 
											"\t\t\t\t <td colspan='3' class='executed'>Tests Executed</td> \n" + 
											"\t\t\t\t <td class='executed'><!---START_EXECUTED--->0<!---END_EXECUTED---></td> \n" +
											"\t\t\t\t <td colspan='3' class='pass'>Tests Passed</td> \n" + 
											"\t\t\t\t <td class='pass'><!---START_PASSED--->0<!---END_PASSED---></td> \n" +
											"\t\t\t\t <td colspan='3' class='fail'>Tests Failed</td> \n" + 
											"\t\t\t\t <td class='fail'><!---START_FAILED--->0<!---END_FAILED---></td> \n" +
											"\t\t\t\t <td colspan='3' class='executed'>Tests In Progress</td> \n" + 
											"\t\t\t\t <td class='executed'><!---START_INPROGRESS--->0<!---END_INPROGRESS---></td> \n" +
										"\t\t\t </tr> \n" +
										"\t\t\t <tr class='heading'> \n" + 
											"\t\t\t\t <th colspan='12'>Total Duration: <!---START_OVERALLTIME--->0:mm 0:ss<!---END_OVERALLTIME---></th> \n" +
											"\t\t\t\t <th colspan='4'><!---JenkinsArchievePath---><a href='" + ".." + Util.getFileSeparator() + "Application Errors" + Util.getFileSeparator() + Constant.APP_ERRORS_REPORT + "'>Application Errors</a></th> \n" + 
										"\t\t\t </tr> \n";
	    overallSummaryReport = overallSummaryReport + resultSummaryStatus;
	    writeToSummaryReport("addResultSummaryStatus");
	}

	private synchronized void updateResultSummaryStatus(String testcaseName) {
		String totalExecutionTime = "TBD";// ComcastTest.totalExecutionTime;
		int nTestsPassed = 1;//ComcastTest.nTestsPassed;
		int nTestsFailed = 1;//ComcastTest.nTestsFailed;
		int nTestsInProgress =1;// ComcastTest.nTestsInProgress;
		int iExecuted = 1;// nTestsPassed + nTestsFailed + nTestsInProgress;

		String strStartText = "<!---START_EXECUTED--->";
		String strEndText = "<!---END_EXECUTED--->";
		int iStart = overallSummaryReport.indexOf(strStartText);
		int iEnd = overallSummaryReport.indexOf(strEndText);
		String strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		String strNewText = strStartText + iExecuted + strEndText;
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		strStartText = "<!---START_PASSED--->";
		strEndText = "<!---END_PASSED--->";
		iStart = overallSummaryReport.indexOf(strStartText);
		iEnd = overallSummaryReport.indexOf(strEndText);
		strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		strNewText = strStartText + nTestsPassed + strEndText;
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		strStartText = "<!---START_FAILED--->";
		strEndText = "<!---END_FAILED--->";
		iStart = overallSummaryReport.indexOf(strStartText);
		iEnd = overallSummaryReport.indexOf(strEndText);
		strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		strNewText = strStartText + nTestsFailed + strEndText;
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		strStartText = "<!---START_INPROGRESS--->";
		strEndText = "<!---END_INPROGRESS--->";
		iStart = overallSummaryReport.indexOf(strStartText);
		iEnd = overallSummaryReport.indexOf(strEndText);
		strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		strNewText = strStartText + nTestsInProgress + strEndText;
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);

		strStartText = "<!---START_OVERALLTIME--->";
		strEndText = "<!---END_OVERALLTIME--->";
		iStart = overallSummaryReport.indexOf(strStartText);
		iEnd = overallSummaryReport.indexOf(strEndText);
		strCurrentText = overallSummaryReport.substring(iStart, iEnd + strEndText.length());
		strNewText = strStartText + totalExecutionTime + strEndText;
		overallSummaryReport = overallSummaryReport.replace(strCurrentText, strNewText);
	}
}
