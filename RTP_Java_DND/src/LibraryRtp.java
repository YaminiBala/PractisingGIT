import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

public class LibraryRtp {
	public String basemsgID = null;
	public Connection connection = null;
	public String xml1;
	public String xml2;
	// public String rowmsg= null;
	public String resultval;
	public static String logger = "";

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

	public Statement dBConnectCommon(Object input, ScriptingContext c)
			throws ClassNotFoundException, SQLException, IOException {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		// connection =
		// DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1",
		// "CWDEVOP","CWDEVOP");

		// connection =
		// DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.33:1521:psymqa1",
		// "CWQA1OP","qa1opw1nt3r");
		Application.showMessage(c.getValue("RTPNormalScenariosdS",
				"DbConnections: CommonDB_UserName"));
		String host = c.getValue("RTPNormalScenariosdS",
				"DbConnections: DB_HOST").trim();
		String password = c.getValue("RTPNormalScenariosdS",
				"DbConnections: CommonDB_Password").trim();
		String username = c.getValue("RTPNormalScenariosdS",
				"DbConnections: CommonDB_UserName").trim();
		String port = c.getValue("RTPNormalScenariosdS",
				"DbConnections: DB_Port").trim();
		String sid = c.getValue("RTPNormalScenariosdS",
				"DbConnections: DB_ServiceName").trim();
		// Application.showMessage("Debug statement"+host+""+password+""+username+""+port+""+sid);

		connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host
				+ ":" + port + ":" + sid + "", username, password);
		Statement st1 = connection.createStatement();
		return st1;
	}

	public ResultSet searchDataSet(Object input, ScriptingContext c,
			String operation, String Time) throws ClassNotFoundException,
			SQLException, IOException {
		ResultSet rs;
		Statement st = dBConnect(input, c);
		rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"
				+ operation
				+ "'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"
				+ Time + "' order by creation_time desc)where rownum <= 20");
		st.close();
		return rs;
	}

	public ResultSet searchDataSetWithAccount(Object input, ScriptingContext c,
			String Account, String Time) throws ClassNotFoundException,
			SQLException, IOException {
		ResultSet rs;
		Statement st = dBConnect(input, c);
		rs = st.executeQuery("select * from (select * from cwmessagelog where user_data2 = '"
				+ Account
				+ "' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"
				+ Time + "' order by creation_time desc)where rownum <= 20");
		st.close();
		return rs;
	}

	public int validationPoint(String ValidateData1, String ValidateData2,
			Object input, ScriptingContext c) {
		int result;
		if (ValidateData1.equals(ValidateData2)) {
			Application
					.showMessage("Validation is Successfull with Input Data ::"
							+ " " + ValidateData1 + " and " + " "
							+ ValidateData2);

			result = 1;
		} else {
			c.report("Validation is NOT Successfull with Input Data..!!! :: "
					+ ValidateData1 + " and " + ValidateData2);

			result = 0;
		}

		return result;

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

	public int verificationPoint(String ValidateData1, String ValidateData2,
			Object input, ScriptingContext c) {

		int result;

		if (ValidateData1.equals(null) || ValidateData2.equals(null)) {
			Application
					.showMessage("One of the Validation Data entered is NULL...!");
			result = 0;
		} else {
			if (ValidateData1.equals(ValidateData2)) {
				Application
						.showMessage("Validation is Successfull with Input Data ::"
								+ " "
								+ ValidateData1
								+ " and "
								+ " "
								+ ValidateData2);

				result = 1;
			} else {
				Application
						.showMessage("Validation is NOT Successfull with Input Data..!!! :: "
								+ ValidateData1 + " and " + ValidateData2);

				result = 0;
			}
		}
		return result;

	}

	public String makeCorpIDfromAccountNumber(String AccountNumber,
			String INbillingSystem) {
		String INcorpID;
		if (INbillingSystem.equalsIgnoreCase("DDP")) {
			INcorpID = AccountNumber.substring(0, 5);
		}

		else {
			INcorpID = AccountNumber.substring(0, 6);
		}
		return INcorpID;
	}

	public void printStart(Object input, ScriptingContext c, String Data) {
		Application
				.showMessage("----------------------------------------------------------");
		Application.showMessage("************" + Data + "************");
		Application
				.showMessage("----------------------------------------------------------");
	}

	public void printEnd(Object input, ScriptingContext c, String Data) {
		Application
				.showMessage("----------------------------------------------------------");
		Application.showMessage("************" + Data + "************");
		Application
				.showMessage("----------------------------------------------------------");
	}

	public int validationPointIgnoreCase(String ValidateData1,
			String ValidateData2, Object input, ScriptingContext c) {
		int result;
		if (ValidateData1.trim().equalsIgnoreCase(ValidateData2.trim())) {
			Application
					.showMessage("Validation is Successfull with Input Data ::"
							+ ValidateData1 + "and" + ValidateData2);

			result = 1;
		} else {
			c.report("Validation is NOT Successfull with Input Data..!!! ::"
					+ ValidateData1 + "and" + ValidateData2);

			result = 0;
		}

		return result;

	}

	public int ORvalidationPoint(String ValidateData1, String ValidateData2,
			String ValidateData3, Object input, ScriptingContext c) {
		int result;
		if (ValidateData1.trim().equals(ValidateData2.trim())) {
			Application
					.showMessage("Validation is Successfull with Input Data ::"
							+ ValidateData1 + "and" + ValidateData2);

			result = 1;
		} else if (ValidateData1.trim().equals(ValidateData3.trim())) {
			Application
					.showMessage("Validation is Successfull with Input Data ::"
							+ ValidateData2 + "and" + ValidateData3);

			result = 1;
		} else {
			c.report("Validation is NOT Successfull with Input Data..!!! ::"
					+ ValidateData1 + "and" + ValidateData2);

			result = 0;
		}

		return result;

	}

	public int ANDvalidationPoint(String ValidateData1, String ValidateData2,
			String ValidateData3, Object input, ScriptingContext c) {
		int result;
		if (ValidateData1.trim().equals(ValidateData2.trim())
				& (ValidateData1.trim().equals(ValidateData3.trim()))) {
			Application
					.showMessage("Validation is Successfull with Input Data ::"
							+ ValidateData1 + "and" + ValidateData2 + "and"
							+ ValidateData3);

			result = 1;
		}

		else {
			c.report("Validation is NOT Successfull with Input Data..!!! ::"
					+ ValidateData1 + "and" + ValidateData2);

			result = 0;
		}

		return result;

	}
	
	//--Yamini's --Dynamic way of Handling Think Time--
	
	public ResultSet reduceThinkTimeRTP(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		LibraryRtp  lC= new LibraryRtp ();
		int count=0;
	     int waitingtime=0;
	     int thinktime=0;
		 ResultSet  rs;
		 Statement st;
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("Time:::"+Time);
	     Application.showMessage("Operation is:::"+c.getValue("OPERATIONVALIDATION"));
	     System.out.println("Time:::"+Time);
	     System.out.println("Operation is:::"+c.getValue("OPERATIONVALIDATION"));
	     do
			{
				Thread.sleep(1000);
		    st =lC. dBConnect1(input, c);	
	        
			//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ContractServices:ContractServicePort/queryContract' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
		    if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("eep:XHSEEPOrder/xhsevent")))
		    		{
		    	rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >= '"+Time+"' order by creation_time desc)where rownum <= 20");
				
		    		}
		    else if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("cmb:commonOrderService/orderUpdate")))
		    {
		    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
		    }
		    else
		    {
		    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and User_data2='"+c.getValue("ACC_input")+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
		    }
			
		//	Application.showMessage("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >= '"+Time+"' order by creation_time desc)where rownum <= 20");
	    
		   
	    	 

			if(!rs.isBeforeFirst())
	         {
	        	
				//Application.showMessage("Yes");
				waitingtime++;
				count++;
				
				rs=null;
				
	         }		
					else
					{//Application.showMessage("Yamini1");
						
					
						break;
					}
			
			
			st.close();
			
			
			
	         }while(count < 120);
	     int timing=waitingtime + 1;
			Application.showMessage("Waited for "+timing+"seconds"+rs);
			System.out.println("Waited for "+timing+"seconds"+rs);
			
			return rs;
	}
	//----------------------
	public ResultSet reduceThinkTimeRTPmsgid(Object input,ScriptingContext c,String rowmsg) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		LibraryRtp  lC= new LibraryRtp ();
		int count=0;
	     int waitingtime=0;
	     int thinktime=0;
		 ResultSet  rs;
		 Statement st;
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("Time:::"+Time);
	     Application.showMessage("Operation is:::"+c.getValue("OPERATIONVALIDATION"));
	     System.out.println("Time:::"+Time);
	     System.out.println("Operation is:::"+c.getValue("OPERATIONVALIDATION"));
	     do
			{
				Thread.sleep(1000);
		    st =lC. dBConnect1(input, c);	
	        
			//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ContractServices:ContractServicePort/queryContract' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
		    if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("eep:XHSEEPOrder/xhsevent")))
		    		{
		    	rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and MSGID !='"+rowmsg+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >= '"+Time+"' order by creation_time desc)where rownum <= 20");
				
		    		}
		    else if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("cmb:commonOrderService/orderUpdate")))
		    {
		    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and MSGID !='"+rowmsg+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
		    }
		    else
		    {
		    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and User_data2='"+c.getValue("ACC_input")+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
		    }
			
		//	Application.showMessage("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >= '"+Time+"' order by creation_time desc)where rownum <= 20");
	    
		   
	    	 

			if(!rs.isBeforeFirst())
	         {
	        	
				//Application.showMessage("Yes");
				waitingtime++;
				count++;
				
				rs=null;
				
	         }		
					else
					{//Application.showMessage("Yamini1");
						
					
						break;
					}
			
			
			st.close();
			
			
			
	         }while(count < 120);
	     int timing=waitingtime + 1;
			Application.showMessage("Waited for "+timing+"seconds"+rs);
			System.out.println("Waited for "+timing+"seconds"+rs);
			
			return rs;
	}
	
	//---------------------
	public String CallsFoundResultSet(Object input, ScriptingContext c)
	{
		String callsPresent="false";
		return callsPresent;
	}
	
	//------------------------
	
	public void findThinktimeOperationRTP(Object input, ScriptingContext c)
	{
		if(c.getValue("RTPTestInterface").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "eep:XHSEEPOrder/xhsevent");
			Application.showMessage("RTPTestinterface Operation available");
		}
		else if(c.getValue("getAccount").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/getAccount");
			Application.showMessage("getaccount Operation available");
		}
		else if(c.getValue("queryLocation").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ls:LocationServicePort/queryLocation");
			Application.showMessage("queryLocation Operation available");
		}
		else if(c.getValue("activateAccount").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "COPSServices:Comcast/ActivateAccount");
			Application.showMessage("activateaccount Operation available");
		}
		else if(c.getValue("processHomeSecurity").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo");
			Application.showMessage("processHomesecurity Operation available");
		}
		else if(c.getValue("createCMSaccountID").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "hss:HomeSecurityServicePort/createCMSAccountID");
			Application.showMessage("createCMSID Operation available");
		}
		else if(c.getValue("OrderUpdate").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "cmb:commonOrderService/orderUpdate");
			Application.showMessage("OrderUpdate Operation available");
		}
		else if(c.getValue("restoreAccount").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/restoreAccount");
			Application.showMessage("restoreAccount Operation available");
		}
		else if(c.getValue("suspendAccount").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/suspendAccount");
			Application.showMessage("suspendAccount Operation available");
		}
		else if(c.getValue("deleteUnactivatedAccount").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/deleteUnactivatedAccount");
			Application.showMessage("deleteUnactivatedAccount Operation available");
		}
		else if(c.getValue("DisconnectAccount").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "COPSServices:Comcast/DisconnectAccount");
			Application.showMessage("disconnectAccount Operation available");
		}
		else if(c.getValue("deactivateAccount").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/deactivateAccount");
			Application.showMessage("deactivateAccount Operation available");
		}
		//RTPTestInterface
		else if(c.getValue("Jasper").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "jaspersrv:TerminalPortType/EditTerminal");
			Application.showMessage("Jasper Operation available");
		}
		else if(c.getValue("Numerex").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "cosims:NumerexSimulatorLogMsg/deactivateUnit");
			Application.showMessage("Numeex Operation available");
		}
		else if(c.getValue("UpdateTier").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/updateAccountTierGroup");
			Application.showMessage("UpdateTier Operation available");
		}
		else if(c.getValue("removeAccountgroup").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/removeAccountGroup");
			Application.showMessage("removeaccountgrp Operation available");
		}
		else if(c.getValue("addAccountGroup").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/addAccountGroup");
			Application.showMessage("addAccountgrp Operation available");
		}
		else if(c.getValue("setAccountBasicInformation").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "COPSServices:Comcast/SetAccountBasicInformation");
			Application.showMessage("setAccountbasic Operation available");
		}
		else if(c.getValue("createAccount").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ucontrolsrv:AccountServicePortType/createAccount");
			Application.showMessage("createAcount Operation available");
		}
		else if(c.getValue("responderInfo").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "intrado:frlResource/responderInfo");
			Application.showMessage("responderInfo Operation available");
		}
		else if(c.getValue("setAccountAuthorityInfo").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "COPSServices:Comcast/SetAccountAuthorityInformation");
			Application.showMessage("setAccountAuthorityInfo Operation available");
		}
		else if(c.getValue("modifyHomesecurity").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "ls:LocationServicePort/modifyHomeSecurity");
		}
		else if(c.getValue("invokeRestservice").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "op:RestClient/invokeRestService");
			Application.showMessage("invokeRestservice Operation available");
		}
		
	//cls
		else if(c.getValue("getAccount_CLS").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "op:RestClient/getAccount");
			Application.showMessage("getAccount_CLS Operation available");
		}
		else if(c.getValue("createAccount_CLS").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "op:RestClient/addAccount");
			Application.showMessage("createAccount_CLS Operation available");
		}
		else if(c.getValue("UpdateTier_CLS").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "op:RestClient/updateAccountTierGroup");
			Application.showMessage("UpdateTier_CLS Operation available");
		}
		else if(c.getValue("addAccountGroup_CLS").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "op:RestClient/addAccountGroup");
			Application.showMessage("addAccountGroup_CLS Operation available");
		}
		else if(c.getValue("removeAccountgroup_CLS").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "op:RestClient/removeAccountGroup");
			Application.showMessage("removeAccountgroup_CLS Operation available");
		}
		else if(c.getValue("getTerminalDetails").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "jaspersrv:TerminalPortType/GetTerminalDetails");
			Application.showMessage("getTerminalDetails Operation available");
		}
		else if(c.getValue("getCustomerPermitRequirements").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "hss:HomeSecurityServicePort/getCustomerPermitRequirements");
			Application.showMessage("getCustomerPermitRequirements Operation available");
		}
		//---
		else if(c.getValue("FindSites").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "cosims:IControlSimulatorLogMsg/FindSites");
		}
		else if(c.getValue("SetSiteStatus").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "cosims:IControlSimulatorLogMsg/SetSiteStatus");
		}
		else if(c.getValue("OutService").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "UCCService13_11:SiteImportSoap/OutService");
		}
		else if(c.getValue("getWorkOrder").equalsIgnoreCase("true"))
		{
			c.setValue("OPERATIONVALIDATION", "SchedulingService:SchedulingServicePort/getWorkOrder");
		}
		else
		{
			Application.showMessage("Operations are not available");
		}
		
	}
	
	public HashMap findingoperations(Object input,ScriptingContext c)
    {
    	HashMap map = new HashMap();
    	
    	 map.put("xhsevent","eep:XHSEEPOrder/xhsevent");
    	 map.put("geodeticQuery","intrado:frlResource/geodeticQuery");
    	 map.put("getWorkOrder","SchedulingService:SchedulingServicePort/getWorkOrder");
    	 map.put("UcontrolgetAccount","ucontrolsrv:AccountServicePortType/getAccount");
    	 map.put("ClSgetAccount","op:RestClient/getAccount");
    	 map.put("ClSgetAccountSim","op:RestClient/getAccountSim");
    	 map.put("ClScreateAccount","op:RestClient/addAccount");
    	 map.put("updateAccount","op:RestClient/updateAccount");
    	 map.put("BNERcall","op:RestClient/invokeRestService");
    	 map.put("CLS_DDS","op:RestClient/invokeRestService");
    	 map.put("getCustomerPermitRequirements","hss:HomeSecurityServicePort/getCustomerPermitRequirements");
    	 map.put("createCMSAccountID", "hss:HomeSecurityServicePort/createCMSAccountID");
    	 map.put("queryLocation", "ls:LocationServicePort/queryLocation");
    	 map.put("processHomeSecurityInfo", "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo");
    	 map.put("SetAccountBasicInformation", "COPSServices:Comcast/SetAccountBasicInformation");
    	 map.put("SetAccountAuthorityInformation", "COPSServices:Comcast/SetAccountAuthorityInformation");
    	 map.put("createAccount", "ucontrolsrv:AccountServicePortType/createAccount");
    	 map.put("responderInfo", "intrado:frlResource/responderInfo");
    	 map.put("modifyHomeSecurity", "ls:LocationServicePort/modifyHomeSecurity");
    	 map.put("orderUpdate", "cmb:commonOrderService/orderUpdate");
    	 //--COS calls
    	 map.put("Ucontrol_addAccountGroup", "ucontrolsrv:AccountServicePortType/addAccountGroup");
    	 map.put("Ucontrol_removeAccountGroup", "ucontrolsrv:AccountServicePortType/removeAccountGroup");
    	 map.put("Ucontrol_updateAccountTierGroup", "ucontrolsrv:AccountServicePortType/updateAccountTierGroup");
    	//--COS CLS calls
    	 map.put("CLS_addAccountGroup", "op:RestClient/addAccountGroup");
    	 map.put("CLS_removeAccountGroup", "op:RestClient/removeAccountGroup");
    	 map.put("CLS_updateAccountTierGroup", "op:RestClient/updateAccountTierGroup");
    	 //--suspend calls
    	 map.put("SuspendAccount", "COPSServices:Comcast/SuspendAccount");
    	 map.put("suspendAccount", "ucontrolsrv:AccountServicePortType/suspendAccount");
    	 
    	//--suspend CLS  calls
    	 map.put("CLS_suspendAccount", "op:RestClient/suspendAccount");
    	
    	 // --restore calls
    	 map.put("ActivateAccount", "COPSServices:Comcast/ActivateAccount");
    	 map.put("restoreAccount", "ucontrolsrv:AccountServicePortType/restoreAccount");
    	 
    	 // --restore CLS calls
    	 map.put("CLS_restoreAccount", "op:RestClient/restoreAccount");
    	 map.put("addAssociation","Numerex:NMRXCCSoap/addAssociation");
    	 
    	//--cancel calls
    	 map.put("deleteUnactivatedAccount", "ucontrolsrv:AccountServicePortType/deleteUnactivatedAccount");
    	 map.put("DisconnectAccount", "COPSServices:Comcast/DisconnectAccount");
    	 map.put("GetTerminalDetails", "jaspersrv:TerminalPortType/GetTerminalDetails");
    	 map.put("EditTerminal", "jaspersrv:TerminalPortType/EditTerminal");
    	 map.put("deactivateUnit", "cosims:NumerexSimulatorLogMsg/deactivateUnit");
    	 
    	//--cancel CLS calls
    	 map.put("removeAccount", "op:RestClient/removeAccount");
    	 map.put("deactivateAccount", "op:RestClient/deactivateAccount");
    	 map.put("deactivateAccountSim", "op:RestClient/deactivateAccountSim");
			
    	 map.put("activateUnit", "Numerex:NMRXCCSoap/activateUnit");
    	 return map;
    }
    
	
	public void printtxtfile(Object input,ScriptingContext c, String folder,String filename) throws IOException
	
	{
		//File file=new File("C:/DOTCOM/30DayDisconnect.txt");
		String path="C:\\".concat(folder);
		 File dir = new File(path);
		 String newdir=dir.toString();
		 if(dir.exists())
		 {
			 
			
		 }
		 else
		 {
			 dir.mkdirs();
		 }
FileOutputStream f = new FileOutputStream(path.concat("\\").concat(filename));
File dir1 = new File(path.concat("\\").concat(filename));
//System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(path.concat("\\").concat(filename)))));

System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(dir1.getAbsoluteFile(),true))));


	}
	
	
	public Statement dBConnect1(Object input, ScriptingContext c)
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
	//	Application.showMessage("The Values are::" + host + ":" + port + ":"
			//	+ sid + ":" + username + password);

		connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host
				+ ":" + port + ":" + sid + "", username, password);

		Statement st = connection.createStatement();
		return st;
	}
	
	//----Code End
	
	
	
	
	//-------------

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
		Application.showMessage("The Values are::" + host + ":" + port + ":"
				+ sid + ":" + username + password);

		connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host
				+ ":" + port + ":" + sid + "", username, password);

		Statement st = connection.createStatement();
		return st;
	}

	public void simulatorChangeCLS(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
	                
		LibraryRtp lib=new LibraryRtp();       

	                Statement st = lib.dBConnect(input, c);
	ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	while (rs.next()) 
	{
	String beforeSim = rs.getString(2);

	Application.showMessage("Db Simulator Before Updation ::"+beforeSim);

	}
	st.close();
	Statement st0 = lib.dBConnect(input, c);
	String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101017050000</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 aut</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><activeStatus>activated</activeStatus><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><homeStreamAcceptValue>application/json</homeStreamAcceptValue><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><homeStreamHeaderConsumerKeyValue>Ne0FCpucvawiXKXkr0aWbTzJ</homeStreamHeaderConsumerKeyValue><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><homeStreamHeaderConsumerSecretValue>NENPUcJbPyMJtUjcaGdZxGC3KUES0tm7</homeStreamHeaderConsumerSecretValue><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><homeStreamHeaderContentTypeValue></homeStreamHeaderContentTypeValue><homeStreamRequestResourcePathForSuspendAccount></homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount></homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup></homeStreamRequestResourcePathForAddAccountGroup><homeStreamURL></homeStreamURL><xhsHomeStreamOperation></xhsHomeStreamOperation></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
	//String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>1</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><threeDaysDiscDuration>2</threeDaysDiscDuration><threeDaysCVRDiscTableCleanUpDuration>2</threeDaysCVRDiscTableCleanUpDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><SIMInfoICCID>8901640100334741514</SIMInfoICCID><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><oldFlowForAuthId>0</oldFlowForAuthId><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><SIMInfoExResult>0</SIMInfoExResult><getTerminalStatus>RETIRED_NAME</getTerminalStatus><addressUpdateEventTrigger>CSGAccountDetailUpdated,DDPLocationUpdate</addressUpdateEventTrigger><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsPNsForCVR>10600085,10300029</xhsPNsForCVR><benrURL>https://secure.api.comcast.net/cvr-billing/qa/rest</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>PUT</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
	st0.executeUpdate(sql);
	//<vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType>

	st0.close();
	Thread.sleep(300000);
	Statement st1 = lib.dBConnect(input, c);
	ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	while (rs1.next()) 
	{

	String afterSim = rs1.getString(2);
	Application.showMessage("Db Simulator After Updation ::"+afterSim);

	}
	st0.close();


	}


	/* Code changed by Shilpa  - Start*/
	
	public Connection dBConnect_CLS_1(Object input, ScriptingContext c)
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
		Application.showMessage("The Values are::" + host + ":" + port + ":"
				+ sid + ":" + username + password);

		connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host
				+ ":" + port + ":" + sid + "", username, password);

		return connection;
	}

	public void dBConnectSimulator_CLS_1(Object input, ScriptingContext c)
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
				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue>application/json</homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue>MgkJ3Gby7Xdsxe8hBGCyCwib</homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue>uD15pbTAVDqsauHJdzRJ9MtBQ0davhDJ</homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue>application/json</homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><homeStreamRequestResourcePathForSuspendAccount>/suspended</homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount>/restored</homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup>/group/</homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup>/group/</homeStreamRequestResourcePathForRemoveAccountGroup><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount><homeStreamRequestResourcePathForUpdateAccountTierGroup>/tiergroup/</homeStreamRequestResourcePathForUpdateAccountTierGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL>https://homesecurity-acct-qa03.codebig2.net/rest</homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation>deleteUnactivatedAccount,createAccount,suspendAccount,restoreAccount,deactivateAccount,removeAccount,getAccount,updateAccountTierGroup,addAccountGroup,removeAccountGroup</xhsHomeStreamOperation><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount></hsConfig>");
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
	
	//----<addressUpdateEventTrigger>DDPLocationUpdate,CSGAccountDetailUpdated</addressUpdateEventTrigger>
	public void dBConnectSimulator_CLS_1_AddressUpdates(Object input, ScriptingContext c)
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
				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><addressUpdateEventTrigger>DDPLocationUpdate,CSGAccountDetailUpdated</addressUpdateEventTrigger><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue>application/json</homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue>MgkJ3Gby7Xdsxe8hBGCyCwib</homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue>uD15pbTAVDqsauHJdzRJ9MtBQ0davhDJ</homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue>application/json</homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><homeStreamRequestResourcePathForSuspendAccount>/suspended</homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount>/restored</homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup>/group/</homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup>/group/</homeStreamRequestResourcePathForRemoveAccountGroup><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount><homeStreamRequestResourcePathForUpdateAccountTierGroup>/tiergroup/</homeStreamRequestResourcePathForUpdateAccountTierGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL>https://homesecurity-acct-qa03.codebig2.net/rest</homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation>deleteUnactivatedAccount,createAccount,suspendAccount,restoreAccount,deactivateAccount,removeAccount,getAccount,updateAccountTierGroup,addAccountGroup,removeAccountGroup,updateAccount</xhsHomeStreamOperation><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount></hsConfig>");
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
	
	//---------------------------
	
	public void dBConnectSimulator_CLS_withCLSON(Object input, ScriptingContext c)
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
				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><oldFlowForAuthId>0</oldFlowForAuthId><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>1</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue>application/json</homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue>MgkJ3Gby7Xdsxe8hBGCyCwib</homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue>uD15pbTAVDqsauHJdzRJ9MtBQ0davhDJ</homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue>application/json</homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><homeStreamRequestResourcePathForSuspendAccount>/suspended</homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount>/restored</homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup>/group/</homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup>/group/</homeStreamRequestResourcePathForRemoveAccountGroup><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount><homeStreamRequestResourcePathForUpdateAccountTierGroup>/tiergroup/</homeStreamRequestResourcePathForUpdateAccountTierGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL>https://homesecurity-acct-qa03.codebig2.net/rest</homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation>deleteUnactivatedAccount,createAccount,suspendAccount,restoreAccount,deactivateAccount,removeAccount,getAccount,updateAccountTierGroup,addAccountGroup,removeAccountGroup,updateAccount</xhsHomeStreamOperation><ValidPNR>0</ValidPNR><isClsGetSimulator>1</isClsGetSimulator><isClsDeactivateSimulator>1</isClsDeactivateSimulator></hsConfig>");
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
	
	
	
	//---------------------------
	
	public void simulatorChange1(Object input, ScriptingContext c)
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
				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName></homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue></homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName></homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue></homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName></homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue></homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName></homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue></homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName></homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue></homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><homeStreamRequestResourcePathForSuspendAccount></homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount></homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup></homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup></homeStreamRequestResourcePathForRemoveAccountGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL></homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation></xhsHomeStreamOperation><homeStreamRequestResourcePathForDeactivateAccount></homeStreamRequestResourcePathForDeactivateAccount></hsConfig>");		;
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
	

	
	public void IterationLogic30DayDisconnect(Object input, ScriptingContext c)
			throws IOException {
		String step = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: STEP");
		Application.showMessage("STEP is ::" + step);
		c.setValue("STEP", step);

	}

	public void IterationLogicUpgradeDownGrade(Object input, ScriptingContext c)
			throws IOException {

		String sN = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: XML_ScenarioName");
		Application.showMessage("Scenario is ::" + sN);
		if (sN.equalsIgnoreCase("Demi")) {
			c.setValue("IsDemi", "true");

		} else {
			c.setValue("IsDemi", "false");
		}
	}
	
	//----------------
	public void readConsole()
	{
		Console console=System.console();
	
	}
	//------------

	public void IterationLogicResumeFlows(Object input, ScriptingContext c)
			throws IOException {
		String step = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: STEP");
		Application.showMessage("STEP is ::" + step);
		c.setValue("STEP", step);
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

			String sc = c.getValue("RTPNormalScenariosdS",
					"RTP_InputParams: SCENARIO_NUMBER");
			String tc = c.getValue("RTPNormalScenariosdS",
					"RTP_InputParams: TestCaseName");
			String sN = c.getValue("RTPNormalScenariosdS",
					"RTP_InputParams: XML_ScenarioName");
			Application
					.showMessage("_______________________________________________");
			Application.showMessage("Starting TestCase...." + tc);
			Application.showMessage("SCENARIO_NUMBER::" + sc);
			Application.showMessage("Scenario is ::" + sN);
			String Time = getTimeInstance();
			c.setValue("T1", Time);
			c.put("T1", Time);
			Application.showMessage("BaseTime is ::" + Time);
			Thread.sleep(2000);

			if (sc.equals("1")) {

				c.setValue("IsInstall", "true");
				c.setValue("IsCos", "false");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "false");
				c.setValue("IsCancel", "false");
				c.setValue("IsDisconnect", "false");
				// c.setValue("IscompletedInstall","false");
				// c.setValue("IsCossameserv","false");

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
				c.setValue("IsDisconnect", "false");
				// c.setValue("IscompletedInstall","false");
				// c.setValue("IsCossameserv","false");

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
				c.setValue("IsDisconnect", "false");
				// c.setValue("IscompletedInstall","false");
				// c.setValue("IsCossameserv","false");

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
				c.setValue("IsDisconnect", "false");
				// c.setValue("IscompletedInstall","false");
				// c.setValue("IsCossameserv","false");

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

			else if (sc.equals("3")) {
				c.setValue("IsInstall", "false");
				c.setValue("IsCos", "false");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "false");
				c.setValue("IsCancel", "false");
				c.setValue("IsDisconnect", "true");
				// c.setValue("IscompletedInstall","false");
				// c.setValue("IsCossameserv","false");

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
				c.setValue("IsDisconnect", "false");
				// c.setValue("IscompletedInstall","false");
				// c.setValue("IsCossameserv","false");

				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application
						.showMessage("Iteration set up is for CANCEL at scenario number ::"
								+ sc);
			} else if (sc.equals("8")) {
				// c.setValue("IscompletedInstall","true");
				c.setValue("IsInstall", "false");
				c.setValue("IsCos", "false");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "false");
				c.setValue("IsCancel", "false");
				c.setValue("IsDisconnect", "false");

				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application
						.showMessage("Iteration set up is for completed restart at scenario number ::"
								+ sc);
			}

			else if (sc.equals("21")) {
				// c.setValue("IsCossameserv","true");
				c.setValue("IsInstall", "false");
				c.setValue("IsCos", "false");
				c.setValue("IsSuspend", "false");
				c.setValue("IsRestore", "false");
				c.setValue("IsCancel", "false");
				c.setValue("IsDisconnect", "false");

				c.put("result", "true");
				if (sN.equalsIgnoreCase("Demi")) {
					c.setValue("IsDemi", "true");
				} else {
					c.setValue("IsDemi", "false");
				}
				Application
						.showMessage("Iteration set up is for completed restart at scenario number ::"
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
			throws ClassNotFoundException, IOException, InterruptedException {

		getBaseTime(input, c);
		Thread.sleep(1000);

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
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>ACTIVE</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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

	public void simulatorChange30DayDisconnect(String status, Object input,
			ScriptingContext c) throws ClassNotFoundException, IOException,
			SQLException, InterruptedException {

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
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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

	public void simulatorChange30DayDisconnectSimInfo(String status,
			Object input, ScriptingContext c) throws ClassNotFoundException,
			IOException, SQLException, InterruptedException {

		String SimInfostatus = c.getValue("RTPDataSourceCollections",
				"dB_Simulator: SimInfoStatus");
		Application.showMessage("SimInfostatus from input is ::"
				+ SimInfostatus);
		String iccid = c.getValue("RTPDataSourceCollections",
				"dB_Simulator: iccId");
		Application.showMessage("iccid  from input is ::" + iccid);

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
				+ "</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>1</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: accountId_Simulator")
				+ "</accountId><iccId>"
				+ iccid
				+ "</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"
				+ c.getValue("CcentralNum")
				+ "</centralStationAccountNumber><uControlGroupValue>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: uControlGroupValue")
				+ "</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"
				+ status
				+ "</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"
				+ c.getValue("RTPDataSourceCollections",
						"dB_Simulator: isFoxtrotFlowEnable")
				+ "</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><SIMInfoICCID>8901640100334742785</SIMInfoICCID><SIMInfoExResult>0</SIMInfoExResult><SIMInfoStatus>"
				+ SimInfostatus
				+ "</SIMInfoStatus><simInfoExStatus>GOODSTANDING,TESTMODE,AUTOACTIVATE,RMA,SUSPENDED,NWSUSPENDED,PENDING</simInfoExStatus></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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

	public String xpathValue(String xmlstr, String xpval) {
		// String content;
		InputSource source = new InputSource(new StringReader(xmlstr));
		XPath xPath = XPathFactory.newInstance().newXPath();
		String pathvalue = null;

		try {
			pathvalue = xPath.evaluate(xpval, source);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return (pathvalue);
	}

	public String RemoveNameSpaces(String sIpStr) {
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

		pP = Pattern.compile("ucontrolTyp:");
		mM = pP.matcher(sRet);
		sRet = mM.replaceAll("");

		pP = Pattern.compile("ucontrolsrv:");
		mM = pP.matcher(sRet);
		sRet = mM.replaceAll("");

		return sRet;
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

	public String getBaseTime(Object input, ScriptingContext c) {
		String Time;

		Date todayDate = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
		DateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		todayDateFormat.setTimeZone(timeZone);
		Time = todayDateFormat.format(todayDate);
		c.put("BaseTime", Time.trim());
		Application.showMessage("The Base Time retrieved is ::"
				+ c.get("BaseTime").toString());
		c.put("T1", Time);
		return Time;
	}

	public String getTimeInstance() {
		String Time;
		Date todayDate = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
		DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY HH:mm:ss");
		todayDateFormat.setTimeZone(timeZone);
		Time = todayDateFormat.format(todayDate);
		return Time;
	}

	public String getDurationInMinutes(String T1, String T2)
			throws ParseException {
		// String Duration;
		String Duration1;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-YY HH:mm:ss");
		Date d1 = null;
		Date d2 = null;
		d1 = format.parse(T1);
		d2 = format.parse(T2);
		long diff = d2.getTime() - d1.getTime();
		long mins = diff / (1000 * 60);
		long secs1 = diff % (1000 * 60);
		long sSecs = secs1 / 100;
		long mSecs = secs1 % 10;
		long secs = sSecs + mSecs;

		/*
		 * long diffSeconds = diff / 1000 % 60; long diffMinutes = diff / (60 *
		 * 1000) % 60; long diffHours = diff / (60 * 60 * 1000); Duration=
		 * diffHours+" "+diffMinutes+" "+diffSeconds;
		 */

		Duration1 = mins + "." + secs;
		return Duration1;
	}

	public void logWritter(String log, String FileName) throws IOException {
		File file = new File(FileName);
		// Application.showMessage("The data is ::"+FileData);

		if (!file.exists()) {
			file.createNewFile();

		}

		BufferedReader br = new BufferedReader(new FileReader(file));
		String Data = br.readLine();
		String Line;
		while ((Line = br.readLine()) != null) {

			Data = Data + "\r\n" + Line;
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(Data + "\r\n" + log);
		bw.close();
	}

	public void UpdateDurationText(String T1, String T2, String TC,
			String FileName) throws ParseException, IOException {
		String Duration = getDurationInMinutes(T1, T2);
		System.out.println("Duration is ::" + Duration);
		String Log = T2 + "," + TC + "," + Duration;
		logWritter(Log, FileName);
	}

	public static void AddLog(String Log, String space) {
		if (space.equalsIgnoreCase("Y")) {
			logger = logger + "\r\n" + Log + "\r\n";
		} else if (space.equalsIgnoreCase("N")) {
			logger = logger + Log + "\r\n";
		} else {
			logger = logger + Log + "\n";
		}
	}

	public void NoDataSetFound(Object input, ScriptingContext c,
			String Account, String Operation) throws InterruptedException,
			ClassNotFoundException, IOException, SQLException {

		ResultSet rs;
		int v1 = 0, i = 0;
		Thread.sleep(10000);
		String Time = (String) c.get("BaseTime");
		Application.showMessage("BaseTime is::" + Time);
		Statement st = dBConnect(input, c);
		rs = st.executeQuery("select * from (select * from cwmessagelog where user_data2 = '"
				+ Account
				+ "' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"
				+ Time + "' order by creation_time desc)where rownum <= 20");
		Application.showMessage("The dataSet is::" + rs.toString());

		if (rs.getRow() == 0) {
			Application.showMessage("No Record Sets Found..!");
		} else {
			while (rs.next()) {
				String operation = rs.getString("OPERATION");
				Application.showMessage("OPeration is ::" + operation);

				if (operation.equals(Operation)) {
					Application.showMessage("Extra call OrderUpdate Found");
					c.report("Order Update call found..!");
					v1 = 1;

				} else {
					Application
							.showMessage("Extra call OrderUpdate Found for operation ::"
									+ Operation);
				}

				if (v1 == 1) {
					c.report("Extra Call Found for Operation ::" + Operation);
					break;
				} else {
					Application.showMessage(i
							+ "th row data is not an Extra call ");
				}
				i++;
			}
			st.close();
		}
	}

	public String clsRemoveAsciiCharacter(String data) {

		
		
		  HashMap<String, String> replacements = new HashMap<String, String>();
		  // private static final long serialVersionUID = 1L;
		 
		  replacements.put("&lt;", "<");
		  replacements.put("&gt;", ">"); 
		  replacements.put("&amp;lt;", "<");
		  replacements.put("&amp;gt;", ">"); 
		  replacements.put("&amp;#34;", " ");
		  
		  
		   String input = data; String regexp =
		  "&lt;|&gt;|&amp;lt;|&amp;gt;|&amp;#34;";
		  
		  StringBuffer sb = new StringBuffer(); Pattern p =
		  Pattern.compile(regexp); Matcher m = p.matcher(input);
		  
		  while (m.find()) m.appendReplacement(sb,
		  replacements.get(m.group())); m.appendTail(sb); return sb.toString();
		 
	}

	public int getProductDetails(Object input, ScriptingContext c,
			String senddata, String tagName)
			throws ParserConfigurationException, SAXException, IOException {
		int count = 0;
		int nodeCheck = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(senddata));
		Document doc = builder.parse(is);
		Application.showMessage("Root element :"
				+ doc.getDocumentElement().getNodeName());
		if (doc.hasChildNodes()) {

			NodeList nodeList = doc.getElementsByTagName(tagName);
			Application.showMessage("nodeList :" + nodeList);
			for (int i = 0; i < nodeList.getLength(); i++) {

				Node elemNode = nodeList.item(i);

				if (elemNode.getNodeName() == tagName) {

					Application.showMessage("\nNode Name ="
							+ elemNode.getNodeName());

					Application.showMessage("Node Content ="
							+ elemNode.getTextContent());

					count++;
					Application.showMessage("group:::" + count);

					if (tagName.equalsIgnoreCase("group")) {
						if ((elemNode.getTextContent()
								.equalsIgnoreCase("XfinityEvents"))
								|| (elemNode.getTextContent()
										.equalsIgnoreCase("XfinityEvents-Insight"))) {
							nodeCheck = 1;
							Application
									.showMessage("Validation is successfull with XfinityEvents");

						} else {
							Application
									.showMessage("Create SendXML is not validated with  XfinityEvents/XfinityEvents-Insight present");
						}

					} else {
						Application
								.showMessage("GetAccount XHS Events Validation");
					}

				} else {
					Application.showMessage("No xhs Events to validate");
				}
			}
			Application.showMessage("countValue:::" + count);
		}

		if (tagName.equalsIgnoreCase("ucontrolsrv:group")) {
			if (nodeCheck == 1) {
				Application
						.showMessage("createAccount Send XML is validated with Xfinityevents/XfinityEvents-Insight");
			} else {
				c.report("createAccount SendXML  not validated with Xfinityevents/XfinityEvents-Insight");
			}
		}

		return count;
	}

	public void CommonDBValidate(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, SQLException {
		LibraryRtp lC = new LibraryRtp();
		ResultSet rs1;
		Date todayDate = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
		DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
		todayDateFormat.setTimeZone(timeZone);
		String strTodayDate = todayDateFormat.format(todayDate);
		Application.showMessage("Todays Date as per EST is::" + strTodayDate);

		try {

			Statement st1 = lC.dBConnectCommon(input, c);

			// Application.showMessage("Account Number is "+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
			String Acc = c.getValue("RTPNormalScenariosdS",
					"RTP_InputParams: XML_AccNumber");
			rs1 = st1
					.executeQuery("select * from(select * from cwpworklist where ACCOUNTNUMBER ='"
							+ Acc
							+ "' AND creation_date > '"
							+ strTodayDate
							+ "')where rownum < 2");
			if (rs1 == null) {
				Application
						.showMessage("Null Record set found in WorKlist DB for AccountNumber'"
								+ c.getValue("RTPNormalScenariosdS",
										"RTP_InputParams: XML_AccNumber"));
			}

			while (rs1.next()) {
				String errorCode = rs1.getString("ERRORCODE");
				String errorSource = rs1.getString("ERRORSOURCE");
				Application.showMessage("Error Source from common is"
						+ errorSource);
				String errorText = rs1.getString("ERRORTEXT");
				String PARTICIPANTOPERATION = rs1
						.getString("PARTICIPANTOPERATION");
				String BILLINGSYSTEM = rs1.getString("BILLINGSYSTEM");
				String SERVICETYPE = rs1.getString("SERVICETYPE");
				String ORDER_ID = rs1.getString("ORDER_ID");
				Application.showMessage("Error Code is ::" + errorCode);
				Application.showMessage("errorSource is ::" + errorSource);
				Application.showMessage("PARTICIPANTOPERATION is ::"
						+ PARTICIPANTOPERATION);
				Application.showMessage("errorText is ::" + errorText);
				Application.showMessage("BILLINGSYSTEM is ::" + BILLINGSYSTEM);
				Application.showMessage("SERVICETYPE is ::" + SERVICETYPE);
				Application.showMessage("ORDER_ID is ::" + ORDER_ID);

				Application
						.showMessage("****** Validating Common Data Base worklisting********");
				if (errorText.equals(c.get("ErrorTextOu"))) {
					Application
							.showMessage("Error text from order update is validated successfully with error text from common DB");
				} else {
					Application.showMessage("ERROR TEXT IS NOT VALIDATED");
				}

				if (errorCode.equals(c.get("ErrorCodeOu"))) {
					Application
							.showMessage("Error code from order update is validated successfully with error code from common DB");
				} else {
					Application.showMessage("ERROR CODE IS NOT VALIDATED");
				}

				if (errorSource.equals(c.get("ErrorSourceOu"))) {
					Application
							.showMessage("Error SOURCE from order update is validated successfully with error Source from common DB");
				} else {
					Application.showMessage("ERROR SOURCE IS NOT VALIDATED");
				}

			}

			st1.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

	}

}
