import java.io.IOException;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.StatementEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.omg.CORBA.portable.ApplicationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

public class LibraryRtp_UpDown {
	public String basemsgID = null;
	public Connection connection = null;
	public String xml1;
	public String xml2;	// public String rowmsg= null;
	public String resultval;
	

	public void Opconnection() {
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		Application
				.showMessage("-------- Oracle JDBC Connection Testing ------");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Class.forName("jdbc:oracle:thin.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out
					.println("Connection Failed as No JDBC Driver Intialized");
			e.printStackTrace();
			return;
		}

		System.out.println("Oracle JDBC Driver Registered!");
		// Connection connection = null;

	}

	public String nodeFromKey(String xmlStr, String str1, String str2)
			throws NullPointerException {
		if (xmlStr.contains(str1) && xmlStr.contains(str2)) {
			int startPosition = 0;
			int endPosition = 0;
			startPosition = xmlStr.indexOf(str1) + str1.length();

			if (startPosition == -1) {
				System.out.printf("No Value found for ::%s\n", str1);
				return (null);
			} else if (xmlStr.indexOf(str1) == -1) {
				System.out.printf("No Value found for ::%s\n", str1);
			}

			endPosition = xmlStr.indexOf(str2, startPosition);
			if (endPosition == -1) {
				System.out.printf("No Value found for ::%s\n", str2);
			}
			String resultval = xmlStr.substring(startPosition, endPosition);
			if (resultval == null) {
				return "NO Data Fetched from in-between strings..!Check the strings..!";
			} else
				return (resultval);
		} else {
			return "NO Data Fetched from in-between strings..!Check the strings..!";
		}
	}

	public String GetValueByXPath(String sXml, String sxpath)
			throws SAXException, IOException, XPathExpressionException,
			NullPointerException {
		String val = null;
		try {
			// sXml.replaceAll(" xsi:", " ");
			InputSource source = new InputSource(new StringReader(sXml));
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(false);
			factory.setIgnoringElementContentWhitespace(false);

			DocumentBuilder builder;
			Document doc = null;

			builder = factory.newDocumentBuilder();
			doc = builder.parse(source);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile(sxpath);
			Node oNode = (Node) expr.evaluate(doc, XPathConstants.NODE);
			if (oNode == null) {
				val = "XPath ERROR.Either NULL or Xpath needs to be Fixed";
			} else {
				val = oNode.getTextContent();

			}

			xpath = null;
			expr = null;
			oNode = null;
			doc = null;
			builder = null;

		} catch (NullPointerException | ParserConfigurationException
				| SAXException | IOException e) {
			e.printStackTrace();
		}

		return val;

	}

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

	public void IterationLogicUpgradeDownGrade(Object input, ScriptingContext c)
			throws IOException {

		c.clear();
		String sN = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: XML_ScenarioName");
		Application.showMessage("Scenario is ::" + sN);
		if (sN.equalsIgnoreCase("Demi")) {
			c.setValue("IsDemi", "true");

		} else {
			c.setValue("IsDemi", "false");
		}
	}

	public void IterationLogic(Object input, ScriptingContext c) {
		try {
			c.clear();

			String sc = c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: SCENARIO_NUMBER");
			String tc = c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: TestCaseName");
			String sN = c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: XML_ScenarioName");
			Application
					.showMessage("_______________________________________________");
			Application.showMessage("Starting TestCase...." + tc);
			Application.showMessage("SCENARIO_NUMBER::" + sc);
			Application.showMessage("Scenario is ::" + sN);

			if (sc.equals("1")) {

				c.setValue("IsInstall", "true");
				c.setValue("IsCos", "false");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "false");
				c.setValue("IsCancel", "false");
				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application.showMessage("Itearion flag values"
						+ c.getValue("IsInstall") + c.getValue("IsCos")
						+ c.getValue("IsSuspend") + c.getValue("IsRestore")
						+ c.getValue("IsCancel"));
				Application
						.showMessage("Iteration set up is for Install at scenario number ::"
								+ sc);
			} else if (sc.equals("2")) {
				c.setValue("IsInstall", "false");
				c.setValue("IsCos", "true");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "false");
				c.setValue("IsCancel", "false");
				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application.showMessage("Itearion flag values"
						+ c.getValue("IsInstall") + c.getValue("IsCos")
						+ c.getValue("IsSuspend") + c.getValue("IsRestore")
						+ c.getValue("IsCancel"));

				Application
						.showMessage("Iteration set up is for COS at scenario number ::"
								+ sc);
			}

			else if (sc.equals("4")) {
				c.setValue("IsInstall", "false");
				c.setValue("IsCos", "false");
				c.setValue("IsSuspend", "true");
				c.setValue("IsRestore", "false");
				c.setValue("IsCancel", "false");
				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application
						.showMessage("Iteration set up is for SUSPEND at scenario number ::"
								+ sc);
			}

			else if (sc.equals("5")) {
				c.setValue("IsInstall", "false");
				c.setValue("IsCos", "false");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "true");
				c.setValue("IsCancel", "false");
				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application
						.showMessage("Iteration set up is for RESORE at scenario number ::"
								+ sc);
			}

			else if (sc.equals("11")) {
				c.setValue("IsInstall", "false");
				c.setValue("IsCos", "false");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "false");
				c.setValue("IsCancel", "true");
				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application
						.showMessage("Iteration set up is for CANCEL at scenario number ::"
								+ sc);
			}

		} catch (Exception e) {

		}

	}

	public void reportingToExcel(Object input, ScriptingContext c) {

		if (c.get("result").equals("true")) {
			// c.setValue("RESULT","PASSED");
			Application.showMessage("TestCase is a PASS");
		} else {
			// c.setValue("RESULT","FAILED");
			Application.showMessage("Test Case is a Failure");

		}
	}

	public void SaveValuesfromInstall(Object input, ScriptingContext c) {
		try {
			c.put("I_AccInput", c.getValue("ACC_input"));
			c.put("I_CcentralNum", c.getValue("CcentralNum"));
			c.put("I_PostalCode", c.getValue("PostalCode"));
			// c.put("I_AccInput", c.getValue("ACC_input"));
		} catch (Exception e) {

		}

	}

	public void LoadValuestoGlobalList(Object input, ScriptingContext c) {
		try {
			c.setValue("ACC_input", (String) c.get("I_AccInput"));
			c.setValue("CcentralNum", (String) c.get("I_CcentralNum"));
			c.setValue("PostalCode", (String) c.get("I_PostalCode"));
		} catch (Exception e) {

		}

	}

	public void baseMsgid_retrieval(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException {

		try {

			Statement st = dBConnect(input, c);
			ResultSet rs = st
					.executeQuery("select msgid from (select msgid from cwmessagelog order by creation_time desc) where rownum < 2");
			while (rs.next()) {
				basemsgID = rs.getString(1);
				c.setValue("BaseMsgid", basemsgID);
				Application.showMessage("Base Message ID is ::"
						+ c.getValue("BaseMsgid"));

			}
			if (basemsgID == null) {
				System.out.println("No MsgId Found");
			} else {
				System.out.printf("MsgId Found is %s\n", basemsgID);
			}
			st.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

	}

	public void releaseMemory(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException {
		c.setValue("ACC_input", "null");
		c.setValue("BaseMsgid", "null");
		c.setValue("CcentralNum", "null");
		c.setValue("HouseNumber", "null");
		c.setValue("FirstName", "null");
		c.setValue("LastName", "null");
		c.setValue("Address", "null");
	}

	public void simulatorChange(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, SQLException,
			InterruptedException {

		// PreparedStatement ps= connection.prepareStatement("my_temp_proc(?)");
		Application.showMessage("The Simulator setting for Ucontrol"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: useUcontrolSimulator"));
		Statement st = dBConnect(input, c);
		ResultSet rs = st
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		while (rs.next()) {
			String beforeSim = rs.getString(2);
			// c.setValue("BaseMsgid", basemsgID);
			Application.showMessage("Db Simulator Before Updation ::"
					+ beforeSim);

		}
		st.close();
		Statement st0 = dBConnect(input, c);

		String sql = "update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: useUcontrolSimulator")
				+ "</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: accountId_Simulator")
				+ "</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"
				+ c.getValue("CcentralNum")
				+ "</centralStationAccountNumber><uControlGroupValue>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: uControlGroupValue")
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>ACTIVE</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
		st0.executeUpdate(sql);

		// ps.setString(1, sql);

		// ps.execute();

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

	public void simulatorChange30DayDisconnectHandleInvalidService(
			String status, Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, SQLException,
			InterruptedException {

		// String status=
		// c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		// Application.showMessage("Status needs to be updated is ::"+status);
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
		String sql = "update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: useUcontrolSimulator")
				+ "</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: accountId_Simulator")
				+ "</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"
				+ c.getValue("CcentralNum")
				+ "</centralStationAccountNumber><uControlGroupValue>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: uControlGroupValue")
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"
				+ status
				+ "</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><replaceInvSrvEnbl>1</replaceInvSrvEnbl></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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

	public void simulatorChangeDeceasedCustomer(String status, Object input,
			ScriptingContext c) throws ClassNotFoundException, IOException,
			SQLException, InterruptedException {

		// String status=
		// c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		// Application.showMessage("Status needs to be updated is ::"+status);
		Statement st = dBConnect(input, c);
		Application.showMessage("I AM HERE");
		ResultSet rs = st
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		while (rs.next()) {
			String beforeSim = rs.getString(2);

			Application.showMessage("Db Simulator Before Updation ::"
					+ beforeSim);
			System.out.println("Db Simulator Before Updation ::"
					+ beforeSim);


		}
		st.close();
		Statement st0 = dBConnect(input, c);
		Application.showMessage("I AM HERE");
		String sql = "update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"+ c.getValue("RTPDataSourceCollections","dB_Simulator: useUcontrolSimulator")+ "</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: accountId_Simulator")
				+ "</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"
				+ c.getValue("CcentralNum")
				+ "</centralStationAccountNumber><uControlGroupValue>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: uControlGroupValue")
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"
				+ status
				+ "</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><changeReason>Deceased</changeReason></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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
			System.out.println("Db Simulator After Updation ::" + afterSim);

		}
		st0.close();

	}
	
	
	//------------------
	public void simulatorChangeDeceasedCustomer_CLS(Object input, ScriptingContext c)
			throws ClassNotFoundException, SQLException, IOException,
			InterruptedException {
		
		LibraryRtp lib = new LibraryRtp();
		
		Connection conn = lib.dBConnect_CLS_1(input, c);
		String name = "homesecurityUtil:hsConfig";
		PreparedStatement pstmt = conn
				.prepareStatement(" update CWFTEMPLATECONFIG set DATA=? where NAME='"
						+ name + "'");
		Statement st = conn.createStatement();
		pstmt.setString(
				1,
				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><oldFlowForAuthId>0</oldFlowForAuthId><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>1</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue>application/json</homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue>MgkJ3Gby7Xdsxe8hBGCyCwib</homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue>uD15pbTAVDqsauHJdzRJ9MtBQ0davhDJ</homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue>application/json</homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><homeStreamRequestResourcePathForSuspendAccount>/suspended</homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount>/restored</homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup>/group/</homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup>/group/</homeStreamRequestResourcePathForRemoveAccountGroup><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount><homeStreamRequestResourcePathForUpdateAccountTierGroup>/tiergroup/</homeStreamRequestResourcePathForUpdateAccountTierGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL>https://homesecurity-acct-qa03.codebig2.net/rest</homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation>deleteUnactivatedAccount,createAccount,suspendAccount,restoreAccount,deactivateAccount,removeAccount,getAccount,updateAccountTierGroup,addAccountGroup,removeAccountGroup,updateAccount</xhsHomeStreamOperation><ValidPNR>0</ValidPNR><isClsGetSimulator>1</isClsGetSimulator><isClsDeactivateSimulator>1</isClsDeactivateSimulator><changeReason>"+c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: ChangeReason")+"</changeReason></hsConfig>");
		;
		// pstmt.executeQuery();

		pstmt.executeUpdate();
		conn.setAutoCommit(true);

		ResultSet rs = st
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		while (rs.next()) {
			String beforeSim = rs.getString(1);

			Application.showMessage("Db Simulator Before Updation ::"
					+ beforeSim);

		}
		st.close();

		// <vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType>

		Thread.sleep(300000);
		Statement st1 = lib.dBConnect(input, c);
		ResultSet rs1 = st1
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		while (rs1.next()) {

			String afterSim = rs1.getString(2);
			Application
					.showMessage("Db Simulator After Updation ::" + afterSim);

		}

	}
	
	//-----------

	public int updateLongSql(Statement oSt, String sTableName,
			String sFieldName, String sWhereClause, String sNewVal)
			throws SQLException {
		int i = 0, j = 0, iRet = 0;
		String sQuery = "";

		i = sNewVal.length();
		Application.showMessage("Length is: " + i);
		if (i > 1000)
			i = 1000;

		sQuery = "update " + sTableName + " set " + sFieldName + " = '"
				+ sNewVal.substring(0, i) + "' " + sWhereClause;
		Application.showMessage(sQuery);
		iRet = oSt.executeUpdate(sQuery);

		if (iRet > 0) {
			while (i <= sNewVal.length()) {
				j = sNewVal.length() - i;
				if (j > 0) {
					if (j > 1000)
						j = 1000;
					sQuery = "update " + sTableName + " set " + sFieldName
							+ " = " + sFieldName + " || '"
							+ sNewVal.substring(i, i + j) + "' " + sWhereClause;
					Application.showMessage(sQuery);
					oSt.executeUpdate(sQuery);
				}
				i = i + 1000;
			}
		}
		return iRet;
	}

	public String extractXml(ResultSet rs1, String data) throws SQLException {

		Blob blob = rs1.getBlob(data);
		byte[] bdata = blob.getBytes(1, (int) blob.length());
		xml1 = new String(bdata);
		return (xml1);
	}

	public void IterationLogic_Insight(Object input, ScriptingContext c) {
		try {

			String sc = c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: SCENARIO_NUMBER");
			String tc = c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: TestCaseName");
			String sN = c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: XML_ScenarioName");
			Application
					.showMessage("_______________________________________________");
			Application.showMessage("Starting TestCase...." + tc);
			Application.showMessage("SCENARIO_NUMBER::" + sc);
			Application.showMessage("Scenario is ::" + sN);

			if (sc.equals("1")) {

				c.setValue("IsInstall", "true");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "false");
				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application.showMessage("Itearion flag values"
						+ c.getValue("IsInstall") + c.getValue("IsCos")
						+ c.getValue("IsSuspend") + c.getValue("IsRestore")
						+ c.getValue("IsCancel"));
				Application
						.showMessage("Iteration set up is for Install at scenario number ::"
								+ sc);
			}

			else if (sc.equals("4")) {
				c.setValue("IsInstall", "false");
				c.setValue("IsSuspend", "true");
				c.setValue("IsRestore", "false");
				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application
						.showMessage("Iteration set up is for SUSPEND at scenario number ::"
								+ sc);
			}

			else if (sc.equals("5")) {
				c.setValue("IsInstall", "false");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "true");
				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application
						.showMessage("Iteration set up is for RESORE at scenario number ::"
								+ sc);
			}

		} catch (Exception e) {

		}

	}

	public Map<String, String> splitter(String input) {

		// String input ="350 + ThermostatDataProvider";
		// String input ="350";
		Map<String, String> retMap = new HashMap<String, String>();
		String service = " ";
		String group = " ";
		boolean i = input.contains("+");
		String Singlegroup = "false";
		String Multigroup = "false";
		int y = 0;
		if (i == true) {
			Multigroup = "true";

			for (y = 0; input.charAt(y) != '+'; y++) {
				String temp = String.valueOf(input.charAt(y));
				group = group.concat(temp).trim();

			}
			for (y = y + 2; y < input.length(); y++) {
				String temp = String.valueOf(input.charAt(y));
				service = service.concat(temp).trim();
			}
			// Application.showMessage("TierGroup is ::"+group);
			// Application.showMessage("TierService is ::"+service);
			retMap.put("group", group.trim());
			retMap.put("service", service.trim());
			retMap.put("IsMulti", Multigroup.trim());
			retMap.put("IsSingle", Singlegroup.trim());

		} else {

			Singlegroup = "true";
			retMap.put("group", input.trim());
			retMap.put("service", " ");
			retMap.put("IsMulti", Multigroup.trim());
			retMap.put("IsSingle", Singlegroup.trim());

			Application.showMessage("Only Tier Group is there");
		}
		return retMap;

	}

	public Boolean validationPointIgnoreCase(String ValidateData1,
			String ValidateData2, Object input, ScriptingContext c) {
		Boolean result;
		if (ValidateData1.equalsIgnoreCase(ValidateData2)) {
			Application
					.showMessage("Validation is Successfull with Input Data ::"
							+ ValidateData1 + "and" + ValidateData2);

			result = true;
		} else {
			//c.report("Validation is NOT Successfull with Input Data..!!! ::"+ ValidateData1 + "and" + ValidateData2);

			result = false;
		}

		return result;

	}

	public Boolean validationPoint(String ValidateData1, String ValidateData2,
			Object input, ScriptingContext c) {
		Boolean result;
		if (ValidateData1.equals(ValidateData2)) {
			Application
					.showMessage("Validation is Successfull with Input Data ::"
							+ ValidateData1 + "and" + ValidateData2);

			result = true;
		} else {
			c.report("Validation is NOT Successfull with Input Data..!!! ::"
					+ ValidateData1 + "and" + ValidateData2);

			result = false;
		}

		return result;

	}

	public String validationPointIccID(String ValidateData1,
			String ValidateData2, Object input, ScriptingContext c) {
		String IccID = null;
		Application.showMessage("InputICCID/DataSheet::"+ValidateData2);
		Application.showMessage("IccID from database::"+ValidateData1);
		if ((ValidateData1.isEmpty() && ValidateData2.isEmpty())|| (ValidateData1==null && ValidateData2==null))
		{
			c.report("Both InputData IccID and DB IccID null.. !! ::"
					+ ValidateData1 + "and" + ValidateData2);
		} else if (ValidateData1.isEmpty()&& ValidateData2!=null) {
			IccID = ValidateData2;
		} else if (ValidateData2.isEmpty()&& ValidateData1!=null ) {
			IccID = ValidateData1;
		} else if(ValidateData1!=null && ValidateData2!=null)
		{
			IccID=ValidateData2;
		}
			
		Application.showMessage("IccID:::"+IccID);
		return IccID;
	}

	public void simulatorChange30DayDisconnect(String status, Object input,
			ScriptingContext c) throws ClassNotFoundException, IOException,
			SQLException, InterruptedException {

		/*// String status=
		// c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		// Application.showMessage("Status needs to be updated is ::"+status);
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
		String sql = "update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: useUcontrolSimulator")
				+ "</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: accountId_Simulator")
				+ "</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"
				+ c.getValue("CcentralNum")
				+ "</centralStationAccountNumber><uControlGroupValue>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: uControlGroupValue")
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"
				+ status
				+ "</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><getTerminalStatus>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: getTerminalStatus")
				+ "</getTerminalStatus></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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
*/
	}
	
	

	public void simulatorChange30DayDisconnectgetTerminalDetails(String status,
			Object input, ScriptingContext c) throws ClassNotFoundException,
			IOException, SQLException, InterruptedException {

		// String status=
		// c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		// Application.showMessage("Status needs to be updated is ::"+status);
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
		String sql = "update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: useUcontrolSimulator")
				+ "</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: accountId_Simulator")
				+ "</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"
				+ c.getValue("CcentralNum")
				+ "</centralStationAccountNumber><uControlGroupValue>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: uControlGroupValue")
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"
				+ status
				+ "</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><replaceInvSrvEnbl>1</replaceInvSrvEnbl><getTerminalStatus></getTerminalStatus></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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
	
	public void simulatorChange30DayDisconnectgetTerminalDetailsAfterDisconnect(String status,
			Object input, ScriptingContext c) throws ClassNotFoundException,
			IOException, SQLException, InterruptedException {

		// String status=
		// c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		// Application.showMessage("Status needs to be updated is ::"+status);
		Statement st = dBConnect(input, c);
		ResultSet rs = st
				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
		while (rs.next()) {
			String beforeSim = rs.getString(2);

			Application.showMessage("Db Simulator Before Updation ::"
					+ beforeSim);
			System.out.println("Db Simulator Before Updation ::"
					+ beforeSim);

		}
		st.close();
		Statement st0 = dBConnect(input, c);
		String sql = "update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: useUcontrolSimulator")
				+ "</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: accountId_Simulator")
				+ "</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"
				+ c.getValue("CcentralNum")
				+ "</centralStationAccountNumber><uControlGroupValue>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: uControlGroupValue")
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"
				+ status
				+ "</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><replaceInvSrvEnbl>1</replaceInvSrvEnbl><getTerminalStatus>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: getTerminalStatus")
				+ "</getTerminalStatus></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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
			System.out.println("Db Simulator After Updation ::" + afterSim);

		}
		st0.close();

	}
	
	public void simulatorChange30DayDisconnectsimInfoEX(String status,
			Object input, ScriptingContext c) throws ClassNotFoundException,
			IOException, SQLException, InterruptedException {

		// String status=
		// c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		// Application.showMessage("Status needs to be updated is ::"+status);
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
		String sql = "update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: useUcontrolSimulator")
				+ "</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: accountId_Simulator")
				+ "</accountId><iccId>8901640100735449527</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"
				+ c.getValue("CcentralNum")
				+ "</centralStationAccountNumber><uControlGroupValue>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: uControlGroupValue")
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"
				+ status
				+ "</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><replaceInvSrvEnbl>1</replaceInvSrvEnbl><getTerminalStatus>RETIRED_NAME</getTerminalStatus><SIMInfoExResult>0</SIMInfoExResult><SIMInfoICCID>8901640100334741514</SIMInfoICCID><SIMInfoStatus>"+ c.getValue("RTPDataSourceCollections","dB_Simulator: SimInfoEXStatus")+"</SIMInfoStatus><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>1</useSIMInfoSimulator></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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
	public Statement mysqldbConnect(String Ccnum,String accountNumber) throws ClassNotFoundException, SQLException, IOException, InstantiationException, IllegalAccessException
	{ Statement st = null;
		try{
	
	 Class.forName("com.mysql.jdbc.Driver");
	
	   
	   final String DB_URL = "jdbc:mysql://10.255.159.180:3307/my_sql";

	   //  Database credentials
	   final String USER = "mithun";
	   final String PASS = "Tester123";   
	   String sql;
	   System.out.println("Connecting to Database");
	   Application.showMessage("Connecting to Database");
	      connection = DriverManager.getConnection(DB_URL,USER,PASS);
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      Application.showMessage("Creating statement...");
	     st = connection.createStatement();
	     
            
             
	     Application.showMessage("Records to be Updated::");
              System.out.println("Records to be Updated::");
              Application.showMessage("update my_sql.userentrynew set cCentralID='"+Ccnum+"'where AccountNumber="+accountNumber);
	      sql = "update my_sql.userentrynew set cCentralID='"+Ccnum+"'where AccountNumber='"+accountNumber+"'";
	     int i= st.executeUpdate(sql);
            System.out.println("Records Updated::" + i);
            Application.showMessage("Records Updated::" + i);
	      st.close();
	      connection.close();
	}
	catch (ClassNotFoundException e) { // 
        Application.showMessage("Driver not found " + e);
    } catch (SQLException e) {
    	Application.showMessage("Connect not possible" + e);
    }
	   	
		return st;
	}
	public String RemoveNameSpaces(String sIpStr) 
	{
        String sRet = sIpStr;
        Pattern pP = Pattern.compile("<[a-zA-Z]*:");
        Matcher mM = pP.matcher(sRet);
        sRet = mM.replaceAll("<");

        pP = Pattern.compile("</[a-zA-Z]*:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("</");
        
        pP = Pattern.compile(" xmlns.*?(\"|\').*?(\"|\')");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(""); 

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
        
        pP = Pattern.compile("ns3:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("");
        
        pP = Pattern.compile("ns2:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("");
        
        return sRet;
   }
	public void storeCCnum(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException, InstantiationException, IllegalAccessException
	{
		
		//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
		String accountNumber=c.getValue("ACC_input");
		String Ccnum = c.getValue("CcentralNum");
		Application.showMessage("AccountNumber::"+accountNumber);
		Application.showMessage("cCentralnumber::"+Ccnum);
		mysqldbConnect(Ccnum, accountNumber);
	}		
	

}
