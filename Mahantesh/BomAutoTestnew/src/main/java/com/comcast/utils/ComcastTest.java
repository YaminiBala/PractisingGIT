package com.comcast.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import com.cognizant.framework.ReportSettings;
//import com.cognizant.framework.ReportThemeFactory;
//import com.cognizant.framework.ReportThemeFactory.Theme;
//import com.cognizant.framework.Status;

import com.comcast.reporting.*;
import com.comcast.reporting.ReportSettings;
import com.comcast.reporting.ReportThemeFactory;
import com.comcast.reporting.ReportThemeFactory.Theme;
import com.comcast.utils.SeleniumReport;







import com.gargoylesoftware.htmlunit.javascript.IEConditionalCompilationScriptPreProcessor;




/**
 * Base class for all Comcast Test
 *
 */
public class ComcastTest implements ITestResult{
	public String testCaseName=null;
	private long startTime;
	private long endTime;
	private static String watchedLog;
	public ITestContext context;
	
	public static DataTable dataTable;
	protected SeleniumReport report;
	protected WebDriver browser;
	protected WebDriver browserBacc;
	protected TestSettings settings;
	

	/*  public TestWatcher watchman= new TestWatcher() {
		  
	      @Override
	      protected void failed(Throwable e, Description description) {
	    	  String reportPath=ReportPath.getInstance().getReportPath();
	    	  String testCaseQCName=testName.getMethodName();
	    	  createResultFile(reportPath,"Failed");
	    	  System.out.println("Test Case Failed");
	    	  createZipFileOfReport(reportPath,testCaseQCName);	    	
	    	  //report.reportFailEvent("Overall Status", "Overall Status");
	    	  if(settings.getQCUpdate().equals("Yes")){
	    		  String reportPath=ReportPath.getInstance().getReportPath();
		    	  String testCaseQCName=testName.getMethodName();
		    	  QCConnectorClass qc=new QCConnectorClass();
		    	  List<String> TCIDs=getQCTestCaseCoverage();
		    	  System.out.println("Test Case failed"+getQCTestCaseCoverage());
		    	  for(int i=0;i<TCIDs.size();i++){
		    		  qc.updateQCStatus(TCIDs.get(i),"Failed",reportPath,testCaseQCName);  
		    	  }
		          
	    	  }else{
	    		  System.out.println("QC Update is not set");
	    	  }
	    	 
	         }
	      

	      @Override
	      protected void succeeded(Description description) {
	    	  String reportPath=ReportPath.getInstance().getReportPath();
	    	  String testCaseQCName=testName.getMethodName();
	    	  //System.out.println("Before zipping");
	    	  createResultFile(reportPath,"Passed");
	    	  System.out.println("Test Case Passed");
	    	  createZipFileOfReport(reportPath,testCaseQCName);
	    	  //System.out.println("After zipping");
	    	  //report.reportPassEvent("Overall Status", "Overall Status");	      
	    	  if(settings.getQCUpdate().equals("Yes")){
	    	  String reportPath=ReportPath.getInstance().getReportPath();
	    	  String testCaseQCName=testName.getMethodName();
	    	  QCConnectorClass qc=new QCConnectorClass();
	    	  List<String> TCIDs=getQCTestCaseCoverage();
	    	  System.out.println("Test Case passed"+getQCTestCaseCoverage());
	    	  for(int i=0;i<TCIDs.size();i++){
	    		  qc.updateQCStatus(TCIDs.get(i),"Passed",reportPath,testCaseQCName);  
	    	  }
	        
	         }else{
	    		  System.out.println("QC Update is not set");
	    	  }
	      }
	     };
	     */
	    /* @BeforeClass

	       public void beforeTest_startTestMain(ITestContext context) {

	              String ngTestName = context.getCurrentXmlTest().getName();
	       }  
*/
	    @BeforeTest
	     public void setupData(ITestContext context) {
	    	synchronized (this) {
	    		System.out.println("Comcast Before Test.............");
		settings=new TestSettings();
		ComcastTest test=new ComcastTest();
		browser=getDriver(settings.getBrowser());		
		
		if(context.getCurrentXmlTest().getName().contains("[")){
			testCaseName = context.getCurrentXmlTest().getName();
		}else{
			testCaseName = context.getCurrentXmlTest().getName();
		}
		System.out.println("testCaseName :: " + testCaseName);
		//testCaseName="ValidateX1_DVRDCT_CSGResidential_1";
		initializeReport(testCaseName);
		startTime = System.currentTimeMillis();
		
		try{
		
				 dataTable = new DataTable(testCaseName);
			}
				
		catch(Exception e){				
			report.updateTestLog("VerfiyTestDataRow", "Test Data Row Not Present <Please check and add test data>",Status.FAIL);
			browser.quit();			
			System.out.println("Test Data Row not present. Please check");
		}
	    }
	}

		
	/**
	 * Initialize Test Report
	 * @param testCaseName
	 */
	protected void initializeReport(String testCaseName) {
		String reportPath=ReportPath.getInstance().getReportPath();
		ReportSettings reportSettings = new ReportSettings(reportPath, testCaseName);
		reportSettings.generateExcelReports=false;
		reportSettings.generateHtmlReports=true;
		reportSettings.includeTestDataInReport=false;
		reportSettings.takeScreenshotFailedStep=true;
		reportSettings.takeScreenshotPassedStep=true;
		report = new SeleniumReport(reportSettings, ReportThemeFactory.getReportsTheme(Theme.TQM));
		report.setDriver(browser);
		report.initializeReportTypes();
		report.initializeTestLog();
		report.addTestLogHeading(testCaseName);
		report.addTestLogSubHeading("Browser :"+ settings.getBrowser(), "URL", settings.getApplicationURL());
		report.addTestLogTableHeadings();
		report.addTestLogSection("Detailed Steps");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
	  System.out.println("method name:" + result.getMethod().getMethodName());
	}
	
	@AfterTest
	public void tearDown(){
		endTime=System.currentTimeMillis();
		Long timeTaken=endTime-startTime-20;//-20 is to compensate for things other than test script execution
		int h = (int) ((timeTaken / 1000) / 3600);
		int m = (int) (((timeTaken / 1000) / 60) % 60);
		int s = (int) ((timeTaken / 1000) % 60);
		String time=""+h+":hh "+m+":mm"+s+"ss";
		report.addTestLogFooter(time);
		browser.quit();
	}
	
	/**
	 * Get the browser object specified in the property
	 * @param browserName
	 * @return
	 */
	protected WebDriver getDriver(String browserName) {
		WebDriver driver = null;
//		if (browserName.equalsIgnoreCase("firefox")) {	
//			driver= new FirefoxDriver();
//		}
		if (browserName.equalsIgnoreCase("firefox")) {
		//	ProfilesIni profilesIni = new ProfilesIni();
		//	FirefoxProfile profile = profilesIni.getProfile("default");
		//	profile.setAcceptUntrustedCertificates(true);
		//	profile.setAssumeUntrustedCertificateIssuer(false);
		//	profile.setEnableNativeEvents(true);
			try{
				System.setProperty("webdriver.firefox.driver", "C://Program Files (x86)//Mozilla Firefox//firefox");
			//driver =new FirefoxDriver();
			}catch(Exception ex){
				System.out.println();
			}

		}
		if(browserName.equalsIgnoreCase("chrome")){
			//System.setProperty("webdriver.chrome.driver", "C:\\Selenium WorkSpace\\Einstein\\BrowserSpecificDrivers\\chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", TestUtils.getRelativePath()+ File.separator + "BrowserSpecificDrivers"+ File.separator + "chromedriver.exe");
            System.out.println(System.getProperty("webdriver.chrome.driver"));
            //added by dhruv to close extensions in google chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions");
                         driver=new ChromeDriver(options);                             
		}
		if(browserName.equalsIgnoreCase("iexplore") || browserName.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver",
					TestUtils.getRelativePath()+"/BrowserSpecificDrivers/IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
//			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,
	//				true);
			capabilities.setCapability("ACCEPT_SSL_CERTS", true);
			driver=new InternetExplorerDriver(capabilities);
		
		}
		if(browserName.equalsIgnoreCase("safari")){
			driver=new SafariDriver();
		}
//		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		return driver;
	}
	
	public void reinitializeDriverForReport() {
    	browser=getDriver(settings.getBrowser()); 
    	report.setDriver(browser);  
    }
	
	private List<String> getQCTestCaseCoverage() {
		List<String> coveredTestCases = new ArrayList<String>();

		try {
			Class clazz = Class.forName(this.getClass().getName());
			Method method = clazz.getMethod(testCaseName);
			//System.out.println(testName.getMethodName());

			if (method.isAnnotationPresent(QCTestCases.class)) {
				System.out.println(method.getAnnotations());
				QCTestCases qcTestCases = method.getAnnotation(QCTestCases.class);
				System.out.println(qcTestCases);
				for (String testCase : qcTestCases.covered()) {
					coveredTestCases.add(testCase);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		} catch (NoSuchMethodException e) {
			e.printStackTrace(System.err);
		}

		return coveredTestCases;
	}
	
	public void createZipFileOfReport(String reportPath,String testCaseQCName){		
		File dir = new File(reportPath);

		try {			
			List<File> files = (List<File>) FileUtils.listFiles(
					dir, TrueFileFilter.INSTANCE,
					TrueFileFilter.INSTANCE);
			byte[] b;

			FileOutputStream fout = new FileOutputStream(reportPath+"\\"
					+ testCaseQCName + ".zip");
			ZipOutputStream zout = new ZipOutputStream(
					new BufferedOutputStream(fout));

			for (int i = 0; i < files.size(); i++) {
			if(files.get(i).getName().contains(testCaseQCName)){
				b = new byte[1024];
				FileInputStream fin = new FileInputStream(
						files.get(i));
				zout.putNextEntry(new ZipEntry(files.get(i)
						.getName()));
				int length;
				while (((length = fin.read(b, 0, 1024))) > 0) {
					zout.write(b, 0, length);
				}
				zout.closeEntry();
				fin.close();	
			}
				
			}
			zout.close();

		} catch (Exception e) {
			System.out.println("Exception caught");
		}
		/*String fileName = new File(reportPath + testCaseQCName
				+ ".zip").getName();
		String folderName = new File(reportPath + testCaseQCName
				+ ".zip").getParent();*/
	}
	public void createResultFile(String reportPath,String runStatus){
		BufferedWriter writer = null;
		try
		{
		    writer = new BufferedWriter( new FileWriter(reportPath+"\\result.txt"));
		    writer.write(runStatus);

		}
		catch ( IOException e)
		{
		}
		finally
		{
		    try
		    {
		        if ( writer != null)
		        writer.close( );
		    }
		    catch ( IOException e)
		    {
		    }
		}
	}
	
	public String getMemoryUsedByApplication(String strAppl) throws IOException{
		Process p = Runtime.getRuntime().exec("tasklist");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(strAppl)) {
				break;
			}
		}
		return line;
	}

	@Override
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(ITestResult arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getEndMillis() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInstanceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITestNGMethod getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getStartMillis() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IClass getTestClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITestContext getTestContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTestName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Throwable getThrowable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSuccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEndMillis(long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameters(Object[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setThrowable(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}
	
	 
}
