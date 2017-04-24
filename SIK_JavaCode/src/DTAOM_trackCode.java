import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.Context;
import com.parasoft.api.ScriptingContext;

public class DTAOM_trackCode {

	public int OutPut = 1;
	public String basemsgID = null;
	public Connection connection = null;
	public String xml1;
	public String xml2;
	public boolean firstname = false;
	public boolean lastname = false;
	// public String rowmsg= null;
	public String resultval;

	public Statement dBConnect(Object input, ScriptingContext c)
			throws ClassNotFoundException, SQLException, IOException {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// connection =
		// DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1",
		// "CWDEVOP","CWDEVOP");

		// connection =
		// DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.33:1521:psymqa1",
		// "CWQA1OP","qa1opw1nt3r");

		String host = c.getValue("DB_Host");
		String password = c.getValue("dB_Password");
		String username = c.getValue("dB_Username");
		String port = c.getValue("dB_Port");
		String sid = c.getValue("dB_serviceName");

		connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host
				+ ":" + port + ":" + sid + "", username, password);

		Statement st = connection.createStatement();
		return st;
	}

	/*
	 * public Statement dBConnectCommon(Object input , ScriptingContext c)
	 * throws ClassNotFoundException, SQLException, IOException {
	 * Class.forName("oracle.jdbc.driver.OracleDriver");
	 * 
	 * 
	 * //connection =
	 * DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1"
	 * , "CWDEVOP","CWDEVOP");
	 * 
	 * //connection =
	 * DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.33:1521:psymqa1"
	 * , "CWQA1OP","qa1opw1nt3r");
	 * Application.showMessage(c.getValue("RTPNormalScenariosdS"
	 * ,"DbConnections: CommonDB_UserName")); String host =
	 * c.getValue("RTPNormalScenariosdS","DbConnections: DB_HOST").trim();
	 * String password =
	 * c.getValue("RTPNormalScenariosdS","DbConnections: CommonDB_Password"
	 * ).trim(); String username =
	 * c.getValue("RTPNormalScenariosdS","DbConnections: CommonDB_UserName"
	 * ).trim(); String port =
	 * c.getValue("RTPNormalScenariosdS","DbConnections: DB_Port").trim();
	 * String sid =
	 * c.getValue("RTPNormalScenariosdS","DbConnections: DB_ServiceName"
	 * ).trim();
	 * //Application.showMessage("Debug statement"+host+""+password+""+
	 * username+""+port+""+sid);
	 * 
	 * connection =
	 * DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port+":"+sid
	 * +"", username,password); Statement st1 = connection.createStatement();
	 * return st1; }
	 */

	public Statement dBConnectCommon(Object input, ScriptingContext c)
			throws ClassNotFoundException, SQLException, IOException {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// connection =
		// DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1",
		// "CWDEVOP","CWDEVOP");

		// connection =
		// DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.33:1521:psymqa1",
		// "CWQA1OP","qa1opw1nt3r");
		String host = c.getValue("DB_Host");
		String password = c.getValue("dB_PasswordCommon");
		String username = c.getValue("dB_UsernameCommon");
		String port = c.getValue("dB_Port");
		String sid = c.getValue("dB_serviceName");
		// Application.showMessage("Debug statement"+host+""+password+""+username+""+port+""+sid);

		connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host
				+ ":" + port + ":" + sid + "", username, password);
		Statement st1 = connection.createStatement();
		return st1;
	}

	public void simulatorChangeSIK(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, SQLException,
			InterruptedException {

		// PreparedStatement ps= connection.prepareStatement("my_temp_proc(?)");

		Statement st = dBConnect(input, c);
		ResultSet rs = st
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='swivelSIK:swivelSIKConfig'");
		while (rs.next()) {
			String beforeSim = rs.getString(2);
			// c.setValue("BaseMsgid", basemsgID);
			Application.showMessage("Db Simulator Before Updation ::"
					+ beforeSim);

		}
		st.close();
		Statement st0 = dBConnect(input, c);

		// String
		// fullSql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><UpDownGradeForDotcom>0</UpDownGradeForDotcom><enableSMBFlow>1</enableSMBFlow><discDuration>30</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>30</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101010096771</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>100|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><simUControlErrorCode>UCE-15101</simUControlErrorCode><simUCtrlCreateErrCd>0&lt;/errorCode&gt;</simUCtrlCreateErrCd><simUControlErrorMessage>Account 9999647611502 not found</simUControlErrorMessage><activationCode>277813</activationCode><discoLetterLocation>/opt/home/DTA/abinitio/vantage/bhs/dtdevdummy/test</discoLetterLocation><discoLetterLocation_UI_Test>/opt/home/DTA/abinitio/vantage/bhs/dtdevdummy/test</discoLetterLocation_UI_Test><discoLetterTranScript>/opt/home/DTA/abinitio/vantage/bhs/dtdev/script/transfer.sh</discoLetterTranScript><discoLetterTranScript_UI_Test>/app/sym-vision/script/transfer.sh</discoLetterTranScript_UI_Test><status>ACTIVE</status><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><LineOfService>XHS</LineOfService><suspendAccount>0</suspendAccount><IsDotcomXhsSikEnabled>1</IsDotcomXhsSikEnabled><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><disLetterFile1>HS9LETTER_DANTOMHS_</disLetterFile1><disLetterfileDateFormat1>yyyyMMdd_hhmmss</disLetterfileDateFormat1><disLetterFileExt1>.extract</disLetterFileExt1><disLetterFile2>HS9LETTER_DANTOMHS_</disLetterFile2><disLetterfileDateFormat2>yyyyMMdd_hhmmss</disLetterfileDateFormat2><disLetterFileExt2>.extract.fin</disLetterFileExt2><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>0</isFoxtrotFlowEnable><AOservicesProductIds>10097</AOservicesProductIds><peripheralProductsIds>40031</peripheralProductsIds><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isDotcomXhsSIKLogEnabled>1</isDotcomXhsSIKLogEnabled><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
		// String
		// sql1="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><UpDownGradeForDotcom>0</UpDownGradeForDotcom><enableSMBFlow>1</enableSMBFlow><discDuration>30</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>30</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101010096771</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>100|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><simUControlErrorCode>UCE-15101</simUControlErrorCode><simUCtrlCreateErrCd>0&lt;/errorCode&gt;</simUCtrlCreateErrCd><simUControlErrorMessage>Account 9999647611502 not found</simUControlErrorMessage><activationCode>277813</activationCode>";
		// String
		// sql2="<discoLetterLocation>/opt/home/DTA/abinitio/vantage/bhs/dtdevdummy/test</discoLetterLocation><discoLetterLocation_UI_Test>/opt/home/DTA/abinitio/vantage/bhs/dtdevdummy/test</discoLetterLocation_UI_Test><discoLetterTranScript>/opt/home/DTA/abinitio/vantage/bhs/dtdev/script/transfer.sh</discoLetterTranScript><discoLetterTranScript_UI_Test>/app/sym-vision/script/transfer.sh</discoLetterTranScript_UI_Test><status>ACTIVE</status><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><LineOfService>XHS</LineOfService><suspendAccount>0</suspendAccount><IsDotcomXhsSikEnabled>1</IsDotcomXhsSikEnabled><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><disLetterFile1>HS9LETTER_DANTOMHS_</disLetterFile1><disLetterfileDateFormat1>yyyyMMdd_hhmmss</disLetterfileDateFormat1><disLetterFileExt1>.extract</disLetterFileExt1><disLetterFile2>HS9LETTER_DANTOMHS_</disLetterFile2><disLetterfileDateFormat2>yyyyMMdd_hhmmss</disLetterfileDateFormat2><disLetterFileExt2>.extract.fin</disLetterFileExt2><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>0</isFoxtrotFlowEnable><AOservicesProductIds>10097</AOservicesProductIds><peripheralProductsIds>40031</peripheralProductsIds><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isDotcomXhsSIKLogEnabled>1</isDotcomXhsSIKLogEnabled><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
		String sql = "update CWFTEMPLATECONFIG set DATA='<swivelSIKConfig><name>swivelSIK:swivelSIKConfig</name><cancelOrdErrorsForRetry>Order-CancelOrder-501</cancelOrdErrorsForRetry><getOrdErrorsForRetry>Order-GetOrderForModification-604</getOrdErrorsForRetry><sikSerivceConfigErrors>Order-SubmitOrder-103|Order-SubmitOrder-104|Order-SubmitOrder-201|Order-SubmitOrder-202|Order-SubmitOrder-212</sikSerivceConfigErrors><subOrdSinOrdNotExactMatchError>Order-SubmitOrder-213</subOrdSinOrdNotExactMatchError><subOrdMulOrdExistsError>Order-SubmitOrder-211</subOrdMulOrdExistsError><modOrdCannotModifiedError>Order-GetOrderForModification-604</modOrdCannotModifiedError><sikmaxRetryCount>3</sikmaxRetryCount><sikmaxRetryInterval>2</sikmaxRetryInterval><sikretryIntervalForServiceRetry>5</sikretryIntervalForServiceRetry><sikpauseIntervalForServiceRetry>120</sikpauseIntervalForServiceRetry><sikretryLimitBeforeQueuePause>3</sikretryLimitBeforeQueuePause><lsmaxRetryCount>2</lsmaxRetryCount><lsmaxRetryInterval>5</lsmaxRetryInterval><lsretryIntervalForServiceRetry>10</lsretryIntervalForServiceRetry><lspauseIntervalForServiceRetry>120</lspauseIntervalForServiceRetry><lsretryLimitBeforeQueuePause>5</lsretryLimitBeforeQueuePause><csgOperatorToggle>1</csgOperatorToggle><RMA_CM_EMTA_KIT>HSD-RMA-MODEM-75</RMA_CM_EMTA_KIT><RMA_SMALL_KIT>VID-RMA-DCT700-70</RMA_SMALL_KIT><RMA_LARGE_KIT>VID-RMA-DCT2xxx-72</RMA_LARGE_KIT><RMA_LABEL_KIT>RMA-DF-LABEL-01</RMA_LABEL_KIT><RMA_AcquisitionMethods>RMA_CM_EMTA_KIT,RMA_LABEL_KIT,RMA_LARGE_KIT,RMA_SMALL_KIT</RMA_AcquisitionMethods><CSGSIKOperator>ZZO|ZZQ</CSGSIKOperator><enableDDPSIKRSHWorkOrder>1</enableDDPSIKRSHWorkOrder><DTATrackCode>VI321,VT370,VTC51</DTATrackCode><DTAChannel>"
				+ c.getValue("dS_SIK", "Sik_Dotcom: Simulator_ChannelValue")
				+ "</DTAChannel><DTASwapCode>"
				+ c.getValue("dS_SIK", "Sik_Dotcom: Simulator_SwapValue")
				+ "</DTASwapCode></swivelSIKConfig>' where NAME='swivelSIK:swivelSIKConfig'";
		st0.executeUpdate(sql);

		// ps.setString(1, sql);

		// ps.execute();

		// String a
		// ="<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><UpDownGradeForDotcom>0</UpDownGradeForDotcom><enableSMBFlow>1</enableSMBFlow><discDuration>30</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>30</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101010096771</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>100|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><simUControlErrorCode>UCE-15101</simUControlErrorCode><simUCtrlCreateErrCd>0&lt;/errorCode&gt;</simUCtrlCreateErrCd><simUControlErrorMessage>Account 9999647611502 not found</simUControlErrorMessage><activationCode>277813</activationCode><discoLetterLocation>/opt/home/DTA/abinitio/vantage/bhs/dtdevdummy/test</discoLetterLocation><discoLetterLocation_UI_Test>/opt/home/DTA/abinitio/vantage/bhs/dtdevdummy/test</discoLetterLocation_UI_Test><discoLetterTranScript>/opt/home/DTA/abinitio/vantage/bhs/dtdev/script/transfer.sh</discoLetterTranScript><discoLetterTranScript_UI_Test>/app/sym-vision/script/transfer.sh</discoLetterTranScript_UI_Test><status>ACTIVE</status>";
		// String b
		// ="<getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><LineOfService>XHS</LineOfService><suspendAccount>0</suspendAccount><IsDotcomXhsSikEnabled>1</IsDotcomXhsSikEnabled><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><disLetterFile1>HS9LETTER_DANTOMHS_</disLetterFile1><disLetterfileDateFormat1>yyyyMMdd_hhmmss</disLetterfileDateFormat1><disLetterFileExt1>.extract</disLetterFileExt1><disLetterFile2>HS9LETTER_DANTOMHS_</disLetterFile2><disLetterfileDateFormat2>yyyyMMdd_hhmmss</disLetterfileDateFormat2><disLetterFileExt2>.extract.fin</disLetterFileExt2><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>0</isFoxtrotFlowEnable><AOservicesProductIds>10097</AOservicesProductIds><peripheralProductsIds>40031</peripheralProductsIds><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isDotcomXhsSIKLogEnabled>1</isDotcomXhsSIKLogEnabled><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>";
		// Application.showMessage("Before new fun");
		// st0.executeUpdate("update CWFTEMPLATECONFIG set DATA='+a'where NAME='homesecurityUtil:hsConfig'");
		// updateLongSql(st0, "CWFTEMPLATECONFIG", "DATA",
		// "where NAME='homesecurityUtil:hsConfig'", a+b);
		// Application.showMessage("Afer new fun");
		st0.close();
		Thread.sleep(300000);
		Statement st1 = dBConnect(input, c);
		ResultSet rs1 = st1
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='swivelSIK:swivelSIKConfig'");
		while (rs1.next()) {
			String afterSim = rs1.getString(2);
			// c.setValue("BaseMsgid", basemsgID);
			Application
					.showMessage("Db Simulator After Updation ::" + afterSim);

		}
		st0.close();

	}

	public void submitOrder_Validate(Object input, ScriptingContext c)
			throws InterruptedException, ClassNotFoundException, IOException,
			XPathExpressionException, SAXException {
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		sikLibraryClass sL = new sikLibraryClass();
		ResultSet rs;
		int callFound = 0, v1 = 0, v2 = 0, v3 = 0, v4 = 0;
		String xmldata1 = "receive_data";
		String xmldata2 = "SEND_DATA";
		String Time = c.get("BaseTime").toString();
		String csgDTA=null;
		Context AppContext = Application.getContext();
		Application
				.showMessage("-----------------------------------------------------");
		Application
				.showMessage("**********submitOrder_Validate Function************");
		Application
				.showMessage("----------------------------------------------------");
		HashMap Operation=sL.findingoperations(input, c);
		c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
			     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));
		try {
			//Statement st = sL.dBConnect(input, c);
			//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"
				//	+ Time + "' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);

			while (rs.next()) {

				String rowmsg;
				rowmsg = rs.getString(1);
				Application.showMessage("MessageID is::" + rowmsg);

				if (rs.getBlob("receive_data") == null) {

					Application.showMessage("Your Recieve XML is NULL \n");

					String senddata = sL.extractXml(rs, xmldata2);
					Application.showMessage("Your Recieve XML is::\n"
							+ senddata);
				} else if (rs.getBlob("SEND_DATA") == null) {
					Application.showMessage("Your SEND XML is NULL \n");
					String recievedata = sL.extractXml(rs, xmldata1);
					Application
							.showMessage("RECIEVE XML is ::\n" + recievedata);
				} else {

					String senddata = sL.extractXml(rs, xmldata2);
					String recievedata = sL.extractXml(rs, xmldata1);
					Application.showMessage("Recieve XML is::  \n"
							+ recievedata);
					Application.showMessage("SEND XML is :: \n" + senddata);

					String sik_AccountNumber = sL.nodeFromKey(senddata,
							"<sik:AccountNumber>", "</sik:AccountNumber>");
					Application.showMessage("accountNumber is ::"
							+ sik_AccountNumber);

					if (sik_AccountNumber == null) {
						continue;
					} else if (sik_AccountNumber.equals(c
							.getValue("accountNumber"))) {
						Application.showMessage("Recieve XML is::  \n"
								+ recievedata);
						Application.showMessage("SEND XML is :: \n" + senddata);
						// System.out.printf("SEND XML is %s \n",senddata);
						Application
								.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
						Application
								.showMessage("Validation is Successfull with Input Account Number"
										+ sik_AccountNumber);
						callFound = 1;
					} else {
						continue;
					}

					if (callFound == 1) {

						if (senddata
								.contains("<sik:E911_acceptance>1</sik:E911_acceptance>")) {
							Application
									.showMessage("E911 acceptance Validated as 1 for CDV Order");
						} else if (senddata
								.contains("<sik:E911_acceptance>0</sik:E911_acceptance>")) {
							Application
									.showMessage("E911 acceptance Validated as 0 for CDV Order");
						}

						else {
							Application.showMessage("Is not an CDV Order");

						}
						// String sik_Email=
						// sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");
						String sik_Email = sL.nodeFromKey(senddata,
								"<sik:Email>", "</sik:Email>");

						Application.showMessage("sik_Email is ::" + sik_Email);
						if (sik_Email == null) {
							c.report(" sik_Email is NULL");
							continue;
						} else {
							Application
									.showMessage("sik_Email from Send Xml  from processHomeSecurityInfo_Validate is ::"+ c.getValue("emailAddress").toString());
							
							if (sik_Email.equalsIgnoreCase(c
									.getValue("emailAddress"))) {
								Application
										.showMessage("*******Validation Point 4 :: WebServicall-emailAddress********");
								Application
										.showMessage("Validation is Successfull with emailAddress::"
												+ " " + sik_Email);
								v1 = 1;
							}

							else {
								Application.showMessage("emailAddress at Send Xml not Validated as "+ sik_Email);
								continue;
							}
						}

						

						String sik_FirstName = sL.nodeFromKey(senddata,
								"<sik:FirstName>", "</sik:FirstName>");
						Application.showMessage("firstName is ::"
								+ sik_FirstName);
						Application.showMessage("Firstname:::"+c.getValue("FirstName"));
								
             
						if (c.getValue("FirstName") == null || sik_FirstName.contains("NO Data Fetched from in-between strings")) {
							Application.showMessage("Firstname is empty");
							
							firstname = true;
							
						} else {
							Application
									.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"
											+ " " + sik_FirstName);
							if (sik_FirstName.equals(c.getValue("FirstName"))) {
								Application
										.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
								Application
										.showMessage("Validation is Successfull with FirstName::"
												+ " " + sik_FirstName);
								v2 = 1;
							} else {
								Application
										.showMessage("FirstName at Send Xml not Validated as "
										+ sik_FirstName);
							}
						}
						if (firstname == true) {
						String sik_LastName = sL.nodeFromKey(senddata,
								"<sik:LastName>", "</sik:LastName>");
							String lastName = c.getValue("LastName");
						Application
									.showMessage("lastName from sendXML is ::"
											+ sik_LastName);
							Application
									.showMessage("lastName from inputsheet is ::"
											+ lastName);

							if (lastName == null || lastName.isEmpty() || lastName.equals("")) {
								lastname = true;
						} else {
							Application
									.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"
											+ " " + sik_LastName);
							if (sik_LastName.equals(c.getValue("LastName"))) {
								Application
										.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
								Application
										.showMessage("Validation is Successfull with FirstName::"
												+ " " + sik_LastName);
								v2 = 1;
							} else {
									Application
											.showMessage("LastName at Send Xml not Validated as "
										+ sik_LastName);
							}
						}
							if (lastname == true) {
								String companyName = c.getValue("Sik_CSGDTA",
										"dS_SIK: companyname");
								Application.showMessage("CompanyName::" + companyName);
								if (sik_LastName.equalsIgnoreCase(companyName)) {
									Application
											.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
									Application
											.showMessage("Validation is Successfull with FirstName::"
													+ " " + sik_LastName);
									v2 = 1;
								} else {
									c.report("sendXML not validated for Firstname/lastname/company name");
								}
							}
						}

						String sik_ShipmentPriority = sL.nodeFromKey(senddata,
								"<sik:ShipmentPriority>",
								"</sik:ShipmentPriority>");
						Application.showMessage("ShipmentPriority is ::"
								+ sik_ShipmentPriority);

						String sik_FTA = sL.nodeFromKey(senddata, "<sik:FTA>",
								"</sik:FTA>");
						Application.showMessage("sik_FTA is ::" + sik_FTA);

						Application.showMessage("SHIP_City from bfc"
								+ c.get("SHIP_City"));

						Application.showMessage("FTA from Get Loc"
								+ c.get("GL_ftaAgent"));
						// Application.showMessage("FTA from Get Loc"
						// +c.get("GL_FranchiseTaxArea"));

						if (c.getValue("BillingSystem").equalsIgnoreCase("DDP")) {

							if (((String) c.get("GL_ftaAgent")).trim().equals(
									"0".concat(sik_FTA.trim()))) {
								Application
										.showMessage("FTA is been Validated with Get Location ftaAgent As Flow is DDP ::"
												+ sik_FTA);
							} else {
								c.report("FTA is NOT been Validated with Get Location ftaAgent As Flow is DDP ::"
										+ sik_FTA);

							}
						} else if (c.getValue("BillingSystem")
								.equalsIgnoreCase("CSG")) {
							if ((((String) c.get("GL_FranchiseTaxArea")).trim())
									.equals(sik_FTA.trim())) {
								Application
										.showMessage("FTA is been Validated with Get Location FranchiseTaxArea As Flow is CSG ::"
												+ sik_FTA);
							} else {
								Application
								.showMessage("FTA is NOT been Validated with Get Location FranchiseTaxArea As Flow is CSG ::"
										+ sik_FTA);

							}
						}

						String sik_Address1 = sL.nodeFromKey(senddata,
								"<sik:Address1>", "</sik:Address1>");
						Application.showMessage("sik_Address1 is ::"
								+ sik_Address1);

						String sik_Address2 = sL.nodeFromKey(senddata,
								"<sik:Address2>", "</sik:Address2>");
						Application.showMessage("sik_Address2 is ::"
								+ sik_Address2);

						String sik_City = sL.nodeFromKey(senddata,
								"<sik:City>", "</sik:City>");
						Application.showMessage("sik_City is ::" + sik_City);

						String sik_State = sL.nodeFromKey(senddata,
								"<sik:State>", "</sik:State>");
						Application.showMessage("sik_State is ::" + sik_State);

						String sik_Zip = sL.nodeFromKey(senddata, "<sik:Zip>",
								"</sik:Zip>");
						Application.showMessage("sik_Zip is ::" + sik_Zip);

						String sik_IsAlternateAddress = sL.nodeFromKey(
								senddata, "<sik:IsAlternateAddress>",
								"</sik:IsAlternateAddress>");
						Application.showMessage("sik_IsAlternateAddress is ::"
								+ sik_IsAlternateAddress);
						/*------------------------------------------------------------------------------------------------
						 *         PSN required validation
						 *         
						 * 
						 ----------------------------------------------------------------------------------------------*/
						
						csgDTA=c.getValue("csgDTA").toString();
						if(csgDTA.equalsIgnoreCase("NO"))
						{
						String DTAChannel = c.getValue("dS_SIK",
								"Sik_Dotcom: DTA_Channel").trim();// fetching
																	// channel
																	// value
																	// sent in
																	// request
						Application.showMessage("DTAChannel ::" + DTAChannel);
						String DTASwapCode = c.getValue("dS_SIK",
								"Sik_Dotcom: SW_Value").trim();// fetching swap
																// value sent in
																// request
						Application.showMessage("DTASwapCode ::" + DTASwapCode);
						String SimulatorChannel = c.getValue("dS_SIK",
								"Sik_Dotcom: Simulator_ChannelValue").trim();// fetching
																				// channel
																				// value
																				// configured
																				// in
																				// UI
																				// through
																				// Input
						Application
								.showMessage("Simulator value of channel configured ::"
										+ SimulatorChannel);
						if (SimulatorChannel.equals("")
								|| SimulatorChannel.equals(null)) {

							String PSNRequired = sL.GetValueByXPath(senddata,
									".//@PSNRequired");
							Application.showMessage("PSNRequired is ::"
									+ PSNRequired);
							sL.validationPointIgnoreCase(
									PSNRequired,
									"XPath ERROR.Either NULL or Xpath needs to be Fixed",
									input, c);

							String SwapFlag = sL.GetValueByXPath(senddata,
									".//@SWAP");
							Application
									.showMessage("SwapFlag is ::" + SwapFlag);
							sL.validationPointIgnoreCase(
									SwapFlag,
									"XPath ERROR.Either NULL or Xpath needs to be Fixed",
									input, c);
							Application
									.showMessage("Channel not configured..!");
						} else {
							if ((DTAChannel.equals("") || DTAChannel
									.equals(null))
									&& (DTASwapCode.equals("") || DTASwapCode
											.equals(null))) {

								Application
										.showMessage("Both DTAChannel and DTASwapCode is not present..!");

							} else if (DTAChannel.equalsIgnoreCase("DTA")
									&& DTASwapCode.equalsIgnoreCase("MPEG4")) {

								String PSNRequired = sL.GetValueByXPath(
										senddata, ".//@PSNRequired");
								Application.showMessage("PSNRequired is ::"
										+ PSNRequired);
								sL.validationPointIgnoreCase(PSNRequired,
										"True", input, c);

								String SwapFlag = sL.GetValueByXPath(senddata,
										".//@SWAP");
								Application.showMessage("SwapFlag is ::"
										+ SwapFlag);
								sL.validationPointIgnoreCase(SwapFlag, "True",
										input, c);
								Application
										.showMessage("Both DTAChannel and DTASwapCode is present..!");

							} else if (DTAChannel.equalsIgnoreCase("DTA")
									&& (DTASwapCode.equals("") || DTASwapCode
											.equals(null))) {

								String PSNRequired = sL.GetValueByXPath(
										senddata, ".//@PSNRequired");
								Application.showMessage("PSNRequired is ::"
										+ PSNRequired);
								sL.validationPointIgnoreCase(PSNRequired,
										"True", input, c);

								String SwapFlag = sL.GetValueByXPath(senddata,
										".//@SWAP");
								Application.showMessage("SwapFlag is ::"
										+ SwapFlag);
								sL.validationPointIgnoreCase(
										SwapFlag,
										"XPath ERROR.Either NULL or Xpath needs to be Fixed",
										input, c);
								Application
										.showMessage("Only DTAChannel is present..!");

							} else if ((DTAChannel.equals("") || DTAChannel
									.equals(null))
									&& DTASwapCode.equalsIgnoreCase("MPEG4")) {
								String PSNRequired = sL.GetValueByXPath(
										senddata, ".//@PSNRequired");
								Application.showMessage("PSNRequired is ::"
										+ PSNRequired);
								sL.validationPointIgnoreCase(
										PSNRequired,
										"XPath ERROR.Either NULL or Xpath needs to be Fixed",
										input, c);

								String SwapFlag = sL.GetValueByXPath(senddata,
										".//@SWAP");
								Application.showMessage("SwapFlag is ::"
										+ SwapFlag);
								sL.validationPointIgnoreCase(
										SwapFlag,
										"XPath ERROR.Either NULL or Xpath needs to be Fixed",
										input, c);
								Application
										.showMessage("Only SwapCode is present..!");

							} else {
								String PSNRequired = sL.GetValueByXPath(
										senddata, ".//@PSNRequired");
								Application.showMessage("PSNRequired is ::"
										+ PSNRequired);
								sL.validationPointIgnoreCase(PSNRequired,
										"True", input, c);

							}

						

						// -------------------------------------------------------------------------------------------------------//
						//
						// SIK Address validation module with Alternate address
						// Logic
						//
						// -------------------------------------------------------------------------------------------------------//
						}
						if (sik_IsAlternateAddress.equals("1")) {
							if (c.get("SI_Add1") == null
									|| c.get("SI_Add2") == null
									|| c.get("SI_City") == null
									|| c.get("SI_State") == null
									|| c.get("SI_Zip") == null) {
								if (((String) c.get("SHIP_StreetAddress"))
										.equalsIgnoreCase(sik_Address1)) {
									Application
											.showMessage("Street Address of sik is Validated with Shipping Contact as ::"
													+ sik_Address1);
								} else {
									c.report("Street Address of sik is NOT Validated with Shipping Contact as ::"
											+ sik_Address1);

								}

								if (((String) c.get("SHIP_City"))
										.equalsIgnoreCase(sik_City)) {
									Application
											.showMessage("City of sik is Validated with Shipping Contact as ::"
													+ sik_City);
								} else {
									c.report("City of sik is NOT Validated with Shipping Contact as ::"
											+ sik_City);

								}

								if (((String) c.get("SHIP_State"))
										.equalsIgnoreCase(sik_State)) {
									Application
											.showMessage("State of sik is Validated with Shipping Contact as ::"
													+ sik_State);
								} else {
									c.report("State of sik is NOT Validated with Shipping Contact as ::"
											+ sik_State);

								}

								if (((String) c.get("SHIP_ZipCode"))
										.equalsIgnoreCase(sik_Zip)) {
									Application
											.showMessage("ZipCode of sik is Validated with Shipping Contact as ::"
													+ sik_Zip);
								} else {
									c.report("ZipCode of sik is NOT Validated with Shipping Contact as ::"
											+ sik_Zip);

								}
							}

							else {

								if (((String) c.get("SI_Add1"))
										.equalsIgnoreCase(sik_Address1)) {
									Application
											.showMessage("Street Address of sik is Validated with Special Instruction as ::"
													+ sik_Address1);
								} else {
									c.report("Street Address of sik is NOT Validated with Special Instruction as ::"
											+ sik_Address1);

								}

								if (((String) c.get("SI_City"))
										.equalsIgnoreCase(sik_City)) {
									Application
											.showMessage("City of sik is Validated with Special Instruction as ::"
													+ sik_City);
								} else {
									c.report("City of sik is NOT Validated with Special Instruction as ::"
											+ sik_City);

								}

								if (((String) c.get("SI_State"))
										.equalsIgnoreCase(sik_State)) {
									Application
											.showMessage("State of sik is Validated with Shipping Contact as ::"
													+ sik_State);
								} else {
									c.report("State of sik is NOT Validated with Shipping Contact as ::"
											+ sik_State);

								}

								if (((String) c.get("SI_Zip"))
										.equalsIgnoreCase(sik_Zip)) {
									Application
											.showMessage("ZipCode of sik is Validated with Special Instruction as ::"
													+ sik_Zip);
								} else {
									c.report("ZipCode of sik is NOT Validated with Shipping Contact as ::"
											+ sik_Zip);

								}
							}
						} else if (sik_IsAlternateAddress.equals("0")) {
							if (c.get("StreetName_Loc").toString()
									.equalsIgnoreCase(sik_Address1.trim())) {
								Application
										.showMessage("Street Address of sik is Validated with Get Location as ::"
												+ sik_Address1);
							} else {
								c.report("Street Address of sik is NOT Validated with Get Location as ::"
										+ sik_Address1);

							}

							if (((String) c.get("GL_City"))
									.equalsIgnoreCase(sik_City)) {
								Application
										.showMessage("City of sik is Validated with Get Location as ::"
												+ sik_City);
							} else {
								c.report("City of sik is NOT Validated with Get Location as ::"
										+ sik_City);

							}

							if (((String) c.get("GL_state"))
									.equalsIgnoreCase(sik_State)) {
								Application
										.showMessage("State of sik is Validated with Get Location as ::"
												+ sik_State);
							} else {
								c.report("State of sik is NOT Validated with Get Locationt as ::"
										+ sik_State);

							}

							if (((String) c.get("GL_zip5"))
									.equalsIgnoreCase(sik_Zip)) {
								Application
										.showMessage("ZipCode of sik is Validated with Get Location as ::"
												+ sik_Zip);
							} else {
								c.report("ZipCode of sik is NOT Validated with Get Location as ::"
										+ sik_Zip);

							}
						} else {
							c.report("SIK Address not Validated");
						}

						String sik_ProductCode = sL.nodeFromKey(senddata,
								"<sik:ProductCode>", "</sik:ProductCode>");
						Application.showMessage("sik_ProductCode is ::"
								+ sik_ProductCode);

						if (c.getValue("orderEntryOption").equalsIgnoreCase(
								"OWN")) {

							Boolean isverified = sL.modemVerification(
									sik_ProductCode, c, input);
							if (isverified == true) {
								Application.showMessage("Verified Modem as"
										+ sik_ProductCode);
							} else {
								Application.showMessage("NOT Verified Modem as"
										+ sik_ProductCode);
							}

						}

						String sik_DNCSIPAddress = sL.nodeFromKey(senddata,
								"<sik:DNCSIPAddress>", "</sik:DNCSIPAddress>");
						Application.showMessage("sik_DNCSIPAddress is ::"
								+ sik_DNCSIPAddress);

						String sik_HeadEndVendor = sL.nodeFromKey(senddata,
								"<sik:HeadEndVendor>", "</sik:HeadEndVendor>");
						Application.showMessage("sik_HeadEndVendor is ::"
								+ sik_HeadEndVendor);

						if (sik_HeadEndVendor == null) {
							c.report("Send Xml sik_HeadEndVendor is NULL");
							continue;
						} else {
							// Application.showMessage("sik_HeadEndVendor from Send Xml  from SIK is ::"+" "+sik_LastName);
							if (sik_HeadEndVendor.equalsIgnoreCase(c.get(
									"GL_headendType").toString())) {
								Application
										.showMessage("*******Validation Point ## :: WebServicall-sik_HeadEndVendor********");
								Application
										.showMessage("Validation is Successfull with sik_HeadEndVendor::"
												+ " " + sik_HeadEndVendor);
								v4 = 1;
							} else {
								c.report("sik_HeadEndVendor at Send Xml not Validated as "
										+ sik_HeadEndVendor);
							}
						}}

						String sik_E911_acceptance = sL.nodeFromKey(senddata,
								"<sik:E911_acceptance>",
								"</sik:E911_acceptance>");
						Application.showMessage("sik_E911_acceptance is ::"
								+ sik_E911_acceptance);

						String sik_AgentID = sL.nodeFromKey(senddata,
								"<sik:AgentID>", "</sik:AgentID>");
						Application.showMessage("sik_AgentID is ::"
								+ sik_AgentID);

						String sik_WorkOrderID = sL.nodeFromKey(senddata,
								"<sik:WorkOrderID>", "</sik:WorkOrderID>");
						Application.showMessage("sik_WorkOrderID is ::"
								+ sik_WorkOrderID);

						if (recievedata.contains("<OrderID>")) {

							String OrderID_sik = sL.nodeFromKey(recievedata,
									"<OrderID>", "</OrderID>");
							Application.showMessage("OrderID is::"
									+ OrderID_sik);
							// AppContext.put("CancelOrderID",
							// OrderID_sik,true);
							// c.put("OrderID_SIK", OrderID_sik);
							// Application.showMessage("The AppContext Order ID is::"+AppContext.get("CancelOrderID"));
							// Application.showMessage("The put Order ID is::"+c.get("OrderID_SIK"));
							c.setValue("OrderIDToCancel", OrderID_sik);
							Integer OrderId = Integer.parseInt(OrderID_sik);
							sL.retOrderId(OrderId);
						}

						else if (recievedata
								.contains("<cct:code>Order-SubmitOrder-213</cct:code>")) {

							// AppContext.put("CancelOrderID", "1234",true);
							// Application.showMessage("Order Id not created");
							// c.report("ORDER ID NOT FOUND..!");
							if (c.getValue("dS_SIK", "Sik_Dotcom: IsModify")
									.equalsIgnoreCase("1")) {
								Application.showMessage("Modify Order...!");
							} else {
								c.report("ORDER ID NOT FOUND..!");
							}
						}

						break;
					}
				}
			}

			if (v1 * callFound * v2   == 1) {
				Application
						.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
			} else {
				OutPut = 0;
				c.put("result", "false");
				c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
			}
			//st.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

	/*---------------------------------------------------------------------------------------
	 * DOTCOM XHS
	 * 	
	 ---------------------------------------------------------------------------------------*/

	public void simulatorChange(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, SQLException,
			InterruptedException {

		Statement st = dBConnect(input, c);
		ResultSet rs = st
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		while (rs.next()) {
			String beforeSim = rs.getString(2);

			Application.showMessage("Db Simulator Before Updation ::"
					+ beforeSim);

		}
		st.close();
		Statement st0 = dBConnect(input, c);
		String sql = "update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><UpDownGradeForDotcom>0</UpDownGradeForDotcom><enableSMBFlow>1</enableSMBFlow><discDuration>30</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>30</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101010096771</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>100|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><simUControlErrorCode>UCE-15101</simUControlErrorCode><simUCtrlCreateErrCd>0&lt;/errorCode&gt;</simUCtrlCreateErrCd><simUControlErrorMessage>Account 9999647611502 not found</simUControlErrorMessage><activationCode>277813</activationCode><discoLetterLocation>/opt/home/DTA/abinitio/vantage/bhs/dtdevdummy/test</discoLetterLocation><discoLetterLocation_UI_Test>/opt/home/DTA/abinitio/vantage/bhs/dtdevdummy/test</discoLetterLocation_UI_Test><discoLetterTranScript>/opt/home/DTA/abinitio/vantage/bhs/dtdev/script/transfer.sh</discoLetterTranScript><discoLetterTranScript_UI_Test>/app/sym-vision/script/transfer.sh</discoLetterTranScript_UI_Test><status>ACTIVE</status><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><LineOfService>XHS</LineOfService><suspendAccount>0</suspendAccount><IsDotcomXhsSikEnabled>1</IsDotcomXhsSikEnabled><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><disLetterFile1>HS9LETTER_DANTOMHS_</disLetterFile1><disLetterfileDateFormat1>yyyyMMdd_hhmmss</disLetterfileDateFormat1><disLetterFileExt1>.extract</disLetterFileExt1><disLetterFile2>HS9LETTER_DANTOMHS_</disLetterFile2><disLetterfileDateFormat2>yyyyMMdd_hhmmss</disLetterfileDateFormat2><disLetterFileExt2>.extract.fin</disLetterFileExt2><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>0</isFoxtrotFlowEnable><AOservicesProductIds>"
				+ c.getValue("dS_SIK", "Sik_Dotcom: AO Services")
				+ "</AOservicesProductIds><peripheralProductsIds>"
				+ c.getValue("dS_SIK", "Sik_Dotcom: ProductID")
				+ "</peripheralProductsIds><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isDotcomXhsSIKLogEnabled>1</isDotcomXhsSIKLogEnabled><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
		st0.executeUpdate(sql);

		st0.close();
		Thread.sleep(300000);
		Statement st1 = dBConnect(input, c);
		ResultSet rs1 = st1
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		while (rs1.next()) {

			String afterSim = rs1.getString(2);
			Application
					.showMessage("Db Simulator After Updation ::" + afterSim);

		}
		st0.close();

	}

	/*------------------------------------------------------------------------------------
	 * 
	 * 				DOT COM XHS SUBMIT ORDER
	 * 
	 ------------------------------------------------------------------------------------------*/
	public void submitOrder_Validate_xhs(Object input, ScriptingContext c)
			throws InterruptedException, ClassNotFoundException, IOException,
			XPathExpressionException, SAXException {
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		sikLibraryClass sL = new sikLibraryClass();
		ResultSet rs;
		int callFound = 0, v1 = 0, v2 = 0, v3 = 0, v4 = 0;
		String xmldata1 = "receive_data";
		String xmldata2 = "SEND_DATA";
		String Time = c.get("BaseTime").toString();
		Context AppContext = Application.getContext();
		Application
				.showMessage("-----------------------------------------------------");
		Application
				.showMessage("**********submitOrder_Validate Function************");
		Application
				.showMessage("----------------------------------------------------");
		HashMap Operation=sL.findingoperations(input, c);
		c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
			     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));
		try {
			//Statement st = sL.dBConnect(input, c);
		//	rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"
				//	+ Time + "' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);

			while (rs.next()) {

				String rowmsg;
				rowmsg = rs.getString(1);
				Application.showMessage("MessageID is::" + rowmsg);

				if (rs.getBlob("receive_data") == null) {

					Application.showMessage("Your Recieve XML is NULL \n");

					String senddata = sL.extractXml(rs, xmldata2);
					Application.showMessage("Your Recieve XML is::\n"
							+ senddata);
				} else if (rs.getBlob("SEND_DATA") == null) {
					Application.showMessage("Your SEND XML is NULL \n");
					String recievedata = sL.extractXml(rs, xmldata1);
					Application
							.showMessage("RECIEVE XML is ::\n" + recievedata);
				} else {

					String senddata = sL.extractXml(rs, xmldata2);
					String recievedata = sL.extractXml(rs, xmldata1);
					Application.showMessage("Recieve XML is::  \n"
							+ recievedata);
					Application.showMessage("SEND XML is :: \n" + senddata);

					String sik_AccountNumber = sL.nodeFromKey(senddata,
							"<sik:AccountNumber>", "</sik:AccountNumber>");
					Application.showMessage("accountNumber is ::"
							+ sik_AccountNumber);

					if (sik_AccountNumber == null) {
						continue;
					} else if (sik_AccountNumber.equals(c
							.getValue("accountNumber"))) {
						Application.showMessage("Recieve XML is::  \n"
								+ recievedata);
						Application.showMessage("SEND XML is :: \n" + senddata);
						// System.out.printf("SEND XML is %s \n",senddata);
						Application
								.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
						Application
								.showMessage("Validation is Successfull with Input Account Number"
										+ sik_AccountNumber);
						callFound = 1;
					} else {
						continue;
					}

					if (callFound == 1) {

						if (senddata
								.contains("<sik:E911_acceptance>1</sik:E911_acceptance>")) {
							Application
									.showMessage("E911 acceptance Validated as 1 for CDV Order");
						} else if (senddata
								.contains("<sik:E911_acceptance>0</sik:E911_acceptance>")) {
							Application
									.showMessage("E911 acceptance Validated as 0 for CDV Order");
						}

						else {
							Application.showMessage("Is not an CDV Order");

						}
						// String sik_Email=
						// sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");
						String sik_Email = sL.nodeFromKey(senddata,
								"<sik:Email>", "</sik:Email>");

						String sik_FirstName = sL.nodeFromKey(senddata,
								"<sik:FirstName>", "</sik:FirstName>");
						Application.showMessage("firstName is ::"
								+ sik_FirstName);

						String sik_LastName = sL.nodeFromKey(senddata,
								"<sik:LastName>", "</sik:LastName>");
						Application
								.showMessage("lastName is ::" + sik_LastName);

						String sik_ShipmentPriority = sL.nodeFromKey(senddata,
								"<sik:ShipmentPriority>",
								"</sik:ShipmentPriority>");
						Application.showMessage("ShipmentPriority is ::"
								+ sik_ShipmentPriority);

						String sik_FTA = sL.nodeFromKey(senddata, "<sik:FTA>",
								"</sik:FTA>");
						Application.showMessage("sik_FTA is ::" + sik_FTA);

						Application.showMessage("SHIP_City from bfc"
								+ c.get("SHIP_City"));

						Application.showMessage("FTA from Get Loc"
								+ c.get("GL_ftaAgent"));
						// Application.showMessage("FTA from Get Loc"
						// +c.get("GL_FranchiseTaxArea"));

						String sik_E911_acceptance = sL.nodeFromKey(senddata,
								"<sik:E911_acceptance>",
								"</sik:E911_acceptance>");
						Application.showMessage("sik_E911_acceptance is ::"
								+ sik_E911_acceptance);

						String sik_AgentID = sL.nodeFromKey(senddata,
								"<sik:AgentID>", "</sik:AgentID>");
						Application.showMessage("sik_AgentID is ::"
								+ sik_AgentID);

						String sik_WorkOrderID = sL.nodeFromKey(senddata,
								"<sik:WorkOrderID>", "</sik:WorkOrderID>");
						Application.showMessage("sik_WorkOrderID is ::"
								+ sik_WorkOrderID);

						if (recievedata.contains("<OrderID")) {

							String OrderID_sik = sL.nodeFromKey(recievedata,
									"<OrderID>", "</OrderID>");
							Application.showMessage("OrderID is::"
									+ OrderID_sik);
							// AppContext.put("CancelOrderID",
							// OrderID_sik,true);
							// c.put("OrderID_SIK", OrderID_sik);
							// Application.showMessage("The AppContext Order ID is::"+AppContext.get("CancelOrderID"));
							// Application.showMessage("The put Order ID is::"+c.get("OrderID_SIK"));
							c.setValue("OrderIDToCancel", OrderID_sik);
							Integer OrderId = Integer.parseInt(OrderID_sik);
							sL.retOrderId(OrderId);
						}

						else if (recievedata
								.contains("<cct:code>Order-SubmitOrder-213</cct:code>")) {

							// AppContext.put("CancelOrderID", "1234",true);
							// Application.showMessage("Order Id not created");
							// c.report("ORDER ID NOT FOUND..!");
							if (c.getValue("dS_SIK", "Sik_Dotcom: IsModify")
									.equalsIgnoreCase("1")) {
								Application.showMessage("Modify Order...!");
							} else {
								c.report("ORDER ID NOT FOUND..!");
							}
						}

						break;
					}
				}
			}

			if (callFound == 1) {
				Application
						.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
			} else {
				OutPut = 0;
				c.put("result", "false");
				c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
			}
		//	st.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

	/*--------------------------------------------------------------------------------------------------
	 * 
	 * 				ORDER UPDATE
	 * 
	 --------------------------------------------------------------------------------------------------*/

	public void OrderUpdateFlagEnabled_Validate(String INOrdStatus,
			String INbillingOrderId, String INinputChannel,
			String INsalesChannel, String INshipmentType, String INproductType,
			String INbillingSystem, String INordType, String INcustomerType,
			String WORKORDER_ID, Object input, ScriptingContext c)
			throws InterruptedException, ClassNotFoundException, IOException,
			XPathException, SAXException {
		Thread.sleep(10000); // Think time in JVM [waits for 10 secs here]

		String Time = c.get("BaseTime").toString();
		sikLibraryClass sL = new sikLibraryClass();
		ResultSet rs;
		int v4 = 0, v1 = 0, v2 = 0, v3 = 0, v5 = 0, v6 = 0, v7 = 0, v8 = 0, v9 = 0, v10 = 0, v11 = 0, v12 = 0, v13 = 0, v14 = 0, v15 = 0, v16 = 0;
		int callFound = 0;
		String xmldata1 = "receive_data";
		String xmldata2 = "SEND_DATA";
		String currentDate = sL.getsysTime();
		String AccountNumber = c.getValue("accountNumber");
		Application.showMessage("Date of execution is ::" + currentDate);
		Application
				.showMessage("--------------------------------------------------------");
		Application
				.showMessage("***********OrderUpdate_Validate Function**************");
		Application
				.showMessage("--------------------------------------------------------");

		try {
			Statement st = sL.dBConnect(input, c);
			rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"
					+ Time
					+ "' and USER_DATA2='"
					+ AccountNumber
					+ "' order by creation_time desc)where rownum <= 20");

			while (rs.next()) {

				String rowmsg;
				rowmsg = rs.getString(1);
				Application.showMessage("MessageID is::" + rowmsg);

				if (rs.getBlob("SEND_DATA") == null) {
					Application.showMessage("Your SEND XML is NULL \n");
					String recievedata = sL.extractXml(rs, xmldata1);
					Application
							.showMessage("RECIEVE XML is ::\n" + recievedata);
				} else if (rs.getBlob("receive_data") == null) {

					String senddata = sL.extractXml(rs, xmldata2);

					Application.showMessage("senddata is ::" + senddata);

					String billingAccountNo = sL.nodeFromKey(senddata, "<id>",
							"</id>");
					Application.showMessage("billingAccountNo is ::"
							+ billingAccountNo);
					v1 = sL.verificationPoint(billingAccountNo,
							INbillingOrderId, input, c);

					String ordStatus = sL.nodeFromKey(senddata,
							"<value name=\"ordStatus\">",
							"</value><value name=\"selfInstall\">");
					Application.showMessage("The ordStatus from Request is::"
							+ ordStatus);
					v2 = sL.verificationPoint(ordStatus, INOrdStatus, input, c);

					if (v1 * v2 == 1) {
						callFound = 1;
					} else {
						continue;
					}

					if (callFound == 1) {
						String sendDataT = sL.RemoveNameSpaces(senddata);
						// String guid=
						// sL.nodeFromKey(senddata,"<value name=\"guid\">","</value><value name=\"accountNumber\">");
						String guid = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value");
						Application.showMessage("The guid from Request is::"
								+ guid);
						v3 = sL.validationPointIgnoreCase(guid,
								INbillingOrderId, input, c);

						// String accountNumber=
						// sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
						String accountNumber = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[2]");
						Application
								.showMessage("The accountNumber from Request is::"
										+ accountNumber);
						v4 = sL.validationPointIgnoreCase(accountNumber,
								AccountNumber, input, c);

						String INcorpID = sL.makeCorpIDfromAccountNumber(
								accountNumber, INbillingSystem);
						// String corpId=
						// sL.nodeFromKey(senddata,"<value name=\"corpId\">","</value><value name=\"billingOrderId\">");
						String corpId = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[3]");
						Application.showMessage("The corpId from Request is::"
								+ corpId);
						v5 = sL.validationPointIgnoreCase(corpId, INcorpID,
								input, c);

						// String billOrder=
						// INbillingOrderId.substring(accountNumber.length());
						// String billingOrderId=
						// sL.nodeFromKey(senddata,"<value name=\"billingOrderId\">","</value><value name=\"ordType\">");
						String billingOrderId = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[4]");
						Application
								.showMessage("The billingOrderId from Request is::"
										+ billingOrderId);
						v6 = sL.validationPointIgnoreCase(billingOrderId,
								WORKORDER_ID, input, c);

						// String ordType=
						// sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
						String ordType = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[5]");
						Application.showMessage("The ordType from Request is::"
								+ ordType);
						v7 = sL.validationPointIgnoreCase(ordType, INordType,
								input, c);

						// String ordSource=
						// sL.nodeFromKey(senddata,"<value name=\"ordSource\">","</value><value name=\"ordStatus\">");
						String ordSource = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[6]");
						Application
								.showMessage("The ordSource from Request is::"
										+ ordSource);
						v8 = sL.validationPointIgnoreCase(ordSource, "OP",
								input, c);

						// String selfInstall=
						// sL.nodeFromKey(senddata,"<value name=\"selfInstall\">","</value><value name=\"inputChannel\">");
						String selfInstall = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[8]");
						Application
								.showMessage("The selfInstall from Request is::"
										+ selfInstall);
						v9 = sL.validationPointIgnoreCase(selfInstall, "1",
								input, c);

						// String inputChannel=
						// sL.nodeFromKey(senddata,"<value name=\"inputChannel\">","</value><value name=\"customerType\">");
						String inputChannel = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[9]");
						Application
								.showMessage("The inputChannel from Request is::"
										+ inputChannel);
						v10 = sL.validationPointIgnoreCase(inputChannel,
								INinputChannel, input, c);

						// String customerType=
						// sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"billingSystem\">");
						String customerType = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[10]");
						Application
								.showMessage("The customerType from Request is::"
										+ customerType);
						v11 = sL.validationPointIgnoreCase(customerType,
								INcustomerType, input, c);

						// String billingSystem=
						// sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
						String billingSystem = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[11]");
						Application
								.showMessage("The billingSystem from Request is::"
										+ billingSystem);
						v12 = sL.validationPointIgnoreCase(billingSystem,
								INbillingSystem, input, c);

						// String active=
						// sL.nodeFromKey(senddata,"<value name=\"active\">","</value><value name=\"salesChannel\">");
						String active = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[12]");
						Application.showMessage("The active from Request is::"
								+ active);
						v13 = sL.validationPointIgnoreCase(active, "1", input,
								c);

						// String salesChannel=
						// sL.nodeFromKey(senddata,"<value name=\"salesChannel\">","</value><value name=\"otherInfo\">");
						String salesChannel = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[13]");
						Application
								.showMessage("The salesChannel from Request is::"
										+ salesChannel);
						v14 = sL.validationPointIgnoreCase(salesChannel,
								INsalesChannel, input, c);

						// String productType=
						// sL.nodeFromKey(senddata,"<value name=\"productType\">","</value></header>");
						String productType = sL.GetValueByXPath(sendDataT,
								"/comRequest/header/value[16]");
						Application
								.showMessage("The productType from Request is::"
										+ productType);
						v15 = sL.validationPointIgnoreCase(productType,
								INproductType, input, c);

						// String street1=
						// sL.nodeFromKey(senddata,"<value name=\"street1\">","</value><value name=\"street2\">");
						String street1 = sL.GetValueByXPath(sendDataT,
								"/comRequest/address/value");
						Application.showMessage("The street1 from Request is::"
								+ street1);
						// v2= sL.validationPointIgnoreCase(ordStatus,
						// INOrdStatus, input, c);

						// String street2=
						// sL.nodeFromKey(senddata,"<value name=\"street2\">","</value><value name=\"city\">");
						String street2 = sL.GetValueByXPath(sendDataT,
								"/comRequest/address/value[2]");
						Application.showMessage("The street2 from Request is::"
								+ street2);
						// v2= sL.validationPointIgnoreCase(ordStatus,
						// INOrdStatus, input, c);

						// String city=
						// sL.nodeFromKey(senddata,"<value name=\"city\">","</value><value name=\"state\">");
						String city = sL.GetValueByXPath(sendDataT,
								"/comRequest/address/value[3]");
						Application.showMessage("The city from Request is::"
								+ city);
						// v2= sL.validationPointIgnoreCase(ordStatus,
						// INOrdStatus, input, c);

						// String state=
						// sL.nodeFromKey(senddata,"<value name=\"state\">","</value><value name=\"zip\">");
						String state = sL.GetValueByXPath(sendDataT,
								"/comRequest/address/value[4]");
						Application.showMessage("The state from Request is::"
								+ state);
						// v2= sL.validationPointIgnoreCase(ordStatus,
						// INOrdStatus, input, c);

						// String address=
						// sL.nodeFromKey(senddata,"<value name=\"address\">","</value><value name=\"guid\">");
						String address = sL.GetValueByXPath(sendDataT,
								"/comRequest/shipping/value");
						Application
								.showMessage("The address from OrderUpdate is::"
										+ address);
						// v2= sL.validationPointIgnoreCase(ordStatus,
						// INOrdStatus, input, c);

						// String zip=
						// sL.nodeFromKey(senddata,"<value name=\"zip\">","</value><value name=\"zip4\">");
						String zip = sL.GetValueByXPath(sendDataT,
								"/comRequest/address/value[5]");
						Application.showMessage("The zip from Request is::"
								+ zip);
						// v2= sL.validationPointIgnoreCase(ordStatus,
						// INOrdStatus, input, c);

						/*
						 * String PhoneNumber= sL.GetValueByXPath(sendDataT,
						 * "/comRequest/customer/value[4]");
						 * Application.showMessage
						 * ("The PhoneNumber from BFC Request is::"
						 * +c.get("Cphone").toString());
						 * 
						 * Application.showMessage(
						 * "The PhoneNumber from Request is::"+PhoneNumber); v2=
						 * sL.validationPointIgnoreCase(PhoneNumber,
						 * c.get("Cphone").toString(), input, c);
						 * 
						 * 
						 * 
						 * //String zip4=
						 * sL.nodeFromKey(senddata,"<value name=\"zip4\">"
						 * ,"</value><value name=\"franchiseTaxArea\">"); String
						 * zip4= sL.GetValueByXPath(sendDataT,
						 * "/comRequest/address/value[6]");
						 * Application.showMessage
						 * ("The zip4 from Request is::"+zip4); // v2=
						 * sL.validationPointIgnoreCase(ordStatus, INOrdStatus,
						 * input, c);
						 * 
						 * 
						 * //String franchiseTaxArea= sL.nodeFromKey(senddata,
						 * "<value name=\"franchiseTaxArea\">"
						 * ,"</value><value name=\"selfInstall\">"); String
						 * franchiseTaxArea= sL.GetValueByXPath(sendDataT,
						 * "/comRequest/address/value[7]");
						 * Application.showMessage
						 * ("The franchiseTaxArea from Request is::"
						 * +franchiseTaxArea); //v2=
						 * sL.validationPointIgnoreCase(ordStatus, INOrdStatus,
						 * input, c);
						 * 
						 * 
						 * 
						 * // String firstName=
						 * sL.nodeFromKey(senddata,"<value name=\"ordStatus\">"
						 * ,"</value><value name=\"selfInstall\">"); String
						 * firstName= sL.GetValueByXPath(sendDataT,
						 * "/comRequest/shipping/value[3]");
						 * Application.showMessage
						 * ("The firstName from Request is::"+firstName); // v2=
						 * sL.validationPointIgnoreCase(ordStatus, INOrdStatus,
						 * input, c);
						 * 
						 * 
						 * 
						 * //String lastName=
						 * sL.nodeFromKey(senddata,"<value name=\"ordStatus\">"
						 * ,"</value><value name=\"selfInstall\">"); String
						 * lastName= sL.GetValueByXPath(sendDataT,
						 * "/comRequest/shipping/value[4]");
						 * Application.showMessage
						 * ("The lastName from Request is::"+lastName); // v2=
						 * sL.validationPointIgnoreCase(ordStatus, INOrdStatus,
						 * input, c);
						 * 
						 * //String shipmentType=
						 * sL.nodeFromKey(senddata,"<value name=\"ordStatus\">"
						 * ,"</value><value name=\"selfInstall\">"); String
						 * shipmentType= sL.GetValueByXPath(sendDataT,
						 * "/comRequest/shipping/value[6]");
						 * Application.showMessage
						 * ("The shipmentType from Request is::"+shipmentType);
						 * //v2= sL.validationPointIgnoreCase(ordStatus,
						 * INOrdStatus, input, c);
						 * 
						 * String note= sL.nodeFromKey(senddata,"<note><note>",
						 * "</note><description>");
						 * Application.showMessage("The note from Request is::"
						 * +note); //= sL.validationPointIgnoreCase(note,
						 * "Order Validated", input, c);
						 * 
						 * String desc=
						 * sL.nodeFromKey(senddata,"</note><description>"
						 * ,"</description><append>"); Application.showMessage(
						 * "The Description from Request is::"+desc); // v2=
						 * sL.validationPointIgnoreCase(desc,
						 * "DOTCOM: Order Validated Successfully", input, c);
						 */

					}

					break;
				}
			}

			if (v1 * v2 * v3 * v4 * v5 * v6 * v7 * v8 * v9 * v10 * v11 * v12
					* v13 * v14 * v15 * callFound == 1) {
				OutPut = 1;
				Application
						.showMessage("WebService Call :: OrderUpdate is Success[All validation points are Success]");

			} else {
				OutPut = 0;
				c.put("result", "false");
				c.report("WebService Call ::Order Update is Failed with Validation Errors");

			}
			st.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();

		}

		// return OutPut;
	}

	// -------------------------------------------No
	// Worklisting----------------------------------------

	public void getDatasetUsingUserData2(Object input, ScriptingContext c)
			throws InterruptedException, ClassNotFoundException, IOException,
			SQLException {

		// LibraryRtp_UpDown lU=new LibraryRtp_UpDown();
		// vC.execution(input, c);
		int v1 = 0, i = 0;
		Date todayDate = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
		DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
		todayDateFormat.setTimeZone(timeZone);
		String strTodayDate = todayDateFormat.format(todayDate);
		Application.showMessage("Todays Date as per EST is::" + strTodayDate);
		sikLibraryClass lU = new sikLibraryClass();
		java.sql.Statement st = lU.dBConnect(input, c);
		ResultSet rs;
		rs = st.executeQuery("select * from cwmessagelog where user_data2='"
				+ c.getValue("dS_SIK", "Sik_Dotcom: AccountNo")
				+ "' AND creation_time > '" + strTodayDate + "'");
		while (rs.next()) {
			String operation = rs.getString("OPERATION");
			Application.showMessage("OPeration is ::" + operation);

			if (operation
					.equals("ucontrolsrv:AccountServicePortType/createAccount")) {

				Application.showMessage("Extra call createAccount Found");
				v1 = 1;
				continue;

			} else if (operation
					.equals("srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo")) {

				Application
						.showMessage("Extra call processHomeSecurityInfo Found");
				v1 = 1;
				continue;
			} else if (operation
					.equals("hss:HomeSecurityServicePort/createCMSAccountID")) {
				Application.showMessage("Extra call createCMSAccountID Found");
				v1 = 1;
				continue;
			}

			else if (operation
					.equals(" hss:HomeSecurityServicePort/getCustomerPermitRequirements")) {
				Application
						.showMessage("Extra call getCustomerPermitRequirements Found");
				v1 = 1;
				continue;
			}

			else if (operation.equals("ls:LocationServicePort/queryLocation")) {
				Application.showMessage("Extra call queryLocation Found");
				v1 = 1;
				continue;
			}

			else if (operation
					.equals("COPSServices:Comcast/SetAccountBasicInformation")) {
				Application
						.showMessage("Extra call SetAccountBasicInformation Found");
				v1 = 1;
				continue;
			}

			else if (operation.equals("intrado:frlResource/responderInfo")) {
				Application.showMessage("Extra call responderInfo Found");
				v1 = 1;
				continue;
			}

			else if (operation
					.equals("ls:LocationServicePort/modifyHomeSecurity")) {
				Application.showMessage("Extra call modifyHomeSecurity Found");
				v1 = 1;
				continue;
			}

			else if (operation
					.equals("COPSServices:Comcast/SetAccountAuthorityInformation")) {
				Application
						.showMessage("Extra call SetAccountAuthorityInformation Found");
				v1 = 1;
				continue;
			}

			/*
			 * else if(operation.equals("cmb:commonOrderService/orderUpdate")) {
			 * Application.showMessage("Extra call OrderUpdate Found"); v1=1;
			 * continue; }
			 */

			if (v1 == 1) {
				// OutPut=0;
				c.report("Extra Call Found");
				break;
			} else {
				Application
						.showMessage(i + "th row data is not an Extra call ");
			}
			i++;

		}
	}
	public void csgENImsg_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
    {
    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
    sikLibraryClass sL = new sikLibraryClass();
    ResultSet  rs;
    int LocNull=0;
    int OrderIdNull=0;
    int PhoneNull=0;
    int CustInfoNull=0;
    int callFound=0,v1=0,v2=0,v3=0,v4=0;
    String xmldata1 ="receive_data";
    String xmldata2 ="SEND_DATA";
    String Bid=c.getValue("BaseMsgid");
   


  Application.showMessage("-----------------------------------------------------");
  Application.showMessage("***********csgENImsg_Validate Function**************");       
  Application.showMessage("----------------------------------------------------");
  HashMap Operation=sL.findingoperations(input, c);
  c.setValue("OPERATIONVALIDATION",(String) Operation.get("CSGENIMessage"));
  	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CSGENIMessage"));
    try
    {
     // Statement st =sL. dBConnect(input, c);  
    //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:CSGENIMessageServicePort/processCSGENIMessage' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
    	rs=sL.reduceThinkTimeSIK(input, c);
      while (rs.next())
      {

           
         if(rs.getBlob("receive_data")==null)
         {
         
           Application.showMessage("Your Recieve XML is NULL \n");
           
           String senddata =sL.extractXml(rs,xmldata2);
           Application.showMessage("Your Recieve XML is::\n"+senddata);
        }
         else if(rs.getBlob("SEND_DATA")==null)
         {
           Application.showMessage("Your SEND XML is NULL \n");   
           String recievedata=sL.extractXml(rs,xmldata1);
           Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
         }
         else
         { 
                       
                String senddata = sL.extractXml(rs,xmldata2);           
                String recievedata = sL.extractXml(rs,xmldata1);      
                Application.showMessage("senddata is ::"+senddata); 
                Application.showMessage("recievedata is ::"+recievedata); 
                
                //String accountNumber_DDS=sL.GetValueByXPath(recievedata,"/bfcopcom:msgInfo/bfcopcom:parameters/bfcopcom:parameters[2]/bfcopcom:value");
                
                
                
                   String OrderId_csgeni= sL.nodeFromKey(recievedata,"<OrderId>","</OrderId>");
                     Application.showMessage("Order ID is ::"+OrderId_csgeni); 
                      
                      if(OrderId_csgeni==null)
                       {
                            OrderIdNull=1;
                            c.put("pOrderIdNull", OrderIdNull);
                            Application.showMessage("OrderID is empty for the customer..!!");
                             break;
                       }
                      else
                      {
                        Application.showMessage("Order iD from Input Xcel is "+c.getValue("OrderID"));
                             if(OrderId_csgeni.equals(c.getValue("OrderID").trim()))
                         {
                               Application.showMessage("Recieve XML is::  \n"+ recievedata);
                               Application.showMessage("SEND XML is :: \n"+senddata);
                                //System.out.printf("SEND XML is %s \n",senddata);
                                Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                Application.showMessage("Validation is Successfull with Order ID"+OrderId_csgeni);
                             v2=1;
                             callFound=1;
                             
                             Application.showMessage("Recieve XML is::  \n"+ recievedata);
                               Application.showMessage("SEND XML is :: \n"+senddata);
                         }
                         else
                         {
                                c.report("OrderID Validation Failed!!!");
                                break;
                         }
                       
                       }
                if(callFound==1)
                 {
                   
                  String accId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/ItemList/Item/AccountId");
               // String accId_csgeni= sL.nodeFromKey(recievedata,"</ServiceIdentifier><AccountId>","</AccountId><BillingIdentifier>");
               Application.showMessage("accountNumber is ::"+accId_csgeni);   
               
                if(accId_csgeni==null)
                {
                       c.report("Send Xml accId_csgeni is NULL");
                       continue;
                }
               else
               {
                       if(accId_csgeni.equals(c.getValue("SwivelCSG","sik_SwivelCSG: AccountId_ITEMLIST")))
                  {
                         
                         //System.out.printf("SEND XML is %s \n",senddata);
                         Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
                         Application.showMessage("Validation is Successfull with Input accountNumber"+accId_csgeni);
                         callFound=1;
                  }
                  else
                  {
                         continue;
                  }
                
                }
               
                
                       String LocationId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Location/LocationId");

                  //String LocationId_csgeni= sL.nodeFromKey(recievedata,"<Location><LocationId>","</LocationId><Address>");
                      Application.showMessage("accountNumber is ::"+LocationId_csgeni);     
                       if(LocationId_csgeni==null)
                       {
                         Application.showMessage("LocationID is empty for the customer..!!");
                        LocNull=1;
                        c.put("pLocNull", LocNull);
                             continue;
                       }
                      else
                      {
                             if(LocationId_csgeni.equals(c.getValue("locationID")))
                         {
                               Application.showMessage("Recieve XML is::  \n"+ recievedata);
                               Application.showMessage("SEND XML is :: \n"+senddata);
                                //System.out.printf("SEND XML is %s \n",senddata);
                                Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                Application.showMessage("Validation is Successfull with locationID"+LocationId_csgeni);
                             v1=1;
                         }
                         else
                         {
                                
                                c.report("locationID Validation Failed!!!");
                                break;
                         }
                       
                       }
                      
                     
                      
                     // String Homeph_csgeni= sL.nodeFromKey(recievedata,"<Phone><Home>","</Home><Business>");
                      
                      String Homeph_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Phone/Home");
                     
                      Application.showMessage("Home Phone is ::"+Homeph_csgeni); 
                      
                      String Bussinessph_csgeni= sL.nodeFromKey(recievedata,"</Home><Business>","</Business></Phone>");
                     Application.showMessage("Bussiness Phone is ::"+Bussinessph_csgeni); 
                      
                      if(Homeph_csgeni==null && Bussinessph_csgeni==null)
                       {
                           
                              Application.showMessage("Home and Bussiness Phone Numbers are empty for the customer..!!");
                             PhoneNull=1;
                            c.put("pPhoneNull", PhoneNull);
                               continue;
                       }
                      
                      String CustomerId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/CustomerId");
                    // String CustomerId_csgeni= sL.nodeFromKey(recievedata,"<CustomerId>","</CustomerId>");
                     Application.showMessage("CustomerId is ::"+CustomerId_csgeni); 
                      
                     // String Firstn_csgeni= sL.nodeFromKey(recievedata,"<Name><First>","</First><Last>");
                    String Firstn_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Name/First");
                     Application.showMessage("First Name is ::"+OrderId_csgeni); 
                      
                      //String LastN_csgeni= sL.nodeFromKey(recievedata,"</First><Last>","</Last>");
                    String LastN_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Name/Last");
                     Application.showMessage("Last Name is ::"+OrderId_csgeni); 
                      
                      if(CustomerId_csgeni==null || Firstn_csgeni==null ||LastN_csgeni==null  )
                       {
                            CustInfoNull=1;
                            c.put("pCustInfoNull", CustInfoNull);
                             continue;
                       }
                      else
                      {
                             if(CustomerId_csgeni.equals(c.getValue("CustomerId")))
                         {
                               Application.showMessage("Recieve XML is::  \n"+ recievedata);
                               Application.showMessage("SEND XML is :: \n"+senddata);
                                //System.out.printf("SEND XML is %s \n",senddata);
                                Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                Application.showMessage("Validation is Successfull with Customer ID"+CustomerId_csgeni);
                             v3=1;
                         }
                         else
                         {
                                c.report("CustomerId Validation Failed!!!");
                         }
                       
                       }
                     
                   
                break;
                }
          }
      }
      
      if(v1*callFound ==1)
      {
           Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
      }
      else
      {
            c.put("result", "false");
            c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
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
}
