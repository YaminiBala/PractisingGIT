import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class XHOM_ExecutionClassR_Validations {
	XHOM_HomeStream_Validations HV = new XHOM_HomeStream_Validations();
	XHOM_DemiInstall_Validations DIV = new XHOM_DemiInstall_Validations();
	XHOM_DemiDisconnect_Validations DD = new XHOM_DemiDisconnect_Validations();
	XHOM_RTP_ValidationMethods RDV = new XHOM_RTP_ValidationMethods();
	XHOM_BasicMethods BM = new XHOM_BasicMethods();
	XHOM_ScheduleUpgradeDowngradeExecutionClass SUDE = new XHOM_ScheduleUpgradeDowngradeExecutionClass();
	XHOM_CVR_ValidationCalls cvrv = new XHOM_CVR_ValidationCalls();
	String P3;

	// cancelOnDiscpart1
	// cancelOnDiscpart2
	// RestoreValidations
	// SusResStatusCheckValidation

	// -------------------------cANCEL ON dISC ----demi
	// Validations-------------------

	public void ExecutionOrder_Demi(Object input, ScriptingContext c)
			throws Exception {

		Thread.sleep(5000);
		HV.getAccoutCos_Validate(input, c);
		if (c.get("getStatus").toString().equalsIgnoreCase("ACTIVATED")) {
			HV.SuspendAccount_Validate(input, c);
			HV.deactivateAccount_Validate_CLS(input, c);
			DD.processHomeSecurityInfo_Validate_Dis(input, c);
			 DIV. orderUpdate_Validate(input, c,"DIS",c.getValue("sSERVICEorCURRENTSERVICE"));

		}
		// String status=
		// c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		// lR2.simulatorChange30DayDisconnect(status, input, c);

		 else
		 { 
			 HV.getAccoutCos_Validate(input, c);
			 HV.deleteUnactivatedAccount_Validate(input, c);
			 DD.processHomeSecurityInfo_Validate_Dis(input, c);
			 DD.DisconnectAccount_CANCEL_Validate(input, c);
			 DIV. orderUpdate_Validate(input, c,"CAN",c.getValue("sSERVICEorCURRENTSERVICE"));
		 
		  
		  }
		 
	}
	
	//-------------------------cANCEL ON dISC ---- Insight Validations-------------------

	public void ExecutionOrder_Insight(Object input, ScriptingContext c)
			throws Exception {

			Thread.sleep(5000);
			HV.getAccoutCos_Validate(input, c);
			
		if (c.get("getStatus").toString().equalsIgnoreCase("ACTIVATED"))
		{

			HV.SuspendAccount_Validate(input, c);
			HV.deactivateAccount_Validate_CLS(input, c);
			DD.processHomeSecurityInfo_Validate_Dis(input, c);
			 DIV. orderUpdate_Validate(input, c,"DIS",c.getValue("sSERVICEorCURRENTSERVICE"));

		}

		else 
		{
			
			HV.deleteUnactivatedAccount_Validate(input, c);
			DD.processHomeSecurityInfo_Validate_Dis(input, c);
			 DIV. orderUpdate_Validate(input, c,"CAN",c.getValue("sSERVICEorCURRENTSERVICE"));
		}

	}
	//-----------------------------Cancel ON Disconnect part 1 Validations------------------------------
	public void CancelOnDiscPart1(Object input, ScriptingContext c)
			throws Exception {
		
	String XML_ScenarioName = c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName");
	if(XML_ScenarioName.equalsIgnoreCase("DEMI"))
	{
		RDV.demicalls(input, c);
			
	}
	else
	{
		RDV.Insightcalls(input, c);
	}
		
		
		
	}
	//-----------------------------Cancel ON Disconnect part 2 Validations------------------------------
	public void CancelOnDiscPart2(Object input, ScriptingContext c)
			throws Exception 
			{
		
	String XML_ScenarioName = c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName");
	if(XML_ScenarioName.equalsIgnoreCase("DEMI"))
	{
		Application.showMessage("Entering Demi");
		ExecutionOrder_Demi(input, c);
			
	}
	else
	{
		Application.showMessage("Entering Insight");
		ExecutionOrder_Insight(input, c);
	}
		
		
		
			}
	//-----------------------------Restore Validations------------------------------
	
	
	public void RestoreValidations(Object input, ScriptingContext c) throws Exception
	{

		String SusFlag= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SuspendedFlag");
		Application.showMessage("Suspended Flag is ::"+SusFlag);
		
		String Acc=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_AccNumber");
		Application.showMessage("Account Number is  ::"+Acc);
		c.put("AddressDetails","notnull");
	    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		{
			
		 if (SusFlag.equals("1"))
		 {
	      Application.showMessage("Have  Resume ..!");
	      HV.getAccoutCos_Validate(input, c);
		  DIV.queryLocation_Validate(input, c);
		  DD.Activate_COPS_validate(input, c);
		  DD.processHomeSecurityInfo_Validate_Res(input, c);
		  HV.RestoreAccount_Validate(input, c);
		  DIV. orderUpdate_Validate(input, c,"NEW",c.getValue("sSERVICEorCURRENTSERVICE"));
		  
		  DIV.storeCCnum(input, c);
		  //simulatorChange(input, c); 
		 }
		 else
		 {
			 Application.showMessage("!..NO Restore calls..!");
			 DIV.queryLocation_Validate(input, c);	
			 HV.CLSgetAccount_Validate(input, c);
			 BM.NoDataSetFound(input, c, Acc, "cmb:commonOrderService/orderUpdate");
			 BM.NoDataSetFound(input, c, Acc, "COPSServices:Comcast/ActivateAccount");
			 BM.NoDataSetFound(input, c, Acc, "ucontrolsrv:AccountServicePortType/restoreAccount");
			 BM.NoDataSetFound(input, c, Acc, "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo");
		 }
		
		}
		
		else if(c.getValue("IsDemi").equalsIgnoreCase("false"))
		{
		 if (SusFlag.equals("1"))
		 {
	      Application.showMessage("Have  Resume ..!");
	      HV.getAccoutCos_Validate(input, c);
		  DIV.queryLocation_Validate(input, c);
		  DD.processHomeSecurityInfo_Validate_Res(input, c);
		  DIV. orderUpdate_Validate(input, c,"NEW",c.getValue("sSERVICEorCURRENTSERVICE"));
		  DIV.storeCCnum(input, c);
		  
		 }
		 else if(SusFlag.equals("0"))
		 {
			 Application.showMessage("!..NO Restore calls..!");
			 DIV.queryLocation_Validate(input, c);	
			 HV.CLSgetAccount_Validate(input, c);
			 BM.NoDataSetFound(input, c, Acc, "cmb:commonOrderService/orderUpdate");
			 BM.NoDataSetFound(input, c, Acc, "COPSServices:Comcast/ActivateAccount");
			 BM.NoDataSetFound(input, c, Acc, "ucontrolsrv:AccountServicePortType/restoreAccount");
			 BM.NoDataSetFound(input, c, Acc, "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo");
		 }
		 
		 else
		 {
			 Application.showMessage("!..NO Restore calls..!");
			 BM.NoDataSetFound(input, c, Acc, "cmb:commonOrderService/orderUpdate");
			 BM.NoDataSetFound(input, c, Acc, "COPSServices:Comcast/ActivateAccount");
			 BM.NoDataSetFound(input, c, Acc, "ucontrolsrv:AccountServicePortType/restoreAccount");
			 BM.NoDataSetFound(input, c, Acc, "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo");
		 }
		}
	
	}
	
	//---------------------------------------------------SusResStatusCheckValidation method-----------------------
	
	public void SusResStatusCheckValidation(Object input, ScriptingContext c) throws Exception
	{

			Application.showMessage("Validation starts for Demi....");
			Thread.sleep(10000);
			HV.getAccoutCos_Validate(input, c);
			Thread.sleep(10000);

			DIV.queryLocation_Validate(input, c);
			c.put("Scenario", c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SCENARIO_NUMBER"));
			if (c.get("Scenario").equals("4")) 
				{
					Application.showMessage("during SUSPEND  validation");
					c.put("Status", c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: Status"));
						if (c.get("Status").equals("activated"))
						{
							HV.SuspendAccount_Validate(input, c);
							DD.processHomeSecurityInfo_Validate_Sus(input, c);
							DIV.orderUpdate_Validate_SupressErrorSus(input, c);
						}
						else
						{
							DD.processHomeSecurityInfo_Validate_Sus(input, c);
							DIV.orderUpdate_Validate_SupressErrorSus(input, c);
						}
				}

			else if (c.get("Scenario").equals("5")) {

				Application.showMessage("during Restore  validation");
				c.put("Status", c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: Status"));
				if (c.get("Status").equals("activated"))
				{
					Application.showMessage("during RESTORE  validation");
					HV.RestoreAccount_Validate(input, c);
					DD.processHomeSecurityInfo_Validate_Res(input, c);
					DIV.orderUpdate_Validate_IgnoreWorklistRestore(input, c);
				}

				else
				{

					DD.processHomeSecurityInfo_Validate_Res(input, c);
					DIV.orderUpdate_Validate_IgnoreWorklistRestore(input, c);

				}

			}

			else if (c.get("Scenario").equals("11"))
			{
				Application.showMessage("during CANCEL  validation");
				HV.deleteUnactivatedAccount_Validate(input, c);
				DD.DisconnectAccount_CANCEL_Validate(input, c);
				//DD.processHomeSecurityInfo_Validate(input, c);
				//DIV.OrderUpdate_tocheckAEE(input, c);

			}

			DIV.storeCCnum(input, c);
			Thread.sleep(150000);

	}
	
	public void XHOM_ExecutionOrderPartOne_CosACTIVE30day(Object input,
			ScriptingContext c) throws Exception {

		
		 RDV.demicalls(input, c);

		Thread.sleep(5000);

	}

	//-----------------
	
	 public void ExecutionOrderPartTwo_DeceasedSimulator(Object input,ScriptingContext c) throws Exception
	    {
	    	
	    	
	    	 LibraryRtp lC =new LibraryRtp();
	    	 XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
			List<String> disReleaseIssues=new ArrayList<String>();
	    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");	    	
	         String changeValue= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: ChangeReason");
	      	 if(changeValue.equals("Deceased"))
	      		 
	      	 {
	      		
		      	
		      	DD.processHomeSecurityInfo_Validate_Dis(input, c);//DDSInstall
		      	disReleaseIssues.add(c.get("DDSInstall").toString());
		      	HV.deactivateAccount_Validate_CLS(input, c);//deactivateAccountIssues
		      	disReleaseIssues.add(c.get("deactivateAccountIssues").toString());
		      	HV.SuspendAccount_Validate(input, c);//suspendAccountIssues
		      	disReleaseIssues.add(c.get("suspendAccountIssues").toString());
				DD.DisconnectAccount_CANCEL_Validate(input, c);//DisconnectAccountIssues
				disReleaseIssues.add(c.get("DisconnectAccountIssues").toString());
				DIV.orderUpdate_Validate(input, c, "DIS",  c.getValue("sSERVICEorCURRENTSERVICE"));//setOrderstatusIssue
				disReleaseIssues.add(c.get("setOrderstatusIssue").toString());  
				XH.generateReport(input, c, disReleaseIssues, "Releasing from Table Deceased Flow-->");
	      	 }
	      	 
	      	 else
	      	 {
	      		 
	      		 
	      		ExecutionOrderPartTwo_CosACTIVE(input, c);
	        	 
	      	 }
	      	
	       
	    	 
	    }
	    
	
	
	//-------------------
	public void XHOM_ExecutionOrderPartThree_CosACTIVE(Object input,
			ScriptingContext c) throws Exception {
		XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
List<String> Dis_cosIssues=new ArrayList<String>();
		// vC.queryLocation_Validate(input, c);
		String sN = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: SCENARIO_NUMBER");
		String ExistingService = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
		String NewService = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: NEWSERVICE");
		if (sN.equals("2")) {

			BM.cosLogicFlow_CLS(input, c, ExistingService, NewService);//removeAccGrpIssues,addGroupIssues
			Dis_cosIssues.add(c.get("removeAccGrpIssues").toString());
			Dis_cosIssues.add(c.get("addGroupIssues").toString());
			DIV.orderUpdate_Validate(input, c,  c.get("Status").toString(), NewService);
			Dis_cosIssues.add(c.get("setOrderstatusIssue").toString());

		} else {

			DIV.orderUpdate_Validate(input, c,  "NEW", ExistingService);
			Dis_cosIssues.add(c.get("setOrderstatusIssue").toString());
		}

		String ComStatus = "Cancelled";// status u need to check against the
										// 30Day Table

		BM.XHOM_thirtyDisconnectTableValidation(ComStatus, input, c);//c.put("ThirtyTableValidationIssues",
		Dis_cosIssues.add(c.get("ThirtyTableValidationIssues").toString());
		XH.generateReport(input, c, Dis_cosIssues, "COS/Install after Disconnect-->");
		

	}

	public void XHOM_thirtyDay_Downgrade(Object input, ScriptingContext c)
			throws Exception {
List<String> DisDowngradeIssues=new ArrayList<String>();
XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
		BM.rtpTestInterface_Validate(input, c);//xhsEventIssues
		DisDowngradeIssues.add(c.get("xhsEventIssues").toString());
		DD.processHomeSecurityInfo_Validate_Dis(input, c);//DDSInstall
		DisDowngradeIssues.add(c.get("DDSInstall").toString());
		HV.SuspendAccount_Validate(input, c);//suspendAccountIssues
		DisDowngradeIssues.add(c.get("suspendAccountIssues").toString());
		HV.deactivateAccount_Validate_CLS(input, c);//deactivateAccountIssues
		DisDowngradeIssues.add(c.get("deactivateAccountIssues").toString());
		DD.DisconnectAccount_CANCEL_Validate(input, c);//DisconnectAccountIssues
		DisDowngradeIssues.add(c.get("DisconnectAccountIssues").toString());
		DIV.queryLocation_Validate(input, c);//queryLocationIssues
		DisDowngradeIssues.add(c.get("queryLocationIssues").toString());
		DIV.processHomeSecurityInfo_Validate(input, c);//DDSDisIssues
		DisDowngradeIssues.add(c.get("DDSDisIssues").toString());
		
		HV.CLS_createAccount_ErrorValidate(input, c);//addAccountErrorIssues
		DisDowngradeIssues.add(c.get("addAccountErrorIssues").toString());
		DIV.ErrororderUpdate_Validate(input, c, "NEW", c.getValue("ExistingGroup"), "UCNTRL-RESP-CODE-UCE-15122");//errorSetOrderSttaus
		DisDowngradeIssues.add(c.get("errorSetOrderSttaus").toString());
		String ComStatus = "Completed";
		BM.XHOM_thirtyDisconnectTableValidation(ComStatus, input, c);// ThirtyTableValidationIssues
		DisDowngradeIssues.add(c.get("ThirtyTableValidationIssues").toString());
		if (P3 != null) {
			if (P3.startsWith("8901650")) {
				DD.Jasper_Validate(input, c);//JasVal
				DisDowngradeIssues.add(c.get("JasVal").toString());
			} else if (P3.startsWith("8901640")) {
				DD.NumerixCos_Validate(input, c);
			}
		} else {
			Application.showMessage("ICCID is Null");
		}
	
XH.generateReport(input, c, DisDowngradeIssues,"Disconnect Downgrade Functionality issues-->");
	}

	public void ExecutionOrderPartTwo_CosACTIVE(Object input, ScriptingContext c)
			throws Exception {
		XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
		c.put("AddressDetails","notnull");
		// Thread.sleep(10000);
		List<String> disIssues=new ArrayList<String>();
		DIV.queryLocation_Validate(input, c);//queryLocationIssues
		disIssues.add(c.get("queryLocationIssues").toString());
		DIV.getCustomerPermitRequirements_Validate(input, c);//getCustomerPermitReqIssues
		disIssues.add(c.get("getCustomerPermitReqIssues").toString());
		Thread.sleep(5000);
		String statusCheck = "Open";
		BM.XHOM_thirtyDisconnectTableValidation(statusCheck, input, c);//ThirtyTableValidationIssues
		disIssues.add(c.get("ThirtyTableValidationIssues").toString());
		XH.generateReport(input, c, disIssues, "DEMI calls Issues Before entering into ThirtyDaysTable-->");
		
	}

	public void XHOM_CosAndRestoreValidations(Object input, ScriptingContext c)
			throws IOException, ClassNotFoundException, InterruptedException,
			SQLException, XPathExpressionException, NullPointerException,
			SAXException, ParserConfigurationException, Exception {
		// simulatorChange(input, c);
		ChangeOfServiceClass CS = new ChangeOfServiceClass();
		RTP_SimulatorClass_UpDown sim = new RTP_SimulatorClass_UpDown();
		RTP_ValidationsClass rv = new RTP_ValidationsClass();
		String newService = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: NEWSERVICE");
		String oldService = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
		String Suspended_Falg = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: SusFlag");
		Application.showMessage("Suspended flag is:" + Suspended_Falg);
		if (c.getValue("IsDemi").equalsIgnoreCase("true"))

		{

			Application.showMessage("Demi Service");
			if (Suspended_Falg.equalsIgnoreCase("TRUE")) {
				Application.showMessage("Suspended flag is true");
				if (newService.equals(oldService)) {
					Application.showMessage("Have Resume calls ..!");
					
					HV.getAccoutCos_Validate(input, c);//getAccountExistingIssue
					
					DD.Activate_COPS_validate(input, c);//Activate_COPSIssue
					DD.processHomeSecurityInfo_Validate_Res(input, c);//DDS_Res
					
					HV.RestoreAccount_Validate(input, c);//restoreAccountIssues
					DIV.orderUpdate_Validate(input, c, "RES", c.getValue("sSERVICEorCURRENTSERVICE"));//setOrderstatusIssue
				} else {
					Application
							.showMessage("Have Both Resume + COS webservice calls..!");
					HV.getAccoutCos_Validate(input, c);//getAccountExistingIssue

					DD.Activate_COPS_validate(input, c);//Activate_COPSIssue
					DD.processHomeSecurityInfo_Validate_Res(input, c);//DDS_Res
					
					HV.RestoreAccount_Validate(input, c);//restoreAccountIssues
					//DIV.orderUpdate_Validate(input, c, "RES", c.getValue("sSERVICEorCURRENTSERVICE"));

					

					BM.cosLogicFlow_CLS(input, c, oldService, newService);//addGroupIssues,removeAccGrpIssues
					DIV.orderUpdate_Validate(input, c, "CHG", newService);//setOrderstatusIssue
				}

			}

			else {
				Application.showMessage("Suspended flag is false");
				if (newService.equals(oldService)) {

					Application.showMessage("No Resume calls..!");
					HV.getAccoutCos_Validate(input, c);//getAccountExistingIssue
					

				}

				else {
					Application.showMessage("Have COS webservice calls..!");
					
					HV.getAccoutCos_Validate(input, c);//getAccountExistingIssue
					BM.cosLogicFlow_CLS(input, c, oldService, newService);//addGroupIssues,removeAccGrpIssues
					DIV.orderUpdate_Validate(input, c, "CHG", newService);//setOrderstatusIssue
				}
			}

		} else

		{
			Application.showMessage("Insight service");

			if (Suspended_Falg.equalsIgnoreCase("TRUE")) {
				if (newService.equals(oldService)) {

					Application.showMessage("Have only Resume ..!");
					
				//	HV.getAccoutCos_Validate(input, c);
					HV.RestoreAccount_Validate(input, c);//restoreAccountIssues
					DD.processHomeSecurityInfo_Validate_Res(input, c);//DDS_Res
					DIV.ErrororderUpdate_Validate(input, c, "RES", c.getValue("sSERVICEorCURRENTSERVICE"),"UCNTRL-RESP-CODE-UCE-15113");
				//	DD.orderUpdate_Validate_Res(input, c);//errorSetOrderSttaus

				}

				else {

					Application
							.showMessage("Have Both Resume + COS webservice calls..!");
					
				//	HV.getAccoutCos_Validate(input, c);;
					HV.RestoreAccount_Validate(input, c);//restoreAccountIssues
					DD.processHomeSecurityInfo_Validate_Res(input, c);//DDS_Res
					
				//DIV.orderUpdate_Validate(input, c, "RES", c.getValue("sSERVICEorCURRENTSERVICE"));
					BM.cosLogicFlow_CLS(input, c, oldService, newService);////addGroupIssues,removeAccGrpIssues
					DIV.ErrororderUpdate_Validate(input, c, "CHG", newService,"UCNTRL-RESP-CODE-UCE-15113");//errorSetOrderSttaus
					//DD.orderUpdate_COD(input, c);
				}

			} else

			{
				Application.showMessage("Suspended Flag is False");
				if (newService.equals(oldService)) {

					Application.showMessage("No resume  ..!");
					//HV.getAccoutCos_Validate(input, c);

				} else {

					Application.showMessage("Have  COS webservice calls..!");
					//HV.getAccoutCos_Validate(input, c);
					
					BM.cosLogicFlow_CLS(input, c, oldService, newService);//addGroupIssues,removeAccGrpIssues
					DIV.orderUpdate_Validate(input, c, "CHG", newService);//setOrderstatusIssue

				}
			}
		}
	}
	
	//-----------(Restore + Cos) Reports-----------
	
	
	public void XHOM_CosAndRestoreValidationsReport(Object input, ScriptingContext c)
			throws IOException, ClassNotFoundException, InterruptedException,
			SQLException, XPathExpressionException, NullPointerException,
			SAXException, ParserConfigurationException, Exception {
		// simulatorChange(input, c);
		String reports = "||";
		String Overallreports = "||";
		XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
		ChangeOfServiceClass CS = new ChangeOfServiceClass();
		RTP_SimulatorClass_UpDown sim = new RTP_SimulatorClass_UpDown();
		RTP_ValidationsClass rv = new RTP_ValidationsClass();
		String newService = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: NEWSERVICE");
		String oldService = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
		String Suspended_Falg = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: SusFlag");
		Application.showMessage("Suspended flag is:" + Suspended_Falg);
		List<String> issuesReport=new ArrayList<String>();
		List<String> ConsoleissuesReport=new ArrayList<String>();
		if (c.getValue("IsDemi").equalsIgnoreCase("true"))

		{

			Application.showMessage("Demi Service");
			if (Suspended_Falg.equalsIgnoreCase("TRUE")) {
				Application.showMessage("Suspended flag is true");
				if (newService.equals(oldService)) {
					
					issuesReport.add(c.get("getAccountExistingIssue").toString());
					issuesReport.add(c.get("Activate_COPSIssue").toString());
					issuesReport.add(c.get("DDS_Res").toString());
					issuesReport.add(c.get("restoreAccountIssues").toString());
					issuesReport.add(c.get("setOrderstatusIssue").toString());
					
					
				
				} else
				{
					
					issuesReport.add(c.get("getAccountExistingIssue").toString());
					issuesReport.add(c.get("Activate_COPSIssue").toString());
					issuesReport.add(c.get("DDS_Res").toString());
					issuesReport.add(c.get("restoreAccountIssues").toString());
					issuesReport.add(c.get("addGroupIssues").toString());
					issuesReport.add(c.get("removeAccGrpIssues").toString());
					issuesReport.add(c.get("setOrderstatusIssue").toString());
				}

			}

					
				

			else {
				
				if (newService.equals(oldService))
				{					
					issuesReport.add(c.get("getAccountExistingIssue").toString());				

				}

				else {
					issuesReport.add(c.get("getAccountExistingIssue").toString());
					issuesReport.add(c.get("addGroupIssues").toString());
					issuesReport.add(c.get("removeAccGrpIssues").toString());
					issuesReport.add(c.get("setOrderstatusIssue").toString());
					
				}
			}

		} else

		{
			Application.showMessage("Insight service");

			if (Suspended_Falg.equalsIgnoreCase("TRUE")) {
				if (newService.equals(oldService))
				{
					
					issuesReport.add(c.get("DDS_Res").toString());
					issuesReport.add(c.get("restoreAccountIssues").toString());
					
					issuesReport.add(c.get("errorSetOrderSttaus").toString());
					
					

				}

				else {

					
					issuesReport.add(c.get("DDS_Res").toString());
					issuesReport.add(c.get("restoreAccountIssues").toString());
					issuesReport.add(c.get("addGroupIssues").toString());
					issuesReport.add(c.get("removeAccGrpIssues").toString());
					issuesReport.add(c.get("errorSetOrderSttaus").toString());
					
				
				}

			} else

			{
				Application.showMessage("Suspended Flag is False");
				if (newService.equals(oldService)) {

					

				} else {

					issuesReport.add(c.get("addGroupIssues").toString());
					issuesReport.add(c.get("removeAccGrpIssues").toString());
					
					issuesReport.add(c.get("setOrderstatusIssue").toString());

				}
			}
		}
		
		
		
		 XH.generateReport(input, c, issuesReport, "Restore + COS Functionality Validation-->");
	}
	
	
	//----------------------------

	public void ExecutionOrderPartTwo_CosSuSpendFlag(Object input,
			ScriptingContext c) throws Exception {

		XHS_resumeFlows_PendingChange RF = new XHS_resumeFlows_PendingChange();
		String status = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: status");
		RTP_ValidationsClass_UpDown RV = new RTP_ValidationsClass_UpDown();
		XHOM_CosAndRestoreValidations(input, c);
		XHOM_CosAndRestoreValidationsReport(input, c);
		String ComStatus = "Cancelled";
		// lRp.simulatorChange30DayDisconnect(status, input, c);
		BM.XHOM_thirtyDisconnectTableValidation(ComStatus, input, c);
		Application.showMessage("thirty Day Status"+c.get("ThirtyTableValidationIssues"));// status
																		// completed.

	}

	public void ExecutionOrderPartTwo_TransferFlag(Object input,
			ScriptingContext c) throws Exception {

		XHOM_DemiInstall_Validations rV = new XHOM_DemiInstall_Validations();
		RTP_ValidationsClass_UpDown rU = new RTP_ValidationsClass_UpDown();
		RTP_SimulatorClass_UpDown rS = new RTP_SimulatorClass_UpDown();
		FunctionLibrary_A fA = new FunctionLibrary_A();
		CancelClass cc = new CancelClass();
		List<String> disIssues=new ArrayList<String>();
		HV.getAccoutCos_Validate(input, c);
		XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
		String changeValue = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: TransferFlag");
		Application.showMessage("Transfer flag Value is::"
				+ c.getValue("RTPDataSourceCollections",
						"RTP_UpDownInputs: TransferFlag"));
		if (changeValue.equalsIgnoreCase("true"))

		{
			// disconnect calls.
			HV.deactivateAccount_Validate_CLS(input, c);//deactivateAccountIssues
			disIssues.add(c.get("deactivateAccountIssues").toString());
			DD.DisconnectAccount_CANCEL_Validate(input, c);//DisconnectAccountIssues
			disIssues.add(c.get("DisconnectAccountIssues").toString());
			DIV.orderUpdate_Validate(input, c, "DIS",  c.getValue("sSERVICEorCURRENTSERVICE"));//setOrderstatusIssue
			disIssues.add(c.get("setOrderstatusIssue").toString());
		}

		else {
			c.put("AddressDetails","notnull");
			DIV.queryLocation_Validate(input, c);//queryLocationIssues
			disIssues.add(c.get("queryLocationIssues").toString());
			DIV.getCustomerPermitRequirements_Validate(input, c);//getCustomerPermitReqIssues
			disIssues.add(c.get("getCustomerPermitReqIssues").toString());
			Thread.sleep(5000);
			String statusCheck = "Open";
			BM.XHOM_thirtyDisconnectTableValidation(statusCheck, input, c);//ThirtyTableValidationIssues
			disIssues.add(c.get("ThirtyTableValidationIssues").toString());
			
			
		}
		XH.generateReport(input, c, disIssues, "Transfer Flag scenarios Issues-->");
		
	}

	public void ExecutionOrderPartTwo_HandleInvalidService(Object input,
			ScriptingContext c) throws Exception {

		Thread.sleep(8000);
		DIV.queryLocation_Validate(input, c);
		String statusCheck = "Open";
		BM.XHOM_thirtyDisconnectTableValidation(statusCheck, input, c);
		/*
		 * String status = c.getValue("RTPDataSourceCollections",
		 * "RTP_UpDownInputs: status");
		 * Application.showMessage("Status needs to be updated is ::" + status);
		 * lRp.simulatorChange30DayDisconnectHandleInvalidService(status, input,
		 * c);
		 */
		Thread.sleep(50000);

	}

	public void IgnoreUCE15101_CVR_CLS(Object input, ScriptingContext c)
			throws Exception {
		XHOM_CVR_AllFlows_ValidationCalls allFlows = new XHOM_CVR_AllFlows_ValidationCalls();
		XHOM_CVR_ValidationCalls CVR=new XHOM_CVR_ValidationCalls();
		HV.CLSgetAccount_Validate(input, c);
		List<String> cvrIssue=new ArrayList<String>();
		XHOM_LibraryRtp XH=new XHOM_LibraryRtp();

		if (c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: SCENARIO_NUMBER").equalsIgnoreCase("3")) {
			CVR.processHomeSecurityInfoCVR_Validate(input, c,"ADD");//DDS_CVRIssues
			cvrIssue.add(c.get("DDS_CVRIssues").toString());
			CVR.BNERcallCVR_Validate_Post(input, c);//BNERPostIssues
			cvrIssue.add(c.get("BNERPostIssues").toString());
			BM.XHOM_threeDaycvrTableValidation("Open", input, c);//threeDaycvrTableValidationIssues
			cvrIssue.add(c.get("threeDaycvrTableValidationIssues").toString());
			BM.changingdateThreeDayCVRTable(input, c);
			Thread.sleep(180000);
			BM.XHOM_threeDaycvrTableValidation("Completed", input, c);//threeDaycvrTableValidationIssues
			cvrIssue.add(c.get("threeDaycvrTableValidationIssues").toString());
			CVR.processHomeSecurityInfoCVR_Validate(input, c,"REMOVE");//DDS_CVRIssues
		    cvrIssue.add(c.get("DDS_CVRIssues").toString());
		    CVR.BNERcallCVR_Validate(input, c, "0", "Disconnected");//BNERIssues
			cvrIssue.add(c.get("BNERIssues").toString());
			XH.generateReport(input, c, cvrIssue, "Disconnect CVr -ANF Functionality-->");
			
			
			
			
		} else {
			CVR.processHomeSecurityInfoCVR_Validate(input, c,"REMOVE");//DDS_CVRIssues
		    cvrIssue.add(c.get("DDS_CVRIssues").toString());
		    CVR.BNERcallCVR_Validate(input, c, "0", "Cancelled");
		    cvrIssue.add(c.get("BNERIssues").toString());
		    XH.generateReport(input, c, cvrIssue, "Cancel CVr -ANF Functionality-->");
			 
			

		}
	}
	
	//Added by Sruthi
	public void ExecutionOrderPartOne_CosACTIVETerminalDetails(Object input,ScriptingContext c) throws Exception
    {
    	   	
      
	 
	Application.showMessage("Entering ExecutionOrderPartOne_CosACTIVETerminalDetails");
      	demicallsReleasefrom30day(input, c);
    
    	
    }
	
	public void demicallsReleasefrom30day(Object input , ScriptingContext c ) throws Exception
    {
    	 RTP_ValidationsClass_UpDown vC=new RTP_ValidationsClass_UpDown();
    	 LibraryRtp_UpDown lR= new LibraryRtp_UpDown();
    	 RTP_ValidationsClass rV=new RTP_ValidationsClass();
    	 String val="true";
         int noofCallsValidated=1;
         int i;
         OUTER1:
         for( i=0;i<=noofCallsValidated;i++)
         {
      	   Application.showMessage("The val of I is"+i);
      	   switch(i)
      	   {
      	   case 0:
      		  val= DIV.createCMSAccountID_Validate(input, c);         		  
      		   break;
      		   //Need to add method
      	   /*case 1:
      		   val=  OrderUpdate_Releasefromtable(input, c,1);
      		   break;*/
      	  
      		   default:
      			   break;
      	   }
      	   if(val.equalsIgnoreCase("false"))
      	   {
      		   c.report(" quiting entire flow validation");
      		   break OUTER1;
      	   }
      	   else
      	   {
      		   continue OUTER1;
      	   }
         }
	}

	 public void ExecutionOrdergetTerminalDetailsParttwowithSIMULATOR(Object input,ScriptingContext c) throws Exception
	    {
	    List<String> disReleaseIssues=new ArrayList<String>()	;
	    XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	//disconnecting and Releasing entries.
		 
		     String disconnectstatus=c.getValue("RTPDataSourceCollections","dB_Simulator: afterDisconnectStatus");
		     ExecutionOrderPartTwo_CosACTIVE(input,c);
	    	  BM.changingMonthsThirtyDaysTable(input, c);	    	
	    	 Thread.sleep(180000);
	    	 
	    	 
 //Disconnect calls validation
	    	 
	    	 if(disconnectstatus.equalsIgnoreCase("ACTIVE"))
	    	 {
	      	 String ComStatus="Completed";
	      	String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	      	DD.processHomeSecurityInfo_Validate_Dis(input, c);//DDSInstall
	      	disReleaseIssues.add(c.get("DDSInstall").toString());
	      	HV.deactivateAccount_Validate_CLS(input, c);//deactivateAccountIssues
	      	disReleaseIssues.add(c.get("deactivateAccountIssues").toString());
	      	HV.SuspendAccount_Validate(input, c);//suspendAccountIssues
	      	disReleaseIssues.add(c.get("suspendAccountIssues").toString());
			DD.DisconnectAccount_CANCEL_Validate(input, c);//DisconnectAccountIssues
			disReleaseIssues.add(c.get("DisconnectAccountIssues").toString());
			//DIV.orderUpdate_Validate(input, c, "DIS",  c.getValue("sSERVICEorCURRENTSERVICE"));//setOrderstatusIssue
			c.put("ODStatus","DIS");
			  	 
	    	BM.XHOM_thirtyDisconnectTableValidation(ComStatus, input, c);//ThirtyTableValidationIssues
	    	disReleaseIssues.add(c.get("ThirtyTableValidationIssues").toString());
	    	
	    	 }
	    	 
	    	 else
	    	 {
	    		 
	    		HV.deleteUnactivatedAccount_Validate(input, c);//removeAccountIssues
	    		disReleaseIssues.add(c.get("removeAccountIssues").toString());
       		   DD.DisconnectAccount_CANCEL_Validate(input, c);
       		disReleaseIssues.add(c.get("DisconnectAccountIssues").toString());
       		  // DIV.orderUpdate_Validate(input, c,"CAN",c.getValue("sSERVICEorCURRENTSERVICE")); 
       		c.put("ODStatus","CAN");
       		 
       	   
	    		 
	    	 }
	    //Jasper or Numerex Validation
	    	 
	    	 if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId").equalsIgnoreCase("8901640100735449527"))
	    	 {
	    		 DD.NumerixCos_Validate(input, c);
	    		 disReleaseIssues.add(c.get("NumeDeactivateUnitIssues").toString());
	    		 DIV.ErrororderUpdate_Validate(input, c,c.get("ODStatus").toString(),c.getValue("sSERVICEorCURRENTSERVICE"),"NUMEREX-RESP-CODE-300"); 
	    		 disReleaseIssues.add(c.get("errorSetOrderSttaus").toString());  
	    	 }
	    	 else
	    	 {
	    		 DD.getTerminalDetails_Validate(input, c);//getTerminalDetailsIssues
	    		 disReleaseIssues.add(c.get("getTerminalDetailsIssues").toString()); 
		    	 String getTerminalDetailsStatus= c.getValue("RTPDataSourceCollections","dB_Simulator: getTerminalStatus");
		    	 if(getTerminalDetailsStatus.equals("RETIRED_NAME") && c.get("getTerminalDetailsIssues").toString().equalsIgnoreCase("no"))
		    	 {    
		    		Application.showMessage("No EDIT TERMINAL Call ");
		    		System.out.println("No EDIT TERMINAL Call ");
		    		//DD.Jasper_Validate(input, c);	 //JasVal  
		    		//disReleaseIssues.add(c.get("JasVal").toString()); 
		    	 }
		    	 else
		    	 {
		    		
		    				    	 }
		    	 DIV.orderUpdate_Validate(input, c,c.get("ODStatus").toString(),c.getValue("sSERVICEorCURRENTSERVICE")); 
		 	    
		    	 disReleaseIssues.add(c.get("setOrderstatusIssue").toString()); 
	    	 }
	    	 
	    	
	    	 XH.generateReport(input, c, disReleaseIssues, "Issues after releasing from Table-->");
	    	 
	    }
	 
	 //------------cvr4xi
	 
	 public void DisconnectCVR4XI_HSservices(Object input,ScriptingContext c) throws Exception
	    {
	    List<String> disReleaseIssues=new ArrayList<String>()	;
	    XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	//disconnecting and Releasing entries.
		 
		    // String disconnectstatus=c.getValue("RTPDataSourceCollections","dB_Simulator: afterDisconnectStatus");
		     ExecutionOrderPartTwo_CosACTIVE(input,c);
	    	  BM.changingMonthsThirtyDaysTable(input, c);	    	
	    	 Thread.sleep(180000);
	    	 DD.processHomeSecurityInfo_Validate_Dis(input, c);
	    	 disReleaseIssues.add(c.get("DDSInstall").toString());
	    	 DD.DisconnectAccount_CANCEL_Validate(input, c);//DisconnectAccountIssues
				disReleaseIssues.add(c.get("DisconnectAccountIssues").toString());
				c.put("ODStatus","DIS");
				DIV.orderUpdate_Validate(input, c,c.get("ODStatus").toString(),c.getValue("sSERVICEorCURRENTSERVICE")); 
		 	    
		    	 disReleaseIssues.add(c.get("setOrderstatusIssue").toString()); 
	    	 XH.generateReport(input, c, disReleaseIssues, "Issues after releasing from Table-->");
	    	 
	    }
	 public void SusResStatusCheckValidation_ExceutionOrderPartOne(Object input,
             ScriptingContext c) throws ClassNotFoundException, IOException,
             InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException, InstantiationException, IllegalAccessException,Exception {
	
Thread.sleep(6000);
XHOM_BasicMethods BM = new XHOM_BasicMethods();
BM.rtpTestInterface_Validate(input, c);
//vC.queryLocation_Validate(input, c);

HV.getAccoutCos_Validate(input, c);
//fA.processHomeSecurityInfo_Active_Validate(input, c);
//vC.createAccount_Validate(input, c);
Thread.sleep(10000);
//vC.orderUpdate_Validate(input, c);
//vC.OrderUpdate_tocheckAEE(input, c);

//lRp.simulatorChange(input, c);

}

}