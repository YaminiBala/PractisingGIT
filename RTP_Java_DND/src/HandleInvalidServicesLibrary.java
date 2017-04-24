

	import java.io.IOException;
import java.io.StringReader;
	import java.sql.Connection;
	import java.sql.DriverManager;
import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

	import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


	public class HandleInvalidServicesLibrary 
	{
		RTP_ValidationsClass sv=new RTP_ValidationsClass();
		//ChangeOfServiceValidation cs=new ChangeOfServiceValidation();
		public Connection connection = null;
		public void InvalidServicessimulatorChange1(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
		{
			
			//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
			//Application.showMessage("The Simulator setting for Ucontrol"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolSimulator"));
			
			Statement st = dBConnect(input, c);
			
	        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        
	        while (rs.next()) 
	        {
	         String beforeSim = rs.getString(2);
	        //c.setValue("BaseMsgid", basemsgID);
	        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
	        
	        }
	        st.close();
	        Statement st0 = dBConnect(input, c);
	        
	       
	        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId></accountId><iccId></iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber></centralStationAccountNumber><uControlGroupValue></uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status></status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><iContServiceDemi>350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV</iContServiceDemi><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);
	       
	      // ps.setString(1, sql);
	       
	       //ps.execute();
	      
	        st0.close();
	        Thread.sleep(300000);
	        Statement st1 = dBConnect(input, c);
	        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs1.next()) 
	        {
	         String afterSim = rs1.getString(2);
	        
	        Application.showMessage("Db Simulator After Updation ::"+afterSim);
	        
	        }
	        st0.close();
	     
	        
		}
		public void DisconnectInvalidServicessimulatorChange1(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
		{
			
			//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
			//Application.showMessage("The Simulator setting for Ucontrol"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolSimulator"));
			Statement st = dBConnect(input, c);
	        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs.next()) 
	        {
	         String beforeSim = rs.getString(2);
	        //c.setValue("BaseMsgid", basemsgID);
	        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
	        
	        }
	        st.close();
	        Statement st0 = dBConnect(input, c);
	        
	       
	     /*   String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>1</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: iccId")+"</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>350| ThermostatDataProvider</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: status")+"</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);*/
	        
	        //String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>0</useIControlSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId></accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber></centralStationAccountNumber><uControlGroupValue>350| ThermostatDataProvider</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status></status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><iContServiceDemi>350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV</iContServiceDemi><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        //st0.executeUpdate(sql);
	        
	      String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><scheduleDisconnectreleaseTime>06:00</scheduleDisconnectreleaseTime><activeStatus>activated</activeStatus><getAccountStatus>ACTIVE</getAccountStatus><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><iContServiceDemi>350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV</iContServiceDemi><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);
	      // ps.setString(1, sql);
	       
	       //ps.execute();
	      
	        st0.close();
	        Thread.sleep(300000);
	        Statement st1 = dBConnect(input, c);
	        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs1.next()) 
	        {
	         String afterSim = rs1.getString(2);
	        
	        Application.showMessage("Db Simulator After Updation ::"+afterSim);
	        
	        }
	        st0.close();
	     
	        
		}
		
		/* Changed by Shilpa -Start */
		
		public Connection dBConnect1(Object input , ScriptingContext c) throws ClassNotFoundException, SQLException, IOException
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");	
			
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.33:1521:psymqa1", "CWQA1OP","qa1opw1nt3r");
			
			String host = c.getValue("DB_Host");
			String password = c.getValue("dB_Password");
			String username = c.getValue("dB_Username");
			String port = c.getValue("dB_Port");
			String sid = c.getValue("dB_serviceName");
			
			
			connection = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port+":"+sid +"", username,password);
		    
			/*Statement st = connection.createStatement();
			return st;*/
			return connection;
		}
		
		public void DisconnectInvalidServicessimulatorChange2(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
		{
			
			Connection conn = dBConnect1(input, c);
			Statement st = conn.createStatement();
	        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs.next()) 
	        {
	         String beforeSim = rs.getString(1);
	        //c.setValue("BaseMsgid", basemsgID);
	        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
	        
	        }
	        st.close();
	        Statement st0 = dBConnect(input, c);
	        
	       
	     /*   String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>1</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: iccId")+"</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>350| ThermostatDataProvider</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: status")+"</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);*/
	        
	        //String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>0</useIControlSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId></accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber></centralStationAccountNumber><uControlGroupValue>350| ThermostatDataProvider</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status></status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><iContServiceDemi>350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV</iContServiceDemi><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        //st0.executeUpdate(sql);
	        
	        String name = "homesecurityUtil:hsConfig";
			PreparedStatement pstmt = conn
					.prepareStatement(" update CWFTEMPLATECONFIG set DATA=? where NAME='"
							+ name + "'");
			
			pstmt.setString(
					1,
			"<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><scheduleDisconnectreleaseTime>06:00</scheduleDisconnectreleaseTime><activeStatus>activated</activeStatus><getAccountStatus>ACTIVE</getAccountStatus><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><iContServiceDemi>350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV</iContServiceDemi><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>");

	     /* String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><scheduleDisconnectreleaseTime>06:00</scheduleDisconnectreleaseTime><activeStatus>activated</activeStatus><getAccountStatus>ACTIVE</getAccountStatus><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><iContServiceDemi>350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV</iContServiceDemi><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);*/
	      // ps.setString(1, sql);
	       
	       //ps.execute();
			
			pstmt.executeUpdate();
			conn.setAutoCommit(true);

			ResultSet rs1 = st
					.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	      
	        st0.close();
	        Thread.sleep(300000);
	      /*  Statement st1 = dBConnect(input, c);
	        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");*/
	        while (rs1.next()) 
	        {
	         String afterSim = rs1.getString(1);
	        
	        Application.showMessage("Db Simulator After Updation ::"+afterSim);
	        
	        }
	        st0.close();
	     
	        
		}
		
		
		/* Changed by Shilpa -End */
		
		public void InvalidServicessimulatorChange0(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
		{
			
			//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
			//Application.showMessage("The Simulator setting for Ucontrol"+c.getValue("RTPDataSourceCollections", "dB_Simulator: useUcontrolSimulator"));
			Statement st = dBConnect(input, c);
	        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs.next()) 
	        {
	         String beforeSim = rs.getString(2);
	        //c.setValue("BaseMsgid", basemsgID);
	        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
	        
	        }
	        st.close();
	        Statement st0 = dBConnect(input, c);
	        
	       
	        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber")+"</accountId><iccId>"+c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ICCID")+"</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>"+c.getValue("RTPNormalScenariosdS", "RTP_InputParams: UcontrolServiceSusRes")+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"+c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SimulatorStatus")+"</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><iContServiceDemi>350 + ThermostatDataProvider + SecureEV,350 + ThermostatDataProvider + ThermostatDataProvider,350 + SecureEV</iContServiceDemi><eepServiceDemi>350 + ThermostatDataProvider,350,300 + SecureEV + ThermostatDataProvider,300,300 + SecureEV,300 + ThermostatDataProvider</eepServiceDemi></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);
	       
	      // ps.setString(1, sql);
	       
	       //ps.execute();
	      
	        st0.close();
	        Thread.sleep(300000);
	        Statement st1 = dBConnect(input, c);
	        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs1.next()) 
	        {
	         String afterSim = rs1.getString(2);
	        
	        Application.showMessage("Db Simulator After Updation ::"+afterSim);
	        
	        }
	        st0.close();
	     
	        
		}
		public void DisconnectInvalidServicessimulatorChange0(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
		{
			
			//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
			//Application.showMessage("The Simulator setting for Ucontrol"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolSimulator"));
			Statement st = dBConnect(input, c);
	        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs.next()) 
	        {
	         String beforeSim = rs.getString(2);
	        //c.setValue("BaseMsgid", basemsgID);
	        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
	        
	        }
	        st.close();
	        Statement st0 = dBConnect(input, c);
	        
	       
	        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: iccId")+"</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue></uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: status")+"</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus>" +
	        		"<getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);
	       
	      // ps.setString(1, sql);
	       
	       //ps.execute();
	      
	        st0.close();
	        Thread.sleep(300000);
	        Statement st1 = dBConnect(input, c);
	        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs1.next()) 
	        {
	         String afterSim = rs1.getString(2);
	        
	        Application.showMessage("Db Simulator After Updation ::"+afterSim);
	        
	        }
	        st0.close();
	     
	        
		}
		public Statement dBConnect(Object input , ScriptingContext c) throws ClassNotFoundException, SQLException, IOException
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");	
			
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.33:1521:psymqa1", "CWQA1OP","qa1opw1nt3r");
			
			String host = c.getValue("DB_Host");
			String password = c.getValue("dB_Password");
			String username = c.getValue("dB_Username");
			String port = c.getValue("dB_Port");
			String sid = c.getValue("dB_serviceName");
			
			
			connection = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port+":"+sid +"", username,password);
		    
			Statement st = connection.createStatement();
			return st;
		}
		public String SetServiceIdentificationRule(String OrderServices,Object input, ScriptingContext c) throws IOException
		 {
			 String NewService=null;
			 //String Service=c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"); 
			 String Service=OrderServices;
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
		 public  List<String> extractServices(String services,Object input,ScriptingContext c)
		   {
			  
			   String symbol="+";
			   String simSymbol="|";
			   String value= "/";
			   if(services.contains(simSymbol))
			   {
			   String check=services.replace(simSymbol, value);
			   List<String> Groupservices=Arrays.asList(check.split("/"));
			   int size=Groupservices.size();
			   c.setValue("ExistingGroup",Groupservices.get(0));
			   for(int i=0; i<size;i++)
			   {
				   Application.showMessage("Services "+i+" are::"+Groupservices.get(i).trim());
			   }
			   return Groupservices; 
			   }
			  
			   else if(services.contains(symbol))
			   {
				   String check=services.replace(symbol, value);
				   List<String> Groupservices=Arrays.asList(check.split(" / "));
				   int size=Groupservices.size();
				   c.setValue("ExistingGroup",Groupservices.get(0));
				   for(int i=0; i<size;i++)
				   {
					   Application.showMessage("Services "+i+" are::"+Groupservices.get(i).trim());
				   }
				   return Groupservices; 
			   }
			   else 
			   {
				   
				   List<String> Groupservices=new ArrayList<String>();
				   Groupservices.add(services);
				   int size=Groupservices.size();
				   c.setValue("ExistingGroup",Groupservices.get(0));
				   for(int i=0; i<size;i++)
				   {
					   Application.showMessage("Services "+i+" are::"+Groupservices.get(i).trim());
				   }
				   return Groupservices; 
			   }
			   
		   }
		 
		 public String SetServiceIdentificationRule100TDPEV(String OrderServices,Object input, ScriptingContext c) throws IOException
		 {
			 String NewService=null;
			 //String Service=c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"); 
			  String Service=OrderServices;
			  //String IsValidFromService = "true";
			  c.put("nService", Service);
		      NewService =c.get("nService").toString();
			 //c.put("pIsValidFromService",IsValidFromService);
			  Application.showMessage("Service to be validated is::"+NewService);
			 // Application.showMessage("Your Base Service is::"+c.get("pIsValidFromService").toString());
			  return NewService;
	 }
		 
		 
		 public  List<String> removeAccountServices100TDPEV(String removeservices,String ExistingService,Object input,ScriptingContext c) throws IOException
		   {
			 String validremoveservices=SetServiceIdentificationRule(ExistingService, input, c);
			
			 List<String> removeAccountGroup=extractServices(validremoveservices, input, c);
			 List<String> ExistingAccountGroup=extractServices(removeservices, input, c);
			 List<String> removeAccountservices=new ArrayList<String>();
			Boolean ii=true;
			if(ii)
			{
				Application.showMessage("Entering ii");
				if(removeAccountGroup.size() <= 1)
				{
					Application.showMessage("Enter removeAccount");
					 for(int i=1;i<ExistingAccountGroup.size();i++)
					 {
                       // c.setValue("IsRemoveaccountgroup", "true");
						 Application.showMessage("Enter After removeAccount");
						 removeAccountservices.add(removeAccountGroup.get(i));
					 }
				}
				else
				{
			 for(int i=1;i<removeAccountGroup.size();i++)
			 {
				 //Application.showMessage("Enter");
				 for(int j=1;j<ExistingAccountGroup.size();j++)
				 {
					// Application.showMessage("Enter");
					 
			    if((removeAccountGroup.get(i)).equals(ExistingAccountGroup.get(j)))
				 {
					 //Application.showMessage("Enterdhfh");
					// c.setValue("IsRemoveaccountgroup", "false");
					 break;
				 }
				 else
				 {
					 Application.showMessage("Enter last");				
					 
					 removeAccountservices.add(ExistingAccountGroup.get(j));
					
				 }
					 
				 }
			 }
				}
			}
			else
			{
			
			}
			
			if(removeAccountservices.size() >0)
			{
				c.setValue("IsRemoveaccountgroup", "true");
				Application.showMessage("Finding value"+removeAccountservices.get(0));
			}
			else
			{
				c.setValue("IsRemoveaccountgroup", "false");
			}
			 
			Application.showMessage("Value of removeAccountgroup is :: "+c.getValue("IsRemoveaccountgroup"));
			return removeAccountservices;
			
					
			
			
			//return removeAccountServices100TDPEV;
			  
			  
			   
		   }
		 
		 public  List<String> addAccountServices(String addservices,String ExistingService,Object input,ScriptingContext c) throws IOException
		   {
			 String validaddservices=SetServiceIdentificationRule(addservices, input, c);
			 List<String> addAccountGroup=extractServices(validaddservices, input, c);
			 List<String> ExistingAccountGroup=extractServices(ExistingService, input, c);
			 List<String> addAccountservices=new ArrayList<String>();
			 if((addAccountGroup.get(0)).equals(ExistingAccountGroup.get(0)))
			 {
			 c.setValue("IsUpdateTier", "false");
			 }
			 else
			 {
				 c.setValue("IsUpdateTier", "true");
				 c.setValue("updatedvalue",addAccountGroup.get(0));
			 }
			 Application.showMessage("Update Tier Value"+c.getValue("IsUpdateTier"));
			 Application.showMessage("Update Value"+c.getValue("updatedvalue"));
			 if(ExistingAccountGroup.size() <= 1)
				{
					 for(int i=1;i<addAccountGroup.size();i++)
					 {
                   
						 
						 addAccountservices.add(addAccountGroup.get(i));
					 }
				}
			 else
			 {
			 for(int i=1;i<addAccountGroup.size();i++)
			 {
				 for(int j=1;j<ExistingAccountGroup.size();j++)
				 {
				 if((addAccountGroup.get(i)).equals(ExistingAccountGroup.get(j)))
				 {
					// c.setValue("IsAddaccountgroup", "false");
					 if(addAccountservices.contains(addAccountGroup.get(i)))
					 {
						 addAccountservices.remove(addAccountGroup.get(i));
					 }
					 else
					 {
					 break;
					 }
				 }
				 else
				 {
					 //if(addAccountservices.contains(addAccountGroup.get(i)))
					 if(addAccountservices.contains(addAccountGroup.get(i)))
					 {
						 break;
					 }
					 else
					 {
					 addAccountservices.add(addAccountGroup.get(i));
					 continue;
					 }
				 }
				 }
			 }
			 }
			 if(addAccountservices.size() >0)
				{
					c.setValue("IsAddaccountgroup", "true");
				}
				else
				{
					c.setValue("IsAddaccountgroup", "false");
				}
			 
			return addAccountservices;
			  
			  
			   
		   }
		 
		 public  List<String> removeAccountServices(String removeservices,String ExistingService,Object input,ScriptingContext c) throws IOException
		   {
			 String validremoveservices=SetServiceIdentificationRule(ExistingService, input, c);
			// List<String> removeAccountGroup=extractServices(validremoveservices, input, c);
			 List<String> removeAccountGroup=extractServices(validremoveservices, input, c);
			 
			// List<String> ExistingAccountservices=extractServices(removeservices, input, c);
			//String ExistingAccountservices=SetServiceIdentificationRule(removeservices, input, c);
			 List<String> ExistingAccountGroup=extractServices(removeservices, input, c);
			 List<String> removeAccountservices=new ArrayList<String>();
			Boolean ii=true;
			if(ii)
			{
				Application.showMessage("Enter");
				if(removeAccountGroup.size() <= 1)
				{
					Application.showMessage("Enter");
					 for(int i=1;i<ExistingAccountGroup.size();i++)
					 {
                       // c.setValue("IsRemoveaccountgroup", "true");
						 Application.showMessage("Enter");
						 
						 removeAccountservices.add(ExistingAccountGroup.get(i));
					 }
				}
				else
				{
			 for(int i=1;i<removeAccountGroup.size();i++)
			 {
				 //Application.showMessage("Enter");
				 for(int j=1;j<ExistingAccountGroup.size();j++)
				 {
					// Application.showMessage("Enter");
					 
			    if((removeAccountGroup.get(i)).equals(ExistingAccountGroup.get(j)))
				 {
					 //Application.showMessage("Enterdhfh");
					// c.setValue("IsRemoveaccountgroup", "false");
					 break;
				 }
				 else
				 {
					 Application.showMessage("Enter");				
					 
					 removeAccountservices.add(removeAccountGroup.get(j));
					
				 }
					 
				 }
			 }
				}
			}
			else
			{
			
			}
			
			if(removeAccountservices.size() >0)
			{
				c.setValue("IsRemoveaccountgroup", "true");
			}
			else
			{
				c.setValue("IsRemoveaccountgroup", "false");
			}
			 
			Application.showMessage("Value of removeAccountgroup is"+c.getValue("IsRemoveaccountgroup"));
			 
			return removeAccountservices;
			  
			  
			   
		   }
		 
		
		 public String GetValueByXPath(String sXml, String sxpath) throws SAXException, IOException, XPathExpressionException
		 {
			 String val=null;
			 try
			 {
				 //sXml.replaceAll(" xsi:", " ");
			     InputSource source = new InputSource(new StringReader(sXml));
			     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			     factory.setNamespaceAware(false);    
			     factory.setIgnoringElementContentWhitespace(false);
		      
			     DocumentBuilder builder;        
			     Document doc = null;
			 
				 builder = factory.newDocumentBuilder();  
				 doc = builder.parse(source);
				 XPathFactory xpathFactory = XPathFactory.newInstance();            
			     XPath xpath = xpathFactory.newXPath(); 
			     XPathExpression expr = xpath.compile(sxpath);
			     Node oNode= (Node) expr.evaluate(doc,XPathConstants.NODE);
			     val=oNode.getTextContent();
			   
			     xpath=null; expr=null; oNode=null; doc=null; builder=null;
			     
			 }
			 catch (ParserConfigurationException | SAXException | IOException e) 
			 {           
				 e.printStackTrace();       
			 }
			 
			 return val;
			 
		 }
		 public String RemoveNameSpaces(String sIpStr) 
			{
		        String sRet = sIpStr;
		        Pattern pP = Pattern.compile("<[a-zA-Z]*:");
		        Matcher mM = pP.matcher(sRet);
		        sRet = mM.replaceAll("<");
		        
		        pP = Pattern.compile("<[a-zA-Z]*[0-9]*:");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll("<");
		        
		        pP = Pattern.compile("</[a-zA-Z]*[0-9]*:");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll("</");
		     
		        pP = Pattern.compile("</[a-zA-Z]*:");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll("</");
		        
		        pP = Pattern.compile(" xmlns.*?(\"|\').*?(\"|\')");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll(" "); 

		        pP = Pattern.compile(" xsi:");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll(" ");
		       // bfcopcom
		        pP = Pattern.compile("sik:");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll(" ");
		        
		        pP = Pattern.compile("bfcopcom:");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll(" ");
		        
		        pP = Pattern.compile("comt:");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll(" ");
		        
		               
		        pP = Pattern.compile("SignatureRequired=\"FALSE\"");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll(" ");
		        
		        pP = Pattern.compile("type=\" SingleComponentProduct\"");
		        mM = pP.matcher(sRet);
		        sRet = mM.replaceAll(" ");
		        
		        return sRet;
		   }

		 public String orderUpdate_ValidateCancel(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
			{
				Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
			     LibraryRtp  lC= new LibraryRtp ();
				 ResultSet  rs;
				 String rscallpresent="true";
				 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
				// String xmldata1 ="receive_data";
			     String xmldata2 ="SEND_DATA";
			     String Time= c.get("BaseTime").toString();
			    // c.setValue("OrderUpdate","true");
			     Application.showMessage("-------------------------------------------------------------");
			     Application.showMessage("***********OrderUpdate_Validate_Validate Function**************");       
			     Application.showMessage("-------------------------------------------------------------");
			     System.out.println("-------------------------------------------------------------");
			     System.out.println("***********OrderUpdate_Validate_Validate Function**************");       
			     System.out.println("-------------------------------------------------------------");
			   
			    // lC.findThinktimeOperationRTP(input, c);
			   //  c.setValue("OrderUpdate","false"); 
			     HashMap Operation=lC.findingoperations(input, c);
			     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
			     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
			     System.out.println("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));

				try
				{
				//	Statement st =lC. dBConnect(input, c);	
			    //     rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
					rs=lC.reduceThinkTimeRTP(input, c);
					if(rs == null)
                	{
                		rscallpresent = "false";
                		
                	
                		
                	}
                	else
{
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

				 	            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
				 	            Application.showMessage("ordSource is ::"+ordSource); 
				 	            
				 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
				 	            {
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
					 	          String validService=SetServiceIdentificationRule(c.getValue("sSERVICEorCURRENTSERVICE"),input,c);
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
						            	 
						            	 if(Service.equals(validService.trim()))
							             {
							            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
							            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
							            	 v4=1;
							            	 c.setValue("Product",Service);
							             }
							             else
							             {
							            	// c.report("Service at Send Xml not Validated as "+Service);
							            	 v4=1;
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
				 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
					            	 if(ordType.equals("CAN"))
						             {
						            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
						            	 Application.showMessage("Validation is Successfull with ordType::"+" "+ordType);
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
				 	            
				 	            if(senddata.contains("error"))
					 	         {
					 	        	 c.report("Error in the OrderUpdate Call...!");
					 	         }
					 	         else
					 	         {
					 	        	 Application.showMessage("No errors detected...!");
					 	        	 v5=1;
					 	         }
				 	           
				 	           
				 	          
				 	           /* String errorCode= lC.nodeFromKey(senddata,"</value><value name=\"errorCode\">","</value><value name=\"errorText\">");
				 	            Application.showMessage("errorCode is ::"+errorCode); 
				 	            
				 	           if(errorCode==null)
					            {
						            c.report("Send Xml errorCode is NULL");
						            continue;
					            }
					            else
					            {
					            	 Application.showMessage("customerType from Send Xml   is ::"+" "+errorCode);
					            	 if(errorCode.equals("UCNTRL-RESP-CODE-UCE-15113"))
						             {
						            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
						            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
						            	 v5=1;
						             }
						             else
						             {
						            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
						             }
					            }	
				 	            */
				 	           	 	           
				            break;
				            }
			            }
			               
			         }
			         if(v1*callFound*v2*v3*v4 * v5 ==1)
			         {
			        	 c.setValue("IsDemi", "false");
			         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
			         }
			         else
			         {
			        	 c.setValue("IsDemi", "false");
			        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
			         }
			      //   st.close();
				}
				}
				catch (SQLException e)
				{
				    System.out.println("Connection Failed! Check output console");
					e.printStackTrace();
				
				}
				return rscallpresent;
			 }
		 
		 //------Order Update Res------
		 
		 public String orderUpdate_ValidateRes(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
			{
				Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
			     LibraryRtp  lC= new LibraryRtp ();
				 ResultSet  rs;
				 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
				 String rscallpresent="true";
				// String xmldata1 ="receive_data";
			     String xmldata2 ="SEND_DATA";
			     String Time= c.get("BaseTime").toString();
			    // c.setValue("OrderUpdate","true");
			     Application.showMessage("-------------------------------------------------------------");
			     Application.showMessage("***********OrderUpdate_Validate Function**************");       
			     Application.showMessage("-------------------------------------------------------------");
			    // lC.findThinktimeOperationRTP(input, c);
			   //  c.setValue("OrderUpdate","false");
			     HashMap Operation=lC.findingoperations(input, c);
			     c.setValue("OPERATIONVALIDATION",(String) Operation.get("UcontrolgetAccount"));
			     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("UcontrolgetAccount"));

				try
				{
					//Statement st =lC. dBConnect(input, c);	
			        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
					rs=lC.reduceThinkTimeRTP(input, c);
					if(rs == null)
                	{
                		rscallpresent = "false";
                		
                	
                		
                	}
                	else
{

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

				 	            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
				 	            Application.showMessage("ordSource is ::"+ordSource); 
				 	            
				 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
				 	            {
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
					 	          String validService=SetServiceIdentificationRule(c.getValue("sSERVICEorCURRENTSERVICE"),input,c);
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
						            	 
						            	 if(Service.equals(validService.trim()))
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
				 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
					            	 if(ordType.equals("RES"))
						             {
						            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
						            	 Application.showMessage("Validation is Successfull with ordType::"+" "+ordType);
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
				 	            
				 	            if(senddata.contains("error"))
					 	         {
					 	        	 c.report("Error in the OrderUpdate Call...!");
					 	         }
					 	         else
					 	         {
					 	        	 Application.showMessage("No errors detected...!");
					 	        	 v5=1;
					 	         }
				 	           
				 	           
				 	          
				 	           /* String errorCode= lC.nodeFromKey(senddata,"</value><value name=\"errorCode\">","</value><value name=\"errorText\">");
				 	            Application.showMessage("errorCode is ::"+errorCode); 
				 	            
				 	           if(errorCode==null)
					            {
						            c.report("Send Xml errorCode is NULL");
						            continue;
					            }
					            else
					            {
					            	 Application.showMessage("customerType from Send Xml   is ::"+" "+errorCode);
					            	 if(errorCode.equals("UCNTRL-RESP-CODE-UCE-15113"))
						             {
						            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
						            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
						            	 v5=1;
						             }
						             else
						             {
						            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
						             }
					            }	
				 	            */
				 	           	 	           
				            break;
				            }
			            }
			               
			         }
			         if(v1*callFound*v2*v3*v4 * v5 ==1)
			         {
			        	 c.setValue("IsDemi", "false");
			         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
			         }
			         else
			         {
			        	 c.setValue("IsDemi", "false");
			        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
			         }
			       //  st.close();
				}	
				}
				catch (SQLException e)
				{
				    System.out.println("Connection Failed! Check output console");
					e.printStackTrace();
					
				}
				return rscallpresent;
				
				
				
	}
		 
		 //---CancelOrderupdate------
		 
		 public String orderUpdate_Validatesus(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
			{
				Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
			     LibraryRtp  lC= new LibraryRtp ();
				 ResultSet  rs;
				 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
				// String xmldata1 ="receive_data";
			     String xmldata2 ="SEND_DATA";
			     String rscallpresent="true";
			     String Time= c.get("BaseTime").toString();
			    // c.setValue("OrderUpdate","true");
			     Application.showMessage("-------------------------------------------------------------");
			     Application.showMessage("***********OrderUpdate_Validate Function**************");       
			     Application.showMessage("-------------------------------------------------------------");
			   //  lC.findThinktimeOperationRTP(input, c);
			   //  c.setValue("OrderUpdate","false");
			     HashMap Operation=lC.findingoperations(input, c);
			     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
			     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));

				try
				{
				//	Statement st =lC. dBConnect(input, c);	
			      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
					rs=lC.reduceThinkTimeRTP(input, c);
					if(rs == null)
                	{
                		rscallpresent = "false";
                		
                	
                		
                	}
                	else
{

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

				 	            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
				 	            Application.showMessage("ordSource is ::"+ordSource); 
				 	            
				 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
				 	            {
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
					 	          String validService=SetServiceIdentificationRule(c.getValue("sSERVICEorCURRENTSERVICE"),input,c);
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
						            	 
						            	 if(Service.equals(validService.trim()))
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
				 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
					            	 if(ordType.equals("NPD"))
						             {
						            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
						            	 Application.showMessage("Validation is Successfull with ordType::"+" "+ordType);
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
				 	            
				 	            if(senddata.contains("error"))
					 	         {
					 	        	 c.report("Error in the OrderUpdate Call...!");
					 	         }
					 	         else
					 	         {
					 	        	 Application.showMessage("No errors detected...!");
					 	        	 v5=1;
					 	         }
				 	           
				 	           
				 	          
				 	           /* String errorCode= lC.nodeFromKey(senddata,"</value><value name=\"errorCode\">","</value><value name=\"errorText\">");
				 	            Application.showMessage("errorCode is ::"+errorCode); 
				 	            
				 	           if(errorCode==null)
					            {
						            c.report("Send Xml errorCode is NULL");
						            continue;
					            }
					            else
					            {
					            	 Application.showMessage("customerType from Send Xml   is ::"+" "+errorCode);
					            	 if(errorCode.equals("UCNTRL-RESP-CODE-UCE-15113"))
						             {
						            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
						            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
						            	 v5=1;
						             }
						             else
						             {
						            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
						             }
					            }	
				 	            */
				 	           	 	           
				            break;
				            }
			            }
			               
			         }
			         if(v1*callFound*v2*v3*v4 * v5 ==1)
			         {
			        	 c.setValue("IsDemi", "false");
			         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
			         }
			         else
			         {
			        	 c.setValue("IsDemi", "false");
			        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
			         }
			       //  st.close();
				}	
				}
				catch (SQLException e)
				{
				    System.out.println("Connection Failed! Check output console");
					e.printStackTrace();
					
				}
				return rscallpresent;
			 }
		 
		 //---------------Order Update COS-----
		 
		 public void orderUpdate_ValidateCOS(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
			{
				Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
			     LibraryRtp  lC= new LibraryRtp ();
				 ResultSet  rs;
				 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
				// String xmldata1 ="receive_data";
			     String xmldata2 ="SEND_DATA";
			    
			     Application.showMessage("-------------------------------------------------------------");
			     Application.showMessage("***********OrderUpdate_Validate Function**************");       
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

				 	            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
				 	            Application.showMessage("ordSource is ::"+ordSource); 
				 	            
				 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
				 	            {
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
					 	        // String cosservice=c.getValue(RTPNormalScenariosdS,"RTP_InputParams: NEWSERVICE");
					 	         //  c.setValue("validService", "cosservice");
					 	           String cosservice=c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE");
					 	          Application.showMessage("Service is ::"+cosservice); 
					 	           c.setValue("validservice",cosservice);
					 	          String validService=SetServiceIdentificationRule(cosservice,input,c);
					 	         Application.showMessage("ValidService is ::"+validService);
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
						            	 
						            	 if(Service.equals(validService.trim()))
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
				 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
					            	 if(ordType.equals("CHG") ||ordType.equals("NEW") )
						             {
						            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
						            	 Application.showMessage("Validation is Successfull with ordType::"+" "+ordType);
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
				 	            
				 	            if(senddata.contains("error"))
					 	         {
					 	        	 c.report("Error in the OrderUpdate Call...!");
					 	         }
					 	         else
					 	         {
					 	        	 Application.showMessage("No errors detected...!");
					 	        	 v5=1;
					 	         }
				 	           
				 	           
				 	          
				 	           /* String errorCode= lC.nodeFromKey(senddata,"</value><value name=\"errorCode\">","</value><value name=\"errorText\">");
				 	            Application.showMessage("errorCode is ::"+errorCode); 
				 	            
				 	           if(errorCode==null)
					            {
						            c.report("Send Xml errorCode is NULL");
						            continue;
					            }
					            else
					            {
					            	 Application.showMessage("customerType from Send Xml   is ::"+" "+errorCode);
					            	 if(errorCode.equals("UCNTRL-RESP-CODE-UCE-15113"))
						             {
						            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
						            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
						            	 v5=1;
						             }
						             else
						             {
						            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
						             }
					            }	
				 	            */
				 	           	 	           
				            break;
				            }
			            }
			               
			         }
			         if(v1*callFound*v2*v3==1)
			         {
			        	 c.setValue("IsDemi", "false");
			         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
			         }
			         else
			         {
			        	 c.setValue("IsDemi", "false");
			        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
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
		 
		 
		 //******************** for 100TDP EV **********************************************//
		 
		 public void orderUpdate_ValidateCOS100TDPEV(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
			{
				Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
			     LibraryRtp  lC= new LibraryRtp ();
				 ResultSet  rs;
				 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
				// String xmldata1 ="receive_data";
			     String xmldata2 ="SEND_DATA";
			     String Time= c.get("BaseTime").toString();
			     c.setValue("OrderUpdate","true");
			     Application.showMessage("-------------------------------------------------------------");
			     Application.showMessage("***********OrderUpdate_Validate Function**************");       
			     Application.showMessage("-------------------------------------------------------------");
			     lC.findThinktimeOperationRTP(input, c);
			     c.setValue("OrderUpdate","false"); 
				try
				{
					//Statement st =lC. dBConnect(input, c);	
			        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
					rs=lC.reduceThinkTimeRTP(input, c);
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

				 	            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
				 	            Application.showMessage("ordSource is ::"+ordSource); 
				 	            
				 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
				 	            {
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
					 	        // String cosservice=c.getValue(RTPNormalScenariosdS,"RTP_InputParams: NEWSERVICE");
					 	         //  c.setValue("validService", "cosservice");
					 	           String cosservice=c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE");
					 	          Application.showMessage("Service is ::"+cosservice); 
//					 	           c.setValue("validservice",cosservice);
//					 	          String validService=SetServiceIdentificationRule(cosservice,input,c);
//					 	         Application.showMessage("ValidService is ::"+validService);
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
//				 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
//				 			             {
//				 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
//				 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
//				 			            	 v4=1;
//				 			            	 c.setValue("Product",Service);
//				 			             }
//				 			             else
//				 			             {
//				 			            	 c.report("Service at Send Xml not Validated as "+Service);
//				 			             }
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
					            	 if(ordType.equals("CHG") ||ordType.equals("NEW") )
						             {
						            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
						            	 Application.showMessage("Validation is Successfull with ordType::"+" "+ordType);
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
				 	            
				 	            if(senddata.contains("error"))
					 	         {
					 	        	 c.report("Error in the OrderUpdate Call...!");
					 	         }
					 	         else
					 	         {
					 	        	 Application.showMessage("No errors detected...!");
					 	        	 v5=1;
					 	         }
				 	           
				 	         
				 	          
				 	          				 	           	 	           
				            break;
				            }
			            }
			               
			         }
			         if(v1*callFound*v2*v3==1)
			         {
			        	 c.setValue("IsDemi", "false");
			         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
			         }
			         else
			         {
			        	 c.setValue("IsDemi", "false");
			        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
			         }
			      //   st.close();
				}	
				catch (SQLException e)
				{
				    System.out.println("Connection Failed! Check output console");
					e.printStackTrace();
					return;
				}
			 }
		//-----********SIMRestore Account Validation*****---------
		 
		 public void SIMRestoreAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
			{
				
				Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
			     LibraryRtp  lC= new LibraryRtp ();
				 ResultSet  rs;
				 int callFound=0,v1=0,v2=0;
				 String xmldata1 ="receive_data";
			     String xmldata2 ="SEND_DATA";
			
			     Application.showMessage("-------------------------------------------------");
			     Application.showMessage("*****RestoreAccount_Validate_Validate Function******");       
			     Application.showMessage("-------------------------------------------------");
			     
			     
			  
			     
				try
				{
					 Statement st =lC. dBConnect(input, c);	
			         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:SimulatorUcontrolClient/restoreAccount'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
				            
				            if(id.equals(c.getValue("ACC_input")))
			            	{
				            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
				            	Application.showMessage("SEND XML is :: \n"+senddata);
			            		//System.out.printf("SEND XML is %s \n",senddata);
			            		Application.showMessage("*******Validation Point 1 :: WebServicall-RestoreAccount_Validate Call********");
			            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
			            		callFound=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            	
				            if(callFound==1)
				            {
				      
				            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
					            Application.showMessage("errorCode is ::"+errorCode);
					           
					            
					           
					            if(errorCode==null || errorCode.equals("0"))
					            {
					            	
						            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
						            	 v1=1;
						            }	
						            else
						            {
						            	c.report("error code is not validated");
						            }
					            }		

				            else
				            {
				               
				            continue;
				            }
				            
				                        
			             }
			         }
			         if(v1 ==1)
			         {
			         	Application.showMessage("WebService Call :: RestoreAccount_Validate is Success[All validation points are Success]");
			         }
			         else
			         {
			        	 c.report("WebService Call :: RestoreAccount_Validate is Failed with Validation Errors");
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
		//-----*****SIMSuspendAccount Validate****---------
		 public void SIMSuspendAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
			{
				
				Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
			     LibraryRtp  lC= new LibraryRtp ();
				 ResultSet  rs;
				 int callFound=0,v1=0,v2=0;
				 String xmldata1 ="receive_data";
			     String xmldata2 ="SEND_DATA";
			
			     Application.showMessage("-------------------------------------------------");
			     Application.showMessage("*****SuspendAccount_Validate Function******");       
			     Application.showMessage("-------------------------------------------------");
			     
			     
			  
			     
				try
				{
					 Statement st =lC. dBConnect(input, c);	
			         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:SimulatorUcontrolClient/suspendAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
				            
				            if(id.equals(c.getValue("ACC_input")))
			            	{
				            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
				            	Application.showMessage("SEND XML is :: \n"+senddata);
			            		//System.out.printf("SEND XML is %s \n",senddata);
			            		Application.showMessage("*******Validation Point 1 :: WebServicall-Suspend Call********");
			            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
			            		callFound=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            	
				            if(callFound==1)
				            {
				      
				            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
					            Application.showMessage("errorCode is ::"+errorCode);
					           			            			           
					           	if(errorCode==null)
					            {
					            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
					            	 v1=1;
					            }	
					            else
					            {
					            	c.report("error code is not validated");
					            }

					            
					            
				               
				            
				            }
				            else
				            {
				            continue;
				            }
				            
				                        
			             }
			         }
			         if(v1 ==1)
			         {
			         	Application.showMessage("WebService Call :: Suspend is Success[All validation points are Success]");
			         }
			         else
			         {
			        	 c.report("WebService Call :: Suspend is Failed with Validation Errors");
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
		 public String createAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, SAXException 
		 {
		 	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		      LibraryRtp  lC= new LibraryRtp ();
		 	 ResultSet  rs;
		 	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
		 	 String xmldata1 ="receive_data";
		      String xmldata2 ="SEND_DATA";
		      String Time= c.get("BaseTime").toString();
		      String rscallpresent="true";
		     // c.setValue("createAccount","true");
		      Application.showMessage("-----------------------------------------------------");
		      Application.showMessage("***********createAccount_Validate_Validate Function***********");       
		      Application.showMessage("----------------------------------------------------");
		      System.out.println("-----------------------------------------------------");
		      System.out.println("***********createAccount_Validate_Validate Function***********");       
		      System.out.println("----------------------------------------------------");
		     // lC.findThinktimeOperationRTP(input, c);
		    //  c.setValue("createAccount","false");
		      HashMap Operation=lC.findingoperations(input, c);
			     c.setValue("OPERATIONVALIDATION",(String) Operation.get("createAccount"));
			     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClScreateAccount"));
			     System.out.println("OPERATIONValidation is "+(String) Operation.get("ClScreateAccount"));


		 	try
		 	{
		 		// Statement st =lC. dBConnect(input, c);	
		       //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		 		rs=lC.reduceThinkTimeRTP(input, c);
		 		if(rs == null)
            	{
            		rscallpresent = "false";
            		
            	
            		
            	}
            	else
           {
		          while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
		          {
		         	
		         
		            String rowmsg=rs.getString(1);
		             if(rs.getBlob("receive_data")==null)
		             {
		             
		             	Application.showMessage("Your Recieve XML is NULL \n");
		             	System.out.println("Your Recieve XML is NULL \n");
		             	
		             	String senddata =lC.extractXml(rs,xmldata2);
		             	Application.showMessage("Your Recieve XML is::\n"+senddata);
		             	System.out.println("Your Recieve XML is::\n"+senddata);
		             }
		             else if(rs.getBlob("SEND_DATA")==null)
		             {
		             	Application.showMessage("Your SEND XML is NULL \n");	
		             	System.out.println("Your SEND XML is NULL \n");	
		             	String recievedata=lC.extractXml(rs,xmldata1);
		             	Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
		             	System.out.println("RECIEVE XML is ::\n"+recievedata); 
		             }
		             else
		             { 
		            	 String senddatacls = lC.extractXml(rs,xmldata2);                                        
                         String recievedatacls = lC.extractXml(rs,xmldata1);      
                             String     senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
                             String     recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
          
		 	          
		            	 
		            	 // String senddata = lC.extractXml(rs,xmldata2);           
		 	            //String recievedata = lC.extractXml(rs,xmldata1);      
		 	          	           
		 	            String accountId_ucontrolsrv = lC.nodeFromKey(senddata,"<ucontrolsrv:accountId>","</ucontrolsrv:accountId>");
		  	            Application.showMessage("accountNumber is ::"+accountId_ucontrolsrv); 
		  	          System.out.println("accountNumber is ::"+accountId_ucontrolsrv); 
		 	            if(accountId_ucontrolsrv.equals(c.getValue("ACC_input")))
		             	{
		 	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		 	            	Application.showMessage("SEND XML is :: \n"+senddata);
		 	            	System.out.println("Recieve XML is::  \n"+ recievedata);
		 	            	System.out.println("SEND XML is :: \n"+senddata);
		             		//System.out.printf("SEND XML is %s \n",senddata);
		             		Application.showMessage("*******Validation Point 1 :: WebServicall-createAccount_Validate********");
		             		Application.showMessage("Validation is Successfull with Input Account Number"+accountId_ucontrolsrv);
		             		System.out.println("*******Validation Point 1 :: WebServicall-createAccount_Validate********");
		             		System.out.println("Validation is Successfull with Input Account Number"+accountId_ucontrolsrv);
		             		callFound=1;
		             	}
		             	else
		             	{
		             		continue;
		             	}
		             	
		 	            if(callFound==1)
		 	            {
		 	      
		 	 	            String status_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:status>","</ucontrolsrv:status>");
		 	 	            Application.showMessage("ucontrolsrv:status is ::"+status_ucontrolsrv); 
		 	 	          System.out.println("ucontrolsrv:status is ::"+status_ucontrolsrv); 
		 	 	            
		 	 	           if(status_ucontrolsrv==null)
		 		            {
		 			            c.report(" ucontrolsrv:status is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("ucontrolsrv:status from Send Xml  from createAccount_Validate is ::"+" "+status_ucontrolsrv);
		 		            	System.out.println("ucontrolsrv:status from Send Xml  from createAccount_Validate is ::"+" "+status_ucontrolsrv);
		 		            	 if(status_ucontrolsrv.equalsIgnoreCase("NEW"))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-ucontrolsrv:status********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv:status::"+" "+status_ucontrolsrv);
		 			            	System.out.println("*******Validation Point 2 :: WebServicall-ucontrolsrv:status********");
		 			            	System.out.println("Validation is Successfull with ucontrolsrv:status::"+" "+status_ucontrolsrv);
		 			            	 v1=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("ucontrolsrv:status at Send Xml not Validated as "+status_ucontrolsrv);
		 			             }
		 		            }					

		 	 	            String firstName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:firstName>","</ucontrolsrv:firstName>");
		 	 	            Application.showMessage("ucontrolsrv:firstName is ::"+firstName_ucontrolsrv); 
		 	 	          System.out.println("ucontrolsrv:firstName is ::"+firstName_ucontrolsrv); 
		 	 	            
		 	 	           if(firstName_ucontrolsrv==null)
		 		            {
		 			            c.report("Send Xml FirstName is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	
		 		            	 if(firstName_ucontrolsrv.equals(c.getValue("FirstName")))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ucontrolsrv:firstName********");
		 			            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+firstName_ucontrolsrv);
		 			            	System.out.println("*******Validation Point 3 :: WebServicall-ucontrolsrv:firstName********");
		 			            	System.out.println("Validation is Successfull with FirstName::"+" "+firstName_ucontrolsrv);
		 			            	
		 			            	 v2=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("FirstName at Send Xml not Validated as "+firstName_ucontrolsrv);
		 			             }
		 		            }

		 	 	            String lastName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:lastName>","</ucontrolsrv:lastName>");
		 	 	            Application.showMessage("ucontrolsrv:lastName is ::"+lastName_ucontrolsrv); 
		 	 	          System.out.println("ucontrolsrv:lastName is ::"+lastName_ucontrolsrv); 
		 	 	            
		 	 	           if(lastName_ucontrolsrv==null)
		 		            {
		 			            c.report("Send Xml ucontrolsrv:lastName is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	
		 		            	 if(lastName_ucontrolsrv.equals(c.getValue("LastName")))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-ucontrolsrv:firstName********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv:lastName::"+" "+lastName_ucontrolsrv);
		 			            	System.out.println("*******Validation Point 4 :: WebServicall-ucontrolsrv:firstName********");
		 			            	System.out.println("Validation is Successfull with ucontrolsrv:lastName::"+" "+lastName_ucontrolsrv);
		 			            	
		 			            	 v3=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("ucontrolsrv:lastName at Send Xml not Validated as "+lastName_ucontrolsrv);
		 			             }
		 		            }

		 	 	            String ucontrolsrv_phoneNumber= lC.nodeFromKey(senddata,"<ucontrolsrv:phoneNumber>","</ucontrolsrv:phoneNumber>");
		 	 	            Application.showMessage("ucontrolsrv:phoneNumber is ::"+ucontrolsrv_phoneNumber); 
		 	 	          System.out.println("ucontrolsrv:phoneNumber is ::"+ucontrolsrv_phoneNumber); 
		 	 	            c.setValue("phoneNumber", ucontrolsrv_phoneNumber);
		 	 	            
		 	 	            String ucontrolsrv_emailAddress= lC.nodeFromKey(senddata,"<ucontrolsrv:emailAddress>","</ucontrolsrv:emailAddress>");
		 	 	            Application.showMessage("ucontrolsrv:emailAddress is ::"+ucontrolsrv_emailAddress); 
		 	 	          System.out.println("ucontrolsrv:emailAddress is ::"+ucontrolsrv_emailAddress); 
		 	 	          
		 	 	            if(ucontrolsrv_emailAddress==null)
		 		            {
		 			            c.report("Send Xml emailAddress is NULL");
		 			            
		 		            }
		 		            else
		 		            {
		 		            	
		 		            	 if(ucontrolsrv_emailAddress.equalsIgnoreCase(c.getValue("emailAddress")))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ucontrolsrv_emailAddress********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv_emailAddress::"+" "+ucontrolsrv_emailAddress);
		 			            	System.out.println("*******Validation Point 5 :: WebServicall-ucontrolsrv_emailAddress********");
		 			            	System.out.println("Validation is Successfull with ucontrolsrv_emailAddress::"+" "+ucontrolsrv_emailAddress);
		 			            	
		 			            	 v4=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("ucontrolsrv_emailAddress at Send Xml not Validated as "+ucontrolsrv_emailAddress);
		 			             }
		 		            }	
		 	 	            
		 	 	            String ucontrolsrv_address1= lC.nodeFromKey(senddata,"<ucontrolsrv:address1>","</ucontrolsrv:address1>");
		 	 	            Application.showMessage("ucontrolsrv:address1 is ::"+ucontrolsrv_address1); 
		 	 	          System.out.println("ucontrolsrv:address1 is ::"+ucontrolsrv_address1); 
		 	 	            c.setValue("Address", ucontrolsrv_address1);
		 	 	           
		 	 	            String ucontrolsrv_city= lC.nodeFromKey(senddata,"<ucontrolsrv:city>","</ucontrolsrv:city>");
		 	 	            Application.showMessage("City is ::"+ucontrolsrv_city); 
		 	 	          System.out.println("City is ::"+ucontrolsrv_city); 
		 	 	            
		 	 	           if(ucontrolsrv_city==null)
		 		            {
		 			            c.report("Send Xml City is NULL");
		 			          
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("City from Send Xml  from ucontrolsrv_city is ::"+" "+ucontrolsrv_city);
		 		            	System.out.println("City from Send Xml  from ucontrolsrv_city is ::"+" "+ucontrolsrv_city);
		 		            	 if(ucontrolsrv_city.equalsIgnoreCase(c.get("Ecity").toString().trim()))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
		 			            	 Application.showMessage("Validation is Successfull with City::"+" "+ucontrolsrv_city);
		 			            	System.out.println("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
		 			            	System.out.println("Validation is Successfull with City::"+" "+ucontrolsrv_city);
		 			            	
		 			            	 v5=1;
		 			             }
		 			             else if(ucontrolsrv_city.equalsIgnoreCase(c.get("city").toString().trim()))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
		 			            	 Application.showMessage("Validation is Successfull with City::"+" "+ucontrolsrv_city);
		 			            	System.out.println("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
		 			            	System.out.println("Validation is Successfull with City::"+" "+ucontrolsrv_city);
		 			            	 
		 			            	 v5=1;
		 			            	 
		 			             }
		 		            	 
		 			             else
		 			             {
		 			            	 c.report("City at Send Xml not Validated as "+ucontrolsrv_city); 
		 			             }
		 		            }	
		 	 	            
		 	 	            String ucontrolsrv_province= lC.nodeFromKey(senddata,"<ucontrolsrv:province>","</ucontrolsrv:province>");
		 	 	            Application.showMessage("ucontrolsrv:province is ::"+ucontrolsrv_province); 
		 	 	          System.out.println("ucontrolsrv:province is ::"+ucontrolsrv_province); 
		 	 	            
		 	 	            String ucontrolsrv_postalCode= lC.nodeFromKey(senddata,"<ucontrolsrv:postalCode>","</ucontrolsrv:postalCode>");
		 	 	            Application.showMessage("ucontrolsrv:postalCode is ::"+ucontrolsrv_postalCode);
		 	 	          System.out.println("ucontrolsrv:postalCode is ::"+ucontrolsrv_postalCode); 
		 	 	            
		 	 	            
		 	 	            c.setValue("PostalCode", ucontrolsrv_postalCode);
		 	 	            
		 	 	            String ucontrolsrv_portalUserSSOId= lC.nodeFromKey(senddata,"<ucontrolsrv:portalUserSSOId>","</ucontrolsrv:portalUserSSOId>");
		 	 	            Application.showMessage("ucontrolsrv:portalUserSSOId is ::"+ucontrolsrv_portalUserSSOId);
		 	 	          System.out.println("ucontrolsrv:portalUserSSOId is ::"+ucontrolsrv_portalUserSSOId); 
		 	 	            
		 	 	            
		 	 	           if(ucontrolsrv_portalUserSSOId==null)
		 		            {
		 			            c.report("Send Xml ucontrolsrv_portalUserSSOId is NULL");
		 			           
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("portalUserSSOId from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_portalUserSSOId);
		 		            	System.out.println("portalUserSSOId from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_portalUserSSOId);
		 		            	 if(ucontrolsrv_portalUserSSOId.equalsIgnoreCase(c.getValue("authorizationGuid")))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 7 :: WebServicall-ucontrolsrv_portalUserSSOId********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv_portalUserSSOId::"+" "+ucontrolsrv_portalUserSSOId);
		 			            	System.out.println("*******Validation Point 7 :: WebServicall-ucontrolsrv_portalUserSSOId********");
		 			            	System.out.println("Validation is Successfull with ucontrolsrv_portalUserSSOId::"+" "+ucontrolsrv_portalUserSSOId);
		 			            	
		 			            	 v6=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("ucontrolsrv_portalUserSSOId at Send Xml not Validated as "+ucontrolsrv_portalUserSSOId);
		 			             }
		 		            }	
		 	 	           // -----------********Services Validation****------------------
		 	 	         String senddataT=RemoveNameSpaces(senddata);
		 	 	           String CreateAcc_Services=c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE");
		 	 	           String Create_ValidServices=SetServiceIdentificationRule(CreateAcc_Services,input,c);
		 	 	         List<String> create_listServices=extractServices(Create_ValidServices,input,c);
		 	 	         Application.showMessage("check::"+create_listServices.size());
		 	 	       System.out.println("check::"+create_listServices.size());
		 	 	           boolean i=true;
		 	 	         
		 	 	        	 if(create_listServices.size()==3)
		 	 	      
		 	 	         {
		 	 	        		String group=lC.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
			 	 	        	String Tier1=lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
		 	 	        	String Tier2=lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
			                Application.showMessage("group is::"+group); 
			                Application.showMessage("Tier1 is::"+Tier1); 
			                Application.showMessage("Tier2 is::"+Tier2); 
			                System.out.println("group is::"+group); 
			                System.out.println("Tier1 is::"+Tier1); 
			                System.out.println("Tier2 is::"+Tier2); 
			                if(i)
				 	 	      {
			                if(group ==null || Tier1==null || Tier2==null)
			                {
			            	Application.showMessage("GroupTier services are NULL");
			            	System.out.println("GroupTier services are NULL");
				            //continue;
			                }
			                else if((group.trim()).equals(create_listServices.get(0)) && (Tier1.trim()).equals(create_listServices.get(1)) && (Tier2.trim()).equals(create_listServices.get(2)) )
			                {
			            	    Application.showMessage("GroupTier services--send Xml  from CreateAccount is Success");
			            	    System.out.println("GroupTier services--send Xml  from CreateAccount is Success");
			            	   v7=1;
			                }
			                else
			                {
			                Application.showMessage("group and Tier validation is not success in Create Account");
			                System.out.println("group and Tier validation is not success in Create Account");
			                c.report("group and Tier validation is not success in Create Account");
			                }
				 	 	      }
		 	 	         }
		 	 	         else if(create_listServices.size()==2)
		 	 	         {
		 	 	        	String group=lC.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
		 	 	        	String Tier1=lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
		 	 	        	
			                Application.showMessage("group is::"+group); 
			                Application.showMessage("Tier1 is::"+Tier1); 
			                System.out.println("group is::"+group); 
			                System.out.println("Tier1 is::"+Tier1); 
			                
			                if(i)
				 	 	      {
			                if(group ==null || Tier1==null )
			                {
			            	Application.showMessage("GroupTier services are NULL");
			            	System.out.println("GroupTier services are NULL");
				            //continue;
			                }
			                else if((group.trim()).equals(create_listServices.get(0)) && (Tier1.trim()).equals(create_listServices.get(1))  )
			                {
			            	    Application.showMessage("GroupTier services--send Xml  from CreateAccount is Success");
			            	    System.out.println("GroupTier services--send Xml  from CreateAccount is Success");
			            	   v7=1;
			                }
			                else
			                {
			                Application.showMessage("group and Tier validation is not success in Create Account");
			                System.out.println("group and Tier validation is not success in Create Account");
			                c.report("group and Tier validation is not success in Create Account");
			                } 
				 	 	      }
		 	 	         }
		 	 	         else
		 	 	         {
		 	 	        	String group=lC.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group></account>");
		 	 	        	
		 	 	        	
			                Application.showMessage("group is::"+group); 
			                System.out.println("group is::"+group); 
			                
			                if(i)
				 	 	      {
			                if(group ==null )
			                {
			            	Application.showMessage("GroupTier services are NULL");
			            	System.out.println("GroupTier services are NULL");
				            //continue;
			                }
			                else if((group.trim()).equals(create_listServices.get(0)) )
			                {
			            	    Application.showMessage("GroupTier services--send Xml  from CreateAccount is Success");
			            	    System.out.println("GroupTier services--send Xml  from CreateAccount is Success");
				            	  
			            	   v7=1;
			                }
			                else
			                {
			                Application.showMessage("group validation is not success in Create Account");
			                System.out.println("group validation is not success in Create Account");
			                c.report("group and Tier validation is not success in Create Account");
			                }
				 	 	      }
		 	 	         }
		 	 	        	String errorCode_createAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
		 	 	            Application.showMessage("errorCode is::"+errorCode_createAcc);
		 	 	          System.out.println("errorCode is::"+errorCode_createAcc);
		 	 	            
		 	 	           if(errorCode_createAcc==null)
		 		            {
		 			            c.report("Send Xml errorCode_createAcc is NULL");
		 			           
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("errorCode_createAcc from Send Xml  is ::"+" "+errorCode_createAcc);
		 		            	System.out.println("errorCode_createAcc from Send Xml  is ::"+" "+errorCode_createAcc);
		 		            	 if(errorCode_createAcc.equals("0"))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
		 			            	System.out.println("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
		 			            	System.out.println("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
		 			            	 
		 			            	 v8=1;
		 			             }
		 		            	 else
		 			             {
		 			            	 c.report("errorCode_createAcc at Send Xml not Validated as "+errorCode_createAcc);
		 			             }
		 		            }	
		 	 	            
		 	 	            
		 	 	            String accountId_createAcc= lC.nodeFromKey(recievedata,"<accountId xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</accountId>");
		 	 	            Application.showMessage("accountId is::"+accountId_createAcc); 
		 	 	          System.out.println("accountId is::"+accountId_createAcc); 
		 	 	                    

		 	 	            
		 	            break;
		 	            }
		              }
		          }

		          if(v1*callFound*v2*v3*v4*v5*v6*v7*v8 ==1)
		          {
		          	Application.showMessage("WebService Call :: CreateAccount is Success[All validation points are Success]");
		          	System.out.println("WebService Call :: CreateAccount is Success[All validation points are Success]");
		          }
		          else
		          {
		         	 c.put("result", "false");
		         	 c.report("WebService Call ::CreateAccount is Failed with Validation Errors");
		          }
		        //  st.close();
		 	}}
		 	catch(Exception e)
		 	{
		 		
			 	    System.out.println("Connection Failed! Check output console");
			 		e.printStackTrace();
			 	
			 	
		 	}
			return rscallpresent;
		 
		 
		 			
			
		  }
	
		
		 public void DiscreateAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, SAXException 
		 {
		 	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		      LibraryRtp  lC= new LibraryRtp ();
		 	 ResultSet  rs;
		 	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
		 	 String xmldata1 ="receive_data";
		      String xmldata2 ="SEND_DATA";
		     
		      Application.showMessage("-----------------------------------------------------");
		      Application.showMessage("***********createAccount_Validate_Validate Function***********");       
		      Application.showMessage("----------------------------------------------------");
		          
		 	try
		 	{
		 		 Statement st =lC. dBConnect(input, c);	
		          rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		          while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
		          {
		         	
		         
		            String rowmsg=rs.getString(1);
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
		 	          	           
		 	            String accountId_ucontrolsrv = lC.nodeFromKey(senddata,"<ucontrolsrv:accountId>","</ucontrolsrv:accountId>");
		  	            Application.showMessage("accountNumber is ::"+accountId_ucontrolsrv); 	           
		 	            if(accountId_ucontrolsrv.equals(c.getValue("ACC_input")))
		             	{
		 	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		 	            	Application.showMessage("SEND XML is :: \n"+senddata);
		             		//System.out.printf("SEND XML is %s \n",senddata);
		             		Application.showMessage("*******Validation Point 1 :: WebServicall-createAccount_Validate********");
		             		Application.showMessage("Validation is Successfull with Input Account Number"+accountId_ucontrolsrv);
		             		callFound=1;
		             	}
		             	else
		             	{
		             		continue;
		             	}
		             	
		 	            if(callFound==1)
		 	            {
		 	      
		 	 	            String status_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:status>","</ucontrolsrv:status>");
		 	 	            Application.showMessage("ucontrolsrv:status is ::"+status_ucontrolsrv); 
		 	 	            
		 	 	           if(status_ucontrolsrv==null)
		 		            {
		 			            c.report(" ucontrolsrv:status is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("ucontrolsrv:status from Send Xml  from createAccount_Validate is ::"+" "+status_ucontrolsrv);
		 		            	 if(status_ucontrolsrv.equalsIgnoreCase("NEW"))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-ucontrolsrv:status********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv:status::"+" "+status_ucontrolsrv);
		 			            	 v1=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("ucontrolsrv:status at Send Xml not Validated as "+status_ucontrolsrv);
		 			             }
		 		            }					

		 	 	            String firstName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:firstName>","</ucontrolsrv:firstName>");
		 	 	            Application.showMessage("ucontrolsrv:firstName is ::"+firstName_ucontrolsrv); 
		 	 	            
		 	 	           if(firstName_ucontrolsrv==null)
		 		            {
		 			            c.report("Send Xml FirstName is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	
		 		            	 if(firstName_ucontrolsrv.equals(c.getValue("FirstName")))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ucontrolsrv:firstName********");
		 			            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+firstName_ucontrolsrv);
		 			            	 v2=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("FirstName at Send Xml not Validated as "+firstName_ucontrolsrv);
		 			             }
		 		            }

		 	 	            String lastName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:lastName>","</ucontrolsrv:lastName>");
		 	 	            Application.showMessage("ucontrolsrv:lastName is ::"+lastName_ucontrolsrv); 
		 	 	            
		 	 	           if(lastName_ucontrolsrv==null)
		 		            {
		 			            c.report("Send Xml ucontrolsrv:lastName is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	
		 		            	 if(lastName_ucontrolsrv.equals(c.getValue("LastName")))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-ucontrolsrv:firstName********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv:lastName::"+" "+lastName_ucontrolsrv);
		 			            	 v3=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("ucontrolsrv:lastName at Send Xml not Validated as "+lastName_ucontrolsrv);
		 			             }
		 		            }

		 	 	            String ucontrolsrv_phoneNumber= lC.nodeFromKey(senddata,"<ucontrolsrv:phoneNumber>","</ucontrolsrv:phoneNumber>");
		 	 	            Application.showMessage("ucontrolsrv:phoneNumber is ::"+ucontrolsrv_phoneNumber); 
		 	 	            c.setValue("phoneNumber", ucontrolsrv_phoneNumber);
		 	 	            
		 	 	            String ucontrolsrv_emailAddress= lC.nodeFromKey(senddata,"<ucontrolsrv:emailAddress>","</ucontrolsrv:emailAddress>");
		 	 	            Application.showMessage("ucontrolsrv:emailAddress is ::"+ucontrolsrv_emailAddress); 
		 	 	          
		 	 	            if(ucontrolsrv_emailAddress==null)
		 		            {
		 			            c.report("Send Xml emailAddress is NULL");
		 			            
		 		            }
		 		            else
		 		            {
		 		            	
		 		            	 if(ucontrolsrv_emailAddress.equalsIgnoreCase(c.getValue("emailAddress")))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ucontrolsrv_emailAddress********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv_emailAddress::"+" "+ucontrolsrv_emailAddress);
		 			            	 v4=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("ucontrolsrv_emailAddress at Send Xml not Validated as "+ucontrolsrv_emailAddress);
		 			             }
		 		            }	
		 	 	            
		 	 	            String ucontrolsrv_address1= lC.nodeFromKey(senddata,"<ucontrolsrv:address1>","</ucontrolsrv:address1>");
		 	 	            Application.showMessage("ucontrolsrv:address1 is ::"+ucontrolsrv_address1); 
		 	 	            c.setValue("Address", ucontrolsrv_address1);
		 	 	           
		 	 	            String ucontrolsrv_city= lC.nodeFromKey(senddata,"<ucontrolsrv:city>","</ucontrolsrv:city>");
		 	 	            Application.showMessage("City is ::"+ucontrolsrv_city); 
		 	 	            
		 	 	           if(ucontrolsrv_city==null)
		 		            {
		 			            c.report("Send Xml City is NULL");
		 			          
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("City from Send Xml  from ucontrolsrv_city is ::"+" "+ucontrolsrv_city);
		 		            	 if(ucontrolsrv_city.equalsIgnoreCase(c.get("Ecity").toString().trim()))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
		 			            	 Application.showMessage("Validation is Successfull with City::"+" "+ucontrolsrv_city);
		 			            	 v5=1;
		 			             }
		 			             else if(ucontrolsrv_city.equalsIgnoreCase(c.get("city").toString().trim()))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
		 			            	 Application.showMessage("Validation is Successfull with City::"+" "+ucontrolsrv_city);
		 			            	 v5=1;
		 			            	 
		 			             }
		 		            	 
		 			             else
		 			             {
		 			            	 c.report("City at Send Xml not Validated as "+ucontrolsrv_city); 
		 			             }
		 		            }	
		 	 	            
		 	 	            String ucontrolsrv_province= lC.nodeFromKey(senddata,"<ucontrolsrv:province>","</ucontrolsrv:province>");
		 	 	            Application.showMessage("ucontrolsrv:province is ::"+ucontrolsrv_province); 
		 	 	            
		 	 	            String ucontrolsrv_postalCode= lC.nodeFromKey(senddata,"<ucontrolsrv:postalCode>","</ucontrolsrv:postalCode>");
		 	 	            Application.showMessage("ucontrolsrv:postalCode is ::"+ucontrolsrv_postalCode); 
		 	 	            
		 	 	            c.setValue("PostalCode", ucontrolsrv_postalCode);
		 	 	            
		 	 	            String ucontrolsrv_portalUserSSOId= lC.nodeFromKey(senddata,"<ucontrolsrv:portalUserSSOId>","</ucontrolsrv:portalUserSSOId>");
		 	 	            Application.showMessage("ucontrolsrv:portalUserSSOId is ::"+ucontrolsrv_portalUserSSOId); 
		 	 	            
		 	 	           if(ucontrolsrv_portalUserSSOId==null)
		 		            {
		 			            c.report("Send Xml ucontrolsrv_portalUserSSOId is NULL");
		 			           
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("portalUserSSOId from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_portalUserSSOId);
		 		            	 if(ucontrolsrv_portalUserSSOId.equalsIgnoreCase(c.getValue("authorizationGuid")))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 7 :: WebServicall-ucontrolsrv_portalUserSSOId********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv_portalUserSSOId::"+" "+ucontrolsrv_portalUserSSOId);
		 			            	 v6=1;
		 			             }
		 			             else
		 			             {
		 			            	 c.report("ucontrolsrv_portalUserSSOId at Send Xml not Validated as "+ucontrolsrv_portalUserSSOId);
		 			             }
		 		            }	
		 	 	           // -----------********Services Validation****------------------
		 	 	         String senddataT=RemoveNameSpaces(senddata);
		 	 	           String CreateAcc_Services=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
		 	 	           String Create_ValidServices=SetServiceIdentificationRule(CreateAcc_Services,input,c);
		 	 	         List<String> create_listServices=extractServices(Create_ValidServices,input,c);
		 	 	         Application.showMessage("check::"+create_listServices.size());
		 	 	           boolean i=true;
		 	 	         
		 	 	        	 if(create_listServices.size()==3)
		 	 	      
		 	 	         {
		 	 	        		String group=lC.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
			 	 	        	String Tier1=lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
		 	 	        	String Tier2=lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
			                Application.showMessage("group is::"+group); 
			                Application.showMessage("Tier1 is::"+Tier1); 
			                Application.showMessage("Tier2 is::"+Tier2); 
			                if(i)
				 	 	      {
			                if(group ==null || Tier1==null || Tier2==null)
			                {
			            	Application.showMessage("GroupTier services are NULL");
				            //continue;
			                }
			                else if((group.trim()).equals(create_listServices.get(0)) && (Tier1.trim()).equals(create_listServices.get(1)) && (Tier2.trim()).equals(create_listServices.get(2)) )
			                {
			            	    Application.showMessage("GroupTier services--send Xml  from CreateAccount is Success");
			            	   v7=1;
			                }
			                else
			                {
			                Application.showMessage("group and Tier validation is not success in Create Account");
			                c.report("group and Tier validation is not success in Create Account");
			                }
				 	 	      }
		 	 	         }
		 	 	         else if(create_listServices.size()==2)
		 	 	         {
		 	 	        	String group=lC.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
		 	 	        	String Tier1=lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
		 	 	        	
			                Application.showMessage("group is::"+group); 
			                Application.showMessage("Tier1 is::"+Tier1); 
			                
			                if(i)
				 	 	      {
			                if(group ==null || Tier1==null )
			                {
			            	Application.showMessage("GroupTier services are NULL");
				            //continue;
			                }
			                else if((group.trim()).equals(create_listServices.get(0)) && (Tier1.trim()).equals(create_listServices.get(1))  )
			                {
			            	    Application.showMessage("GroupTier services--send Xml  from CreateAccount is Success");
			            	   v7=1;
			                }
			                else
			                {
			                Application.showMessage("group and Tier validation is not success in Create Account");
			                c.report("group and Tier validation is not success in Create Account");
			                } 
				 	 	      }
		 	 	         }
		 	 	         else
		 	 	         {
		 	 	        	String group=lC.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group></account>");
		 	 	        	
			                Application.showMessage("group is::"+group); 
			                
			                if(i)
				 	 	      {
			                if(group ==null )
			                {
			            	Application.showMessage("GroupTier services are NULL");
				            //continue;
			                }
			                else if((group.trim()).equals(create_listServices.get(0)) )
			                {
			            	    Application.showMessage("GroupTier services--send Xml  from CreateAccount is Success");
			            	   v7=1;
			                }
			                else
			                {
			                Application.showMessage("group validation is not success in Create Account");
			                c.report("group and Tier validation is not success in Create Account");
			                }
				 	 	      }
		 	 	         }
		 	 	        	String errorCode_createAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
		 	 	            Application.showMessage("errorCode is::"+errorCode_createAcc);
		 	 	            
		 	 	           if(errorCode_createAcc==null)
		 		            {
		 			            c.report("Send Xml errorCode_createAcc is NULL");
		 			           
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("errorCode_createAcc from Send Xml  is ::"+" "+errorCode_createAcc);
		 		            	 if(errorCode_createAcc.equals("0"))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
		 			            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
		 			            	 v8=1;
		 			             }
		 		            	 else
		 			             {
		 			            	 c.report("errorCode_createAcc at Send Xml not Validated as "+errorCode_createAcc);
		 			             }
		 		            }	
		 	 	            
		 	 	            
		 	 	            String accountId_createAcc= lC.nodeFromKey(recievedata,"<accountId xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</accountId>");
		 	 	            Application.showMessage("accountId is::"+accountId_createAcc); 
		 	 	                    

		 	 	            
		 	            break;
		 	            }
		              }
		          }
		          if(v1*callFound*v2*v3*v4*v5*v6*v7*v8 ==1)
		          {
		          	Application.showMessage("WebService Call :: CreateAccount is Success[All validation points are Success]");
		          }
		          else
		          {
		         	 c.put("result", "false");
		         	 c.report("WebService Call ::CreateAccount is Failed with Validation Errors");
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
		 public void SimgetAccout_ValidateSus(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, SAXException
			{
			 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
			 ResultSet  rs;
			 int groups=1;
			 int callFound=0,v1=0,v3=0;
			 
			 String ServicesValue=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: UcontrolServiceSusRes");
			 List<String> GroupTier=extractServices(ServicesValue,input,c);
			 
			 
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		    
		     Application.showMessage("-------------------------------------------------");
		     Application.showMessage("*****getAccount_Validate _Validate Function******");       
		     Application.showMessage("-------------------------------------------------");
		    /* Map<String, String> ResultMap = new HashMap<String,String>();
		     ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
		     c.setValue("NewGroup",ResultMap.get("group"));
		     c.setValue("NewService", ResultMap.get("service"));
		    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
		     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
		     ResultMap=lC.splitter(c.getValue("Product"));
		     c.setValue("ExistingGroup",ResultMap.get("group"));
		     c.setValue("ExistingService", ResultMap.get("service"));
		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
		     
		     
		     
		     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
		     Application.showMessage("NewService::"+c.getValue("NewService"));
		     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
		     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
		     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
		     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));*/
		    // String ServicesValue=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
		     if(ServicesValue.isEmpty() || ServicesValue.equals(null))
		     {
		    	 Application.showMessage("Current Service is not parameterized in Excel");
		     }
		     else if(ServicesValue.contains("+") || ServicesValue.contains("|"))
		     {
		    	c.setValue("IsMultiExist","true");
		     extractServices(ServicesValue,input,c);
		     }
		     else
		     {
		    	 c.setValue("IsMultiExist","false");
		    	 c.setValue("ExistingGroup",ServicesValue);
		     }
		     HashMap Operation=lC.findingoperations(input, c);
		     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccountSim"));
		     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));

			try
			{
				/* Statement st =lC. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:SimulatorUcontrolClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		        */ rs=lC.reduceThinkTimeRTP(input, c);
				while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
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
		                          
			            /*String senddata = lC.extractXml(rs,xmldata2);           
			            String recievedata = lC.extractXml(rs,xmldata1);  */    

			            				String senddatacls = lC.extractXml(rs,xmldata2);                                        
                                            String recievedatacls = lC.extractXml(rs,xmldata1);      
                                                String     senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
                                                String     recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
        
			            String id= lC.nodeFromKey(senddata,"<resourceId>","</resourceId>");
			            Application.showMessage("id is ::"+id); 	           
			            if(id.equals(c.getValue("ACC_input")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            	
			            if(callFound==1)
			            {
			      
			            	
			 	             String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
			 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
			 	             
			 	             if(errorCode_getAcc==null)
				             {
					            c.report("Recieve Xml Account Number is NULL");
					            continue;
				             }
				             else
				             {
				            	 
				            	 if(errorCode_getAcc.equalsIgnoreCase("0"))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
					            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
					             }
				            }		
			 	            
			 	         /*    //-------------My Code-----
			 	            //---------------------******Group Services Validation******-----------------------------
			 	      	  // getServices(services,c,input);
			 	      	   int groups=0;
			 	      	   //List<String> grouplist=extractServices(services, input);
			 	      	   if((grouplist.size())<1)
			 	      	   {
			 	      		 /*  String group1= lC.xpathValue(senddata,"/GetAccountResponse/account/group[1]");;
			 	                 Application.showMessage("group1 is::"+group1); 
			 	         	
			 	                 if(group1 ==null)
			 	                 {
			 	             	Application.showMessage("Group1 is NULL");
			 	                 //continue;
			 	                 }
			 	                 else
			 	                 {
			 	                 if(group1.equals(grouplist.get(0)))
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
			 	      	} 
			 	      	else
			 	      	{
			 	      		List<String> list=extractServices(input);
			 	      		for(int i=0; i<list.size;i++)
			 	              {
			 	      	      for(int j=1; i<=list.size;j++)
			 	      	   	  { 
			 	      	    
			 	      	   	   String groupTier=lC.xpathValue(senddata,"/GetAccountResponse/account/group[j]");
			 	      	   	    if(groupTier ==null)
			 	                 {
			 	             	Application.showMessage("Group is NULL");
			 	                 //continue;
			 	                 }
			 	                else
			 	                {
			 	      	   	    
			 	      	   	   if((groupTier.trim()).equals(list.get(i).trim()))
			 	      	   	   {
			 	      	   	   groups++;
			 	      	   	   }
			 	      	   	   else
			 	      	   	   {
			 	      	   	   continue;
			 	      	   	   }
			 	      	   	  }
			 	               }
			 	             }
			 	            if(groups.equals(grouplist.size))
			 	             {
			 	                  Application.showMessage("*******Validation Point 4 :: GroupTier********");
			 	      		    Application.showMessage("Validation is Successfull with GroupTier::"+" "+services);
			 	      		    v3=1;
			 	      		}
			 	      		   else
			 	      	   {
			 	      		  v3=0;
			 	      		  c.report("group1 at Recieve Xml not Validated as "+services);
			 	      	   }
			 	      	   
			 	      	   //--------------------**** Validation ends****--------------------------------------------
			 	         }
			 	      	   	   
			 	      			 
			 	                 */
			 	             String recievedataT=RemoveNameSpaces(recievedata);
			 	             String status_getAcc= lC.nodeFromKey(recievedata,"<status>","</status>");
			 	             Application.showMessage("GetAccount Status is::"+status_getAcc); 
			 	             //--------------------Group and services validation------------------------
			 	            if(c.getValue("IsMultiExist").equals("true"))
			            	 {
				 	            //String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group><group>");
			 	            	String group1=GetValueByXPath(recievedataT,"/GetAccountResponse/account/group");
				                Application.showMessage("group1 is::"+group1); 
			            	
				                if(group1 ==null)
				                {
				            	Application.showMessage("Group1 is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
				            	   // String check=c.getValue("ExistingGroup");
				            	    if((group1.trim()).equals(c.getValue("ExistingGroup")))
					                 {
					            	 Application.showMessage("*******Validation Point 4 :: Group********");
					            	 Application.showMessage("Validation is Successfull with Group::"+group1);
					            	 v3=1;
					                 }
					                else
					               {
					                	v3=0;
					            	 c.report("group1 at Recieve Xml not Validated as "+group1);
					               }
				                }
				                
				                
				             if(GroupTier.size()==3)
				 	              {
				 	      	      
				 	      	      
				 	      	   	  String Tier1=GetValueByXPath(recievedataT,"/GetAccountResponse/account/group[2]");
				 	      	   	  
				 	      	   String Tier2=GetValueByXPath(recievedataT,"/GetAccountResponse/account/group[3]");
				 	      	 
				 	      	  boolean i=true;
				 	      	  if(i)
				 	      	  {
				 	      	  
				 	      	   	    if(Tier1 ==null || Tier2==null)
				 	                 {
				 	             	Application.showMessage("Tiers are NULL");
				 	                 //continue;
				 	                 }
				 	      	  	 else if((Tier1.trim()).equals(GroupTier.get(1).trim()) && (Tier2.trim()).equals(GroupTier.get(2).trim()) )
				 	      	   	   {
				 	      	   		 
				 	      	   		Application.showMessage("*******Validation Point 4 :: GroupTier********");
				 	      		    Application.showMessage("Validation is Successfull with GroupTier::"+Tier1+"and"+Tier2);
				 	      		    v3=1;
				 	      	   	   }
				 	      	   	   else
				 	      	   	   {
				 	      	   		v3=0;
					 	      		  c.report("Tier at Recieve Xml not Validated ");
				 	      	   	   }
			 	               
				 	      	  }
				 	      	  else
				 	      	  {
				 	      	  Application.showMessage("Nothing");
				 	      	  }
				 	      	   	    
				 	        } 
				 	      	   	  
				 	               
				 	             
				             else 
				             {
				            	 String Tier1=GetValueByXPath(recievedataT,"/GetAccountResponse/account/group[2]");
				            	 if(Tier1 ==null)
			 	                 {
			 	             	Application.showMessage("Tiers are NULL");
			 	                 //continue;
			 	                 }
				            	 
			 	                else
			 	                {
			 	      	   	    
			 	      	   	   if((Tier1.trim()).equals(GroupTier.get(1).trim()) )
			 	      	   	   {
			 	      	   		Application.showMessage("*******Validation Point 4 :: GroupTier********");
			 	      		    Application.showMessage("Validation is Successfull with GroupTier::"+Tier1);
			 	      		    v3=1;
			 	      	   	   }
			 	      	   	   else
			 	      	   	   {
			 	      	   		v3=0;
				 	      		  c.report("Tier at Recieve Xml not Validated ");
			 	      	   	   }
			 	      	   	  
			 	               }
				        }
			          }
				 	            
				              
				                
			            	
			            	 
			            	 else
			            	 {
			            		 // for only one group
			            		    String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group></account>").trim();
					                Application.showMessage("group1 is::"+group1); 
				            	
					                if(group1 ==null)
					                {
					            	Application.showMessage("Group1 is NULL");
						            //continue;
					                }
					                else
					                {
					                	Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
					            	    if(group1.trim().equals(c.getValue("ExistingGroup")))
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
					                
			            		 
			            	 }
				            
			 	            
			            break;
			            }
		             }
		         }
			
		         if(v1*v3*callFound==1)
		         {
		         	Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::Get Account is Failed with Validation Errors");
		         }
		        // st.close();
			}	
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}
			
			} 
		 public void SimgetAccout_ValidateDis(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, SAXException
			{
			 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
			 ResultSet  rs;
			 int groups=1;
			 int callFound=0,v1=0,v3=0;
			 
			 String ServicesValue="350 + ThermostatDataProvider";
			 List<String> GroupTier=extractServices(ServicesValue,input,c);
			 
			 
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		    
		     Application.showMessage("-------------------------------------------------");
		     Application.showMessage("*****getAccount_Disconnect _Validate Function******");       
		     Application.showMessage("-------------------------------------------------");
		    /* Map<String, String> ResultMap = new HashMap<String,String>();
		     ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
		     c.setValue("NewGroup",ResultMap.get("group"));
		     c.setValue("NewService", ResultMap.get("service"));
		    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
		     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
		     ResultMap=lC.splitter(c.getValue("Product"));
		     c.setValue("ExistingGroup",ResultMap.get("group"));
		     c.setValue("ExistingService", ResultMap.get("service"));
		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
		     
		     
		     
		     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
		     Application.showMessage("NewService::"+c.getValue("NewService"));
		     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
		     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
		     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
		     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));*/
		    // String ServicesValue=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
		     if(ServicesValue.isEmpty() || ServicesValue.equals(null))
		     {
		    	 Application.showMessage("Current Service is not parameterized in Excel");
		     }
		     else if(ServicesValue.contains("+"))
		     {
		    	c.setValue("IsMultiExist","true");
		     extractServices(ServicesValue,input,c);
		     }
		     else
		     {
		    	 c.setValue("IsMultiExist","false");
		    	 c.setValue("ExistingGroup",ServicesValue);
		     }
			try
			{
				 Statement st =lC. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:SimulatorUcontrolClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
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
			            if(id.equals(c.getValue("ACC_input")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            	
			            if(callFound==1)
			            {
			      
			            	
			 	             String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
			 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
			 	             
			 	             if(errorCode_getAcc==null)
				             {
					            c.report("Recieve Xml Account Number is NULL");
					            continue;
				             }
				             else
				             {
				            	 
				            	 if(errorCode_getAcc.equalsIgnoreCase("0"))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
					            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
					             }
				            }		
			 	            
			 	         /*    //-------------My Code-----
			 	            //---------------------******Group Services Validation******-----------------------------
			 	      	  // getServices(services,c,input);
			 	      	   int groups=0;
			 	      	   //List<String> grouplist=extractServices(services, input);
			 	      	   if((grouplist.size())<1)
			 	      	   {
			 	      		 /*  String group1= lC.xpathValue(senddata,"/GetAccountResponse/account/group[1]");;
			 	                 Application.showMessage("group1 is::"+group1); 
			 	         	
			 	                 if(group1 ==null)
			 	                 {
			 	             	Application.showMessage("Group1 is NULL");
			 	                 //continue;
			 	                 }
			 	                 else
			 	                 {
			 	                 if(group1.equals(grouplist.get(0)))
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
			 	      	} 
			 	      	else
			 	      	{
			 	      		List<String> list=extractServices(input);
			 	      		for(int i=0; i<list.size;i++)
			 	              {
			 	      	      for(int j=1; i<=list.size;j++)
			 	      	   	  { 
			 	      	    
			 	      	   	   String groupTier=lC.xpathValue(senddata,"/GetAccountResponse/account/group[j]");
			 	      	   	    if(groupTier ==null)
			 	                 {
			 	             	Application.showMessage("Group is NULL");
			 	                 //continue;
			 	                 }
			 	                else
			 	                {
			 	      	   	    
			 	      	   	   if((groupTier.trim()).equals(list.get(i).trim()))
			 	      	   	   {
			 	      	   	   groups++;
			 	      	   	   }
			 	      	   	   else
			 	      	   	   {
			 	      	   	   continue;
			 	      	   	   }
			 	      	   	  }
			 	               }
			 	             }
			 	            if(groups.equals(grouplist.size))
			 	             {
			 	                  Application.showMessage("*******Validation Point 4 :: GroupTier********");
			 	      		    Application.showMessage("Validation is Successfull with GroupTier::"+" "+services);
			 	      		    v3=1;
			 	      		}
			 	      		   else
			 	      	   {
			 	      		  v3=0;
			 	      		  c.report("group1 at Recieve Xml not Validated as "+services);
			 	      	   }
			 	      	   
			 	      	   //--------------------**** Validation ends****--------------------------------------------
			 	         }
			 	      	   	   
			 	      			 
			 	                 */
			 	             String recievedataT=RemoveNameSpaces(recievedata);
			 	             Application.showMessage("ReceiveDatat==::"+recievedataT);
			 	             String status_getAcc= lC.nodeFromKey(recievedata,"<status>","</status>");
			 	             Application.showMessage("GetAccount Status is::"+status_getAcc); 
			 	             //--------------------Group and services validation------------------------
			 	            if(c.getValue("IsMultiExist").equals("true"))
			            	 {
				 	            //String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group><group>");
			 	            	String group1=GetValueByXPath(recievedataT,"/GetAccountResponse/account/group");
				                Application.showMessage("group1 is::"+group1); 
			            	
				                if(group1 ==null)
				                {
				            	Application.showMessage("Group1 is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
				            	   // String check=c.getValue("ExistingGroup");
				            	    if((group1.trim()).equals(c.getValue("ExistingGroup")))
					                 {
					            	 Application.showMessage("*******Validation Point 4 :: Group********");
					            	 Application.showMessage("Validation is Successfull with Group::"+group1);
					            	 v3=1;
					                 }
					                else
					               {
					                	v3=0;
					            	 c.report("group1 at Recieve Xml not Validated as "+group1);
					               }
				                }
				                
				                
				             if(GroupTier.size()==3)
				 	              {
				 	      	      
				 	      	      
				 	      	   	  String Tier1=GetValueByXPath(recievedataT,"/GetAccountResponse/account/group[2]");
				 	      	   	  
				 	      	   String Tier2=GetValueByXPath(recievedataT,"/GetAccountResponse/account/group[3]");
				 	      	 
				 	      	  boolean i=true;
				 	      	  if(i)
				 	      	  {
				 	      	  
				 	      	   	    if(Tier1 ==null || Tier2==null)
				 	                 {
				 	             	Application.showMessage("Tiers are NULL");
				 	                 //continue;
				 	                 }
				 	      	  	 else if((Tier1.trim()).equals(GroupTier.get(1).trim()) && (Tier2.trim()).equals(GroupTier.get(2).trim()) )
				 	      	   	   {
				 	      	   		 
				 	      	   		Application.showMessage("*******Validation Point 4 :: GroupTier********");
				 	      		    Application.showMessage("Validation is Successfull with GroupTier::"+Tier1+"and"+Tier2);
				 	      		    v3=1;
				 	      	   	   }
				 	      	   	   else
				 	      	   	   {
				 	      	   		v3=0;
					 	      		  c.report("Tier at Recieve Xml not Validated ");
				 	      	   	   }
			 	               
				 	      	  }
				 	      	  else
				 	      	  {
				 	      	  Application.showMessage("Nothing");
				 	      	  }
				 	      	   	    
				 	        } 
				 	      	   	  
				 	               
				 	             
				             else 
				             {
				            	// Application.showMessage("Trial");
				            	 String Tier1=GetValueByXPath(recievedataT,"/GetAccountResponse/account/group[2]");
				            	// Application.showMessage("Trial::"+GroupTier.get(1).trim());
				            	// Application.showMessage("Trial::"+Tier1);
				            		 Boolean check=(Tier1.trim()).equals(GroupTier.get(1).trim());
				            		// Application.showMessage("check::"+check);
			 	      	   	  if(check.equals(true))
			 	      	   	   {
			 	      	   		Application.showMessage("*******Validation Point 4 :: GroupTier********");
			 	      		    Application.showMessage("Validation is Successfull with GroupTier::"+Tier1);
			 	      		    v3=1;
			 	      	   	   }
			 	      	   	   else
			 	      	   	   {
			 	      	   		v3=0;
				 	      		  c.report("Tier at Recieve Xml not Validated ");
			 	      	   	   }
			 	      	   	  
			 	               
				        }
			          }
				 	            
				              
				                
			            	
			            	 
			            	 else
			            	 {
			            		 // for only one group
			            		    String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group></account>").trim();
					                Application.showMessage("group1 is::"+group1); 
				            	
					                if(group1 ==null)
					                {
					            	Application.showMessage("Group1 is NULL");
						            //continue;
					                }
					                else
					                {
					                	Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
					            	    if(group1.trim().equals(c.getValue("ExistingGroup")))
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
					                
			            		 
			            	 }
				            
			 	            
			            break;
			            }
		             }
		         }
			
		         if(v1*v3*callFound==1)
		         {
		         	Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::Get Account is Failed with Validation Errors");
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
		 
		 public void SimulatorCancel(Object input,ScriptingContext c) throws IOException, ClassNotFoundException, SQLException, InterruptedException
		 {
			 if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: UcontrolSimulator").equals("1"))
			 {
				 InvalidServicessimulatorChange1(input, c);
			 }
			 else
			 {
			 Application.showMessage("Since the Ucontrol value in simulator is 0......Simulator changes to 0");
			 }
		 
		 }
		 
		 
		
	}


		 				           
		 			           
		 	 	           
		 				                 	 	            
		 	 	            
		 			            
		          
