import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class HandleInvalidServices 
{
     LibraryRtp lR= new LibraryRtp();
     RTP_ValidationsClass rV=new RTP_ValidationsClass();
     
	
	 
	 public void initialization(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
	 {
		lR.IterationLogic(input, c);
		 execution(input, c);
		 
		 
	 }
	 
	 public void InstallExecution(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, InstantiationException, IllegalAccessException, SAXException, SQLException
	 {
		 rV.demicalls(input, c);
	 }
	 public void CosExecution(Object input, ScriptingContext c) throws ClassNotFoundException, SQLException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
	 {
		    ChangeOfServiceClass CS = new ChangeOfServiceClass();
	    	
	    	Thread.sleep(40000);
	    	CS.getAccoutCos_Validate(input, c);
	    	rV.queryLocation_Validate(input,c);
	    	CS.cosLogicFlow(input, c);   	
	    	orderUpdateCos_Validate(input, c);
		 
		 if(c.get("pIsValidFromService").toString().equalsIgnoreCase("false"))
  	   {
  		   SimulatorSettingsCOSOFF(input, c);
  	   }
  	   else
  	   {
  		  Application.showMessage("No need to turnOFF simulator..!");
  	   }
		 
	 }
	 public void SuspendExecution(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, Exception
	 {
		 rV.SuspendValidations(input, c);
	 }
	 public void RestoreExecution(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
	 {
		 rV.RestoreValidations(input, c);
	 }
	 public void CancelExecution(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
	 {
		 rV.CancelValidations(input, c);
	 }
	 public void DisconnectExecution(Object input, ScriptingContext c)
	 {
		 
	 }
	
	 
	 //350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV
	 public String SetServiceIdentificationRule(Object input, ScriptingContext c) throws IOException
	 {
		 String NewService=null;
		 String Service=c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"); 
		 String IsValidFromService = "true";
		 if(Service.equals(null))
		 {
			 Application.showMessage("Your Input Parameter list has no service in the Excel!!!");
		 }
		 else
		 {
		 Application.showMessage("The Service found is ::"+ Service);
		 }
		 if(Service.equals("350 + ThermostatDataProvider + SecureEV"))
		 { 
			 IsValidFromService="false";
			 c.put("nService", "350 + ThermostatDataProvider"); 
		 }
		 else if(Service.equals("350 + ThermostatDataProvider + ThermostatDataProvider"))
		 {
			 IsValidFromService="false";
			 c.put("nService", "350 + ThermostatDataProvider");
		 }
		 else if(Service.equals("350 + SecureEV"))
		 {
			 IsValidFromService="false";
			 c.put("nService", "350");
		 }
		 else
		 {
			 Application.showMessage("This service is not added to handle invalid Services. Please check the input parameter of Homesecurity Configurations in UI..!! So...");
			 //NewService=c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE");
			 c.put("nService", Service);
		 }
		  NewService =c.get("nService").toString();
		  c.put("pIsValidFromService",IsValidFromService);
		  Application.showMessage("Service to be validated is::"+NewService);
		  Application.showMessage("Your Base Service is::"+c.get("pIsValidFromService").toString());
		  return NewService;
  }
	 
	 
	 public String SetServiceIdentificationRule1COS(Object input, ScriptingContext c) throws IOException
	 {
		 String NewService=null;
		 String Service=c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"); 
		 String IsValidToService = "true";
		 if(Service.equals(null))
		 {
			 Application.showMessage("Your Input Parameter list has no service in the Excel!!!");
		 }
		 else
		 {
		 Application.showMessage("The Service found is ::"+ Service);
		 }
		 if(Service.equals("350 + ThermostatDataProvider + SecureEV"))
		 {
			 IsValidToService="false";
			 c.put("nService", "350 + ThermostatDataProvider"); 
		 }
		 else if(Service.equals("350 + ThermostatDataProvider + ThermostatDataProvider"))
		 {
			 IsValidToService="false";
			 c.put("nService", "350 + ThermostatDataProvider");
		 }
		 else if(Service.equals("350 + SecureEV"))
		 {
			 IsValidToService="false";
			 c.put("nService", "350");
		 }
		 else
		 {
			 
			 Application.showMessage("This service is not added to handle in invalid Services. Please check the input parameter of Homesecurity Configurations in UI..!!");
			 c.put("nService", Service);
		 }
		  NewService =c.get("nService").toString();
		  c.put("pIsValidToService",IsValidToService);
		  Application.showMessage("Service to be validated is::"+NewService);
		  Application.showMessage("Your Base Service is::"+c.get("pIsValidToService").toString());
		  return NewService;
  }
	 
	 
	 public String SetServiceIdentificationRuleSusRes(Object input, ScriptingContext c) throws IOException
	 {
		 String NewService=null;
		 String Service=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolServiceSusRes"); 
		 String IsValidToService = "true";
		 if(Service.equals(null))
		 {
			 Application.showMessage("Your Input Parameter list has no service in the Excel!!!");
		 }
		 else
		 {
		 Application.showMessage("The Service found is ::"+ Service);
		 }
		 if(Service.equals("350 + ThermostatDataProvider + SecureEV"))
		 {
			 IsValidToService="false";
			 c.put("nService", "350 + ThermostatDataProvider"); 
		 }
		 else if(Service.equals("350 + ThermostatDataProvider + ThermostatDataProvider"))
		 {
			 IsValidToService="false";
			 c.put("nService", "350 + ThermostatDataProvider");
		 }
		 else if(Service.equals("350 + SecureEV"))
		 {
			 IsValidToService="false";
			 c.put("nService", "350");
		 }
		 else
		 {
			 
			 Application.showMessage("This service is not added to handle in invalid Services. Please check the input parameter of Homesecurity Configurations in UI..!!");
			 c.put("nService", Service);
		 }
		  NewService =c.get("nService").toString();
		  c.put("pIsValidToService",IsValidToService);
		  Application.showMessage("Service to be validated is::"+NewService);
		  Application.showMessage("Your Base Service is::"+c.get("pIsValidToService").toString());
		  return NewService;
  }
	 public void SimulatorSettings(Object input, ScriptingContext c) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	 {
		
				

				Statement st = lR.dBConnect(input, c);
		        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		        while (rs.next()) 
		        {
		          String beforeSim = rs.getString(2);
		       
		          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
		        
		        }
		        st.close();
		        Statement st0 = lR.dBConnect(input, c);
		        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101017052874</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><sikNonBillingGlobal>0</sikNonBillingGlobal><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><iContServiceDemi>350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV</iContServiceDemi><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
		        st0.executeUpdate(sql);
		       
		    
		        st0.close();
		        Thread.sleep(300000);
		        Statement st1 = lR.dBConnect(input, c);
		        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		        while (rs1.next()) 
		        {
		        	
		         String afterSim = rs1.getString(2);
		         Application.showMessage("Db Simulator After Updation ::"+afterSim);
		        
		        }
		        st0.close();
	 
	     }
	 public  String getUcontrolService(String data)
	    {
	    	String service;
	    	return service= data.replaceAll(" \\+ ", "|");
	    	
	    }
	 
	 public void SimulatorSettingsCOSON(Object input, ScriptingContext c) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	 {
		
				
		        String Service=getUcontrolService(c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"));
                String ccentralNumber=c.getValue("CcentralNum");
                
				Statement st = lR.dBConnect(input, c);
		        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		        while (rs.next()) 
		        {
		          String beforeSim = rs.getString(2);
		       
		          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
		        
		        }
		        st.close();
		        Statement st0 = lR.dBConnect(input, c);
		        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>1</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101017055951</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+ccentralNumber+"</centralStationAccountNumber><uControlGroupValue>"+Service+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getTerminalStatus>null</getTerminalStatus></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
		        st0.executeUpdate(sql);
		       
		    
		        st0.close();
		        Thread.sleep(300000);
		        Statement st1 = lR.dBConnect(input, c);
		        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		        while (rs1.next()) 
		        {
		        	
		         String afterSim = rs1.getString(2);
		         Application.showMessage("Db Simulator After Updation ::"+afterSim);
		        
		        }
		        st0.close();
	 
	     }
	 public void SimulatorSettingsSusResON(Object input, ScriptingContext c) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	 {
		
		        String group=getUcontrolService(c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolServiceSusRes"));
		        String Service=getUcontrolService(c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"));
                String ccentralNumber=c.getValue("CcentralNum");
                
				Statement st = lR.dBConnect(input, c);
		        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		        while (rs.next()) 
		        {
		          String beforeSim = rs.getString(2);
		       
		          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
		        
		        }
		        st.close();
		        Statement st0 = lR.dBConnect(input, c);
		        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>1</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101017055951</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+ccentralNumber+"</centralStationAccountNumber><uControlGroupValue>"+Service+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getTerminalStatus>null</getTerminalStatus></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
		        st0.executeUpdate(sql);
		       
		    
		        st0.close();
		        Thread.sleep(300000);
		        Statement st1 = lR.dBConnect(input, c);
		        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		        while (rs1.next()) 
		        {
		        	
		         String afterSim = rs1.getString(2);
		         Application.showMessage("Db Simulator After Updation ::"+afterSim);
		        
		        }
		        st0.close();
	 
	     }
	 
	 
	 public void SimulatorSettingsCOSOFF(Object input, ScriptingContext c) throws ClassNotFoundException, SQLException, IOException, InterruptedException
	 {
		
				
		        String Service=getUcontrolService(c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"));
                String ccentralNumber=c.getValue("CcentralNum");
                
				Statement st = lR.dBConnect(input, c);
		        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		        while (rs.next()) 
		        {
		          String beforeSim = rs.getString(2);
		       
		          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
		        
		        }
		        st.close();
		        Statement st0 = lR.dBConnect(input, c);
		        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101017055951</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+ccentralNumber+"</centralStationAccountNumber><uControlGroupValue>"+Service+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getTerminalStatus>null</getTerminalStatus></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
		        st0.executeUpdate(sql);
		       
		    
		        st0.close();
		        Thread.sleep(300000);
		        Statement st1 = lR.dBConnect(input, c);
		        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		        while (rs1.next()) 
		        {
		        	
		         String afterSim = rs1.getString(2);
		         Application.showMessage("Db Simulator After Updation ::"+afterSim);
		        
		        }
		        st0.close();
	 
	     }
	 
	 
	 
	 public void execution(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException
	  	{
	  	try
	  	{
	  	//String arg0="RTP_InputParams";
	  	       // String arg1="SCENARIO_NUMBER";
	  		  LibraryRtp Lp=new LibraryRtp();
	  		   Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
	  	        String sc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER");
	  	        Application.showMessage("SCENARIO_NUMBER::"+sc);
	  	        String XML_AccNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");
	  	        c.setValue("ACC_input",XML_AccNumber);
	  	      
	  	        //Application.showMessage("Value is"+c.getValue("ACC_input"));
	  	        //ACC_input=XML_AccNumber;
	  	        Application.showMessage("XML_AccNumber::"+XML_AccNumber);
	  	        String XML_ScenarioName=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_ScenarioName");
	  	        Application.showMessage("XML_ScenarioName::"+XML_ScenarioName);
	  	        String SERVICEorCURRENTSERVICE=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
	  	        Application.showMessage("SERVICEorCURRENTSERVICE::"+SERVICEorCURRENTSERVICE);
	  	        String NEWSERVICE=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: NEWSERVICE");
	  	        Application.showMessage("NEWSERVICE::"+NEWSERVICE);
	  	        String HOUSE_KEY=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: HOUSE_KEY");
	  	        Application.showMessage("HOUSE_KEY"+HOUSE_KEY);
	  	        
	  	        String XML_EndPoint=c.getValue("RTPNormalScenariosdS", "DbConnections: XML_Enpoint");
	  		    String DB_Host=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_HOST");
	  		    String TestCaseName=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseName");
	  		    String dB_Password=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_Password");
	  		    String dB_Username=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_UserName");
	  		    String dB_Port=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_Port");
	  		    String dB_serviceName=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_ServiceName");
	  		    Application.showMessage("Endpoint is ::"+XML_EndPoint);
	  		    Application.showMessage("DB_HOST is ::"+DB_Host);
	  		    Application.showMessage("TestCaseName is ::"+TestCaseName);
	  		    Application.showMessage("dB_Password is ::"+dB_Password);
	  		    Application.showMessage("dB_Username is ::"+dB_Username);
	  		    Application.showMessage("dB_Port is ::"+dB_Port);
	  		    Application.showMessage("dB_serviceName is ::"+dB_serviceName);
	  		    Application.showMessage("XML_AccNumber is ::"+XML_AccNumber);
	  		    c.setValue("sHOUSE_KEY", HOUSE_KEY); 
	  		    c.setValue("ACC_input",XML_AccNumber);
	  		    c.setValue("DB_Host", DB_Host);
	  		    c.setValue("dB_Password",dB_Password);
	  		    c.setValue("dB_Username",dB_Username);
	  		    c.setValue("dB_Port",dB_Port);
	  		    c.setValue("dB_serviceName",dB_serviceName);
	  		    c.setValue("XML_EndPoint",XML_EndPoint);
	  		    c.setValue("sSERVICEorCURRENTSERVICE",SERVICEorCURRENTSERVICE);
	  		    
	  		     if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ScenarioType").toString().equals(null))
	  		     {
	  		    	 Application.showMessage("NoScenario Type defined");
	  		    	 Map<String, String> ResultMap = new HashMap<String,String>();

	  	  	         ResultMap= Lp.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
	  	  		     c.setValue("ExistingGroup",ResultMap.get("group"));
	  	  		     c.setValue("ExistingService", ResultMap.get("service"));
	  	  		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	  		     }
	  		     else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ScenarioType").toString().equalsIgnoreCase("Handle Invalid service"))
	  		     {
	  		    	  
	  		    	 HandleInvalidServices hI=new HandleInvalidServices();
	                 Application.showMessage("Scenario Type defined");
	  		    	 
	  		    	 String service= hI.SetServiceIdentificationRule(input, c);
	  		    	 Application.showMessage("The service is to be Validated..."+service);
	  		    	 Map<String, String> ResultMap = new HashMap<String,String>();

	  	  	         ResultMap= Lp.splitter(service);
	  	  		     c.setValue("ExistingGroup",ResultMap.get("group"));
	  	  		     c.setValue("ExistingService", ResultMap.get("service"));
	  	  		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	  		    	 if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("2"))
	  		    	 {
	  		    	   Application.showMessage("The scenario is COS..!");
	  		    	   hI.SetServiceIdentificationRule(input, c);
	  		    	   if(c.get("pIsValidFromService").toString().equalsIgnoreCase("false"))
	  		    	   {
	  		    		   hI.SimulatorSettingsCOSON(input, c);
	  		    	   }
	  		    	   else
	  		    	   {
	  		    		    hI.SimulatorSettings(input, c);
	  		    	   }
	  		    	 }
	  		    	 else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("4"))
	  		    	 {
	  		    		 Application.showMessage("The scenario is SUSPEND..!");
		  		    	  hI.SetServiceIdentificationRuleSusRes(input, c);
		  		    	   
	  		    	 }
	  		    	 else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("5"))
	  		    	 {
	  		    		  Application.showMessage("The scenario is REstore..!");
		  		    	  hI.SetServiceIdentificationRuleSusRes(input, c);
	  		    	 }
	  		    	 else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("11"))
	  		    	 {
	  		    		 
	  		    	 }
	  		    	 else
	  		    	 {
	  		    		hI.SimulatorSettings(input, c); 
	  		    	 }
	  		    	 
	  		    	
	  		     }
	  		     else
	  		     {
	  		    	 Map<String, String> ResultMap = new HashMap<String,String>();

	  	  	         ResultMap= Lp.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
	  	  		     c.setValue("ExistingGroup",ResultMap.get("group"));
	  	  		     c.setValue("ExistingService", ResultMap.get("service"));
	  	  		     c.setValue("IsMultiExist", ResultMap.get("IsMulti")); 
	  		     }
	  	        
	  	        
	  	        
	  	        Application.showMessage("_______________________________________________");
	  	        
	  	        
	  	        //return sc;
	  	}
	  	catch(Exception e)
	  	{
	  	//return "error";
	  	}
	  	      
	  	 
	  	}
	 
	 
	 public void getAccoutCos_Validate(Object input, ScriptingContext c/*String suspendFlag*/) throws IOException, ClassNotFoundException, InterruptedException
		{
			
			Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
			 ResultSet  rs;
			 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     Map<String, String> ResultMap = new HashMap<String,String>();
		     Application.showMessage("-------------------------------------------------");
		     Application.showMessage("*****GetAccount_Validate Function******");       
		     Application.showMessage("-------------------------------------------------");
		     
			  if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ScenarioType").toString().equals(null))
			  {

			     ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
			     c.setValue("ExistingGroup",ResultMap.get("group"));
			     c.setValue("ExistingService", ResultMap.get("service"));
			     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
			     ResultMap=lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: NEWSERVICE"));
			     c.setValue("NewGroup",ResultMap.get("group"));
			     c.setValue("NewService", ResultMap.get("service"));
			    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
			     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
			     
			     
			     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
			     Application.showMessage("NewService::"+c.getValue("NewService"));
			     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
			     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
			     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
			     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
			  }
			  else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ScenarioType").toString().equalsIgnoreCase("Handle Invalid service"))
			  {
				
				 HandleInvalidServices hI=new HandleInvalidServices();
				 String CurrentService=hI.SetServiceIdentificationRule(input, c);
				 String newService= hI.SetServiceIdentificationRule1COS(input, c);
				 Application.showMessage("The Current Exieting Service is::"+CurrentService);
				 Application.showMessage("The New Service is::"+newService);
				 
				 ResultMap= lC.splitter(CurrentService);
			     c.setValue("ExistingGroup",ResultMap.get("group"));
			     c.setValue("ExistingService", ResultMap.get("service"));
			     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
			     ResultMap=lC.splitter(newService);
			     c.setValue("NewGroup",ResultMap.get("group"));
			     c.setValue("NewService", ResultMap.get("service"));
			    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
			     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
			     
			     
			     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
			     Application.showMessage("NewService::"+c.getValue("NewService"));
			     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
			     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
			     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
			     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
			
				  
			  }
			  else
			  {
				  ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
				     c.setValue("ExistingGroup",ResultMap.get("group"));
				     c.setValue("ExistingService", ResultMap.get("service"));
				     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
				     ResultMap=lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: NEWSERVICE"));
				     c.setValue("NewGroup",ResultMap.get("group"));
				     c.setValue("NewService", ResultMap.get("service"));
				    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
				     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
				     
				     
				     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
				     Application.showMessage("NewService::"+c.getValue("NewService"));
				     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
				     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
				     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
				     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
			  }

		     
		     
			try
			{
				 Statement st =lC. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
		         while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
		         {
		        	
		            String rowmsg = rs.getString(1);
		            if(rs.getBlob("receive_data")==null)
		            {
		            
		            	Application.showMessage("Your Recieve XML is NULL \n");
		            	
		            	String senddata =lC.extractXml(rs,xmldata2);
		            	Application.showMessage("Your Recieve XML is::\n"+senddata);
		            }
		            else if(rs.getBlob("SEND_DATA")==null)
		            {
		            	Application.showMessage("Your SEND XML is NULL \n");	
		            	String recievedata=lC.extractXml(rs,xmldata1);
		            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		            }
		            else
		            { 
		                          
			            String senddata = lC.extractXml(rs,xmldata2);		         
			            String recievedata = lC.extractXml(rs,xmldata1);      
			         		           
			            String id= lC.nodeFromKey(senddata,"<id>","</id>");
			            Application.showMessage("id is ::"+id);
			            Application.showMessage("id from Input is ::"+c.getValue("ACC_input"));
			            
			            if(id.equals(c.getValue("ACC_input")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-GetAccountChangeOfService_Validate********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            	
			            if(callFound==1)
			            {
			      
//			            	 String group1=lC.xpathValue(recievedata,"//ns3:GetAccountResponse/account/ns2:group");
//				 	         Application.showMessage("group1 is::"+group1);
			            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
			                Application.showMessage("errorCode is::"+errorCode); 
		            	
			                if(errorCode ==null)
			                {
			            	Application.showMessage("errorCode is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("errorCode from Recieve Xml  from GetAccount is ::"+" "+errorCode);
			            	    if(errorCode.trim().equals("0"))
				                 {
				            	 Application.showMessage("*******Validation Point 2 :: errorCode********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 v1=1;
				                 }
				                else
				               {
				            	 c.report("errorCode at Recieve Xml not Validated as "+errorCode);
				               }
			                }
			                if(c.getValue("IsDemi").equalsIgnoreCase("true"))
			 	            {
				                String centralStationAccountNumber= lC.nodeFromKey(recievedata,"<ns2:centralStationAccountNumber>","</ns2:centralStationAccountNumber>");
				                Application.showMessage("centralStationAccountNumber is::"+centralStationAccountNumber); 
			            	
				                if(centralStationAccountNumber ==null)
				                {
				            	Application.showMessage("centralStationAccountNumber is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage("centralStationAccountNumber from Recieve Xml  from GetAccount is ::"+" "+centralStationAccountNumber);
				            	    if(centralStationAccountNumber.trim().equals(c.getValue("CcentralNum")))
					                 {
					            	 Application.showMessage("*******Validation Point 3 :: centralStationAccountNumber********");
					            	 Application.showMessage("Validation is Successfull with centralStationAccountNumber::"+" "+centralStationAccountNumber);
					            	 v2=1;
					                 }
					                else
					               {
					            	 c.report("centralStationAccountNumber at Recieve Xml not Validated as "+centralStationAccountNumber);
					               }
				                }
			 	            }
			                else
			                {
			                	v2=1;	
			                }
			            	
			            	 if(c.getValue("IsMultiExist").equals("true"))
			            	 {
				 	            String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
				                Application.showMessage("group1 is::"+group1); 
			            	
				                if(group1 ==null)
				                {
				            	Application.showMessage("Group1 is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
				            	    if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equals(c.getValue("ExistingService")))
					                 {
					            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
					            	 v3=1;
					                 }
					                else
					               {
					                	v3=0;
					            	 c.report("group1 at Recieve Xml not Validated as "+group1);
					               }
				                }
				                
				                
				                String service1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>").trim();
				                Application.showMessage("service1 is::"+service1); 
			            	
				                if(service1 ==null)
				                {
				            	Application.showMessage("service1 is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage("service1 from Recieve Xml  from GetAccount is ::"+" "+service1);
				            	    if(service1.equals(c.getValue("ExistingService")) || service1.equals(c.getValue("ExistingGroup")))
					                 {
					            	 Application.showMessage("*******Validation Point 2 :: service1********");
					            	 Application.showMessage("Validation is Successfull with service1::"+" "+service1);
					            	 v3=1;
					                 }
					                else
					               {
					                	v3=0;
					            	 c.report("group1 at Recieve Xml not Validated as "+service1);
					               }
				                }
				                
				                
			            	}
			            	 
			            	 else
			            	 {
			            		 // for only one group
			            		    String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:internal>").trim();
					                Application.showMessage("group1 is::"+group1); 
				            	
					                if(group1 ==null)
					                {
					            	Application.showMessage("Group1 is NULL");
						            //continue;
					                }
					                else
					                {
					            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
					            	    if(group1.equals(c.getValue("ExistingGroup")))
						                 {
						            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
						            	 v3=1;
						                 }
						                else
						               {
						                	v3=0;
						            	 c.report("group1 at Recieve Xml not Validated as "+group1);
						               }
					                }
					                
			            		 
			            	 }
				            String accountId_getAcc= lC.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
				            Application.showMessage("accountId is::"+accountId_getAcc); 
				             if(accountId_getAcc==null)
				             {
					            c.report("Send Xml Account Number is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+accountId_getAcc);
				            	 if(accountId_getAcc.equals(c.getValue("ACC_input")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
					            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+accountId_getAcc);
					            	 v4=1;
					             }
					             else
					             {
					            	 c.report("Account Number at Send Xml not Validated as "+accountId_getAcc);
					             }
				            }
				             
				                String isSuspend= lC.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
					            Application.showMessage("isSuspend is::"+isSuspend); 
					             if(isSuspend==null)
					             {
						            c.report("Send Xml isSuspend is NULL");
						            continue;
					             }
					             else
					             {
					            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+accountId_getAcc);
					            	 if(isSuspend.equals(c.getValue("ACC_input")))
						             {
						            	 Application.showMessage("*******Validation Point 2 :: WebServicall-isSuspend********");
						            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+isSuspend);
						            	 v4=1;
						             }
						             else
						             {
						            	 c.report("Account Number at Send Xml not Validated as "+accountId_getAcc);
						             }
					             }
					             
				             
				             
			               
			            break;
			            }
			            
			                        
		             }
		         }
		         if(v1*v2*v3*v4==1)
		         {
		         	Application.showMessage("WebService Call :: GetAccount is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.report("WebService Call :: GetAccount is Failed with Validation Errors");
		         }
		         st.close();
			}	
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}
		}
	 
	 
	 public void orderUpdateCos_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
			 ResultSet  rs;
			 int callFound=0,v1=0,v2=0,v3=0,v4=0;
			// String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		    
		     Application.showMessage("-------------------------------------------------------------");
		     Application.showMessage("***********orderUpdateCos_Validate Function**************");       
		     Application.showMessage("-------------------------------------------------------------");
		         
			try
			{
				 Statement st =lC. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
		         {
		        	
		        
		            String rowmsg = rs.getString(1);
		            if(rs.getBlob("receive_data")==null)
		            {
		            
		            	Application.showMessage("Your Recieve XML is NULL \n");
		            	
		            	String senddata = lC.extractXml(rs,xmldata2);           
			           
			          	           
		                String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
		 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
		 	            if(accountNumber_ou==null)
			            {
		 	        	   Application.showMessage("Send Xml billingOrderId is NULL");
				            continue;
			             }
		 	           else if(accountNumber_ou.equals(c.getValue("ACC_input")))
		            	{
			            	
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
		            		Application.showMessage("Validation is Successfull with House Number :"+accountNumber_ou);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
			            
			            if(callFound==1)
			            {
			      
			 	            

			 	            String vomInstance= lC.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
			 	            Application.showMessage("vomInstance is ::"+vomInstance); 

			 	           
			 	            
			 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
			 	            {
			 	        	   
			 	        	  String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
				 	          Application.showMessage("ordSource is ::"+ordSource); 
			 	              String billingOrderId= lC.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
			 	              Application.showMessage("billingOrderId is ::"+billingOrderId); 
			 	            
				 	           if(billingOrderId==null)
					            {
						            c.report("Send Xml billingOrderId is NULL");
						            continue;
					            }
					            else
					            {
					            	
					            	 if(billingOrderId.equals(c.getValue("CcentralNum")))
						             {
						            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingOrderId********");
						            	 Application.showMessage("Validation is Successfull with billingOrderId::"+" "+billingOrderId);
						            	 v1=1;
						             }
						             else
						             {
						            	 c.report("billingOrderId at Send Xml not Validated as "+billingOrderId);
						             }
					            }
				 	           
				 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
				 	            Application.showMessage("Service is ::"+Service); 
				 	            
				 	           if(Service==null)
					            {
						            c.report("Send Xml Service is NULL");
						            continue;
					            }
					            else
					            {
					            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
					            	 HandleInvalidServices hI=new HandleInvalidServices();
					            	 String sNewService= hI.SetServiceIdentificationRule1COS(input, c);
					            	 if(Service.equals(sNewService.trim()))
						             {
						            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
						            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
						            	 v4=1;
						            	 c.setValue("Product",Service);
						             }
						             else
						             {
						            	 c.report("Service at Send Xml not Validated as "+Service);
						             }
					            }	
			 	            
			 	            }
			 	            else
			 	            {
			 	            	v1=1;
			 	            	
			 	            	
			 	                String Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
			 	 	            Application.showMessage("Service is ::"+Service); 
			 	 	            
			 	 	           if(Service==null)
			 		            {
			 			            c.report("Send Xml Service is NULL");
			 			            continue;
			 		            }
			 		            else
			 		            {
			 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
			 		            	 if(Service.equals(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE").trim()))
			 			             {
			 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
			 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
			 			            	 v4=1;
			 			            	 c.setValue("Product",Service);
			 			             }
			 			             else
			 			             {
			 			            	 c.report("Service at Send Xml not Validated as "+Service);
			 			             }
			 		            }	
			 	            }
			 	          
			 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
			 	            Application.showMessage("inputChannel is ::"+inputChannel);
			 	            
			 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
			 	            Application.showMessage("ordType is ::"+ordType);
			 	            
			 	           if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
				            	 if(ordType.equals("CHG"))
					             {
					            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
					            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
					            	 v2=1;
					             }
					             else
					             {
					            	 c.report("ordType at Send Xml not Validated as "+ordType);
					             }
				            }	
			 	            
			 	            
			 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
			 	            Application.showMessage("customerType is ::"+customerType); 
			 	            
			 	           if(customerType==null)
				            {
					            c.report("Send Xml customerType is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("customerType from Send Xml   is ::"+" "+customerType);
				            	 if(customerType.equals("RES"))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
					            	 Application.showMessage("Validation is Successfull with customerType::"+" "+customerType);
					            	 v3=1;
					             }
					             else
					             {
					            	 c.report("customerType at Send Xml not Validated as "+customerType);
					             }
				            }	
			 	            
			 	            
			 	            
			 	           	 	           
			            break;
			            }
		            }
		               
		         }
		         if(v1*callFound*v2*v3*v4 ==1)
		         {
		         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
		         	c.setValue("IsRemoveGroup", "false");
		         	c.setValue("IsUpdateTier", "false");
		         	c.setValue("IsAddGroup", "false");
		         	
		         }
		         else
		         {
		        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
		        	 c.setValue("IsRemoveGroup", "false");
		        	 c.setValue("IsUpdateTier", "false");
		        	 c.setValue("IsAddGroup", "false");
		         }
		         st.close();
			}	
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}
		 }
	 
 }

