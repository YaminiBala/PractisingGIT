
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import java.util.TimeZone;

import javax.sql.StatementEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.omg.CORBA.portable.ApplicationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;




public class LibraryRTP_New 
{
	public String basemsgID = null;
    public Connection connection = null;
	public String xml1;
	public String xml2;
	//public String rowmsg= null;
	public String resultval;
	
	
	
	
	public void Opconnection()
	{
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		Application.showMessage("-------- Oracle JDBC Connection Testing ------");
		
		 
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("jdbc:oracle:thin.OracleDriver");	
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("Connection Failed as No JDBC Driver Intialized");
			e.printStackTrace();
			return;
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
		//Connection connection = null;
		
	}
	 
	public void think_time(Object input, ScriptingContext c) throws InterruptedException
	{
		Thread.sleep(2000);
	}
	
	public String getAttributeName(Object input,ScriptingContext c, String xmldata,String tagname,String attributename) throws ParserConfigurationException, SAXException, IOException
	   {
		   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	       DocumentBuilder builder = factory.newDocumentBuilder();
	       InputSource is = new InputSource(new StringReader(xmldata));
	       Document doc = builder.parse(is);
	       String attribute = null;
	       

	       NodeList nodeList = doc.getElementsByTagName(tagname);
	        
	       for (int i = 0; i < nodeList.getLength(); i++) {                
	           Node node = (Node) nodeList.item(i);
	           
	           if (node.hasAttributes()) {
	          	
	               Attr attr = (Attr) node.getAttributes().getNamedItem(attributename);
	              
	               if (attr != null) {
	              	 
	                   attribute= attr.getValue(); 
	                  // String check=attr.getNodeValue();
	                  // String check1=attr.getNodeName();
	                   Application.showMessage("Reason:: "+attribute); 
	                  // Application.showMessage("attribute: "+check); 
	                  // Application.showMessage("attribute: "+check1); 
	               }
	           }
	       }
		return attribute;

	   }
	
	public String getBaseTime(Object input, ScriptingContext c)
    {
                       String Time;
                       Date todayDate = new Date();
                                   TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
                                   DateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                                   todayDateFormat.setTimeZone(timeZone);
                                   Time=todayDateFormat.format(todayDate);
                                   c.put("BaseTime",Time.trim());
                                   Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
                                   return Time;
    }
	
	public static String getTimeInstance(Object input, ScriptingContext c)
    {
              String Time;
              Date todayDate = new Date();
                 TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
                 DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY HH:mm:ss");
                 todayDateFormat.setTimeZone(timeZone);
                 Time=todayDateFormat.format(todayDate);
                 c.put("T1",Time);
                Application.showMessage(c.get("T1").toString());
                 return Time;
    }
	
	public Boolean validationPoint(String ValidateData1, String ValidateData2, Object input , ScriptingContext c)
	{
		Boolean result;
		if(ValidateData1.equals(ValidateData2))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData1+"and"+ValidateData2);
			
			result=true;
		}
		else
		{
			c.report("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+"and"+ValidateData2);
	
			result=false;
		}
		
		return result;
		
	}
	
	
	public Boolean validationPointIgnoreCase(String ValidateData1, String ValidateData2, Object input , ScriptingContext c)
	{
		Boolean result;
		if(ValidateData1.equalsIgnoreCase(ValidateData2))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData1+"and"+ValidateData2);
			
			result=true;
		}
		else
		{
			c.report("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+"and"+ValidateData2);
	
			result=false;
		}
		
		return result;
		
	}
	
	
	public Boolean ORvalidationPoint(String ValidateData1, String ValidateData2,String ValidateData3, Object input , ScriptingContext c)
	{
		Boolean result;
		if(ValidateData1.equals(ValidateData2))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData1+"and"+ValidateData2);
			
			result=true;
		}
		else if(ValidateData1.equals(ValidateData3))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData2+"and"+ValidateData3);
			
			result=true;
		}
		else
		{
			c.report("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+"and"+ValidateData2);
	
			result=false;
		}
		
		return result;
		
	}
	
	
	public Boolean ANDvalidationPoint(String ValidateData1, String ValidateData2,String ValidateData3, Object input , ScriptingContext c)
	{
		Boolean result;
		if(ValidateData1.equals(ValidateData2) & (ValidateData1.equals(ValidateData3)))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData1+"and"+ValidateData2 +"and"+ValidateData3);
			
			result=true;
		}
		
		else
		{
			c.report("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+"and"+ValidateData2);
	
			result=false;
		}
		
		return result;
		
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
		Application.showMessage("The Values are::"+host+":"+port+":"+sid+":" +username+password);
		
		connection = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port+":"+sid +"", username,password);
	    
		Statement st = connection.createStatement();
		return st;
	}
	
	public void IterationLogic30DayDisconnect(Object input, ScriptingContext c) throws IOException
	{

		String sN=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName");
		Application.showMessage("Scenario is ::"+sN);
		if(sN.equalsIgnoreCase("Demi"))
    	{
    		c.setValue("IsDemi", "true");
    		
    	}
    	else
    	{
    		c.setValue("IsDemi", "false");	
    	}
		String step=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: STEP");
		Application.showMessage("STEP is ::"+step);
		c.setValue("STEP", step);
		
	}
	
	public void IterationLogicUpgradeDownGrade(Object input, ScriptingContext c) throws IOException
	{
		String sN=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName");
		Application.showMessage("Scenario is ::"+sN);
		if(sN.equalsIgnoreCase("Demi"))
    	{
    		c.setValue("IsDemi", "true");
    		
    	}
    	else
    	{
    		c.setValue("IsDemi", "false");	
    	}
	}
	
	public void IterationLogic(Object input, ScriptingContext c)
	{
	  try
	  {
           
		   
	        String sc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER");
	        String tc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseName");
	        String sN=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_ScenarioName");
	        Application.showMessage("_______________________________________________");
	        Application.showMessage("Starting TestCase...."+tc);
	        Application.showMessage("SCENARIO_NUMBER::"+sc);
	        Application.showMessage("Scenario is ::"+sN);
	        
	 
	        if( sc.equals("1"))
	        {
	        	
	        	c.setValue("IsInstall","true");
	        	c.setValue("IsCos","false");
	        	c.setValue("IsSuspend","false");
	        	c.setValue("IsRestore","false");
	        	c.setValue("IsCancel","false");
	        	c.put("result", "true");
	        	if(sN.equalsIgnoreCase("Demi"))
	        	{
	        		c.setValue("IsDemi", "true");
	        	}
	        	else
	        	{
	        		c.setValue("IsDemi", "false");	
	        	}
	        	Application.showMessage("Itearion flag values"+c.getValue("IsInstall")+c.getValue("IsCos")+c.getValue("IsSuspend")+c.getValue("IsRestore")+c.getValue("IsCancel"));
	        	Application.showMessage("Iteration set up is for Install at scenario number ::"+sc);
	        }
	        else if( sc.equals("2"))
	        {
	        	c.setValue("IsInstall","false");
	        	c.setValue("IsCos","true");
	        	c.setValue("IsSuspend","false");
	        	c.setValue("IsRestore","false");
	        	c.setValue("IsCancel","false");
	        	c.put("result", "true");
	        	if(sN.equalsIgnoreCase("Demi"))
	        	{
	        		c.setValue("IsDemi", "true");
	        	}
	        	else
	        	{
	        		c.setValue("IsDemi", "false");	
	        	}
	        	Application.showMessage("Itearion flag values"+c.getValue("IsInstall")+c.getValue("IsCos")+c.getValue("IsSuspend")+c.getValue("IsRestore")+c.getValue("IsCancel"));

	        	Application.showMessage("Iteration set up is for COS at scenario number ::"+sc);
	        }
	        
	        else if( sc.equals("4"))
	        {
	        	c.setValue("IsInstall","false");
	        	c.setValue("IsCos","false");
	        	c.setValue("IsSuspend","true");
	        	c.setValue("IsRestore","false");
	        	c.setValue("IsCancel","false");
	        	c.put("result", "true");
	        	if(sN.equalsIgnoreCase("Demi"))
	        	{
	        		c.setValue("IsDemi", "true");
	        	}
	        	else
	        	{
	        		c.setValue("IsDemi", "false");	
	        	}
	        	Application.showMessage("Iteration set up is for SUSPEND at scenario number ::"+sc);
	        }
	        
	        else if( sc.equals("5"))
	        {
	        	c.setValue("IsInstall","false");
	        	c.setValue("IsCos","false");
	        	c.setValue("IsSuspend","false");
	        	c.setValue("IsRestore","true");
	        	c.setValue("IsCancel","false");
	        	c.put("result", "true");
	        	if(sN.equalsIgnoreCase("Demi"))
	        	{
	        		c.setValue("IsDemi", "true");
	        	}
	        	else
	        	{
	        		c.setValue("IsDemi", "false");	
	        	}
	        	Application.showMessage("Iteration set up is for RESORE at scenario number ::"+sc);
	        }
	        
	        else if( sc.equals("11"))
	        {
	        	c.setValue("IsInstall","false");
	        	c.setValue("IsCos","false");
	        	c.setValue("IsSuspend","false");
	        	c.setValue("IsRestore","false");
	        	c.setValue("IsCancel","true");
	        	c.put("result", "true");
	        	if(sN.equalsIgnoreCase("Demi"))
	        	{
	        		c.setValue("IsDemi", "true");
	        	}
	        	else
	        	{
	        		c.setValue("IsDemi", "false");	
	        	}
	        	Application.showMessage("Iteration set up is for CANCEL at scenario number ::"+sc);
	        }
	        
	        
	       
	      
	   }
	   catch(Exception e)
	   {
	
	   }
	      
	 
	  }
	 
	public void reportingToExcel(Object input, ScriptingContext c)
	{
		
		
		if(c.get("result").equals("true"))
		{		
			//c.setValue("RESULT","PASSED");
			Application.showMessage("TestCase is a PASS");
		}
		else
		{
			//c.setValue("RESULT","FAILED");	
			Application.showMessage("Test Case is a Failure");
			
		}
	}
	public void SaveValuesfromInstall(Object input, ScriptingContext c)
	{
	  try
	  {		   
	        c.put("I_AccInput", c.getValue("ACC_input"));
	        c.put("I_CcentralNum", c.getValue("CcentralNum"));
	        c.put("I_PostalCode", c.getValue("PostalCode"));
	       // c.put("I_AccInput", c.getValue("ACC_input"));	      
	   }
	   catch(Exception e)
	   {
	
	   }
	      
	 
	  }
	
	public void LoadValuestoGlobalList(Object input, ScriptingContext c)
	{
	  try
	  {		   
	       c.setValue("ACC_input",  (String) c.get("I_AccInput"));
	       c.setValue("CcentralNum",  (String) c.get("I_CcentralNum"));
	       c.setValue("PostalCode",  (String) c.get("I_PostalCode"));      
	   }
	   catch(Exception e)
	   {
	
	   }
	      
	 
	  }
	
	
	
	public void baseMsgid_retrieval(Object input, ScriptingContext c) throws ClassNotFoundException, IOException
	{	
		
		
		try
		{
			
            Statement st = dBConnect(input, c);
            ResultSet rs = st.executeQuery("select msgid from (select msgid from cwmessagelog order by creation_time desc) where rownum < 2");
	        while (rs.next()) 
	        {
	        basemsgID = rs.getString(1);
	        c.setValue("BaseMsgid", basemsgID);
	        Application.showMessage("Base Message ID is ::"+c.getValue("BaseMsgid"));
	        
	        }
	        if(basemsgID ==null)
	        {
	          System.out.println("No MsgId Found");	
	        }
	        else
	        {
	         System.out.printf("MsgId Found is %s\n",basemsgID);		
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
	
	public void releaseMemory(Object input, ScriptingContext c) throws ClassNotFoundException, IOException
	{
		 c.setValue("ACC_input", "null");
		 c.setValue("BaseMsgid", "null");
		 c.setValue("CcentralNum", "null");
		 c.setValue("HouseNumber", "null");
		 c.setValue("FirstName", "null");
		 c.setValue("LastName", "null");
		 c.setValue("Address", "null");
	}
	
	public void simulatorChange(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		
		

		Statement st = dBConnect(input, c);
        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs.next()) 
        {
          String beforeSim = rs.getString(2);
       
          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
        
        }
        st.close();
        Statement st0 = dBConnect(input, c);
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: useUcontrolSimulator")+"</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: uControlGroupValue")+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>ACTIVE</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: isFoxtrotFlowEnable")+"</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
        st0.executeUpdate(sql);
       
    
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
	
	public void simulatorChange30DayDisconnect(String status,Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		
		
		//String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		//Application.showMessage("Status needs to be updated is ::"+status);
		Statement st = dBConnect(input, c);
        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs.next()) 
        {
          String beforeSim = rs.getString(2);
       
          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
        
        }
        st.close();
        Statement st0 = dBConnect(input, c);
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: useUcontrolSimulator")+"</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: uControlGroupValue")+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"+status+"</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: isFoxtrotFlowEnable")+"</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><suspended>1</suspended><delayBetweenSuspendDeactivate>3000</delayBetweenSuspendDeactivate><changeReason>Deceased</changeReason></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
        st0.executeUpdate(sql);
       
    
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
	
		
	public int updateLongSql(Statement oSt, String sTableName, String sFieldName, String sWhereClause, String sNewVal) throws SQLException {
		int i = 0, j = 0, iRet = 0;
		String sQuery="";
		
		i = sNewVal.length();
Application.showMessage("Length is: " + i);		
		if(i>1000) i = 1000;

		
		sQuery = "update " + sTableName + " set " +  sFieldName + " = '" + sNewVal.substring(0, i) + "' " + sWhereClause;
Application.showMessage(sQuery);		
		iRet = oSt.executeUpdate(sQuery);
		
		if(iRet>0) {
			while(i<=sNewVal.length()) {
				j = sNewVal.length() - i;
				if(j>0) {
					if(j>1000) j=1000;
					sQuery = "update " + sTableName + " set " + sFieldName + " = " + sFieldName + " || '" + sNewVal.substring(i, i+j) + "' " + sWhereClause;
Application.showMessage(sQuery);					
					oSt.executeUpdate(sQuery);
				}
				i = i + 1000;
			}
		}
		return iRet;
	}
	
	
	
	
	public String extractXml(ResultSet rs1,String data) throws SQLException 
	{	
	
		Blob blob = rs1.getBlob(data);
        byte[] bdata = blob.getBytes(1, (int) blob.length());
        xml1 = new String(bdata);      
		return(xml1);	
	}
	
	public String GetValueByXPath(String sXml, String sxpath) throws SAXException, IOException, XPathExpressionException,NullPointerException
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
	            if( oNode==null)
	            {
	               val="XPath ERROR.Either NULL or Xpath needs to be Fixed"; 
	            }
	            else
	            {
	               val =oNode.getTextContent(); 
	 
	            }
	          
	            xpath=null; expr=null; oNode=null; doc=null; builder=null;
	            
	        }
	       catch (NullPointerException | ParserConfigurationException | SAXException | IOException e) 
	        {           
	               e.printStackTrace();       
	        }
	       
	              return val;
	              
	 }










	public String nodeFromKey(String xmlStr,String str1,String str2) throws NullPointerException
	       {
	           if(xmlStr.contains(str1) && xmlStr.contains(str2))
	           {
	                 int startPosition=0;
	                 int endPosition=0;
	                 startPosition = xmlStr.indexOf(str1) + str1.length();
	              
	                 if(startPosition==-1)
	                 {
	                        System.out.printf("No Value found for ::%s\n",str1);
	               return(null);         
	                 }          
	                 else if(xmlStr.indexOf(str1)==-1)
	                 {
	                        System.out.printf("No Value found for ::%s\n",str1);
	          }
	              
	                 endPosition = xmlStr.indexOf(str2, startPosition); 
	              if(endPosition==-1)
	                 {
	                     System.out.printf("No Value found for ::%s\n",str2);        
	                 }          
	                 String resultval = xmlStr.substring(startPosition, endPosition); 
	                 if(resultval==null)
	                 {
	                     return "NO Data Fetched from in-between strings..!Check the strings..!";
	                 }
	                 else
	                 return (resultval);
	            }
	           else
	           {
	              return "NO Data Fetched from in-between strings..!Check the strings..!"; 
	           }
	}


	
	
	
	
	public  Map<String,String> splitter(String input)
	{
		
		//String input ="350 + ThermostatDataProvider";
		//String input ="350";
		Map<String,String> retMap=new HashMap <String,String>();
		String service=" ";
		String group =" ";
	    boolean i =input.contains("+");
	    String Singlegroup="false";
	    String Multigroup="false";
	    int y=0;
	    if (i==true)
		{
	    	Multigroup="true";
	    	
	    	
	    	
	
	    	for( y=0;input.charAt(y)!='+';y++)
	    	{
	    		String temp=String.valueOf(input.charAt(y));
	    		group= group.concat(temp).trim();
	    		
	    		
	    	}
			for(y=y+2;y<input.length();y++)
			{
				String temp=String.valueOf(input.charAt(y));
				service=service.concat(temp).trim();
			}
			//Application.showMessage("TierGroup is ::"+group);
			//Application.showMessage("TierService is ::"+service);
			retMap.put("group",group.trim());
			retMap.put("service",service.trim());
			retMap.put("IsMulti",Multigroup.trim());
			retMap.put("IsSingle",Singlegroup.trim());
			
		
		}
	    else
	    {
	    	
	    	Singlegroup="true";
	    	retMap.put("group",input.trim());
	    	retMap.put("service"," ");
	    	retMap.put("IsMulti",Multigroup.trim());
			retMap.put("IsSingle",Singlegroup.trim());
	    	
	    	Application.showMessage("Only Tier Group is there");
	    }
		return retMap;
		
		
		
	}
	
	
    
    public static String getDurationInMinutes(String T1,String T2) throws ParseException
    {
            String Duration;
            String Duration1;
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-YY HH:mm:ss");
            Date d1 = null;
            Date d2 = null;
            d1=format.parse(T1);
            d2=format.parse(T2);
            long diff = d2.getTime() - d1.getTime();
            long mins=diff/(1000*60);
            long secs1=diff%(1000*60);
            long sSecs=secs1/100;
            long mSecs=secs1%10;
            long secs=sSecs+mSecs;
            
           /* long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            Duration= diffHours+" "+diffMinutes+" "+diffSeconds;*/
            
            Duration1=mins+"."+secs;
           return Duration1;
    }
    
    public static void logWritter(String log, String FileName) throws IOException
        {
               File file = new File(FileName);
         //Application.showMessage("The data is ::"+FileData);
         
         if (!file.exists()) 
         {
             file.createNewFile();
                                        
         }
             
             BufferedReader br=new BufferedReader(new FileReader(file));
             String Data=br.readLine();
             String Line;
             while ((Line = br.readLine()) != null)   
             {
               
               Data=Data+"\r\n"+Line;
             }
             
             
             FileWriter fw = new FileWriter(file.getAbsoluteFile());
             BufferedWriter bw = new BufferedWriter(fw);
             bw.write(Data+"\r\n"+ log);
             bw.close();
                                                               

        }
    public static void UpdateDurationText(String T1,String T2,String TC, String FileName) throws ParseException, IOException
    {
            String Duration=getDurationInMinutes(T1, T2);
               System.out.println("Duration is ::"+Duration);
               String Log= T2+","+TC+","+Duration;
               logWritter(Log, "C:/Duration/Duration.txt"); 
    }

	
	public Statement mysqldbConnect(String Ccnum,String accountNumber) throws ClassNotFoundException, SQLException, IOException, InstantiationException, IllegalAccessException
		{
		
		 Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		   
		   final String DB_URL = "jdbc:mysql://10.255.159.180:3307/my_sql";

		   //  Database credentials
		   final String USER = "mithun";
		   final String PASS = "Tester123";   
		   String sql;
		   System.out.println("Connecting to Database");
		      connection = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      Statement st = connection.createStatement();
		     
                
                 
		     
                  System.out.println("Records to be Updated::");
		      sql = "update my_sql.userentrynew set cCentralID='"+Ccnum+"'where AccountNumber="+accountNumber;
		     int i= st.executeUpdate(sql);
                System.out.println("Records Updated::" + i);
	      
		      st.close();
		      connection.close();
		   	
			return st;
		}
	
	public void Opconnectionmysql()
	{
		System.out.println("-------- MYSQL JDBC Connection Testing ------");
		Application.showMessage("-------- MYSQL JDBC Connection Testing ------");
		
		 
		try 
		{
			Application.showMessage("Registering");
			Class.forName("com.mysql.jdbc.Driver");
			Application.showMessage("REgistered");
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("jdbc:oracle:thin.OracleDriver");	
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("Connection Failed as No JDBC Driver Intialized");
			e.printStackTrace();
			return;
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
		//Connection connection = null;
		
	}


	public Statement mysqldbConnectwithProduct(String Ccnum,String accountNumber,String group,String service) throws ClassNotFoundException, SQLException, IOException, InstantiationException, IllegalAccessException
		{
		
		 Class.forName("com.mysql.jdbc.Driver").newInstance();
		
	
		   final String DB_URL = "jdbc:mysql://10.255.159.180:3307/my_sql";

		   //  Database credentials
		   final String USER = "mithun";
		   final String PASS = "Tester12";   
		   String sql;
		     
		   System.out.println("Connecting to Database");
		      connection = DriverManager.getConnection(DB_URL,USER,PASS);
		      
		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      Statement st = connection.createStatement();
		     
                
                 
		     
                  System.out.println("Records to be Updated::");
		      sql = "update my_sql.userentrynew set cCentralID='"+Ccnum+"'where AccountNumber="+accountNumber+"'and Group1='"+group+"'and Group2='"+service;
		     int i= st.executeUpdate(sql);
                System.out.println("Records Updated::" + i);
	      
		      st.close();
		      connection.close();
		   	
			return st;
		}
	
	}
	



 
	
	
	

	