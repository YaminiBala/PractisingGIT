package com.comcast.bulkOM;



import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.comcast.bom.commons.BomApplication;
import com.comcast.bom.data.CustomerDetails;
import com.comcast.bom.data.LoginDetails;
import com.comcast.bom.data.Mongoconnect;
import com.comcast.bom.data.Ms;
import com.comcast.bom.pages.BomHomePage;
import com.comcast.bom.pages.BomLoginPage;
import com.comcast.bom.pages.BomUploadFilePage;
import com.comcast.utils.ComcastTest;
import com.comcast.utils.DataTable;
import com.comcast.utils.SoapRequest;

public class X1_DVRDCT extends ComcastTest {
	
	public WebDriver driver;
	// public WebDriver browser;
	public BomLoginPage BLP;
	public BomHomePage BHP;
	public LoginDetails loginInfo;
	public BomApplication bomApplication;
	public SoapRequest SR;
	public Mongoconnect mc;
	public Ms obj;
	public BomUploadFilePage BUF;
	public SoapRequest sr;

	@BeforeTest
	public void prerequisite() throws Exception {
		synchronized (this) {
			bomApplication = new BomApplication(browser, report);
			BHP = bomApplication.openRelevantApplication();
			BLP = new BomLoginPage(browser, report);
			BHP = new BomHomePage(browser, report);
		}
		
		
		browser.manage().window().maximize();
		//BLP = new BomLoginPage(browser, report);
		
		SR = new SoapRequest(report);
		mc = new Mongoconnect(report);
		BUF = new BomUploadFilePage(browser, report);
		obj = new Ms();
	}

	@BeforeMethod
    public void getTestName(Method method)
    {
		 //DataTable.testName =  method.getName();
		loginInfo = LoginDetails.loadFromDatatable(dataTable, method.getName());
        System.out.println("Test name: " + method.getName());       
    }
	

	@Test
	public  void ValidateX1_DVRDCT_CSGResidential_1() throws Exception {
		//dataTable = new DataTable(testCaseName);
		//DataTable.testName = "ValidateX1_DVRDCT_CSGResidential_1";
			//loginInfo = LoginDetails.loadFromDatatable(dataTable, "ValidateX1_DVRDCT_CSGResidential_1");
			@SuppressWarnings("unused")
			CustomerDetails customerDetails = CustomerDetails.loadFromDatatable(dataTable);
			BLP.LoginToBom(loginInfo);
		
			BUF.UploadFileProg(customerDetails);
			mc.preCondition_X1();
			Thread.sleep(10000);
			BHP.enterAccountForX1(customerDetails);
			Thread.sleep(10000);
			mc.postsubmission_CheckEligibility_X1();
			Thread.sleep(10000);
			mc.AMS_Validation();
			mc.QDS_Validation();
			mc.Qss_Validation();
			mc.BOM_ORDER_Validation();
			mc.Getsikorderid();
			//sr.CheckForErrors();
	}
/*	@Test
	public void ValidateX1_DVRDCT_CSGResidential_2() throws Exception {
		ValidateX1_DVRDCT_CSGResidential_1();
	}
	*/
	
	@Test
	public void ValidateX1_DVRDCT_CSGResidential_2() throws Exception {
		
		//DataTable.testName = "ValidateX1_DVRDCT_CSGResidential_2";
		//dataTable = new DataTable(testCaseName);
		//loginInfo = LoginDetails.loadFromDatatable(dataTable,"ValidateX1_DVRDCT_CSGResidential_2");
		
		CustomerDetails customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		BLP.LoginToBom(loginInfo);
		BUF.UploadFileProg(customerDetails);
		mc.preCondition_X1();
		Thread.sleep(10000);
		BHP.enterAccountForX1(customerDetails);
//		Thread.sleep(10000);
		mc.postsubmission_CheckEligibility_X1();
		Thread.sleep(10000);
		mc.AMS_Validation();
		mc.QDS_Validation();
		mc.Qss_Validation();
		mc.BOM_ORDER_Validation();
		mc.Getsikorderid();
		//sr.CheckForErrors();
	}
	
	@Test
	public void ValidateX1_DVRDCT_CSGResidential_3() throws Exception {
		//dataTable = new DataTable(testCaseName);
		//loginInfo = LoginDetails.loadFromDatatable(dataTable);
		CustomerDetails customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		BLP.LoginToBom(loginInfo);
		BUF.UploadFileProg(customerDetails);
		mc.preCondition_X1();
		Thread.sleep(10000);
		BHP.enterAccountForX1(customerDetails);
//		Thread.sleep(10000);
		mc.postsubmission_CheckEligibility_X1();
		Thread.sleep(10000);
		mc.AMS_Validation();
		mc.QDS_Validation();
		mc.Qss_Validation();
		mc.BOM_ORDER_Validation();
		mc.Getsikorderid();
		//sr.CheckForErrors();
	}
	
	/*@Test
	public void ValidateX1_DVRDCT_CSGResidential_4() throws Exception {
		//loginInfo = LoginDetails.loadFromDatatable(dataTable);
		CustomerDetails customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		BLP.LoginToBom(loginInfo);
		BUF.UploadFileProg(customerDetails);
		mc.preCondition_X1();
		Thread.sleep(10000);
		BHP.enterAccountForX1(customerDetails);
//		Thread.sleep(10000);
		mc.postsubmission_CheckEligibility_X1();
		Thread.sleep(10000);
		mc.AMS_Validation();
		mc.QDS_Validation();
		mc.Qss_Validation();
		mc.BOM_ORDER_Validation();
		mc.Getsikorderid();
		//sr.CheckForErrors();
	}
	
	@Test
	public void ValidateX1_DVRDCT_CSGResidential_5() throws Exception {
		loginInfo = LoginDetails.loadFromDatatable(dataTable);
		CustomerDetails customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		BLP.LoginToBom(loginInfo);
		BUF.UploadFileProg(customerDetails);
		mc.preCondition_X1();
		Thread.sleep(10000);
		BHP.enterAccountForX1(customerDetails);
//		Thread.sleep(10000);
		mc.postsubmission_CheckEligibility_X1();
		Thread.sleep(10000);
		mc.AMS_Validation();
		mc.QDS_Validation();
		mc.Qss_Validation();
		mc.BOM_ORDER_Validation();
		mc.Getsikorderid();
		//sr.CheckForErrors();
	}*/
	
	@AfterTest
	public void Cleanup()
	{
		tearDown();
		CustomerDetails customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		mc.BulkomDBCleanup();
		String xmlSendPostString_cancelsik = mc.billerCancelRequest(customerDetails.accountNumber);
		String endPoint_cancel = " http://oct.cable.comcast.com/oct.asmx?wsdl";
		mc.sendARequest_sikcancel(endPoint_cancel, xmlSendPostString_cancelsik);
		
	}
	
	/*@Test
	public void symCommonDemo(){
		loginInfo = LoginDetails.loadFromDatatable(dataTable);
		CustomerDetails customerDetails = CustomerDetails
				.loadFromDatatable(dataTable);
		browser.close();
		browser = getDriver(settings.getBrowser());
		browser.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		report.setDriver(browser);
		BomLoginPage testBom= new BomLoginPage(browser, report);
		testBom.loginSymComm(customerDetails.accountNumber);	
		}*/
}
